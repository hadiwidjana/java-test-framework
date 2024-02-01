package JavaDemo.FE.MobileNative.PageObjects;

import JavaDemo.FE.MobileBaseMethod;
import JavaDemo.FE.WebBaseMethod;
import JavaDemo.Integrations.SpringBoot.LazyAutowired;
import JavaDemo.Integrations.SpringBoot.Page;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;


@Page
// page_url = https://ecommerce-playground.lambdatest.io/index.php?route=account/login
public class MainPageObjectMobile extends MobileBaseMethod {

    @PostConstruct
    public void initPageObject() {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver, Duration.ofSeconds(5)),this);
    }

    @LazyAutowired
    public PropertiesReader propertiesReader;
    @LazyAutowired
    public HomePageMobile homePageMobile;
    @LazyAutowired
    public LoginPageMobile loginPageMobile;


}