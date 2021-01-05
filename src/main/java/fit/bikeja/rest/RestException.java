package fit.bikeja.rest;

import fit.bikeja.dto.RestExceptionDto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.io.Serializable;

public class RestException extends Exception implements Serializable {

    private final Response.Status status;

    public RestException(Response.Status status) {
        super();
        this.status = status;
    }

    public RestException(Response.Status status, String msg) {
        super(msg);
        this.status = status;
    }

    public RestException(Response.Status status, String msg, Exception ex) {
        super(msg, ex);
        this.status = status;
    }

    public RestException(Response.Status status, ConstraintViolationException ex) {
        super(RestException.constraintMsg(ex));
        this.status = status;
    }

    public static String constraintMsg(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation v : ex.getConstraintViolations()) {
            if (sb.length() > 0) {
                sb.append("<br/>");
            }

            sb.append(v.getMessage());
        }

        return sb.toString();
    }

    public Response.Status getStatus() {
        return this.status;
    }

    public RestExceptionDto toDto() {
        RestExceptionDto d = new RestExceptionDto();

        d.setErrorCode(this.status);
        d.setMsg(this.getMessage());

        return d;
    }
}
