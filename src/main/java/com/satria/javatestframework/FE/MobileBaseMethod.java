package com.satria.javatestframework.FE;

import com.satria.javatestframework.utils.Logger.Log;
import com.satria.javatestframework.utils.SpringAnnotations.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidStopScreenRecordingOptions;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.qameta.allure.Attachment;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.time.Duration;
import java.util.Base64;

import static com.satria.javatestframework.utils.AllureReport.AllureAttachments.fileToBytes;
import static com.satria.javatestframework.utils.AllureReport.AllureAttachments.deviceName;

@BaseTest
public class MobileBaseMethod {

    @Autowired
    private WebDriver driver;

    @Value("${device}")
    private String device;
    private AppiumDriver appiumDriver;
    private static AppiumDriver staticDriver;


    @PostConstruct
    public void initMobileBaseMethod() {
        appiumDriver = (AppiumDriver) driver;
        staticDriver = appiumDriver;
    }


    protected static ThreadLocal<String> platform = new ThreadLocal<String>();

    public String getPlatform() {
        return platform.get();
    }

    public static final long WAIT = 10;

    private Point start;
    private Point end;
    private boolean isDisplayed;


    //wait for visibility
    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    //clear element
    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void hideKeyboard() {
        if (device.equals("android")) {
            AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
            androidDriver.hideKeyboard();
        }
    }

    //click
    public void click(WebElement element) {
        waitForVisibility(element);
        try {
            screenshotElement(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
        element.click();
    }


    //send key or text
    public void sendKeys(WebElement element, String txt) {
        waitForVisibility(element);
        try {
            screenshotElement(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
        element.sendKeys(txt);
    }

    //get attribute
    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    //get text
    public String getText(WebElement e, String msg) {
        String txt = null;
        switch (getPlatform()) {
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
        }
        return txt;
    }

    public void scrollDown() {

        Dimension dimension = appiumDriver.manage().window().getSize();
        start = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.8));
        end = new Point((int) (dimension.width * 0.2), (int) (dimension.height * 0.1));
        MobileW3cActions.doSwipe(appiumDriver, start, end, 1000);  //with duration 1s

    }

    public void scrollUp() {

        Dimension dimension = appiumDriver.manage().window().getSize();
        start = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.8));
        end = new Point((int) (dimension.width * 0.2), (int) (dimension.height * 0.1));
        MobileW3cActions.doSwipe(appiumDriver, end, start, 1000);  //with duration 1s
    }

    public void scrollToElement(WebElement e, String direction) {
        for (int i = 0; i < 10; i++) {
            if (isElementDisplayed(e)) {
                break;
            } else {
                if (direction.equalsIgnoreCase("up")) {
                    scrollUp();
                } else {
                    scrollDown();
                }
            }
        }
    }

    public boolean isElementDisplayed(WebElement e) {
        new WebDriverWait(appiumDriver, Duration.ofMillis(500));
        try {
            WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofMillis(500));
            return wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver appiumDriver) {
                    if (e.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception ex) {
            return false;
        }
    }

    @Attachment(value = "Attachment of Element {0}", type = "image/png")
    public byte[] screenshotElement(WebElement element) throws Exception {
        String srcFile = element.getScreenshotAs(OutputType.BASE64).replaceAll("\n", "");
        return Base64.getDecoder().decode(srcFile);
    }

    @Attachment(value = "Attachment of Page Screenshot", type = "image/png")
    public byte[] screenshotPage() throws Exception {
        File srcFile = ((AppiumDriver) driver).getScreenshotAs(OutputType.FILE);
        return fileToBytes(srcFile.getPath());
    }

    public static void startRecordingMobile() {
        BaseStartScreenRecordingOptions options;
        if (deviceName.toLowerCase().equals("android")) {
            options = new AndroidStartScreenRecordingOptions();
        } else {
            options = new IOSStartScreenRecordingOptions();
        }
        try {
            ((CanRecordScreen) staticDriver).startRecordingScreen(options);
        } catch (Exception e) {
            Log.warn("Could not start recording screen on mobile platform: " + e.getMessage());
        }
        Log.info("Mobile recording started");
    }

    @Attachment(value = "Attachment of Mobile Video", type = "video/mp4", fileExtension = ".mp4")
    public static byte[] stopRecordingMobile(boolean enable) {
        BaseStopScreenRecordingOptions options;
        byte[] recording;
        if (deviceName.toLowerCase().equals("android")) {
            options = new AndroidStopScreenRecordingOptions();
        } else {
            options = new IOSStopScreenRecordingOptions();
        }
        try {
            if (enable) return Base64.getDecoder().decode(((CanRecordScreen) staticDriver).stopRecordingScreen(options));
        } catch (Exception e) {
            Log.warn("Could not stop recording screen on mobile platform: " + e.getMessage());
        }
        return null;
    }

    protected void takePhoto() {
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.CAMERA));
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.COMMA));
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.TAB));
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.SPACE));
    }

}
