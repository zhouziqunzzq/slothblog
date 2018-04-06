package controller;

import util.GlobalConfigHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    ) throws IOException {
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
    ) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(request.getParameter("username"));
        out.write("\n");
        out.write(request.getParameter("password"));
        out.flush();
        out.close();
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
