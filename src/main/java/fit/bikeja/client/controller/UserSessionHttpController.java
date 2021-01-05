package fit.bikeja.client.controller;

import fit.bikeja.session.UserSession;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.userSessionDo")
public class UserSessionHttpController extends BaseHttpController {

    @Inject
    UserSession userSession;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case "/login.userSessionDo":
                if (this.userSession.login(req.getParameter("loginName"), req.getParameter("password"))) {
                    resp.sendRedirect("/items");

                } else {
                    this.makeError(req, resp, "/items", "Neplatné přihlašovací jméno nebo heslo!");
                }
                break;

            case "/logout.userSessionDo" :
                if (this.userSession.logout()) {
                    resp.sendRedirect("/items");

                } else {
                    this.makeError(req, resp, "/items", "Uživatel není přihlášen!");
                }
                break;

            default :
                this.makeError(req, resp, "/items", "Unknown action `" + req.getServletPath() + "`!");
                break;
        }
    }
}
