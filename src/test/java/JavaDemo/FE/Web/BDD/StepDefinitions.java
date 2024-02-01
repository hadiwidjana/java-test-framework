package JavaDemo.FE.Web.BDD;

import JavaDemo.FE.ExplicitWaiting;
import JavaDemo.FE.WebBaseMethod;
import JavaDemo.Integrations.SpringBoot.Page;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import com.epam.healenium.SelfHealingDriver;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import static JavaDemo.Integrations.Assertion.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest
public class StepDefinitions extends WebBaseMethod {

    @Autowired
    private PropertiesReader propertiesReader;

    @Autowired
    SelfHealingDriver driver;

    @Given("user is in login page")
    public void user_is_in_login_page() {
        driver.get(propertiesReader.getWebsiteUrl());
    }
    @When("user input valid email and valid password")
    public void user_input_valid_email_and_valid_password() {
        sendKeys(driver.findElement(By.id("user-name")),propertiesReader.getUserEmail());
        sendKeys(driver.findElement(By.id("password")),propertiesReader.getUserPassword());
    }
    @When("user click submit button")
    public void user_click_submit_button() {
        click(driver.findElement(By.id("login-button")));
    }
    @Then("user is redirected to Homepage")
    public void user_is_redirected_to_homepage() {
        assertEquals(driver.findElement(By.cssSelector("div[class='app_logo']")).getText(),"Swag Labs");
    }

}
