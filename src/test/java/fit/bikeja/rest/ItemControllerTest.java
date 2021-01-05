package fit.bikeja.rest;

import fit.bikeja.dto.ItemDto;
import fit.bikeja.dto.UserDto;
import fit.bikeja.service.DbService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {

    @Inject
    DbService dbService;

    @BeforeAll
    public void setupData() {
        this.dbService.truncateAll();
    }

    @Test
    public void test() {
        UserDto u1 = new UserDto();

        u1.setFirstName("Karel");
        u1.setSurName("Novak");
        u1.setLoginName("karel");
        u1.setPassword("123456");

        // nejdrive vytvorit uzivatele

        given()
                .contentType(ContentType.JSON)
                .body(u1)
                .post("/rest/user")
                .then()
                .statusCode(200)
                .assertThat();

        ItemDto i1 = new ItemDto();

        i1.setCaption("hrníček");
        i1.setValue(100.0);
        i1.setCreatedBy_id(1);

        // pak predmet

        given()
                .contentType(ContentType.JSON)
                .body(i1)
                .post("/rest/item")
                .then()
                .statusCode(200)
                .assertThat()
                .body("caption", equalTo(i1.getCaption()))
                .body("createdBy_id", equalTo(i1.getCreatedBy_id()));

        // je tam?

        given()
                .contentType(ContentType.JSON)
                .get("/rest/item")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].caption", equalTo(i1.getCaption()))
                .body("[0].createdBy_id", equalTo(i1.getCreatedBy_id()));

        // update

        i1.setCaption("plechovka");

        given()
                .contentType(ContentType.JSON)
                .body(i1)
                .put("/rest/item/1")
                .then()
                .statusCode(200)
                .assertThat();

        // je zmena?

        given()
                .contentType(ContentType.JSON)
                .get("/rest/item/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("caption", equalTo("plechovka"));

        // vymaz

        given()
                .contentType(ContentType.JSON)
                .delete("/rest/item/1")
                .then()
                .statusCode(200)
                .assertThat();

        // nic nezbylo

        given()
                .contentType(ContentType.JSON)
                .get("/rest/item")
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalTo("[]"));
    }

}
