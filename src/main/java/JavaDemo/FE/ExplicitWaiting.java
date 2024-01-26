package JavaDemo.FE;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import com.epam.healenium.SelfHealingDriver;
import io.appium.java_client.android.AndroidDriver;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

@BaseTest
public class ExplicitWaiting {

    @Autowired
    public SelfHealingDriver driver;
    public static SelfHealingDriver staticDriver;
    public static AndroidDriver androidDriver;


    @PostConstruct
    private void initExplicitWaiting() {
        staticDriver = driver;
        try{
            androidDriver = (AndroidDriver) driver.getDelegate();
        } catch (ClassCastException e){

        }
    }

    /**
     * Implicitly wait
     *
     */
    public void implicitWait(int milliSeconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(milliSeconds));
    }


    public void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * To Wait Until Element to be Clickable
     */
    public void explicitWaitElementToBeClickable(WebElement element, int milliSeconds) {
        for (int i = 1; i <= 5; i++) {
            try {
                WebDriverWait clickableWait = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
                clickableWait.until(ExpectedConditions.elementToBeClickable(element));
                break;
            } catch (TimeoutException e) {
                Log.infoYellow("Error! System cannot do the action.");
                if (i == 5) e.printStackTrace();
            }
        }
    }


    /**
     * To Wait Until Element to be Selectable
     */
    public void explicitWaitElementToBeSelected(WebElement element, int milliSeconds) {
        WebDriverWait selectableWait = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        selectableWait.until(ExpectedConditions.elementToBeSelected(element));
    }


    /**
     * To Wait Until Element has the text
     */
    public void explicitWaitTextToBePresentInElement(WebElement element, int milliSeconds, String text) {
        WebDriverWait textToBePresent = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        textToBePresent.until(ExpectedConditions.textToBePresentInElement(element, text));
    }


    /**
     * To Wait Until Title contains the text
     */
    public void explicitWaitTitleContains(WebElement element, int milliSeconds, String title) {
        WebDriverWait titleContains = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        titleContains.until(ExpectedConditions.titleContains(title));
    }


    /**
     * To Wait Until Title is
     */
    public void explicitWaitTitleIs(WebElement element, int milliSeconds, String title) {
        WebDriverWait titleIs = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        titleIs.until(ExpectedConditions.titleIs(title));
    }


    /**
     * To Wait Until Element to be Visible
     */
    public static boolean explicitWaitVisibilityOfElement(WebElement element, int milliSeconds) {
        for (int i = 1; i <= 5; i++) {
            try {
                WebDriverWait elementToBeVisible = new WebDriverWait(staticDriver, Duration.ofMillis(milliSeconds));
                elementToBeVisible.until(ExpectedConditions.visibilityOf(element));
                return true;
            } catch (TimeoutException e) {
                Log.infoYellow("Error! System cannot detect the element.");
                if (i == 5) e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * To Wait Until Element is Selected
     */
    public void explicitWaitSelectionStateToBe(WebElement element, int milliSeconds, boolean selected) {
        WebDriverWait elementIsSelected = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        elementIsSelected.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
    }


    /**
     * To Wait Until Elements to be Visible
     */
    public void explicitWaitVisibilityOfElements(List<WebElement> element, int milliSeconds) {
        WebDriverWait elementsToBeVisible = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        elementsToBeVisible.until(ExpectedConditions.visibilityOfAllElements(element));
    }


    /*Select using By Method*/

    /**
     * To Wait Until Element to be Clickable
     */
    public void explicitWaitElementToBeClickable(By element, int milliSeconds) {
        WebDriverWait clickableWait = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        clickableWait.until(ExpectedConditions.elementToBeClickable(element));
    }


    /**
     * To Wait Until Element to be Selectable
     */
    public void explicitWaitElementToBeSelected(By element, int milliSeconds) {
        WebDriverWait selectableWait = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        selectableWait.until(ExpectedConditions.elementToBeSelected(element));
    }


    /**
     * To Wait Until Title contains the text
     */
    public void explicitWaitTitleContains(By element, int milliSeconds, String title) {
        WebDriverWait titleContains = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        titleContains.until(ExpectedConditions.titleContains(title));
    }


    /**
     * To Wait Until Title is
     */
    public void explicitWaitTitleIs(By element, int milliSeconds, String title) {
        WebDriverWait titleIs = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        titleIs.until(ExpectedConditions.titleIs(title));
    }


    /**
     * To Wait Until Element to be Visible
     */
    public void explicitWaitVisibilityOfElement(By element, int milliSeconds) {
        WebDriverWait elementToBeVisible = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        elementToBeVisible.until(ExpectedConditions.visibilityOfElementLocated(element));
    }


    /**
     * To Wait Until Element is Selected
     */
    public void explicitWaitSelectionStateToBe(By element, int milliSeconds, boolean selected) {
        WebDriverWait elementToBeVisible = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        elementToBeVisible.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
    }


    /**
     * To Wait for the Alert
     */
    public void explicitWaitForAlert(int milliSeconds) {
        WebDriverWait waitForAlert = new WebDriverWait(driver, Duration.ofMillis(milliSeconds));
        waitForAlert.until(ExpectedConditions.alertIsPresent());
    }
}