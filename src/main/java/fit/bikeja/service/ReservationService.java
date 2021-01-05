package fit.bikeja.service;

import fit.bikeja.dao.ItemDao;
import fit.bikeja.dao.ReservationDao;
import fit.bikeja.dao.UserDao;
import fit.bikeja.dto.ReservationDto;
import fit.bikeja.entity.Reservation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    ReservationDao reservationDao;

    @Inject
    ItemDao itemDao;

    @Inject
    UserDao userDao;

    @PostConstruct
    public void onInit() {
    }

    public Optional<ReservationDto> getReservation(int id) {
        Optional<ReservationDto> res = Optional.empty();

        try {
            res = Optional.of(new ReservationDto(this.reservationDao.getOne(id)));
        } catch (EntityNotFoundException ignored) {
        }

        return res;
    }

    public Reservation createReservation(ReservationDto res) throws ReservationException {
        Reservation r = res.toEntity();

        r.setUser(this.userDao.getOne(res.getUser_id()));
        r.setItem(this.itemDao.getOne(res.getItem_id()));

        if (this.testMayCreateReservation(res)) {
            this.reservationDao.save(r);
            return r;

        } else {
            throw new ReservationException("Platná rezervace tohoto předmětu je již zadaná!");
        }
    }

    public void updateReservation(int id, ReservationDto res) throws ReservationException {
        if (this.testMayCreateReservation(res, id)) {
            Reservation r = this.reservationDao.getOne(id);

            // aktualizace rezervace je mozna pouze validni ano/ne
            r.setValid(res.isValid());
            r.setItem(this.itemDao.getOne(res.getItem_id()));

            this.reservationDao.save(r);

        } else {
            throw new ReservationException("Platná rezervace tohoto předmětu je již zadaná!");
        }
    }

    public boolean testMayCreateReservation(ReservationDto res) {
        return !this.reservationDao.getValidReservation(res.getItem_id()).isPresent();
    }

    public boolean testMayCreateReservation(ReservationDto res, int exceptResId) {
        Optional<Reservation> r = this.reservationDao.getValidReservation(res.getItem_id());
        return !r.isPresent() || r.get().getId() == exceptResId;
    }

    public void deleteReservation(int id) {
        this.reservationDao.deleteById(id);
    }

    public Collection<ReservationDto> getAllReservations() {
        return this.reservationDao.findAll().stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getUserItemReservations(int user_id, int item_id) {
        return this.reservationDao.getUserItemReservations(user_id, item_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getUserActiveReservations(int user_id) {
        return this.reservationDao.getUserActiveReservations(user_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getUserPastReservations(int user_id) {
        return this.reservationDao.getUserPastReservations(user_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getItemReservations(int item_id) {
        return this.reservationDao.getItemReservations(item_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getItemActiveReservations(int item_id) {
        return this.reservationDao.getItemActiveReservations(item_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }

    public Collection<ReservationDto> getItemPastReservations(int item_id) {
        return this.reservationDao.getItemPastReservations(item_id).stream().map(ReservationDto::new).collect(Collectors.toList());
    }
}
