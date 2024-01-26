package JavaDemo.FE;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import JavaDemo.Integrations.AllureReport.AllureAttachments;
import com.epam.healenium.SelfHealingDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidStopScreenRecordingOptions;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
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
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;


@BaseTest
public class MobileBaseMethod extends ExplicitWaiting{

    @Value("${device}")
    private String device;

    protected static ThreadLocal<String> platform = new ThreadLocal<String>();

    public String getPlatform() {
        return platform.get();
    }

    public static final long WAIT = 10;

    private Point start;
    private Point end;
    private boolean isDisplayed;
    private WebDriverWait wait;

    //clear element
    public void clear(WebElement element) {
        explicitWaitVisibilityOfElement(element,5000);
        element.clear();
    }

    public void hideKeyboard() {
        if (device.equals("android")) {
            androidDriver.hideKeyboard();
        }
    }

    //click
    public void click(WebElement element) {
        explicitWaitVisibilityOfElement(element,5000);
        screenshotElementMobile(element);
        element.click();
    }

    public void click(int waitBefore, WebElement element, int waitAfter) {
        try {
            wait(waitBefore * 1000);
            click(element);
            Thread.sleep(waitAfter * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void click(int waitBefore, WebElement element) {
        try {
            Thread.sleep(waitBefore * 1000);
            click(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void click(WebElement element, int waitAfter) {
        try {
            click(element);
            Thread.sleep(waitAfter * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //send key or text
    public void sendKeys(WebElement element, String text) {
        explicitWaitVisibilityOfElement(element,5000);
        screenshotElementMobile(element);
        element.clear();
        element.sendKeys(text);
    }

    public void sendKeys(int waitBefore, WebElement element, String text, int waitAfter) {
        try {
            Thread.sleep(waitBefore * 1000);
            sendKeys(element, text);
            Thread.sleep(waitAfter * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(WebElement element, String text, int waitAfter) {
        try {
            sendKeys(element, text);
            Thread.sleep(waitAfter * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(int waitBefore, WebElement element, String text) {
        try {
            Thread.sleep(waitBefore * 1000);
            sendKeys(element, text);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendKeysKeyboard(String text) {
        new Actions(androidDriver).sendKeys(text).perform();
    }

    public void sendKeysNum(String numbers) {
        char[] numArr = numbers.toCharArray();
        double ratio = (double) 1565 / 2280;
        int x = 0;
        int y = 0;
        double n = 0; //keyboard height
        for (char numChar : numArr) {
            Dimension dimension = androidDriver.manage().window().getSize();

            switch (numChar) {
                case '1' -> {
                    x = (int) (dimension.width * 0.13);
                    y = (int) (dimension.height * (0.743 +n));
                }
                case '2' -> {
                    x = (int) (dimension.width * 0.38);
                    y = (int) (dimension.height * (0.743 +n));
                }
                case '3' -> {
                    x = (int) (dimension.width * 0.62);
                    y = (int) (dimension.height * (0.743+n));
                }
                case '4' -> {
                    x = (int) (dimension.width * 0.13);
                    y = (int) (dimension.height * (0.807+n));
                }
                case '5' -> {
                    x = (int) (dimension.width * 0.38);
                    y = (int) (dimension.height * (0.807+n));
                }
                case '6' -> {
                    x = (int) (dimension.width * 0.62);
                    y = (int) (dimension.height * (0.807+n));
                }
                case '7' -> {
                    x = (int) (dimension.width * 0.13);
                    y = (int) (dimension.height * (0.87+n));
                }
                case '8' -> {
                    x = (int) (dimension.width * 0.38);
                    y = (int) (dimension.height * (0.87+n));
                }
                case '9' -> {
                    x = (int) (dimension.width * 0.62);
                    y = (int) (dimension.height * (0.87+n));
                }
                case ',' -> {
                    x = (int) (dimension.width * 0.13);
                    y = (int) (dimension.height * (0.932+n));
                }
                case '0' -> {
                    x = (int) (dimension.width * 0.38);
                    y = (int) (dimension.height * (0.932+n));
                }
                case '.' -> {
                    x = (int) (dimension.width * 0.62);
                    y = (int) (dimension.height * (0.932+n));
                }
                case '<' -> { //delete
                    x = (int) (dimension.width * 0.87);
                    y = (int) (dimension.height * (0.87+n));
                }

            }
            Point tapLoc = new Point(x, y);
            MobileW3cActions.doTap(androidDriver, tapLoc, 500);  //with duration 1s
            Log.infoGreen("tapping " + numChar + " at x: " + x + ", y: " + y);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }


    //get attribute
    public String getAttribute(WebElement element, String attribute) {
        explicitWaitVisibilityOfElement(element,5000);
        return element.getAttribute(attribute);
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

    public void openNotif(){
        Dimension dimension = androidDriver.manage().window().getSize();
        start = new Point((int) (dimension.width * 0.2), (int) (dimension.height * 0));
        end = new Point((int) (dimension.width * 0.2), (int) (dimension.height * 0.8));
        MobileW3cActions.doSwipe(androidDriver, start, end, 1000);  //with duration 1s
    }

    public void scrollDown() {
        Dimension dimension = androidDriver.manage().window().getSize();
        start = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.8));
        end = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.2));
        MobileW3cActions.doSwipe(androidDriver, start, end, 1000);  //with duration 1s

    }

    public void scrollUp() {
        Dimension dimension = androidDriver.manage().window().getSize();
        end = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.8));
        start = new Point((int) (dimension.width * 0.5), (int) (dimension.height * 0.2));
        MobileW3cActions.doSwipe(androidDriver, start, end, 1000);  //with duration 1s
    }

    public void scrollRight(double heightRatio) {
        Dimension dimension = androidDriver.manage().window().getSize();
        start = new Point((int) (dimension.width * 0.8), (int) (dimension.height * heightRatio));
        end = new Point((int) (dimension.width * 0.2), (int) (dimension.height * heightRatio));
        MobileW3cActions.doSwipe(androidDriver, start, end, 1000);  //with duration 1s
    }


    public void scrollToElement(WebElement e, String direction) {
        for (int i = 0; i < 10; i++) {
            if (explicitWaitVisibilityOfElement(e,5000)) {
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

    @Attachment(value = "Attachment of Element {0}", type = "image/png")
    public static byte[] screenshotElementMobile(WebElement element) {
        String srcFile = element.getScreenshotAs(OutputType.BASE64).replaceAll("\n", "");
        return Base64.getDecoder().decode(srcFile);
    }

    public static void screenshotElementMobile(WebElement element, String location) {
        try {
            byte[] bytes = screenshotElementMobile(element);
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage actualImage = ImageIO.read(is);
            ImageIO.write(actualImage, "PNG", new File(location));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Attachment of Page Screenshot", type = "image/png")
    public byte[] screenshotPage() throws Exception {
        File srcFile = androidDriver.getScreenshotAs(OutputType.FILE);
        return AllureAttachments.fileToBytes(srcFile.getPath());
    }

    public static void startRecordingMobile() {
        BaseStartScreenRecordingOptions options;
        if (AllureAttachments.deviceName.toLowerCase().equals("android")) {
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
        if (AllureAttachments.deviceName.toLowerCase().equals("android")) {
            options = new AndroidStopScreenRecordingOptions();
        } else {
            options = new IOSStopScreenRecordingOptions();
        }
        try {
            if (enable)
                return Base64.getDecoder().decode(((CanRecordScreen) staticDriver).stopRecordingScreen(options));
        } catch (Exception e) {
            Log.warn("Could not stop recording screen on mobile platform: " + e.getMessage());
        }
        return null;
    }

    protected void takePhoto() {
        androidDriver.pressKey(new KeyEvent(AndroidKey.CAMERA));
        androidDriver.pressKey(new KeyEvent(AndroidKey.COMMA));
        androidDriver.pressKey(new KeyEvent(AndroidKey.TAB));
        androidDriver.pressKey(new KeyEvent(AndroidKey.SPACE));
    }

    protected void airplaneMode(boolean isOn) {
        if (isOn) {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().withDataDisabled().build());
        } else {
            androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
        }
    }

    protected void setLocation(double latitude, double longitude, double altitude) {
        androidDriver.setLocation(new Location(latitude, longitude, altitude));
    }

    public void navigateBack(){
        androidDriver.navigate().back();
    }



}
