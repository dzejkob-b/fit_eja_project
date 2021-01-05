package fit.bikeja.client.controller;

import fit.bikeja.dto.ItemDto;
import fit.bikeja.restProxy.ItemProxy;
import fit.bikeja.session.UserSession;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("*.itemDo")
public class ItemHttpController extends BaseHttpController {

    @Inject
    UserSession userSession;

    @Inject
    ItemProxy itemProxy;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!this.userSession.getUser().isPresent()) {
            this.makeError(req, resp, "/items", "Nepovolený přístup uživatel není přihlášen!");
        }

        switch (req.getServletPath()) {
            case "/add.itemDo":
                try {
                    // zalozeni noveho predmetu

                    ItemDto item = new ItemDto();

                    item.setCaption(req.getParameter("caption"));
                    item.setValue(Double.parseDouble(req.getParameter("value")));
                    item.setCreatedBy_id(this.userSession.getUser().get().getId());

                    this.itemProxy.createItem(item);

                    resp.sendRedirect("/items");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items", ex.getMessage());
                }
                break;

            case "/rem.itemDo" :
                try {
                    // vymaz predmetu

                    int remId = Integer.parseInt(req.getParameter("id"));
                    this.itemProxy.deleteItem(remId);

                    resp.sendRedirect("/items");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items", ex.getMessage());
                }
                break;

            case "/resRedir.itemDo" :
                try {
                    int editId = Integer.parseInt(req.getParameter("id"));
                    Optional<ItemDto> editItem = this.itemProxy.getItem(editId);

                    if (editItem.isPresent()) {
                        // nacteni predmetu do session a redirect

                        req.getSession(true).setAttribute("resItem", editItem.get());

                        resp.sendRedirect("/items/reservation");

                    } else {
                        this.makeError(req, resp, "/items", "Předmět nenalezen!");
                    }

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items", ex.getMessage());
                }
                break;

            case "/editRedir.itemDo" :
                try {
                    int editId = Integer.parseInt(req.getParameter("id"));
                    Optional<ItemDto> editItem = this.itemProxy.getItem(editId);

                    if (editItem.isPresent()) {
                        // nacteni predmetu do session a redirect

                        req.getSession(true).setAttribute("editItem", editItem.get());

                        resp.sendRedirect("/items/update");

                    } else {
                        this.makeError(req, resp, "/items", "Předmět nenalezen!");
                    }

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items", ex.getMessage());
                }
                break;

            case "/edit.itemDo" :
                try {
                    // update predmetu

                    ItemDto editItem = (ItemDto) req.getSession(true).getAttribute("editItem");

                    editItem.setCaption(req.getParameter("caption"));
                    editItem.setValue(Double.parseDouble(req.getParameter("value")));

                    this.itemProxy.updateItem(editItem.getId(), editItem);

                    resp.sendRedirect("/items");

                } catch (Exception ex) {
                    this.makeError(req, resp, "/items", ex.getMessage());
                }
                break;

            default :
                this.makeError(req, resp, "/items", "Unknown action `" + req.getServletPath() + "`!");
                break;
        }
    }
}
