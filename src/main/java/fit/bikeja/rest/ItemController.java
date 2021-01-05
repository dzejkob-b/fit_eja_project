package fit.bikeja.rest;

import fit.bikeja.dto.ItemDto;
import fit.bikeja.service.ItemService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Path("/rest/item")
public class ItemController {

    @Inject
    ItemService itemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ItemDto> getAllItems() {
        return this.itemService.getAllItems();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ItemDto getItem(@PathParam("id") int id) throws RestException {
        Optional<ItemDto> item = this.itemService.getItem(id);

        if (item.isPresent()) {
            return item.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Předmět nebyl nalezen!");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ItemDto createItem(ItemDto item) throws RestException {
        try {
            item.setId(this.itemService.createItem(item).getId());

        } catch (ConstraintViolationException ce) {
            throw new RestException(Response.Status.BAD_REQUEST, ce);
        }

        return item;
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ItemDto updateItem(@PathParam("id") int id, ItemDto itemToUpd) throws RestException {
        Optional<ItemDto> exItem = this.itemService.getItem(id);

        if (exItem.isPresent()) {
            try {
                this.itemService.updateItem(id, itemToUpd);

            } catch (ConstraintViolationException ce) {
                throw new RestException(Response.Status.BAD_REQUEST, ce);
            }

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Předmět nebyl nalezen!");
        }

        return itemToUpd;
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public ItemDto deleteItem(@PathParam("id") int id) throws RestException {
        Optional<ItemDto> item = this.itemService.getItem(id);

        if (item.isPresent()) {
            this.itemService.deleteItem(id);
            return item.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Předmět nebyl nalezen!");
        }
    }
}
