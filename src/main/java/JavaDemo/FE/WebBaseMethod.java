package JavaDemo.FE;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.springframework.beans.factory.annotation.Value;
import org.openqa.selenium.interactions.Actions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static JavaDemo.Integrations.Encryption.Encryption.decryptData;
import static JavaDemo.Integrations.Encryption.Encryption.encryptData;

@BaseTest
public class WebBaseMethod extends ExplicitWaiting {


    @Value("${highlight.element}")
    protected String highlight;
    @Value("${screenshot.element}")
    protected String screenshot;

    //to inverse web element to css selector
    final String JS_BUILD_CSS_SELECTOR =
            "for(" +
                    "var e=arguments[0]," +
                    "n=[]," +
                    "i=function(e,n){" +
                    "if(!e||!n)return 0;" +
                    "for(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;" +
                    "return 1};" +
                    "e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){" +
                    "if(e.id){" +
                    "n.unshift('#'+e.id);" +
                    "break}" +
                    "for(var a=1,r=1,o=e.localName," +
                    "l=e.className&&e.className.trim().split(/[\\s,]+/g)," +
                    "t=e.previousSibling;t;t=t.previousSibling)" +
                    "10!=t.nodeType&&t.nodeName==e.nodeName&&(i(l,t.className)&&(l=null),r=0,++a);" +
                    "for(var t=e.nextSibling;t;t=t.nextSibling)" +
                    "t.nodeName==e.nodeName&&(i(l,t.className)&&(l=null),r=0);" +
                    "n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+')'))}return n.join(' > ');";

    //click methods//
    public void click(WebElement element) {
        Log.info("clicking " + element);
        //ensure element on display
        scrollToCenter(element);
        //wait element to be visible
        explicitWaitVisibilityOfElement(element, 5000);
        //wait element to be clickable
        explicitWaitElementToBeClickable(element, 5000);

        //highlight
        if (highlight.equalsIgnoreCase("on")) highlightElement(element);
        if (screenshot.equalsIgnoreCase("on")) {
            //screenshot element
            try {
                screenshotElement(element);
            } catch (IllegalArgumentException illegalException) {
                //retry without allure
                Log.warn("Warning: Gagal menyimpan screenshot element ke allure report!");
            }
        }
        element.click();
    }


    public void clickConditional(WebElement element1, WebElement element2) {
        try {
            //bypass wait too long
            if (element1.isDisplayed()) click(element1);
        } catch (NoSuchElementException element) {
            if (element2.isDisplayed()) click(element2);
        }
    }

