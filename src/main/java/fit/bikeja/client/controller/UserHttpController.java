package fit.bikeja.client.controller;

import fit.bikeja.dto.UserDto;
import fit.bikeja.restProxy.UserProxy;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("*.userDo")
public class UserHttpController extends BaseHttpController {

    @Inject
    UserProxy userProxy;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case "/add.userDo" :
                try {
                    // zalozeni noveho uzivatele

                    UserDto user = new UserDto();

                    user.setLoginName(req.getParameter("loginName"));
                    user.setPassword(req.getParameter("password"));
                    user.setFirstName(req.getParameter("firstName"));
                    user.setSurName(req.getParameter("surName"));

                    this.userProxy.createUser(user);

                    resp.sendRedirect("/users");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/users", ex.getMessage());
                }
                break;

            case "/rem.userDo" :
                try {
                    // vymaz uzivatele

                    int remId = Integer.parseInt(req.getParameter("id"));
                    this.userProxy.deleteUser(remId);

                    resp.sendRedirect("/users");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/users", ex.getMessage());
                }
                break;

            case "/editRedir.userDo" :
                try {
                    int editId = Integer.parseInt(req.getParameter("id"));
                    Optional<UserDto> editUser = this.userProxy.getUser(editId);

                    if (editUser.isPresent()) {
                        // nacteni uzivatele do session a redirect

                        req.getSession(true).setAttribute("editUser", editUser.get());

                        resp.sendRedirect("/users/update");

                    } else {
                        this.makeError(req, resp, "/users", "Uživatel nenalezen!");
                    }

                } catch (Exception ex) {
                    this.makeError(req, resp, "/users", ex.getMessage());
                }
                break;

            case "/edit.userDo" :
                try {
                    // update uzivatele

                    UserDto editUser = (UserDto)req.getSession(true).getAttribute("editUser");

                    editUser.setLoginName(req.getParameter("loginName"));
                    editUser.setPassword(req.getParameter("password"));
                    editUser.setFirstName(req.getParameter("firstName"));
                    editUser.setSurName(req.getParameter("surName"));

                    this.userProxy.updateUser(editUser.getId(), editUser);

                    resp.sendRedirect("/users");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/users", ex.getMessage());
                }
                break;

            default :
                this.makeError(req, resp, "/users", "Unknown action `" + req.getServletPath() + "`!");
                break;
        }
    }

}
