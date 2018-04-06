package controller;

import model.User;
import util.GlobalConfigHelper;
import util.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

public class AuthController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() {
        properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext());
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        switch (request.getRequestURI()) {
            case "/auth/login":
                login(request, response);
                break;
            case "/auth/logout":
                logout(request, response);
                break;
        }
    }

    private void login(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
    /*    PrintWriter out = response.getWriter();
        out.write(request.getParameter("username"));
        out.write("\n");
        out.write(request.getParameter("password"));
        out.flush();
        out.close();
    */
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        User u = new User(properties);
        User user = u.getUserByUsername(username);

        if(user == null) {
            request.setAttribute("error", "用户名不存在！");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        } else {
            if(BCrypt.checkpw(passwd, user.getPassword())) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                        properties.getProperty("TemplatePathRoot") + "index.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "密码错误！");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                        properties.getProperty("TemplatePathRoot") + "index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(request.getRequestURI());
        out.write("\nlogout");
        out.flush();
        out.close();
    }
}
