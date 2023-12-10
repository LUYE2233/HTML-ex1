package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.entity.User;

import java.io.IOException;

@WebServlet("/Filter/Login")
public class LoginServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
        User user = MyDatabaseUtil.login(userID,password);
        if (user != null) {
            req.getSession().setAttribute("user",user);
            req.getRequestDispatcher("/Filter/MainPage").forward(req,resp);
        } else {
            req.setAttribute("tips","login failed");
            req.getRequestDispatcher(BASE + "/Login.jsp").forward(req, resp);
        }
    }
}
