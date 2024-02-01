package JavaDemo.FE.Web.PageObjects;

import JavaDemo.FE.WebBaseMethod;
import JavaDemo.Integrations.SpringBoot.LazyAutowired;
import JavaDemo.Integrations.SpringBoot.Page;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import com.epam.healenium.SelfHealingDriver;
import io.qameta.allure.Step;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static JavaDemo.Integrations.Assertion.Assertions.assertEquals;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/
public class HomePage extends MainPageObject {
    @FindBy(css = "div[class='app_logo']")
    public WebElement divSwagLabs;

    
    
    
    
    @Step("Verify that login successful")
    public HomePage verifyLoginSuccess(){
        assertEquals(divSwagLabs.getText(),"Swag Labs");
        return this;
    }

}