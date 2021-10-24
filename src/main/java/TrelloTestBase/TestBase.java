package TrelloTestBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestBase {
    public String boardId;
    public String idList;
    public String boardName = "trelloBoardName";

    public static HashMap map = new HashMap<>();
    public ArrayList<String> my_cardId = new ArrayList<String>();

    @BeforeSuite
    public void setup() {

        map.put("key", "keyValue");//Will be enter key value
        map.put("token", "TokenValue");//Will be enter token value


        RestAssured.baseURI = "https://api.trello.com/1";

    }

    @BeforeClass
    public void createBoard() {

        Response response =
                given()
                        .queryParam("name", boardName)
                        .queryParams(map)
                        .contentType(ContentType.JSON)
                        .log().all().
                        when()
                        .post("/boards");

        response.then()
                .statusCode(200)
                .extract().body().jsonPath().get("name").equals("trelloBoardName");

        boardId = (String) response.then()
                .extract().jsonPath().getMap("$").get("id");

    }

}
