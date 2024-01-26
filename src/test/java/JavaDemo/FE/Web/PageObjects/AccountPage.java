package JavaDemo.FE.Web.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/login
public class AccountPage extends MainPageObject {

    @FindBy(xpath="//a[text()=' Edit your account information']")
    private WebElement editAccountBtn;

    @Step("Edit account")
    public EditAccountPage editAccount() {
        click(editAccountBtn);
        return editAccountPage;
    }

}