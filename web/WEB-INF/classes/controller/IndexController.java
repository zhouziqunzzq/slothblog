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
    ) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        try {
            Article article = new Article(properties);
            List<Article> articles;
            if (request.getParameter("keyword") == null) {
                // Get latest articles
                articles = article.getLatestArticles(1, perPageArticles);
                request.getSession().removeAttribute("keyword");
            } else {
                // Search articles
                articles = article.searchArticles(request.getParameter("keyword"),
                        1, perPageArticles);
                request.getSession().setAttribute("keyword", request.getParameter("keyword"));
            }
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
