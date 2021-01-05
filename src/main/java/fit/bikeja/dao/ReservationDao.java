package fit.bikeja.dao;

import fit.bikeja.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.QueryParam;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public interface ReservationDao extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.isValid = 1 AND r.item.id = ?1")
    public Optional<Reservation> getValidReservation(int item_id);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 AND r.item.id = ?2")
    public Collection<Reservation> getUserItemReservations(int user_id, int item_id);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 AND r.isValid = true")
    public Collection<Reservation> getUserActiveReservations(int user_id);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 AND r.isValid = false")
    public Collection<Reservation> getUserPastReservations(int user_id);

    @Query("SELECT r FROM Reservation r WHERE r.item.id = ?1")
    public Collection<Reservation> getItemReservations(int item_id);

    @Query("SELECT r FROM Reservation r WHERE r.item.id = ?1 AND r.isValid = true")
    public Collection<Reservation> getItemActiveReservations(int item_id);

    @Query("SELECT r FROM Reservation r WHERE r.item.id = ?1 AND r.isValid = false")
    public Collection<Reservation> getItemPastReservations(int item_id);

}
