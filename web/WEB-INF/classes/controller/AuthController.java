package controller;

import model.User;
import util.GlobalConfigHelper;
import util.BCrypt;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class AuthController extends HttpServlet {
    private Properties properties = null;

    @Override
    public void init() {
        properties = GlobalConfigHelper.getConfigFromContext(this.getServletContext());
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        switch (request.getRequestURI()) {
            case "/auth/logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        switch (request.getRequestURI()) {
            case "/auth/login":
                login(request, response);
                break;
            case "/auth/register":
                register(request, response);
                break;
            case "/auth/register/check-username":
                checkUsername(request, response);
                break;
        }
    }

    private void loginFlash(HttpServletRequest request, String username, String password) {
        request.getSession().setAttribute("flash_login_username", username);
        request.getSession().setAttribute("flash_login_password", password);
    }

    private void regFlash(HttpServletRequest request, String username, String password) {
        request.getSession().setAttribute("flash_reg_username", username);
        request.getSession().setAttribute("flash_reg_password", password);
    }

    private void cleanFlash(HttpServletRequest request) {
        request.getSession().removeAttribute("flash_login_username");
        request.getSession().removeAttribute("flash_login_password");
        request.getSession().removeAttribute("flash_reg_username");
        request.getSession().removeAttribute("flash_reg_password");
    }

    private void login(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        cleanFlash(request);
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        User u = new User(properties);
        User user = u.getUserByUsername(username);
        if (user == null) {
            request.setAttribute("error", "用户名不存在！");
            request.setAttribute("error_type", "INVALID_USERNAME");
            loginFlash(request, username, passwd);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        } else {
            if (BCrypt.checkpw(passwd, user.getPassword())) {
                // Login ok
                request.getSession(true).setAttribute("uid", user.getId());
                response.sendRedirect("/user/" + user.getId());
            } else {
                request.setAttribute("error", "密码错误！");
                request.setAttribute("error_type", "INVALID_PASSWORD");
                loginFlash(request, username, passwd);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                        properties.getProperty("TemplatePathRoot") + "index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private void register(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        cleanFlash(request);
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        String repasswd = request.getParameter("repassword");
        if (username.isEmpty() || passwd.isEmpty() ||
                repasswd.isEmpty() || !passwd.equals(repasswd)) {
            request.setAttribute("error", "注册信息有误，请检查！");
            regFlash(request, username, passwd);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
            return;
        }
        User u = new User(properties, -1, username, BCrypt.hashpw(passwd, BCrypt.gensalt()));
        if (u.insert() < 0) {
            request.setAttribute("error", "注册失败！");
            regFlash(request, username, passwd);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("msg", "注册成功！请登录");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    properties.getProperty("TemplatePathRoot") + "index.jsp");
            dispatcher.forward(request, response);
        }
    }

    public class CheckResult {
        public int code;
        public boolean result;
        public String msg;
    }

    private void checkUsername(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        CheckResult rst = new CheckResult();
        User u = new User(properties);
        rst.code = HttpServletResponse.SC_OK;
        if (request.getParameter("username").isEmpty() ||
                u.getUserByUsername(request.getParameter("username")) != null) {
            rst.result = false;
            rst.msg = "用户名已存在";
        } else {
            rst.result = true;
            rst.msg = "用户名可用";
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(rst));
        response.getWriter().flush();
    }

    private void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        request.getSession(true).removeAttribute("uid");
        response.sendRedirect("/");
    }
}
