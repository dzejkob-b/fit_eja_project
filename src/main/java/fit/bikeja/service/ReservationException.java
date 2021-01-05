package fit.bikeja.service;

import java.io.Serializable;

public class ReservationException extends Exception implements Serializable {

    public ReservationException() {
        super();
    }

    public ReservationException(String msg) {
        super(msg);
    }

    public ReservationException(String msg, Exception ex) {
        super(msg, ex);
    }

}
