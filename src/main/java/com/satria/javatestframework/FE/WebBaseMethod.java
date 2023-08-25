package com.satria.javatestframework.FE;

import com.satria.javatestframework.utils.Logger.Log;
import com.satria.javatestframework.utils.SpringAnnotations.BaseTest;
import io.qameta.allure.Attachment;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static com.satria.javatestframework.utils.ErrorHandling.ErrorHandling.elementErrorHandling;
import static com.satria.javatestframework.utils.Utils.ExcelUtils.*;
import static com.satria.javatestframework.utils.Utils.ReadAndWriteFile.deleteFile;
import static com.satria.javatestframework.utils.Utils.ReadAndWriteFile.readString;


@BaseTest
public class WebBaseMethod {

    @Autowired
    private WebDriver webDriver;

    private static WebDriver staticDriver;

    @PostConstruct
    private void initWebBaseMethod(){
        staticDriver= webDriver;
    }

    @Value("${maxPageLoadTime}")
    private int maxPageLoadTime;

    final String JS_BUILD_CSS_SELECTOR =
            "for(var e=arguments[0],n=[],i=function(e,n){if(!e||!n)return 0;f" +
                    "or(var i=0,a=e.length;a>i;i++)if(-1==n.indexOf(e[i]))return 0;re" +
                    "turn 1};e&&1==e.nodeType&&'HTML'!=e.nodeName;e=e.parentNode){if(" +
                    "e.id){n.unshift('#'+e.id);break}for(var a=1,r=1,o=e.localName,l=" +
                    "e.className&&e.className.trim().split(/[\\s,]+/g),t=e.previousSi" +
                    "bling;t;t=t.previousSibling)10!=t.nodeType&&t.nodeName==e.nodeNa" +
                    "me&&(i(l,t.className)&&(l=null),r=0,++a);for(var t=e.nextSibling" +
                    ";t;t=t.nextSibling)t.nodeName==e.nodeName&&(i(l,t.className)&&(l" +
                    "=null),r=0);n.unshift(r?o:o+(l?'.'+l.join('.'):':nth-child('+a+'" +
                    ")'))}return n.join(' > ');";

    //click methods//
    public void click(WebElement e) throws InterruptedException {
        try{
            Log.info("clicking "+ e);
            waitForClickable(e);
            highlightElement(e);
            screenshotElement(e);
            e.click();
//            waitPageToLoad();
            Thread.sleep(500);}
        catch (IllegalArgumentException illegalException){//retry without allure
            Log.info("clicking "+ e);
            Log.warn("Warning: Gagal menyimpan screenshot element ke allure report!");
            waitForClickable(e);
            highlightElement(e);
            e.click();
//            waitPageToLoad();
            Thread.sleep(500);}
        catch (NoSuchElementException | NoSuchWindowException | TimeoutException | ElementNotInteractableException | NoSuchFrameException errorType) {
            elementErrorHandling(errorType);

        }
    }


    public void clickLang(WebElement indo, WebElement eng) throws Exception {
        try{
            highlightElement(indo);
            indo.click();
            Thread.sleep(500);
            waitPageToLoad();
        } catch(NoSuchElementException e) {
            highlightElement(eng);
            eng.click();
            Thread.sleep(500);
            waitPageToLoad();
        }
    }

    /* To click a certain Web Element using DOM/ JavaScript Executor */
    public void JSclick(WebElement element)
    {
        ((JavascriptExecutor) webDriver).executeScript("return arguments[0].click();", element);
    }
    //click ends//

    //sendKeys methods//
    public void sendKeys(WebElement element, String value) throws InterruptedException {
        try{
            Log.info("typing "+ value +" on "+ element.toString());
            waitForVisibility(element);
            highlightElement(element);
            screenshotElement(element);
            element.clear();
            Thread.sleep(500);
            element.sendKeys(value);
        } catch (IllegalArgumentException illegalException){
            Log.info("typing "+ value +" on "+ element);
            waitForVisibility(element);
            highlightElement(element);
            element.clear();
            Thread.sleep(500);
            element.sendKeys(value);
        } catch (NoSuchElementException | NoSuchWindowException | TimeoutException | ElementNotInteractableException | NoSuchFrameException errorType) {
            elementErrorHandling(errorType);
        }
    }
    public void sendKeysLang(WebElement indo, WebElement eng, String text) throws Exception {
        try{
            highlightElement(indo);
            indo.sendKeys(text);
            Thread.sleep(500);
        } catch(NoSuchElementException e) {
            highlightElement(eng);
            eng.sendKeys(text);
            Thread.sleep(500);
        }
    }
    //sendKeys ends//

