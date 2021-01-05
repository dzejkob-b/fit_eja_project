package fit.bikeja.client.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class BaseHttpController extends HttpServlet {

    protected void makeError(HttpServletRequest req, HttpServletResponse resp, String path, String errorMsg) throws ServletException, IOException {
        req.setAttribute("errorMsg", errorMsg);

        this.getServletContext().getRequestDispatcher(path).forward(req, resp);
    }
}
