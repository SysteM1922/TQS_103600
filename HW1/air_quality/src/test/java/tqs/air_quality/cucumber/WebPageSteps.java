package tqs.air_quality.cucumber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.hamcrest.collection.IsIn;
import org.hamcrest.core.IsInstanceOf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebPageSteps {

    private WebDriver driver;

    @Given("I access the {string} url")
    public void i_access_the_url(String url) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String link) {
        driver.findElement(By.linkText(link)).click();
        if (link.equals("Cache Statistics")) {
            {
                WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
                wait.until(ExpectedConditions.textMatches(By.id("requests"), Pattern.compile("^((?!N/A).)*$")));
            }
        }
    }

    @And("I search for the {string} data for the city {string}")
    public void i_search_for_the_data_in_city(String data, String city) {
        driver.findElement(By.linkText(data)).click();
        driver.findElement(By.id("city_" + data.toLowerCase())).sendKeys(city);
        driver.findElement(By.id(data.toLowerCase() + "_btn")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results")));

            wait.until(ExpectedConditions
                    .not(ExpectedConditions.attributeContains(element, "class", "display: none")));
        }
    }

    @Then("I should see the {string} data for the city {string}")
    public void i_should_see_the_data(String data, String city) {
        String ct;
        switch (data) {
            case "Current":
                ct = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
                assertThat(ct, containsString(city));
                assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
                String aqi = driver.findElement(By.cssSelector("table > tr > td:nth-child(1)")).getText();
                assertThat(Integer.parseInt(aqi), IsInstanceOf.instanceOf(Integer.class));
                String o3 = driver.findElement(By.cssSelector("table > tr > td:nth-child(2)")).getText();
                assertThat(Double.parseDouble(o3), IsInstanceOf.instanceOf(Double.class));
                String so2 = driver.findElement(By.cssSelector("table > tr > td:nth-child(3)")).getText();
                assertThat(Double.parseDouble(so2), IsInstanceOf.instanceOf(Double.class));
                String no2 = driver.findElement(By.cssSelector("td:nth-child(4)")).getText();
                assertThat(Double.parseDouble(no2), IsInstanceOf.instanceOf(Double.class));
                String co = driver.findElement(By.cssSelector("td:nth-child(5)")).getText();
                assertThat(Double.parseDouble(co), IsInstanceOf.instanceOf(Double.class));
                String pm10 = driver.findElement(By.cssSelector("td:nth-child(6)")).getText();
                assertThat(Double.parseDouble(pm10), IsInstanceOf.instanceOf(Double.class));
                String pm25 = driver.findElement(By.cssSelector("td:nth-child(7)")).getText();
                assertThat(Double.parseDouble(pm25), IsInstanceOf.instanceOf(Double.class));
                String moldLevel = driver.findElement(By.cssSelector("table:nth-child(5) tr:nth-child(2) > td"))
                        .getText();
                assertThat(Integer.parseInt(moldLevel), IsInstanceOf.instanceOf(Integer.class));
                String PollenLevelGrass = driver.findElement(By.cssSelector("tr:nth-child(3) > td")).getText();
                assertThat(Integer.parseInt(PollenLevelGrass), IsInstanceOf.instanceOf(Integer.class));
                String PollenLevelWeed = driver.findElement(By.cssSelector("tr:nth-child(4) > td")).getText();
                assertThat(Integer.parseInt(PollenLevelWeed), IsInstanceOf.instanceOf(Integer.class));
                String PollenLevelTree = driver.findElement(By.cssSelector("tr:nth-child(4) > td")).getText();
                assertThat(Integer.parseInt(PollenLevelTree), IsInstanceOf.instanceOf(Integer.class));
                String PredominantPollenType = driver.findElement(By.cssSelector("tr:nth-child(5) > td")).getText();
                assertThat(PredominantPollenType, IsIn.in(Arrays.asList("Molds", "Grasses", "Weeds", "Trees")));
                break;

            case "Forecast":
                ct = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
                assertThat(ct, containsString(city));
                assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
                aqi = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(1)")).getText();
                assertThat(Integer.parseInt(aqi), IsInstanceOf.instanceOf(Integer.class));
                o3 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(2)")).getText();
                assertThat(Double.parseDouble(o3), IsInstanceOf.instanceOf(Double.class));
                so2 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(3)")).getText();
                assertThat(Double.parseDouble(so2), IsInstanceOf.instanceOf(Double.class));
                no2 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(4)")).getText();
                assertThat(Double.parseDouble(no2), IsInstanceOf.instanceOf(Double.class));
                co = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(5)")).getText();
                assertThat(Double.parseDouble(co), IsInstanceOf.instanceOf(Double.class));
                pm10 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(6)")).getText();
                assertThat(Double.parseDouble(pm10), IsInstanceOf.instanceOf(Double.class));
                pm25 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(7)")).getText();
                assertThat(Double.parseDouble(pm25), IsInstanceOf.instanceOf(Double.class));
                String timestamp = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(8)"))
                        .getText();
                assertThat(timestamp, IsInstanceOf.instanceOf(String.class));
                break;

            case "History":
                ct = driver.findElement(By.cssSelector("p:nth-child(1)")).getText();
                assertThat(ct, containsString(city));
                assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
                aqi = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(1)")).getText();
                assertThat(Integer.parseInt(aqi), IsInstanceOf.instanceOf(Integer.class));
                o3 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(2)")).getText();
                assertThat(Double.parseDouble(o3), IsInstanceOf.instanceOf(Double.class));
                so2 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(3)")).getText();
                assertThat(Double.parseDouble(so2), IsInstanceOf.instanceOf(Double.class));
                no2 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(4)")).getText();
                assertThat(Double.parseDouble(no2), IsInstanceOf.instanceOf(Double.class));
                co = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(5)")).getText();
                assertThat(Double.parseDouble(co), IsInstanceOf.instanceOf(Double.class));
                pm10 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(6)")).getText();
                assertThat(Double.parseDouble(pm10), IsInstanceOf.instanceOf(Double.class));
                pm25 = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(7)")).getText();
                assertThat(Double.parseDouble(pm25), IsInstanceOf.instanceOf(Double.class));
                timestamp = driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(8)")).getText();
                assertThat(timestamp, IsInstanceOf.instanceOf(String.class));
                break;
        }
    }

    @Then("I should see the Cache Statistics data")
    public void i_should_see_the_cache_data() {
        String requests = driver.findElement(By.id("requests")).getText();
        String hits = driver.findElement(By.id("hits")).getText();
        String misses = driver.findElement(By.id("misses")).getText();
        assertThat(Integer.parseInt(requests), IsInstanceOf.instanceOf(Integer.class));
        assertThat(Integer.parseInt(hits), IsInstanceOf.instanceOf(Integer.class));
        assertThat(Integer.parseInt(misses), IsInstanceOf.instanceOf(Integer.class));
    }

    @Then("I quit the browser")
    public void i_quit_the_browser() {
        driver.quit();
    }
}
