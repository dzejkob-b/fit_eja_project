package fit.bikeja.dto;

import fit.bikeja.entity.Reservation;

import javax.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

public class ReservationDto {

    private int id;
    private Date created;
    private boolean isValid;
    private int item_id;
    private int user_id;

    public ReservationDto() {
        this.created = new Date();
        this.isValid = true;
    }

    public ReservationDto(int item_id, int user_id) {
        this.created = new Date();
        this.isValid = true;
        this.item_id = item_id;
        this.user_id = user_id;
    }

    public ReservationDto(Reservation res) {
        this.id = res.getId();
        this.created = res.getCreated();
        this.isValid = res.getValid();
        this.item_id = res.getItem().getId();
        this.user_id = res.getUser().getId();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getItem_id() {
        return this.item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Reservation toEntity() {
        return new Reservation(this.created, this.isValid, null, null);
    }
}
