package fit.bikeja.dto;

import fit.bikeja.rest.RestException;

import javax.ws.rs.core.Response;

public class RestExceptionDto {

    private Response.Status errorCode;
    private String msg;

    public Response.Status getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Response.Status errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestException toException() {
        return new RestException(this.errorCode, this.msg);
    }
}
