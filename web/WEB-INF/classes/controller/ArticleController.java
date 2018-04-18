package controller;

import model.Article;
import model.Tag;
import model.User;
import util.GlobalConfigHelper;
import util.URLHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ArticleController extends HttpServlet {
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
        String articleId = URLHelper.getRouterParam(request.getSession().getAttribute("lastURI").toString(), 4);
        int targetUid = Integer.parseInt(URLHelper.getRouterParam(
                request.getSession().getAttribute("lastURI").toString(), 2));
        Article article = new Article(properties).getArticleById(Integer.parseInt(articleId));
        request.setAttribute("article", article);
        request.getSession().setAttribute("targetUid", targetUid);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                properties.getProperty("TemplatePathRoot") + "user/article.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String lastURI = request.getSession().getAttribute("lastURI").toString();
        String subrouter = URLHelper.getRouterParam(lastURI, 5);
        String action = request.getParameter("action");
        switch (subrouter) {
            case "":
                if(action.equals("new"))
                    doArticlePost(request, response);
                else if(action.equals("delete"))
                    doArticleDelete(request, response);
                break;
            case "comment":
                int targetArticleId = Integer.parseInt(
                        URLHelper.getRouterParam(lastURI, 4));
                int articleUserId = Integer.parseInt(
                        URLHelper.getRouterParam(lastURI, 2));
                request.getSession().setAttribute("targetArticleId", targetArticleId);
                request.getSession().setAttribute("articleUserId", articleUserId);
                request.getRequestDispatcher("/comment").forward(request, response);
                break;
        }

    }

    private void doArticleDelete(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String lastURI = request.getSession().getAttribute("lastURI").toString();
        int article_id = Integer.parseInt(URLHelper.getRouterParam(lastURI, 4));
        Article article = new Article(properties).getArticleById(article_id);
        // Check user auth
        if (article.getUser_id() != (int)request.getSession().getAttribute("uid")) {
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
        article.setProperties(properties);
        boolean flag = article.delete(article_id);

        if(!flag) {
            request.setAttribute("error", "文章删除失败！");
            response.sendRedirect(String.format("/user/%d/article/%d",
                    (int) request.getSession().getAttribute("uid"), article_id));
        } else {
            request.setAttribute("msg", "文章删除成功！");
            response.sendRedirect(String.format("/user/%d",
                    (int) request.getSession().getAttribute("uid")));
        }
    }

    private void doArticlePost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tags = request.getParameter("tags");

        List<String> tagList = new ArrayList<String>(Arrays.asList(tags.split(" ")));
        List<Integer> tagIdList = new ArrayList<>();
        Tag tag = new Tag(properties);
        for (String aTagList : tagList) {
            tag.setName(aTagList);
            int tagId = tag.insert();
            tagIdList.add(tagId);
        }

        if (title.isEmpty() || content.isEmpty()) {
            request.setAttribute("error", "标题或内容不能为空！");
            response.sendRedirect(request.getHeader("referer"));
        } else {
            Article article = new Article(properties);
            article.setUser_id((int) request.getSession().getAttribute("uid"));
            article.setTitle(title);
            article.setContent(content);
            int newId = article.insert();
            if (newId == -1) {
                request.setAttribute("error", "发送失败！");
                response.sendRedirect(request.getHeader("referer"));
            } else {
                boolean flag = article.link(tagIdList);
                if (!flag) {
                    request.setAttribute("error", "Link失败！");
                    response.sendRedirect(request.getHeader("referer"));
                }
                response.sendRedirect(String.format("/user/%d/article/%d",
                        (int) request.getSession().getAttribute("uid"), newId));
            }
        }
    }
}
