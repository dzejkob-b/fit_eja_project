package fit.bikeja.rest;

import fit.bikeja.dao.ItemDao;
import fit.bikeja.dao.UserDao;
import fit.bikeja.dto.ReservationDto;
import fit.bikeja.entity.Item;
import fit.bikeja.entity.User;
import fit.bikeja.service.DbService;
import fit.bikeja.service.ReservationService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTest {

    @Inject
    DbService dbService;

    @Inject
    ReservationService reservationService;

    @Inject
    UserDao userDao;

    @Inject
    ItemDao itemDao;

    @BeforeAll
    @Transactional
    public void setupData() {

        this.dbService.truncateAll();

        User u1 = new User();

        u1.setFirstName("Karel");
        u1.setSurName("Novák");
        u1.setLoginName("karel");
        u1.setPassword("123456");

        User u2 = new User();

        u2.setFirstName("Jitka");
        u2.setSurName("Holubová");
        u2.setLoginName("jitka");
        u2.setPassword("123456");

        this.userDao.save(u1);
        this.userDao.save(u2);

        Item i1 = new Item();

        i1.setCaption("hrníček");
        i1.setValue(200.0);
        i1.setCreatedBy(u1);

        Item i2 = new Item();

        i2.setCaption("konvička");
        i2.setValue(550.0);
        i2.setCreatedBy(u1);

        Item i3 = new Item();

        i3.setCaption("mobilní telefon");
        i3.setValue(5500.0);
        i3.setCreatedBy(u2);

        Item i4 = new Item();

        i4.setCaption("lamička");
        i4.setValue(885.0);
        i4.setCreatedBy(u2);

        this.itemDao.save(i1);
        this.itemDao.save(i2);
        this.itemDao.save(i3);
        this.itemDao.save(i4);
    }

    @Test
    public void test() {

        // jedna rezervace

        ReservationDto r1 = new ReservationDto();

        r1.setUser_id(1);
        r1.setItem_id(1);

        given()
                .contentType(ContentType.JSON)
                .body(r1)
                .post("/rest/reservation")
                .then()
                .statusCode(200)
                .assertThat()
                .body("item_id", equalTo(r1.getItem_id()))
                .body("user_id", equalTo(r1.getUser_id()))
                .body("valid", equalTo(r1.isValid()));


        // vice rezervaci zaroven

        ReservationDto r2, r3;

        Collection<ReservationDto> resList = List.of(
                r2 = new ReservationDto(2, 1),
                r3 = new ReservationDto(3, 1)
        );

        given()
                .contentType(ContentType.JSON)
                .body(resList)
                .post("/rest/reservation/list")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].item_id", equalTo(r2.getItem_id()))
                .body("[0].user_id", equalTo(r2.getUser_id()))
                .body("[0].valid", equalTo(r2.isValid()))
                .body("[1].item_id", equalTo(r3.getItem_id()))
                .body("[1].user_id", equalTo(r3.getUser_id()))
                .body("[1].valid", equalTo(r3.isValid()));


        // test jiz zadane rezervace

        ReservationDto r4  = new ReservationDto(2, 1);

        given()
                .contentType(ContentType.JSON)
                .body(r4)
                .post("/rest/reservation")
                .then()
                .statusCode(400)
                .assertThat()
                .body("errorCode", equalTo("BAD_REQUEST"))
                .body("msg", equalTo("Reservation of that item allready exists!"));


        // test update rezervace

        Optional<ReservationDto> r5 = this.reservationService.getReservation(1);

        r5.get().setItem_id(2);

        given()
                .contentType(ContentType.JSON)
                .body(r5.get())
                .put("/rest/reservation/1")
                .then()
                .statusCode(400)
                .assertThat()
                .body("errorCode", equalTo("BAD_REQUEST"))
                .body("msg", equalTo("Reservation of that item allready exists!"));


        // znevalidneni

        r5  = this.reservationService.getReservation(2);

        assertEquals(2, r5.get().getItem_id());

        r5.get().setValid(false);

        given()
                .contentType(ContentType.JSON)
                .body(r5.get())
                .put("/rest/reservation/2")
                .then()
                .statusCode(200)
                .assertThat();


        // test update rezervace - item 2 jiz lze rezervovat

        r5 = this.reservationService.getReservation(3);

        assertEquals(3, r5.get().getItem_id());

        r5.get().setItem_id(2);

        given()
                .contentType(ContentType.JSON)
                .body(r5.get())
                .put("/rest/reservation/3")
                .then()
                .statusCode(200)
                .assertThat()
                .body("item_id", equalTo(2));


        // aktivni rezervace

        given()
                .contentType(ContentType.JSON)
                .get("/rest/reservation/user_active/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].item_id", equalTo(1))
                .body("[0].valid", equalTo(true))
                .body("[1].item_id", equalTo(2))
                .body("[1].valid", equalTo(true));


        // historicke rezervace

        given()
                .contentType(ContentType.JSON)
                .get("/rest/reservation/user_past/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].item_id", equalTo(2))
                .body("[0].valid", equalTo(false));
    }
}
