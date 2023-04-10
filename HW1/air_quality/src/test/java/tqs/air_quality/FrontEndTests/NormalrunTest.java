package tqs.air_quality.FrontEndTests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NormalrunTest {
    private WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void normalrun() {
        driver.get("http://localhost:8080/");
        driver.findElement(By.linkText("Cache Statistics")).click();
        int requests = Integer.parseInt(driver.findElement(By.id("requests")).getText());
        int hits = Integer.parseInt(driver.findElement(By.id("hits")).getText());
        int misses = Integer.parseInt(driver.findElement(By.id("misses")).getText());
        driver.findElement(By.linkText("Current")).click();
        assertThat(driver.getTitle(), is("Air Quality"));
        driver.findElement(By.id("city_current")).click();
        driver.findElement(By.id("city_current")).sendKeys("Castelo Branco");
        driver.findElement(By.id("current_btn")).click();
        assertThat(driver.findElement(By.cssSelector("p:nth-child(1)")).getText(), is("Castelo Branco, PT"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(2)")).getText(), is("lat: 39.82219, long: -7.49087"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
        assertThat(driver.findElement(By.cssSelector("table > tr > td:nth-child(1)")).getText(), is("20"));
        assertThat(driver.findElement(By.cssSelector("table > tr > td:nth-child(2)")).getText(), is("43.272972"));
        assertThat(driver.findElement(By.cssSelector("table > tr > td:nth-child(3)")).getText(), is("0.17695129"));
        assertThat(driver.findElement(By.cssSelector("td:nth-child(4)")).getText(), is("0.04551839"));
        assertThat(driver.findElement(By.cssSelector("td:nth-child(5)")).getText(), is("223.63663"));
        assertThat(driver.findElement(By.cssSelector("td:nth-child(6)")).getText(), is("18.618053"));
        assertThat(driver.findElement(By.cssSelector("td:nth-child(7)")).getText(), is("3.2014515"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(1) > td")).getText(), is("1"));
        assertThat(driver.findElement(By.cssSelector("table:nth-child(5) tr:nth-child(2) > td")).getText(), is("1"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(3) > td")).getText(), is("1"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(4) > td")).getText(), is("1"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(5) > td")).getText(), is("Molds"));
        driver.findElement(By.linkText("Forecast")).click();
        driver.findElement(By.id("city_forecast")).click();
        driver.findElement(By.id("city_forecast")).sendKeys("Castelo Branco");
        driver.findElement(By.id("forecast_btn")).click();
        {
            WebElement element = driver.findElement(By.id("forecast_btn"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        assertThat(driver.findElement(By.cssSelector("p:nth-child(1)")).getText(), is("Castelo Branco, PT"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(2)")).getText(), is("lat: 39.82219, long: -7.49087"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(1)")).getText(), is("20"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(2)")).getText(),
                is("43.3"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(3)")).getText(),
                is("0.2"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(4)")).getText(), is("0"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(5)")).getText(), is("223.6"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(6)")).getText(), is("18.6"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(7)")).getText(), is("3.2"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(8)")).getText(),
                is("17h00, 10/04/2023"));
        driver.findElement(By.linkText("History")).click();
        driver.findElement(By.id("city_history")).click();
        driver.findElement(By.id("city_history")).sendKeys("Castelo Branco");
        driver.findElement(By.id("history_btn")).click();
        {
            WebElement element = driver.findElement(By.id("history_btn"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        assertThat(driver.findElement(By.cssSelector("p:nth-child(1)")).getText(), is("Castelo Branco, PT"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(2)")).getText(), is("lat: 39.82219, long: -7.49087"));
        assertThat(driver.findElement(By.cssSelector("p:nth-child(3)")).getText(), is("Source: WeatherBit"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(1)")).getText(), is("55"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(2)")).getText(),
                is("118"));
        assertThat(driver.findElement(By.cssSelector("table > tr:nth-child(2) > td:nth-child(3)")).getText(), is("2"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(4)")).getText(), is("0"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(5)")).getText(), is("73.3"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(6)")).getText(), is("4"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(7)")).getText(), is("3"));
        assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(8)")).getText(),
                is("17h00, 07/04/2023"));
        driver.findElement(By.linkText("Cache Statistics")).click();
        driver.findElement(By.linkText("History")).click();
        driver.findElement(By.id("history_btn")).click();
        driver.findElement(By.linkText("Cache Statistics")).click();
        assertThat(driver.findElement(By.id("requests")).getText(), is(requests + 4));
        assertThat(driver.findElement(By.id("hits")).getText(), is(hits + 1));
        assertThat(driver.findElement(By.id("misses")).getText(), is(misses + 3));
    }
}
