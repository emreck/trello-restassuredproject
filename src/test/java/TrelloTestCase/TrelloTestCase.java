package TrelloTestCase;

import TrelloTestBase.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TrelloTestCase extends TestBase {
    @Test(priority = 1)
    public void createList() {

        Response response =
                given()

                        .queryParam("name", "listeAdi1")
                        .queryParams(map)
                        .queryParam("idBoard", boardId)
                        .contentType(ContentType.JSON)
                        .log().all().
                        when()
                        .post("/lists");
        response.then()
                .statusCode(200)
                .body("name", equalTo("listeAdi1"));

        idList = (String) response.then()
                .extract().jsonPath().getMap("$").get("id");


    }

    @Test(priority = 2)
    public void createCard() {
        Response response1 =
                given()
                        .queryParams(map)
                        .queryParam("name", "cardCreate1")
                        .queryParam("idList", idList)
                        .contentType(ContentType.JSON)
                        .log().all().
                        when()
                        .post("/cards");

        response1.then()
                .statusCode(200);


        my_cardId.add((String) response1.then()
                .extract().jsonPath().getMap("$").get("id"));


        Response response2 =
                given()
                        .queryParams(map)
                        .queryParam("name", "cardCreate2")
                        .queryParam("idList", idList)
                        .contentType(ContentType.JSON)
                        .log().all().
                        when()
                        .post("/cards");

        response2.then()
                .statusCode(200);


        my_cardId.add((String) response2.then()
                .extract().jsonPath().getMap("$").get("id"));

    }

    @Test(priority = 3)
    public void updateCard() {
        int index = 0;

        for (int i = 0; i < my_cardId.size(); i++) {

            index = (int) (Math.random() * my_cardId.size());


        }
        given()
                .queryParams(map)
                .queryParam("name", "updatecardName")
                .contentType(ContentType.JSON)
                .log().all().
                when()
                .put("/cards/" + my_cardId.get(index)).
                then()
                .statusCode(200);

    }

    @Test(priority = 4)
    public void deleteCard() {
        for (int i = 0; i < my_cardId.size(); i++) {
            given()
                    .queryParams(map)
                    .contentType(ContentType.JSON)
                    .log().all().
                    when()
                    .delete("/cards/" + my_cardId.get(i)).
                    then()
                    .statusCode(200);
        }

    }

    @Test(priority = 5)
    public void deleteBoard() {
        given()
                .queryParams(map)
                .contentType(ContentType.JSON).log().all().
                when()
                .delete("/boards/" + boardId).
                then()
                .statusCode(200)
                .contentType(ContentType.JSON);

    }
}
