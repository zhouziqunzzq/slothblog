package controller;

import java.io.*;
import java.util.List;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

import model.User;
import util.GlobalConfigHelper;

public class IndexController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() {
        properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext());
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
