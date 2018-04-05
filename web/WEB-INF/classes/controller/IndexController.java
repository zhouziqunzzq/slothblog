package controller;

import java.io.*;
import java.util.List;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

import model.User;

public class IndexController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() {
        ServletContext servletContext = this.getServletContext();
        if (servletContext.getAttribute("GlobalConfig") == null) {
            try {
                properties = new Properties();
                properties.load(getServletContext().getResourceAsStream("/WEB-INF/config.properties"));
                servletContext.setAttribute("GlobalConfig", properties);
            } catch (IOException e) {
                System.out.println("Failed to load config.properties");
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            properties = (Properties) servletContext.getAttribute("GlobalConfig");
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            User user = new User(properties);
            List<User> users = user.GetUsers();
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            request.setAttribute("title", properties.getProperty("WebsiteName"));
            request.setAttribute("users", users);
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            System.out.println("Failed to dispatching jsp");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            System.out.println("Failed to open jsp");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
