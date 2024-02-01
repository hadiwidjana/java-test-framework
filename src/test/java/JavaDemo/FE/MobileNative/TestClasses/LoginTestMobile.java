package JavaDemo.FE.MobileNative.TestClasses;

import JavaDemo.FE.MobileNative.PageObjects.MainPageObjectMobile;
import JavaDemo.Integrations.Listener.CustomListener;
import JavaDemo.Integrations.SpringBoot.LazyAutowired;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import com.epam.healenium.SelfHealingDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@SpringBootTest
@Listeners(CustomListener.class)
public class LoginTestMobile extends AbstractTestNGSpringContextTests {


    @Autowired
    public MainPageObjectMobile mainPageObjectMobile;
    @Autowired
    public PropertiesReader propertiesReader;

    @LazyAutowired
    public ApplicationContext applicationContext;

    @Test(description="Login test with valid email and password")
    public void loginTest(){
        mainPageObjectMobile.loginPageMobile.performLogin(propertiesReader.getUserEmail(),propertiesReader.getUserPassword());
    }

}
