package fit.bikeja.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionHandler implements ExceptionMapper<RestException> {

    @Override
    public Response toResponse(RestException ex) {
        return Response.serverError().entity(ex.toDto()).status(ex.getStatus()).build();
    }
}
