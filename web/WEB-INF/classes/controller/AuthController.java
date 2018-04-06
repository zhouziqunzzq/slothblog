package controller;

import model.User;
import util.GlobalConfigHelper;
import util.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
            case "/auth/register":
                register(request, response);
                break;
        }
    }

    private void login(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        User u = new User(properties);
        User user = u.getUserByUsername(username);

        if (user == null) {
            request.setAttribute("error", "用户名不存在！");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        } else {
            if (BCrypt.checkpw(passwd, user.getPassword())) {
                HttpSession session = request.getSession(true);
                session.setAttribute("uid", user.getId());
                response.sendRedirect("/user/" + user.getId());
            } else {
                request.setAttribute("error", "密码错误！");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                        properties.getProperty("TemplatePathRoot") + "index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private void register(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        // TODO: Register Backend
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        User u = new User(properties, -1, username, passwd);
        if(u.insert() < 0) {
            request.setAttribute("error", "注册失败！");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("msg", "注册成功！请登录");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
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
