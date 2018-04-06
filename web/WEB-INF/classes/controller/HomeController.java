package controller;

import util.GlobalConfigHelper;
import util.URLHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class HomeController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() {
        properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        int uid = Integer.parseInt(URLHelper.getRouterParam(request.getRequestURI(), 2));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                properties.getProperty("TemplatePathRoot") + "user/home.jsp");
        dispatcher.forward(request, response);
    }
}
