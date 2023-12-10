package org.kukuking.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.database.MyDatabaseUtil;
import org.kukuking.entity.Message;
import org.kukuking.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Filter/MainPage")
public class MainPageServlet extends HttpServlet {
    private static final String BASE = "/WEB-INF";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Message> messageList = MyDatabaseUtil.randomMessageFromDB(10);
        req.getSession().setAttribute("messageList", messageList);
        req.getRequestDispatcher(BASE + "/MainPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
