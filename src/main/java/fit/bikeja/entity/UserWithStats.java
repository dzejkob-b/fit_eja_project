package fit.bikeja.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Immutable
public class UserWithStats implements Serializable {

    @Id
    @Column(updatable = false, nullable = false)
    private int id;

    @Column()
    private String loginName;

    @Column()
    private String password;

    @Column()
    private String firstName;

    @Column()
    private String surName;

    @Column()
    private int totalReservations;

    @Column()
    private int activeReservations;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getTotalReservations() {
        return this.totalReservations;
    }

    public void setTotalReservations(int totalReservations) {
        this.totalReservations = totalReservations;
    }

    public int getActiveReservations() {
        return this.activeReservations;
    }

    public void setActiveReservations(int activeReservations) {
        this.activeReservations = activeReservations;
    }
}
