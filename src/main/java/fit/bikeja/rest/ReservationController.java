package fit.bikeja.rest;

import fit.bikeja.dto.ReservationDto;
import fit.bikeja.entity.Reservation;
import fit.bikeja.service.ReservationException;
import fit.bikeja.service.ReservationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Path("/rest/reservation")
public class ReservationController {

    @Inject
    ReservationService reservationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationDto getReservation(@PathParam("id") int id) throws RestException {
        Optional<ReservationDto> res = this.reservationService.getReservation(id);

        if (res.isPresent()) {
            return res.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Rezervace nebyla nalezena!");
        }
    }

    @Path("/user_item/{user_id}/{item_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getUserItemReservations(@PathParam("user_id") int user_id, @PathParam("item_id") int item_id) throws RestException {
        return this.reservationService.getUserItemReservations(user_id, item_id);
    }

    @Path("/user_active/{user_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getUserActiveReservations(@PathParam("user_id") int user_id) throws RestException {
        return this.reservationService.getUserActiveReservations(user_id);
    }

    @Path("/user_past/{user_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getUserPastReservations(@PathParam("user_id") int user_id) throws RestException {
        return this.reservationService.getUserPastReservations(user_id);
    }

    @Path("/item/{item_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getItemReservations(@PathParam("item_id") int item_id) throws RestException {
        return this.reservationService.getItemReservations(item_id);
    }

    @Path("/item_active/{item_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getItemActiveReservations(@PathParam("item_id") int item_id) throws RestException {
        return this.reservationService.getItemActiveReservations(item_id);
    }

    @Path("/item_past/{item_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> getItemPastReservations(@PathParam("item_id") int item_id) throws RestException {
        return this.reservationService.getItemPastReservations(item_id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ReservationDto createReservation(ReservationDto res) throws RestException {
        try {
            Reservation r = this.reservationService.createReservation(res);

            res.setId(r.getId());
            res.setItem_id(r.getItem().getId());
            res.setUser_id(r.getUser().getId());

            return res;

        } catch (ReservationException ex) {
            throw new RestException(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @Path("/list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<ReservationDto> createReservations(Collection<ReservationDto> resList) throws RestException {
        try {
            for (ReservationDto res : resList) {
                Reservation r = this.reservationService.createReservation(res);

                res.setId(r.getId());
                res.setItem_id(r.getItem().getId());
                res.setUser_id(r.getUser().getId());
            }

            return resList;

        } catch (ReservationException ex) {
            throw new RestException(Response.Status.BAD_REQUEST, ex.getMessage());
        }
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ReservationDto updateReservation(@PathParam("id") int id, ReservationDto resToUpd) throws RestException {
        Optional<ReservationDto> exRes = this.reservationService.getReservation(id);

        if (exRes.isPresent()) {
            try {
                this.reservationService.updateReservation(id, resToUpd);

            } catch (ReservationException ex) {
                throw new RestException(Response.Status.BAD_REQUEST, ex.getMessage());
            }

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Rezervace nebyla nalezena!");
        }

        return resToUpd;
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationDto deleteReservation(@PathParam("id") int id) throws RestException {
        Optional<ReservationDto> res = this.reservationService.getReservation(id);

        if (res.isPresent()) {
            this.reservationService.deleteReservation(id);
            return res.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Rezervace nebyla nalezena!");
        }
    }
}
