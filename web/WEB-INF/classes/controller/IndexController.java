package controller;

import java.io.*;
import java.util.List;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

import model.Article;
import util.GlobalConfigHelper;

public class IndexController extends HttpServlet {
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
    ) {
        try {
            // Get latest articles
            Article article = new Article(properties);
            List<Article> articles = article.getLatestArticles(1, perPageArticles);
            request.setAttribute("articles", articles);
            // Render index View
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
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
