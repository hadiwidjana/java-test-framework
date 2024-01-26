package JavaDemo.FE.Web.PageObjects;

import JavaDemo.FE.MobileBaseMethod;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import JavaDemo.FE.WebBaseMethod;
import JavaDemo.Integrations.SpringBoot.LazyAutowired;
import JavaDemo.Integrations.SpringBoot.Page;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.support.PageFactory;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/login
public class MainPageObject extends WebBaseMethod {

    @PostConstruct
    public void initPageObject() {
        PageFactory.initElements(driver, this);
    }

    @LazyAutowired
    public PropertiesReader propertiesReader;
    @LazyAutowired
    public HomePage homePage;
    @LazyAutowired
    public LoginPage loginPage;
    @LazyAutowired
    public AccountPage accountPage;
    @LazyAutowired
    public EditAccountPage editAccountPage;

}