    //Hover mouse methods//
    public void hover(WebElement element) throws Exception{
        Actions action = new Actions(webDriver);
        action.moveToElement(element).perform();
        Thread.sleep(500);
//        screenshotElement()(webDriver,element);
    }

    //Hover mouse methods//
    public void clickHold(WebElement clickAndHoldElement, WebElement element) throws Exception{
        Actions actions = new Actions(webDriver);
        Action action = actions.clickAndHold(clickAndHoldElement).build();
        action.perform();
        screenshotElement(element);
        action = actions.release(clickAndHoldElement).build();
        action.perform();
        Thread.sleep(500);
    }

    public void hoverClick(WebElement elementHover, WebElement elementClick) throws Exception{
        Actions actions = new Actions(webDriver);
        actions.moveToElement(elementHover);
        actions.moveToElement(elementClick);
        actions.click().build().perform();
        Thread.sleep(500);
        screenshotElement(elementClick);
    }



    //scroll methods//
    /* To ScrollUp using JavaScript Executor */
    public void scrollUp() throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, -100);");
        Thread.sleep(500);
    }
    /* To ScrollDown using JavaScript Executor */
    //OLD
    public void scrollDown100() throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 100);");
        Thread.sleep(500);
    }
    //500
    public void scrollDown500() throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 500);");
        Thread.sleep(500);

    }
    //700
    public void scrollDown700() throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 700);");
        Thread.sleep(500);

    }


    public void scrollToTop() throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript("scroll(0, 0);");
        Thread.sleep(500);
    }


    //New
    //scroll vertical using pixel
    public void scrollDown(WebElement table, int pixel) throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript(String.format("document.querySelector('%s').scrollTop=%s",getCSS(table),String.valueOf(pixel)));
        Thread.sleep(500);
    }
    public void scrollAlternative(int pixel) throws InterruptedException {
        ((JavascriptExecutor) webDriver).executeScript(String.format("document.scrollingElement.scroll(0,%s)",pixel));
        Thread.sleep(500);
    }
    //scroll horizontal using pixel
    public void scrollToRight(WebElement table, int pixel) throws Exception{
        ((JavascriptExecutor) webDriver).executeScript(String.format("document.querySelector('%s').scrollLeft=%s",getCSS(table),String.valueOf(pixel)));
        Thread.sleep(500);
    }

    public void scrollToCenter(WebElement e) throws InterruptedException {
        waitForVisibility(e);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", e);
    }

    public void scrollToElement(WebElement e)  {
        waitForVisibility(e);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", e);
    }
    //scroll end//

    /* To Accept the Alert Dialog Message */
    public void refreshPage() throws InterruptedException {
        webDriver.navigate().refresh();
        Thread.sleep(3000);
    }

    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(maxPageLoadTime));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(maxPageLoadTime));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitPageToLoad(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(maxPageLoadTime));
        wait.until(driver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void highlightElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)webDriver ;
        js.executeScript("arguments[0].setAttribute('style',' border: 2px dashed red;');", element);
    }


    /* To Clear the content in the input location */
    public void clear(WebElement element)
    {
        element.clear();
    }

    /*To Switch To Frame By Index */
    public void switchToFrameByIndex(int index) {
        webDriver.switchTo().frame(index);
    }


    /*To Switch To Frame By Frame Name */
    public void switchToFrameByFrameName(String frameName) {
        webDriver.switchTo().frame(frameName);
    }


    /*To Switch To Frame By Web Element */
    public void switchToFrameByWebElement(WebElement element) {
        webDriver.switchTo().frame(element);
    }


    /*To Switch out of a Frame */
    public void switchOutOfFrame() {
        webDriver.switchTo().defaultContent();
    }


    /*continue after open a new tab ALAM Method*/
    public void continueOnNewTab() {
        String currentTab = webDriver.getWindowHandle();
        for (String tab : webDriver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                webDriver.switchTo().window(tab);
            }
        }
    }


    /*To Get Tooltip Text */
    public String getTooltipText(WebElement element)
    {
        return element.getAttribute("title").trim();
    }

    /*To Close all Tabs/Windows except the First Tab */
    public void closeAllTabsExceptFirst()
    {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        for(int i=1;i<tabs.size();i++)
        {
            webDriver.switchTo().window(tabs.get(i));
            webDriver.close();
        }
        webDriver.switchTo().window(tabs.get(0));
    }
    /*To Print all the Windows */
    public void printAllTheWindows()
    {
        ArrayList<String> al = new ArrayList<>(webDriver.getWindowHandles());
        for(String window : al)
        {
            System.out.println(window);
        }
    }

    public void UploadFile(String Location, WebElement element) throws Exception
    {
        element.sendKeys(System.getProperty("user.dir") + "/" + Location);
        Thread.sleep(4000);
    }

    public String excelTemplateEN(int no){
        String RFQID = readString("src/test/resources/ExcelFiles/RFQList"+no+".txt").replaceAll("\\x00","");
        String templateFileName = RFQID.replace("/","_")+" - Quotation.xlsx";
        return "src/test/resources/downloadedFiles/"+templateFileName;
    }

    public String excelTemplateID(int no){
        String RFQID = readString("src/test/resources/ExcelFiles/RFQList"+no+".txt").replaceAll("\\x00","");
        String templateFileName = RFQID.replace("/","_")+" - Penawaran.xlsx";
        return "src/test/resources/downloadedFiles/"+templateFileName;
    }

    public void editRFQXlsx(int no) throws Exception {
        String filePath;
        try{
            filePath = System.getProperty("user.dir")+"/"+excelTemplateEN(no);
            System.out.println(filePath);
            getExcelFile(filePath,"Headquarter Demo (1)");
        } catch (FileNotFoundException e) {
            filePath = System.getProperty("user.dir")+"/"+excelTemplateID(no);
            System.out.println(filePath);
            getExcelFile(filePath,"Headquarter Demo (1)");
        }
        updateCell(24,6,10);
        updateCell(25,6,10);
        updateCell(26,6,10);
        updateCell(24,7,10);
        updateCell(25,7,10);
        updateCell(26,7,10);
        writeExcelFile(filePath);
    }

    public void deleteTemplate(int no){
        try{
            deleteFile(System.getProperty("user.dir")+"/"+excelTemplateEN(no));
        } catch (Exception e){
            deleteFile(System.getProperty("user.dir")+"/"+excelTemplateID(no));
        }
    }


    //============================================ Eproc Web =========================================================//

    public void UploadFileGATE(String Location, WebElement element) throws Exception {
        Thread.sleep(3000);
        try{
            element.sendKeys(System.getProperty("user.dir") + "/" + Location);}
        catch (InvalidArgumentException e){
            element.sendKeys(System.getProperty("user.dir") + "\\" + Location.replace("/","\\"));}
    }


    public void editQuotationXlsx(int no) throws Exception {
        String filePath;
        try {
            filePath = System.getProperty("user.dir") + "/" + excelTemplateEN(no);
            System.out.println(filePath);
            getExcelFile(filePath, "HQ 1 (1)");
        } catch (FileNotFoundException e) {
            filePath = System.getProperty("user.dir") + "/" + excelTemplateID(no);
            System.out.println(filePath);
            getExcelFile(filePath, "HQ 1 (1)");
        }
        //Brand Name
        updateCell(24, 5, "Kangen Water");
        updateCell(25, 5, "Thermal Paste");
        updateCell(26, 5, "Keyboard Lenovo");
        //quantity
        updateCell(24, 6, 10.12359);
        updateCell(25, 6, 10.12354);
        updateCell(26, 6, 10.12352);
        //unit cost
        updateCell(24, 7, 10.432167);
        updateCell(25, 7, 20.432523);
        updateCell(26, 7, 12.432589);
        //Discount
        updateCell(24, 8, "Amount (Total)");
        updateCell(25, 8, "Percentage (%)");
        updateCell(26, 8, "Amount (Per Unit)");
        //Discount Value
        updateCell(24, 9, 5.247845);
        updateCell(25, 9, 3.903932);
        updateCell(26, 9, 1.99999);

        writeExcelFile(filePath);

    }

    public void editQuotationBulkXlsx(int no) throws Exception {
        String filePath;
        try {
            filePath = System.getProperty("user.dir") + "/" + excelTemplateEN(no);
            System.out.println(filePath);
            getExcelFile(filePath, "HQ 1 (1)");
        } catch (FileNotFoundException e) {
            filePath = System.getProperty("user.dir") + "/" + excelTemplateID(no);
            System.out.println(filePath);
            getExcelFile(filePath, "HQ 1 (1)");
        }
        //Brand Name
        updateCell(24, 5, "Kangen Water");
        updateCell(25, 5, "Thermal Paste");
        updateCell(26, 5, "Keyboard Lenovo");
        //quantity
        updateCell(24, 6, 10.12359);
        updateCell(25, 6, 10.12354);
        updateCell(26, 6, 10.12352);
        //unit cost
        updateCell(24, 7, 101.432167);
        updateCell(25, 7, 201.432523);
        updateCell(26, 7, 112.432589);
        //Discount
        updateCell(24, 8, "Amount (Total)");
        updateCell(25, 8, "Percentage (%)");
        updateCell(26, 8, "Amount (Per Unit)");
        //Discount Value
        updateCell(24, 9, 20.247845);
        updateCell(25, 9, 3.903932);
        updateCell(26, 9, 1.99999);
        //Delivery Cost
        updateCell(27,12,10000);

        writeExcelFile(filePath);

    }

    public void editRfqXlsxEproc(double Qty1, double Qty2, double Qty3) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/gokomodo-rfq-excel-template.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Sheet1");
        //product
        updateCell(1, 3,"ASUS ROG");
        updateCell(2, 3,"Pepsimen");
        //qty
        updateCell(1, 5, Qty1);
        updateCell(2, 5, Qty2);
        updateCell(3, 5, Qty3);
        //uom
        updateCell(1, 6, "Kg");
        updateCell(2, 6, "Liter");
        updateCell(3, 6, "Pcs");
        //item name
        updateCell(3,4,"Brand T");
        //Product name add ke 3
        updateCell(3, 3,"Vitamin K");
        //No material ke 3
        updateCell(1,2,"YUI111");
        updateCell(2,2,"POP222");
        updateCell(3,2,"RET888");
        //PR ID ke 3
        updateCell(3,1,"RTE321");

        writeExcelFile(filePath);
    }

    public void editRFQfromDTCXlsx( double qty3) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/gokomodo-tc-excel-template.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Sheet1");
        //Product Name
        updateCell(1, 3, "Kangen Water");
        updateCell(2, 3, "Thermal Paste");
        updateCell(3, 3, "Keyboard Asus");
        //Product Name
        updateCell(1, 4, "Le mineral");
        updateCell(2, 4, "Pepsodent");
        updateCell(3, 4, "Asus");
        //quantity
        updateCell(1, 5, 15.543);
        updateCell(2, 5, 10.5);
        updateCell(3, 5, qty3);
        //UOM
        updateCell(3, 6, "Pcs");
        //unit cost
        updateCell(1, 9, 10.500);
        updateCell(2, 9, 20.700);
        updateCell(3, 9, 17.300);
        //Total
        updateCell(1, 10, 163.2015);
        updateCell(2, 10, 227.85);
        updateCell(3, 10, 346);

        writeExcelFile(filePath);

    }

    public void editVendorXlsxEproc2(String name1, String name2, String name3, String name4, String name5) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/Invite New Vendor.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Invite New Vendor");
        //Entity
        updateCell(1,1, "CV");
        updateCell(2,1, "PT");
        updateCell(3,1, "PD");
        updateCell(4,1, "Toko");
        updateCell(5,1, "Koperasi");
        //Company name
        updateCell(1,2, "Maju Makmur");
        updateCell(2,2, "Saka Unity Terdepan");
        updateCell(3,2, "Indah Terang");
        updateCell(4,2, "Kelontong");
        updateCell(5,2, "Karcupil");
        //First Name
        updateCell(1,3, name1);
        updateCell(2,3, name2);
        updateCell(3,3, name3);
        updateCell(4,3, name4);
        updateCell(5,3, name5);
        //Last Name
        updateCell(1,4, "Makmur");
        updateCell(2,4, "Sakaru");
        updateCell(3,4, "Permai");
        updateCell(4,4, "Sijabat");
        updateCell(5,4, "Rodriguez");
        //Contact
        updateCell(1,6, 81234561);
        updateCell(2,6, 87654322);
        updateCell(3,6, 46372813);
        updateCell(4,6, 81357914);
        updateCell(5,6, 82468025);
        //Email
        updateCell(1,7, "Makmur@mail.com");
        updateCell(2,7, "Sakaru@mail.com");
        updateCell(3,7, "Permai@mail.com");
        updateCell(4,7, "Sijabat@mail.com");
        updateCell(5,7, "Rodri@mail.com");

        writeExcelFile(filePath);
    }

    public void editVendorXlsxEproc1(String name1) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/Invite New Vendor.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Invite New Vendor");
        //Entity
        updateCell(1,1, "CV");
        //Company name
        updateCell(1,2, "Maju Makmur");
        //First Name
        updateCell(1,3, name1);
        //Last Name
        updateCell(1,4, "Makmur");
        //Contact
        updateCell(1,6, 81234561);
        //Email
        updateCell(1,7, "Makmur@mail.com");

        writeExcelFile(filePath);
    }

    public void editRfqXlsOfflineVendor(double Qty1, double Qty2, double Qty3, String ExcelLoc) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") +"/"+ ExcelLoc;
        System.out.println(filePath);
        getExcelFile(filePath, "HQ 1 (1)");
        //Brand
        updateCell(23, 5, "Mindong");
        updateCell(24, 5, "Kangen Water");
        updateCell(25, 5, "Rucika");
        //qty
        updateCell(23, 6, Qty1);
        updateCell(24, 6, Qty2);
        updateCell(25, 6, Qty3);
        //unit cost
        updateCell(23, 7, 101);
        updateCell(24, 7, 56.456);
        updateCell(25, 7, 21.84718);
        //Notes
        updateCell(23, 10, "Product 1 check");
        updateCell(24, 10, "Product 2 check");
        updateCell(25, 10, "Product 3 check");

        writeExcelFile(filePath);
    }

    //=========================================== Issue Invalid TC ===================================================//
    //DTC
    public void editDTCXlsxInvalidTC(double Qty1, double Qty2) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/gokomodo-tc-excel-template.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Sheet1");
        //Product name
        updateCell(1,3,"Product 1");
        updateCell(2,3,"Product 2");
        //qty
        updateCell(1, 5, Qty1);
        updateCell(2, 5, Qty2);
        //uom
        updateCell(1, 6, "Pcs");
        updateCell(2, 6, "Ton");
        //Price
        updateCell(1,9,12000);
        updateCell(1,9,10500);

        writeExcelFile(filePath);
    }
    //DTC

    //RFQ
    public void editRFQXlsxInvalidTC(double Qty1, double Qty2, double Qty3) throws Exception {
        String filePath;
        filePath = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/komodo-rfq-excel-template.xlsx";
        System.out.println(filePath);
        getExcelFile(filePath, "Sheet1");
        //Product name
        updateCell(1,3,"Product 1");
        updateCell(2,3,"Product 2");
        //qty
        updateCell(1, 5, Qty1);
        updateCell(2, 5, Qty2);
        //uom
        updateCell(1, 6, "Pcs");
        updateCell(2, 6, "Ton");
        //Price
        updateCell(1,9,12000);
        updateCell(1,9,10500);

        writeExcelFile(filePath);
    }
    //RFQ
    //=========================================== Issue Invalid TC ===================================================//

    public void deleteTemplateRfq() {
        deleteFile(System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/gokomodo-rfq-excel-template.xlsx");
    }

    public void deleteTemplateVendor() {
        deleteFile(System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/Invite New Vendor.xlsx");
    }


    public void deleteTemplateDTC() {
        deleteFile(System.getProperty("user.dir") + "/src/test/resources/downloadedFiles/gokomodo-tc-excel-template.xlsx");
    }

    public void deleteTemplateRfq(String ExcelLoc) {
        deleteFile(ExcelLoc);
    }

    public void deleteTemplateQuotation(int no) {
        try {
            deleteFile(System.getProperty("user.dir") + "/" + excelTemplateEN(no));
        } catch (Exception e) {
            deleteFile(System.getProperty("user.dir") + "/" + excelTemplateID(no));
        }
    }

    public String getCSS(WebElement ele){
        String selector = (String) ((JavascriptExecutor) webDriver).executeScript(JS_BUILD_CSS_SELECTOR, ele);
        return selector;
    }

    public String getXpath(WebElement ele) {
        String str = ele.toString();
        String[] listString;
        if(str.contains("xpath")){
            listString = str.split("xpath:");
        } else if(str.contains("id")){
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
            BufferedImage image = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(100,0,0,2))
                    .coordsProvider(new WebDriverCoordsProvider())
                    .imageCropper(new IndentCropper())     //.addIndentFilter(blur()))
                    .takeScreenshot(staticDriver,element).getImage();
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
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(1000,0,0,2)).takeScreenshot(driver);
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
}
