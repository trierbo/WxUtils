package org.trier.servlet;

import org.trier.wechat.pojo.auth.Token;
import org.trier.wechat.pojo.auth.UserInfo;
import org.trier.wechat.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 网页授权servlet
 */
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            String redirect_uri = "http://" + request.getServerName()
                    + request.getContextPath()
                    + request.getServletPath();
            String url = AuthService.getCodeUrl("snsapi_userinfo", redirect_uri);
            response.sendRedirect(url);
        } else {
            Token token = AuthService.getToken(code);
            PrintWriter out = response.getWriter();
            UserInfo userInfo = AuthService.getUserInfo(token.getAccess_token(), token.getOpenid());
            out.println(userInfo.toString());
        }
    }
}
