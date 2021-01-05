package fit.bikeja.restProxy;

import fit.bikeja.dto.RestExceptionDto;
import fit.bikeja.dto.UserDto;
import fit.bikeja.rest.RestException;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@ApplicationScoped
public class BaseProxy {

    public WebTarget createTarget(String path) {
        Client client = ClientBuilder.newClient();
        return client.target("http://localhost:8080" + path);
    }

    public Invocation.Builder createBuilder(String path) {
        return this.createTarget(path).request(MediaType.APPLICATION_JSON);
    }

    public void testResponseError(Response resp) throws RestException {
        if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
            RestExceptionDto ex = resp.readEntity(new GenericType<RestExceptionDto>() {});
            throw ex.toException();
        }
    }
}
