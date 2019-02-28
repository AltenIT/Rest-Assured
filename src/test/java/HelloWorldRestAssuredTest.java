import io.restassured.http.Cookie;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


class F1Test {

    @Test
    public void CheckFirstF1SeasonFirstRace() {
        given()
                .when()
                .get("http://ergast.com/api/f1/seasons.json")
                .then()
                .statusCode(200)
                .body("MRData.SeasonTable.Seasons[0].season", equalTo("1950" ));
    }


    @Test
    public void CheckFirstsAndSecondOfFirstRace2018() {
        given()
                .when()
                .get("http://ergast.com/api/f1/2018/1/results.json")
                .then()
                .statusCode(200)
                .body("MRData.RaceTable.Races[0].Results[0].Driver.driverId", equalTo("vettel" ))
                .and()
                .body("MRData.RaceTable.Races[0].Results[1].Driver.driverId", equalTo("hamilton" ));
    }

    @Test
    public void CheckAmountofPitStops() {

        String Winner = given().
                when().
                get("http://ergast.com/api/f1/2018/9/results.json")
                .path("MRData.RaceTable.Races[0].Results[0].Driver.driverId");

        given()
                .when()
                .get("http://ergast.com/api/f1/2018/9/drivers/"+Winner+"/pitstops.json")
                .then()
                .statusCode(200)
                .body("MRData.RaceTable.Races[0].PitStops.size()",equalTo(1));
    }

    @Test
    public void Addandremoveitemstocart() {

        String sessionID = given().when().get("http://localhost:8090/shoppingcart").andReturn().sessionId();

        Cookie myCookie = new Cookie.Builder("JSESSIONID", sessionID).
                setSecured(false).
                setDomain("localhost").
                setHttpOnly(false).
                setPath("/shoppingcart").build();

        given().when().cookie(myCookie).body("1 imported pills at 10.09").post("http://localhost:8090/shoppingcart/addcart").then().statusCode(200);
        given().when().cookie(myCookie).get("http://localhost:8090/shoppingcart/report").then().statusCode(200).body("lineItems.size()",equalTo(1));
    }
}