    /* To click a certain Web Element using DOM/ JavaScript Executor */
    public void JSclick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);
    }
    //click ends//

    //sendKeys methods//
    public void sendKeys(WebElement element, String value) {
        Log.info("typing " + value + " on " + element.toString());
        //wait element to be visible
        explicitWaitVisibilityOfElement(element, 5000);
        //ensure element on display
        scrollToCenter(element);
        //highlight
        if (highlight.equalsIgnoreCase("on")) highlightElement(element);
        if (screenshot.equalsIgnoreCase("on")) {
            //screenshot element
            try {
                screenshotElement(element);
            } catch (IllegalArgumentException illegalException) {
                //retry without allure
                Log.warn("Warning: Gagal menyimpan screenshot element ke allure report!");
            }
        }
        element.clear();
        element.sendKeys(value);
    }

    public void sendKeysConditional(WebElement element1, WebElement element2, String text) {
        try {
            //bypass wait too long
            if (element1.isDisplayed()) sendKeys(element1, text);
        } catch (NoSuchElementException element) {
            if (element2.isDisplayed()) sendKeys(element2, text);
        }
    }

    //sendKeys ends//

    //Hover mouse methods//
    public void hover(WebElement element) {
        if (highlight.equalsIgnoreCase("on")) highlightElement(element);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    //Hover mouse methods//
    public void clickHold(WebElement clickAndHoldElement) {
        Actions actions = new Actions(driver);
        Action action = actions.clickAndHold(clickAndHoldElement).build();
        action.perform();
        if (highlight.equalsIgnoreCase("on")) highlightElement(clickAndHoldElement);
        action = actions.release(clickAndHoldElement).build();
        action.perform();
    }

    public void hoverClick(WebElement elementHover, WebElement elementClick) {
        Actions actions = new Actions(driver);
        actions.moveToElement(elementHover);
        actions.moveToElement(elementClick);
        actions.click().build().perform();
        if (highlight.equalsIgnoreCase("on")) highlightElement(elementClick);
    }


    //scroll methods//
    /* To ScrollUp using JavaScript Executor */
    public void scrollUp(int distance) {
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(0, -%s);", distance));
    }

    /* To ScrollDown using JavaScript Executor */
    public void scrollDown(int distance) {
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(0, %s);", distance));
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("scroll(0, 0);");
    }

    public void scrollDown(WebElement table, int pixel) {
        ((JavascriptExecutor) driver).executeScript(String.format("document.querySelector('%s').scrollTop=%s", getCSS(table), pixel));
    }

    public void scrollAlternative(int distance) {
        ((JavascriptExecutor) driver).executeScript(String.format("document.scrollingElement.scroll(0,%s)", distance));
    }

    //scroll horizontal using pixel
    public void scrollToRight(WebElement table, int pixel) {
        ((JavascriptExecutor) driver).executeScript(String.format("document.querySelector('%s').scrollLeft=%s", getCSS(table), String.valueOf(pixel)));
    }

    public void scrollToCenter(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", e);
    }

    public void scrollToElement(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", e);
    }
    //scroll end//

    /* To Accept the Alert Dialog Message */
    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style',' border: 2px dashed red;');", element);
    }


    /* To Clear the content in the input location */
    public void clear(WebElement element) {
        element.clear();
    }

    /*To Switch To Frame By Index */
    public void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    /*To Switch To Frame By Frame Name */
    public void switchToFrameByFrameName(String frameName) {
        driver.switchTo().frame(frameName);
    }

    /*To Switch To Frame By Web Element */
    public void switchToFrameByWebElement(WebElement element) {
        driver.switchTo().frame(element);
    }

    /*To Switch out of a Frame */
    public void switchOutOfFrame() {
        driver.switchTo().defaultContent();
    }

    /*continue after open a new tab ALAM Method*/
    public void continueOnNewTab() {
        String currentTab = driver.getWindowHandle();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
            }
        }
    }

    /*To Get Tooltip Text */
    public String getTooltipText(WebElement element) {
        return element.getAttribute("title").trim();
    }

    /*To Close all Tabs/Windows except the First Tab */
    public void closeAllTabsExceptFirst() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (int i = 1; i < tabs.size(); i++) {
            driver.switchTo().window(tabs.get(i));
            driver.close();
        }
        driver.switchTo().window(tabs.get(0));
    }

    /*To Print all the Windows */
    public void printAllTheWindows() {
        ArrayList<String> al = new ArrayList<>(driver.getWindowHandles());
        for (String window : al) {
            System.out.println(window);
        }
    }

    public void UploadFile(String Location, WebElement element) {
        element.sendKeys(System.getProperty("user.dir") + "/" + Location);
    }


    public String getCSS(WebElement ele) {
        String selector = (String) ((JavascriptExecutor) driver).executeScript(JS_BUILD_CSS_SELECTOR, ele);
        return selector;
    }

    public static String getXpath(WebElement ele) {
        String str = ele.toString();
        String[] listString;
        if (str.contains("xpath")) {
            listString = str.split("xpath:");
        } else if (str.contains("id")) {
            listString = str.split("id:");
        } else {
            throw new NotFoundException("No Xpath or ID found");
        }
        String last = listString[1].trim();
        return last.substring(0, last.length() - 1);
    }

    @Attachment(value = "Attachment of WebElement {0}", type = "image/png")
    public static byte[] screenshotElement(WebElement element) {
        try {
            BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(100, 0, 0, 2))
                    .coordsProvider(new WebDriverCoordsProvider())
                    .imageCropper(new IndentCropper())     //.addIndentFilter(blur()))
                    .takeScreenshot(staticDriver, element).getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unable to Get WebElement.".getBytes();
    }

    /**
     * To Attach the Entire Page Screenshot
     */
    @Attachment(value = "Entire Page Screenshot of {0}", type = "image/png")
    public static byte[] fullPageScreenshot(String name, WebDriver driver) {
        try {
//            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.simple()).takeScreenshot(driver);
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(1000, 0, 0, 2)).takeScreenshot(driver);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Unable to Get Screenshot.".getBytes();
    }

    public static void saveSession(String fileName) {
        // create file named Cookies to store Login Information
        File file = new File("target/cookies/" + fileName + ".data");
        file.mkdirs();
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for (Cookie ck : staticDriver.manage().getCookies()) {
                Bwrite.write(encryptData(ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getSession(String fileName) {
        try {
            staticDriver.manage().deleteAllCookies();
            File file = new File("target/cookies/" + fileName + ".data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(decryptData(strline), ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;
                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = new SimpleDateFormat("E MMM dd hh:mm:ss z yyyy").parse(val);
                    }
                    boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    staticDriver.manage().addCookie(ck); // This will add the stored cookie to your current session}
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
