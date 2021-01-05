package fit.bikeja.restProxy;

import fit.bikeja.dto.RestExceptionDto;
import fit.bikeja.dto.UserDto;
import fit.bikeja.entity.UserWithStats;
import fit.bikeja.rest.RestException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public class UserProxy {

    @Inject
    BaseProxy baseProxy;

    public Collection<UserDto> getAllUsers() throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user").get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<UserDto>>() {});
    }

    public Collection<UserWithStats> getAllUsersWithStats() throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user/all_with_stats").get();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<Collection<UserWithStats>>() {});
    }

    public Optional<UserDto> getUser(int user_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user/" + Integer.toString(user_id)).get();
        this.baseProxy.testResponseError(resp);
        return Optional.ofNullable(resp.readEntity(new GenericType<UserDto>() {}));
    }

    public Optional<UserDto> getUserByLoginNameAndPassword(String loginName, String password) throws RestException {
        Response resp = this.baseProxy.createTarget("/rest/user/login")
                .queryParam("loginName", loginName)
                .queryParam("password", password)
                .request(MediaType.APPLICATION_JSON)
                .get();

        this.baseProxy.testResponseError(resp);
        UserDto user = resp.readEntity(new GenericType<UserDto>() {});
        return Optional.ofNullable(user);
    }

    public UserDto createUser(UserDto user) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user").post(Entity.json(user));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<UserDto>() {});
    }

    public UserDto updateUser(int user_id, UserDto user) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user/" + Integer.toString(user_id)).put(Entity.json(user));
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<UserDto>() {});
    }

    public UserDto deleteUser(int user_id) throws RestException {
        Response resp = this.baseProxy.createBuilder("/rest/user/" + Integer.toString(user_id)).delete();
        this.baseProxy.testResponseError(resp);
        return resp.readEntity(new GenericType<UserDto>() {});
    }
}
