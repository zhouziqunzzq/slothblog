package controller;

import model.Article;
import model.Tag;
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
        System.out.println(request.getSession().getAttribute("lastURI"));
        Article article = new Article(properties).getArticleById(Integer.parseInt(articleId));
        request.setAttribute("article", article);
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
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tags = request.getParameter("tags");

        List<String> tagList = new ArrayList<String>(Arrays.asList(tags.split(" ")));
        List<Integer> tagIdList = new ArrayList<Integer>();
        Tag tag = new Tag(properties);
        for(int i = 0; i < tagList.size(); i++) {
            tag.setName(tagList.get(i));
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
                if(!flag) {
                    request.setAttribute("error", "Link失败！");
                    response.sendRedirect(request.getHeader("referer"));
                }
                response.sendRedirect(String.format("/user/%d/article/%d",
                        (int)request.getSession().getAttribute("uid"), newId));
            }
        }
    }
}
