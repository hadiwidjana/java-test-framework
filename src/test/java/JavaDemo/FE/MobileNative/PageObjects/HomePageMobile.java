package JavaDemo.FE.MobileNative.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/
public class HomePageMobile extends MainPageObjectMobile {


	@FindBy(xpath="//li[@class='nav-item dropdown dropdown-hoverable']//span[contains(text(),'My account')][@class='title']")
	private WebElement myAccountDd;
	@FindBy(xpath="//span[contains(text(),'Login')][@class='title']")
	private WebElement loginBtn;
    @FindBy(xpath = "//a[@data-toggle='mz-pure-drawer']/i")
    private WebElement profileIcon;





    public HomePageMobile openWebsiteUrl(){
        driver.get(propertiesReader.getWebsiteUrl());
        return this;
    }



    @Step("Navigate to login page")
    public LoginPageMobile navigateToLoginPage() {
            click(profileIcon);
        click(loginBtn);
        return loginPageMobile;
    }

}