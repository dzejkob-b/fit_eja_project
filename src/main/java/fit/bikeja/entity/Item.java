package fit.bikeja.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Je třeba zadat název předmětu!")
    private String caption;

    @Column(nullable = false)
    private Double value;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Collection<Reservation> reservations = new ArrayList<>();

    public Item() {
    }

    public Item(String caption, Double value, User createdBy) {
        this.caption = caption;
        this.value = value;
        this.createdBy = createdBy;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Collection<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
