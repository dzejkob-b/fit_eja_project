package fit.bikeja.restProxy;

import fit.bikeja.dto.ItemDto;
import fit.bikeja.rest.RestException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public class ItemProxy {

    @Inject
    BaseProxy baseProxy;

    public Collection<ItemDto> getAllItems() throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/item").get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ItemDto>>() {});
    }

    public Optional<ItemDto> getItem(int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/item/" + Integer.toString(item_id)).get();
        this.baseProxy.testResponseError(resp);
        return Optional.ofNullable(resp.readEntity(new GenericType<ItemDto>() {}));
    }

    public ItemDto createItem(ItemDto item) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/item").post(Entity.json(item));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<ItemDto>() {});
    }

    public ItemDto updateItem(int item_id, ItemDto item) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/item/" + Integer.toString(item_id)).put(Entity.json(item));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<ItemDto>() {});
    }

    public ItemDto deleteItem(int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/item/" + Integer.toString(item_id)).delete();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<ItemDto>() {});
    }
}
