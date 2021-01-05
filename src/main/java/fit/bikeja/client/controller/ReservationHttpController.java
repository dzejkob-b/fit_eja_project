package fit.bikeja.client.controller;

import fit.bikeja.dto.ReservationDto;
import fit.bikeja.restProxy.ItemProxy;
import fit.bikeja.restProxy.ReservationProxy;
import fit.bikeja.session.UserSession;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("*.reservationDo")
public class ReservationHttpController extends BaseHttpController {

    @Inject
    UserSession userSession;

    @Inject
    ItemProxy itemProxy;

    @Inject
    ReservationProxy reservationProxy;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!this.userSession.getUser().isPresent()) {
            this.makeError(req, resp, "/items", "Nepovolený přístup uživatel není přihlášen!");
        }

        switch (req.getServletPath()) {
            case "/add.reservationDo":
                try {
                    int item_id = Integer.parseInt(req.getParameter("item_id"));

                    ReservationDto res = new ReservationDto();

                    res.setValid(true);
                    res.setItem_id(item_id);
                    res.setUser_id(this.userSession.getUser().get().getId());

                    this.reservationProxy.createReservation(res);

                    resp.sendRedirect("/items/reservation");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items/reservation", ex.getMessage());
                }
                break;

            case "/free.reservationDo":
                try {
                    int res_id = Integer.parseInt(req.getParameter("res_id"));

                    Optional<ReservationDto> res = this.reservationProxy.getReservation(res_id);

                    if (res.isPresent()) {
                        res.get().setValid(false);
                        this.reservationProxy.updateReservation(res.get().getId(), res.get());

                        resp.sendRedirect("/items/reservation");

                    } else {
                        this.makeError(req, resp, "/items/reservation", "Rezervace nebyla nalezena!");
                    }

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items/reservation", ex.getMessage());
                }
                break;

            default :
                this.makeError(req, resp, "/items/reservation", "Unknown action `" + req.getServletPath() + "`!");
                break;
        }
    }
}
