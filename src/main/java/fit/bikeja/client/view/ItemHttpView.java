package fit.bikeja.client.view;

import fit.bikeja.dto.ItemDto;
import fit.bikeja.dto.ReservationDto;
import fit.bikeja.dto.UserDto;
import fit.bikeja.rest.RestException;
import fit.bikeja.restProxy.ItemProxy;
import fit.bikeja.restProxy.ReservationProxy;
import fit.bikeja.restProxy.UserProxy;
import fit.bikeja.session.UserSession;
import fit.bikeja.client.ui.*;
import org.h2.store.Page;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({"/items", "/items/update", "/items/reservation"})
public class ItemHttpView extends BaseHttpView {

    @Inject
    ItemProxy itemProxy;

    @Inject
    UserProxy userProxy;

    @Inject
    ReservationProxy reservationProxy;

    @Inject
    UserSession userSession;

    public ItemHttpView() {
        super("/items");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        if (!userSession.isLogged()) {
            // vyzadovani prihlaseni neprihlaseneho uzivatele

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Editace předmětu"));

            cp.addBody(new PageMenu().addDefault());
            cp.addBody(new PageContent("<p>Přístup do správy a rezervace předmětů <strong>vyžaduje přihlášení</strong>.</p>"));

            PageForm pf = new PageForm("/login.userSessionDo", "Přihlášení uživatele", "Přihlásit se");

            pf.addField("Uživatelské jméno", "loginName");
            pf.addField("Heslo", "password");

            cp.addBody(pf);

            resp.getWriter().write(cp.toString());

        } else if (req.getServletPath().equals("/items/update")) {
            // editace predmetu

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Editace předmětu"));

            cp.addBody(new PageMenu().addDefault());
            cp.addBody(new PageLoginLine(this.userSession));
            cp.addBody(new PageBackLink("/items"));

            PageForm pf = new PageForm("/edit.itemDo", "Editace předmětu", "Uložit");

            pf.addField("Název předmětu", "caption", (Object ref) -> ((ItemDto)ref).getCaption());
            pf.addField("Hodnota", "value", (Object ref) -> ((ItemDto)ref).getValue().toString());

            pf.setData(req.getSession(true).getAttribute("editItem"));

            cp.addBody(pf);

            resp.getWriter().write(cp.toString());


        } else if (req.getServletPath().equals("/items/reservation")) {
            // rezervace predmetu

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Rezervace předmětu"));

            cp.addBody(new PageMenu().addDefault());
            cp.addBody(new PageLoginLine(this.userSession));
            cp.addBody(new PageBackLink("/items"));

            ItemDto resItem = (ItemDto)req.getSession(true).getAttribute("resItem");

            PageForm pf = new PageForm("/edit.itemDo", "Rezervace předmětu");

            pf.addField("Název předmětu", "caption", (Object ref) -> ((ItemDto)ref).getCaption());
            pf.addField("Hodnota", "value", (Object ref) -> ((ItemDto)ref).getValue().toString());

            pf.setData(resItem);

            cp.addBody(pf);

            Optional<ReservationDto> res = Optional.empty();

            try {
                res = this.reservationProxy.getUserItemReservations(this.userSession.getUser().get().getId(), resItem.getId()).stream().filter(ReservationDto::isValid).findFirst();

            } catch (RestException ignored) {
            }

            if (res.isPresent()) {
                cp.addBody(new PageActionForm("/free.reservationDo", "Uvolnit rezervaci předmětu").addFieldHidden("res_id", Integer.toString(res.get().getId())).setInCont(true));

            } else {
                cp.addBody(new PageActionForm("/add.reservationDo", "Rezervovat tento předmět").addFieldHidden("item_id", Integer.toString(resItem.getId())).setInCont(true));
            }

            PageTable pt = new PageTable("Přehled rezervací");

            pt.addField("Datum", "created", (Object ref) -> ((ReservationDto)ref).getCreated().toString());
            pt.addField("Aktivní", "isValid", (Object ref) -> ((ReservationDto)ref).isValid() ? "ANO" : "NE");
            pt.addField("Rezervováno kým", "user_id", (Object ref) -> {

                Optional<UserDto> u = Optional.empty();

                try {
                    u = this.userProxy.getUser(((ReservationDto)ref).getUser_id());

                } catch (RestException ignored) {
                }

                return u.map(userDto -> (userDto.getFirstName() + " " + userDto.getSurName())).orElse("neznámý");

            });

            try {
                for (ReservationDto cRes : this.reservationProxy.getItemReservations(resItem.getId())) {
                    pt.addLine(cRes);
                }

            } catch (RestException ignored) {
            }

            cp.addBody(pt);

            resp.getWriter().write(cp.toString());

        } else {
            // vypis predmetu

            PageComposer cp = new PageComposer();

            cp.addHead(new PageHeader("Správa předmětů"));

            cp.addBody(new PageMenu().addDefault());
            cp.addBody(new PageLoginLine(this.userSession));

            PageForm pf = new PageForm("add.itemDo", "Nový předmět", "Vytvořit předmět");

            pf.addField("Název předmětu", "caption");
            pf.addField("Hodnota", "value");

            cp.addBody(pf);

            PageTable tb = new PageTable("Seznam předmětů");

            tb.addField("Přihlašovací jméno", "loginName", (Object line) -> ((ItemDto)line).getCaption());
            tb.addField("Hodnota", "value", (Object line) -> ((ItemDto)line).getValue().toString());
            tb.addField("K dispozici", "avail", (Object line) -> {

                try {
                    return this.reservationProxy.getItemActiveReservations(((ItemDto)line).getId()).isEmpty() ? "ANO" : "NE";

                } catch (RestException ex) {
                    return "NE";
                }

            });
            tb.addField("Zadáno kým", "createdBy_id", (Object line) -> {

                Optional<UserDto> u = Optional.empty();

                try {
                    u = this.userProxy.getUser(((ItemDto)line).getCreatedBy_id());

                } catch (RestException ignored) {
                }

                return u.map(userDto -> (userDto.getFirstName() + " " + userDto.getSurName())).orElse("neznámý");

            });
            tb.addField((Object line) -> {

                return (new PageActionForm("resRedir.itemDo", "Rezervace"))
                        .addFieldHidden("id", Integer.toString(((ItemDto)line).getId()))
                        .toString();
            });
            tb.addField((Object line) -> {

                return (new PageActionForm("editRedir.itemDo", "Editace"))
                        .addFieldHidden("id", Integer.toString(((ItemDto)line).getId()))
                        .toString();
            });
            tb.addField((Object line) -> {

                return (new PageActionForm("rem.itemDo", "Odstranit"))
                        .addFieldHidden("id", Integer.toString(((ItemDto)line).getId()))
                        .setPromptMessage("Skutečně si přejete odstranit předmět?")
                        .toString();
            });

            try {
                for (ItemDto it : this.itemProxy.getAllItems()) {
                    tb.addLine(it);
                }

            } catch (RestException ignored) {
            }

            cp.addBody(tb);

            resp.getWriter().write(cp.toString());
        }
    }

}
