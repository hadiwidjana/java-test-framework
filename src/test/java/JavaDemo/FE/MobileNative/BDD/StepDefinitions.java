package JavaDemo.FE.MobileNative.BDD;

import JavaDemo.FE.WebBaseMethod;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import com.epam.healenium.SelfHealingDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class StepDefinitions extends WebBaseMethod {

    @Autowired
    private PropertiesReader propertiesReader;

    @Autowired
    private SelfHealingDriver driver;

    @Given("user is in login page")
    public void user_is_in_login_page() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        hover(driver.findElement(By.xpath("//li[@class='nav-item dropdown dropdown-hoverable']//span[contains(text(),'My account')][@class='title']")));
        click(driver.findElement(By.xpath("//span[contains(text(),'Login')][@class='title']")));
    }
    @When("user input valid email and valid password")
    public void user_input_valid_email_and_valid_password() {
        sendKeys(driver.findElement(By.xpath("//input[@name='email']")),propertiesReader.getUserEmail());
        sendKeys(driver.findElement(By.xpath("//input[@name='password']")),propertiesReader.getUserPassword());
    }
    @When("user click submit button")
    public void user_click_submit_button() {
        click(driver.findElement(By.xpath("//input[@value='Login']")));
    }
    @Then("user is redirected to Homepage")
    public void user_is_redirected_to_homepage() {
    }
    @Then("user is logged in")
    public void user_is_logged_in() {
    }

}
