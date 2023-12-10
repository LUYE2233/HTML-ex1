package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.entity.User;

import java.io.IOException;

@WebServlet("/Filter/Welcome")
public class WelcomeServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user =(User) req.getSession().getAttribute("user");
        req.getRequestDispatcher(BASE + "/MainPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
