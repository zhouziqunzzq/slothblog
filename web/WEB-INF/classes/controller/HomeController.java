package controller;

import model.Article;
import util.GlobalConfigHelper;
import util.URLHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class HomeController extends HttpServlet {
    private Properties properties = null;
    public static int perPageArticles = 10;

    @Override
    public void init() {
        properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String subrouter = URLHelper.getRouterParam(request.getRequestURI(), 3);
        if (subrouter.equals("")) {
            // Show User Home Index
            int uid = Integer.parseInt(URLHelper.getRouterParam(request.getRequestURI(), 2));
            Article article = new Article(properties);
            List<Article> articles = article.getArticlesByUserId(uid, 1, perPageArticles);
            request.setAttribute("articles", articles);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "/user/home.jsp");
            dispatcher.forward(request, response);
        } else {
            switch (subrouter) {
                case "article":
                    request.getSession().setAttribute("lastURI", request.getRequestURI());
                    request.getRequestDispatcher("/article").forward(request, response);
                    break;
                case "profile":
                    try {
                        request.setAttribute("targetUid", Integer.parseInt(
                                URLHelper.getRouterParam(request.getRequestURI(), 2)));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid uid " +
                                URLHelper.getRouterParam(request.getRequestURI(), 2));
                        e.printStackTrace();
                        request.setAttribute("targetUid", null);
                    }
                    request.getRequestDispatcher("/profile").forward(request, response);
                    break;
            }
        }

    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        int uid = Integer.parseInt(URLHelper.getRouterParam(request.getRequestURI(), 2));
        String subrouter = URLHelper.getRouterParam(request.getRequestURI(), 3);
        if (subrouter.equals(""))
            response.sendRedirect("/user/" + uid);
        else {
            switch (subrouter) {
                case "article":
                    request.getRequestDispatcher("/article").forward(request, response);
                    break;
                case "profile":
                    request.getRequestDispatcher("/profile").forward(request, response);
                    break;
            }
        }
    }
}
