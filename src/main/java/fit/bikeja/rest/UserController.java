package fit.bikeja.rest;

import fit.bikeja.dto.UserDto;
import fit.bikeja.entity.UserWithStats;
import fit.bikeja.service.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Path("/rest/user")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<UserDto> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GET
    @Path("/all_with_stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<UserWithStats> getAllUsersWithStats() {
        return this.userService.getAllUsersWithStats();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUser(@PathParam("id") int id) throws RestException {
        Optional<UserDto> user = this.userService.getUser(id);

        if (user.isPresent()) {
            return user.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Uživatel nebyl nalezen!");
        }
    }

    @Path("/login")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUserByLoginNameAndPassword(@QueryParam("loginName") String loginName, @QueryParam("password") String password) throws RestException {
        Optional<UserDto> user = this.userService.getUserByLoginNameAndPassword(loginName, password);

        if (user.isPresent()) {
            return user.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Uživatel nebyl nalezen!");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto createUser(UserDto user) throws RestException {
        try {
            user.setId(this.userService.createUser(user).getId());

        } catch (ConstraintViolationException ce) {
            throw new RestException(Response.Status.BAD_REQUEST, ce);

        } catch (Exception ex) {
            throw new RestException(Response.Status.BAD_REQUEST, "Uživatel s tímto uživatelským jménem již existuje!");
        }

        return user;
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto updateUser(@PathParam("id") int id, UserDto userToUpd) throws RestException {
        Optional<UserDto> exUser = this.userService.getUser(id);

        if (exUser.isPresent()) {
            try {
                this.userService.updateUser(id, userToUpd);

            } catch (ConstraintViolationException ce) {
                throw new RestException(Response.Status.BAD_REQUEST, ce);

            } catch (Exception ex) {
                throw new RestException(Response.Status.BAD_REQUEST, "Uživatel s tímto uživatelským jménem již existuje!");
            }

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Uživatel nebyl nalezen!");
        }

        return userToUpd;
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto deleteUser(@PathParam("id") int id) throws RestException {
        Optional<UserDto> user = this.userService.getUser(id);

        if (user.isPresent()) {
            this.userService.deleteUser(id);
            return user.get();

        } else {
            throw new RestException(Response.Status.NOT_FOUND, "Uživatel nebyl nalezen!");
        }
    }
}
