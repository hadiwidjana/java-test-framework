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


@Page
// page_url = https://ecommerce-playground.lambdatest.io/
public class HomePage extends MainPageObject {


	@FindBy(xpath="//li[@class='nav-item dropdown dropdown-hoverable']//span[contains(text(),'My account')][@class='title']")
	private WebElement myAccountDd;
	@FindBy(xpath="//span[contains(text(),'Login')][@class='title']")
	private WebElement loginBtn;
    @FindBy(xpath = "//a[@data-toggle='mz-pure-drawer']/i")
    private WebElement profileIcon;





    public HomePage openWebsiteUrl(){
        driver.get(propertiesReader.getWebsiteUrl());
        return this;
    }



    @Step("Navigate to login page")
    public LoginPage navigateToLoginPage() {
        if (propertiesReader.getDevice().contains("android")){
            click(profileIcon);
        } else {
            hover(myAccountDd);
        }
        click(loginBtn);
        return loginPage;
    }

}