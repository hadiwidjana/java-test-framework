package JavaDemo.FE.Web.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/login
public class LoginPage extends MainPageObject {

    @FindBy(xpath="//input[@name='email']")
    private WebElement emailField;
    @FindBy(xpath="//input[@name='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginBtn;




    @Step("Perform login")
    public AccountPage performLogin(String email, String password) {
        sendKeys(emailField,email);
        sendKeys(passwordField,password);
        click(loginBtn);
        return accountPage;
    }

}