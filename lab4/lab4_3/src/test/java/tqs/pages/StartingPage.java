package tqs.pages;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@ExtendWith(SeleniumJupiter.class)
public class StartingPage {
	
	private final String URL = "https://blazedemo.com/";

	private WebDriver driver;

	public StartingPage(WebDriver driver) {
		this.driver = driver;

		driver.get(URL);
		PageFactory.initElements(driver, this);
	}
}
