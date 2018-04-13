package controller;

import model.Comment;
import util.GlobalConfigHelper;
import util.URLHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class CommentController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() { properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext()); }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String content = request.getParameter("content");
        int user_id = (int) request.getSession().getAttribute("uid");
        int article_id = Integer.parseInt(URLHelper.getRouterParam(request.getRequestURI(), 4));
        int articleUserId = Integer.parseInt(URLHelper.getRouterParam(request.getRequestURI(), 2));
        Comment comment = new Comment(properties);
        comment.setContent(content);
        comment.setArticle_id(article_id);
        comment.setUser_id(user_id);
        int commentId = comment.insert();
        if (commentId == -1) {
            request.setAttribute("error", "评论发送失败！");
            response.sendRedirect(request.getHeader("referer"));
        } else {
            response.sendRedirect(String.format("/user/%d/article/%d", articleUserId, article_id));
        }
    }
}
