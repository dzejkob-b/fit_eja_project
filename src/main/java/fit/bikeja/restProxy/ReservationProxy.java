package fit.bikeja.restProxy;

import fit.bikeja.dto.ReservationDto;
import fit.bikeja.rest.RestException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public class ReservationProxy {

    @Inject
    BaseProxy baseProxy;

    public Collection<ReservationDto> getAllReservations() throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation").get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public ReservationDto createReservation(ReservationDto res) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation").post(Entity.json(res));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<ReservationDto>() {});
    }

    public ReservationDto updateReservation(int res_id, ReservationDto res) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/" + Integer.toString(res_id)).put(Entity.json(res));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<ReservationDto>() {});
    }

    public Optional<ReservationDto> getReservation(int res_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/" + Integer.toString(res_id)).get();
        this.baseProxy.testResponseError(resp);
        return Optional.ofNullable(resp.readEntity(new GenericType<ReservationDto>() {}));
    }

    public Collection<ReservationDto> getUserItemReservations(int user_id, int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/user_item/" + Integer.toString(user_id) + "/" + Integer.toString(item_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public Collection<ReservationDto> getUserActiveReservations(int user_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/user_active/" + Integer.toString(user_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public Collection<ReservationDto> getUserPastReservations(int user_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/user_past/" + Integer.toString(user_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public Collection<ReservationDto> getItemReservations(int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/item/" + Integer.toString(item_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public Collection<ReservationDto> getItemActiveReservations(int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/item_active/" + Integer.toString(item_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

    public Collection<ReservationDto> getItemPastReservations(int item_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/reservation/item_past/" + Integer.toString(item_id)).get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<ReservationDto>>() {});
    }

}
