package JavaDemo.FE.Web.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Page
// page_url = https://www.saucedemo.com/
public class LoginPage extends MainPageObject {

    @FindBy(id="user-name")
    private WebElement usernameField;
    @FindBy(id="password")
    private WebElement passwordField;
    @FindBy(id = "login-button")
    private WebElement loginBtn;


    public LoginPage openWebsiteUrl(){
        driver.get(propertiesReader.getWebsiteUrl());
        return this;
    }


    @Step("Perform login")
    public HomePage performLogin(String email, String password) {
        sendKeys(usernameField,email);
        sendKeys(passwordField,password);
        click(loginBtn);
        return homePage;
    }

}