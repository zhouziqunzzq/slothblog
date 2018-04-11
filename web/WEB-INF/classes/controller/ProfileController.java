package controller;

import util.GlobalConfigHelper;
import model.UserInfo;

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
}
