package fit.bikeja.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Je třeba zadat přihlašovací jméno!")
    @Length(min = 4, max = 20, message = "Přihlašovací jméno musí být dlouhé 4 až 20 znaků!")
    @Pattern(regexp = "([a-zA-Z]{1}[a-zA-Z0-9]*$)", message = "Přihlašovací jméno se může skládat pouze z alfanumerických znaků (nesmí začínat číslem)")
    private String loginName;

    @Column(nullable = false)
    @NotBlank(message = "Je třeba zadat heslo!")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Je třeba zadat jméno!")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Je třeba zadat příjmení!")
    private String surName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Reservation> reservations = new ArrayList<>();

    public User() {
    }

    public User(String loginName, String password, String firstName, String surName) {
        this.loginName = loginName;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
    }

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

    public Collection<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
