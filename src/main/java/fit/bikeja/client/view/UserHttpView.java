package fit.bikeja.client.view;

import fit.bikeja.dto.UserDto;
import fit.bikeja.entity.UserWithStats;
import fit.bikeja.rest.RestException;
import fit.bikeja.restProxy.UserProxy;
import fit.bikeja.client.ui.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/users", "/users/update"})
public class UserHttpView extends BaseHttpView {

    @Inject
    UserProxy userProxy;

    public UserHttpView() {
        super("/users");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        if (req.getServletPath().equals("/users/update")) {
            // editace konkretniho uzivatele

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Editace uživatele"));

            cp.addBody(new PageMenu().addDefault());
            cp.addBody(new PageBackLink("/users"));

            PageForm pf = new PageForm("/edit.userDo", "Editace uživatele", "Uložit");

            pf.addField("Přihlašovací jméno", "loginName", (Object ref) -> ((UserDto)ref).getLoginName());
            pf.addField("Heslo", "password", (Object ref) -> ((UserDto)ref).getPassword());
            pf.addField("Jméno", "firstName", (Object ref) -> ((UserDto)ref).getFirstName());
            pf.addField("Příjmení", "surName", (Object ref) -> ((UserDto)ref).getSurName());

            pf.setData(req.getSession(true).getAttribute("editUser"));

            cp.addBody(pf);

            resp.getWriter().write(cp.toString());

        } else {
            // prehled uzivatelu

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Správa uživatelů"));

            cp.addBody(new PageMenu().addDefault());

            PageForm pf = new PageForm("add.userDo", "Nový uživatel", "Vytvořit uživatele");

            pf.addField("Přihlašovací jméno", "loginName");
            pf.addField("Heslo", "password");
            pf.addField("Jméno", "firstName");
            pf.addField("Příjmení", "surName");

            cp.addBody(pf);

            PageTable tb = new PageTable("Seznam uživatelů");

            tb.addField("Přihlašovací jméno", "loginName", (Object line) -> ((UserWithStats)line).getLoginName());
            tb.addField("Heslo", "password", (Object line) -> ((UserWithStats)line).getPassword());
            tb.addField("Jméno", "firstName", (Object line) -> ((UserWithStats)line).getFirstName());
            tb.addField("Příjmení", "surName", (Object line) -> ((UserWithStats)line).getSurName());
            tb.addField("Aktivních rezervací", "activeReservations", (Object line) -> Integer.toString(((UserWithStats)line).getActiveReservations())).setAlign(1);
            tb.addField("Celkem rezervací", "activeReservations", (Object line) -> Integer.toString(((UserWithStats)line).getTotalReservations())).setAlign(1);
            tb.addField((Object line) -> {

                return (new PageActionForm("editRedir.userDo", "Editace"))
                        .addFieldHidden("id", Integer.toString(((UserWithStats)line).getId()))
                        .toString();
            });
            tb.addField((Object line) -> {

                return (new PageActionForm("rem.userDo", "Odstranit"))
                        .addFieldHidden("id", Integer.toString(((UserWithStats)line).getId()))
                        .setPromptMessage("Skutečně si přejete odstranit uživatele?")
                        .toString();
            });

            try {
                for (UserWithStats u : this.userProxy.getAllUsersWithStats()) {
                    tb.addLine(u);
                }

            } catch (RestException ignored) {
            }

            cp.addBody(tb);

            resp.getWriter().write(cp.toString());
        }
    }
}
