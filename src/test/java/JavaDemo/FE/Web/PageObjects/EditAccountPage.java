package JavaDemo.FE.Web.PageObjects;

import JavaDemo.Integrations.SpringBoot.Page;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static JavaDemo.Integrations.Assertion.Assertions.assertEquals;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/edit
public class EditAccountPage extends MainPageObject {

    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement firstNameField;
    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement lastNameField;
    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@name='telephone']")
    private WebElement telephoneField;

    @Step("Perform login")
    public EditAccountPage verifyAccountInfo() {
        assertEquals(firstNameField.getAttribute("value"), propertiesReader.getUserFirstName());
        return this;
    }

}