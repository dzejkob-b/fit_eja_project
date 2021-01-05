package fit.bikeja.rest;

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
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

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

        UserDto u2 = new UserDto();

        u2.setFirstName("Vaclav");
        u2.setSurName("Zapotocky");
        u2.setLoginName("vaclav");
        u2.setPassword("123456");

        // vytvoreni uzivatele

        given()
                .contentType(ContentType.JSON)
                .body(u1)
                .post("/rest/user")
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstName", equalTo(u1.getFirstName()))
                .body("surName", equalTo(u1.getSurName()))
                .body("loginName", equalTo(u1.getLoginName()));

        // vytvoreni dalsiho uzivatele

        given()
                .contentType(ContentType.JSON)
                .body(u2)
                .post("/rest/user")
                .then()
                .statusCode(200)
                .assertThat();

        // kontrola, zda jsou uzivatele vraceni

        given()
                .contentType(ContentType.JSON)
                .get("/rest/user")
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].firstName", equalTo(u1.getFirstName()))
                .body("[1].firstName", equalTo(u2.getFirstName()));

        // kontrola konkretniho uzivatele

        given()
                .contentType(ContentType.JSON)
                .get("/rest/user/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstName", equalTo(u2.getFirstName()));

        // kontrola dalsiho uzivatele

        given()
                .contentType(ContentType.JSON)
                .delete("/rest/user/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstName", equalTo(u1.getFirstName()));

        // smazani uzivatele

        given()
                .contentType(ContentType.JSON)
                .delete("/rest/user/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("firstName", equalTo(u2.getFirstName()));

        // kontrola ze neni zadny uzivatel

        given()
                .contentType(ContentType.JSON)
                .get("/rest/user")
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalTo("[]"));
    }
}
