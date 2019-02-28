import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


class F1Test {

     @Test
     public void CheckFirstF1Season() {
         given()
                 .when()
                 .get("http://ergast.com/api/f1/seasons.json")
                 .then()
                 .statusCode(200)
                 .body("MRData.SeasonTable.Seasons[0].season", equalTo("1950" ));
     }
}