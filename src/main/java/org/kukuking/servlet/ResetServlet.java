package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.entity.User;

import java.io.IOException;


@WebServlet("/Filter/Reset")
public class ResetServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BASE + "/Reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
        String oldPassword = req.getParameter("oldPassword");
        User user = MyDatabaseUtil.login(userID,oldPassword);
        if (user != null) {
            MyDatabaseUtil.reset(userID, password);
            user.setPassword(password);
            req.getSession().setAttribute("user",user);
            req.getRequestDispatcher("/Filter/MainPage").forward(req,resp);
        } else {
            req.setAttribute("tips","reset failed");
            req.getRequestDispatcher(BASE + "/Reset.jsp").forward(req, resp);
        }
    }
}
