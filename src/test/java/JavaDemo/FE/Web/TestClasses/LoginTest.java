package JavaDemo.FE.Web.TestClasses;

import JavaDemo.FE.Web.PageObjects.HomePage;
import JavaDemo.Integrations.Listener.CustomListener;
import JavaDemo.Integrations.SpringBoot.LazyAutowired;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import com.epam.healenium.SelfHealingDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@SpringBootTest
@Listeners(CustomListener.class)
public class LoginTest extends AbstractTestNGSpringContextTests {


    @Autowired
    public HomePage homePage;
    @Autowired
    public PropertiesReader propertiesReader;

    @LazyAutowired
    public ApplicationContext applicationContext;

//    @AfterTest
//    public void teardown() {
//        this.applicationContext
//                .getBean(AndroidDriver.class)
//                .quit();
//    }
    @Test(description="Login test with valid email and password")
    public void loginTest(){
        homePage.openWebsiteUrl()
                .navigateToLoginPage()
                .performLogin(propertiesReader.getUserEmail(),propertiesReader.getUserPassword())
                .editAccount()
                .verifyAccountInfo();
    }

}
