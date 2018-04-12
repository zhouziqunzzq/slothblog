package controller;

import model.UserInfo;
import util.GlobalConfigHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class ProfileController extends HttpServlet {
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
        int targetUid;
        if (request.getAttribute("targetUid") != null) {
            targetUid = Integer.parseInt(request.getAttribute("targetUid").toString());
            UserInfo userInfo = new UserInfo(properties);
            userInfo = userInfo.getUserInfoByUserId(targetUid);
            request.setAttribute("userInfo", userInfo);
            request.getSession().setAttribute("targetUid", targetUid);
        } else {
            request.setAttribute("userInfo", null);
        }
        request.getRequestDispatcher(
                properties.getProperty("TemplatePathRoot") + "/user/profile.jsp")
                .forward(request, response);
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int userId = (int) request.getSession().getAttribute("uid");
        String email = request.getParameter("email");
        UserInfo.Gender gender = UserInfo.Gender.values()[Integer.parseInt(request.getParameter("gender"))];
        String nickname = request.getParameter("nickname");
        String intro = request.getParameter("intro");

        UserInfo userinfo = new UserInfo(properties);
        userinfo.setUser_id(userId);
        userinfo.setEmail(email);
        userinfo.setGender(gender);
        userinfo.setNickname(nickname);
        userinfo.setIntro(intro);

        int userInfoId = userinfo.insert();
        if (userInfoId == -1)
            request.getSession().setAttribute("error", "设置失败！");
        response.sendRedirect("/user/" + userId + "/profile");
    }
}
