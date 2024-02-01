package JavaDemo.FE.MobileNative.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/login
public class LoginPageMobile extends MainPageObjectMobile {

    @AndroidFindBy(xpath="//android.widget.EditText[@content-desc=\"test-Username\"]")
    private WebElement usernameField;
    @AndroidFindBy(xpath="//android.widget.EditText[@content-desc=\"test-Password\"]")
    private WebElement passwordField;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]")
    private WebElement loginBtn;




    @Step("Perform login")
    public HomePageMobile performLogin(String email, String password) {
        sendKeys(usernameField,email);
        sendKeys(passwordField,password);
        click(loginBtn);
        return homePageMobile;
    }

}