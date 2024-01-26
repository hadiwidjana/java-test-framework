package JavaDemo.FE;

import JavaDemo.Integrations.GoogleAPI.AppDistribution;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import com.epam.healenium.SelfHealingDriver;
import com.epam.healenium.appium.wrapper.DriverWrapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;
import org.openqa.selenium.devtools.v120.network.model.Request;
import org.openqa.selenium.devtools.v120.network.model.RequestId;
import org.openqa.selenium.devtools.v120.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;


@BaseTest
public class DriverFactory {

    @Value("${device}")
    private String device;
    @Value("${device.size}")
    private String deviceSize;
    private static ChromeDriver chromeDriver;
    private static FirefoxDriver firefoxDriver;
    private EdgeDriver edgeDriver;
    private InternetExplorerDriver internetExplorerDriver;
    private SafariDriver safariDriver;
    private AndroidDriver androidDriver;
    private IOSDriver iosDriver;
    private ChromeOptions chromeOptions;
    private FirefoxProfile firefoxProfile;
    private FirefoxOptions firefoxOptions;
    //    @Value("${android.location}")
//    private String Location;
    @Value("${maxPageLoadTime}")
    private int maxPageLoadTime;
    @Value("${android.name}")
    private String androidName;
    @Value("${android.headless}")
    private boolean androidHeadless;
    @Value("${android.package}")
    private String androidPackage;
    @Value("${android.activity}")
    private String androidActivity;
    @Value("${ios.name}")
    private String iosName;
    @Value("${ios.headless}")
    private boolean iosHeadless;
    @Value("${ios.platform}")
    private String iosPlatform;
    @Value("${android.udid}")
    private String androidUdid;
    @Value("${ios.udid}")
    private String iosUdid;


    @Autowired
    private AppiumServer server;

    @Autowired
    private AppDistribution appDistribution;

    String downloadFileLocation;


    @PostConstruct
    public void initDriverFactory() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            downloadFileLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\downloadedFiles";
        } else {
            downloadFileLocation = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles";
        }
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "device", havingValue = "chrome")
    @Primary
    public SelfHealingDriver chromeDriver(@Value("${device.headless}") boolean deviceHeadless, @Value("${device.debug}") boolean deviceDebug, @Value("${device.throttle}") boolean deviceThrottle) {
        chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFileLocation);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments("--remote-allow-origins=*");
        if (deviceHeadless) {
            chromeOptions.addArguments("enable-automation");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-browser-side-navigation");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--disable-dev-shm-usage");
        }
        chromeDriver = new ChromeDriver(chromeOptions);
        if (deviceDebug) GetStatusNetwork("chrome");
        if (deviceThrottle) doThrottle();
        browserConfig(chromeDriver);
        return SelfHealingDriver.create(chromeDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "firefox")
    @Primary
    public SelfHealingDriver firefoxDriver(@Value("${device.headless}") boolean deviceHeadless) {
        firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.dir", downloadFileLocation);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/zip");
        firefoxOptions = new FirefoxOptions();
        if (deviceHeadless) {
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--profile-root");
            firefoxOptions.addArguments("--no-sandbox");
        }
        firefoxOptions.setProfile(firefoxProfile);
        firefoxDriver = new FirefoxDriver(firefoxOptions);
        browserConfig(firefoxDriver);
        return SelfHealingDriver.create(firefoxDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "ie")
    @Primary
    public SelfHealingDriver ieDriver() {
        internetExplorerDriver = new InternetExplorerDriver();
        browserConfig(internetExplorerDriver);
        return SelfHealingDriver.create(internetExplorerDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "edge")
    @Primary
    public SelfHealingDriver edgeDriver() {
        edgeDriver = new EdgeDriver();
        browserConfig(edgeDriver);
        return SelfHealingDriver.create(edgeDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "safari")
    @Primary
    public SelfHealingDriver safariDriver() {
        safariDriver = new SafariDriver();
        browserConfig(safariDriver);
        return SelfHealingDriver.create(internetExplorerDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "chrome_android")
    @Primary
    public SelfHealingDriver chromeAndroidDriver() {
        if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
        server.getAppiumService().start();
        server.getAppiumService().clearOutPutStreams();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.setBrowserVersion("91.0");
        UiAutomator2Options androidCapabilities = new UiAutomator2Options()
                .setDeviceName(androidName)
                .setIsHeadless(androidHeadless)
                .setPlatformName("Android")
                .withBrowserName("Chrome")
                .setNewCommandTimeout(Duration.ofMinutes(3));
        androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), androidCapabilities);
        return SelfHealingDriver.create(androidDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "firefox_android")
    @Primary
    public SelfHealingDriver firefoxAndroidDriver() {
        if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
        server.getAppiumService().start();
        server.getAppiumService().clearOutPutStreams();
        Map<String, Object> firefoxOptions1 = new HashMap<>();
        firefoxOptions1.put("androidPackage", "org.mozilla.firefox");
        firefoxOptions1.put("androidActivity", "org.mozilla.gecko.BrowserApp");
        firefoxOptions1.put("androidDeviceSerial", androidUdid);
        GeckoOptions androidFirefoxCapabilities = new GeckoOptions()
                .setAutomationName("Gecko")
                .setPlatformName("mac")
                .withBrowserName("firefox").setMozFirefoxOptions(firefoxOptions1);
        androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), androidFirefoxCapabilities);
        return SelfHealingDriver.create(androidDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "android")
    @Primary
    public SelfHealingDriver androidDriver() {
        if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
        server.getAppiumService().start();
        server.getAppiumService().clearOutPutStreams();
        UiAutomator2Options desiredCapabilities = new UiAutomator2Options()
                .setDeviceName(androidName)
                .setIsHeadless(androidHeadless)
                .setAdbExecTimeout(Duration.ofSeconds(120))
                .setUdid(androidUdid)
                .setPlatformName("Android")
                .setApp(appDistribution.releaseBinary)
                .setNewCommandTimeout(Duration.ofSeconds(1000))
                .setAppActivity(androidActivity)
                .setAppPackage(androidPackage)
                .gpsEnabled()
                .autoGrantPermissions();
        androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), desiredCapabilities);
        return SelfHealingDriver.create(androidDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "iosSafari")
    @Primary
    public SelfHealingDriver iosSafariDriver() {
        if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
        server.getAppiumService().start();
        server.getAppiumService().clearOutPutStreams();
        XCUITestOptions iosCapabilities = new XCUITestOptions()
                .setDeviceName(iosName)
                .setIsHeadless(iosHeadless)
                .setPlatformVersion(iosPlatform)
                .withBrowserName("Safari")
                .setPlatformName("IOS");
        iosDriver = new IOSDriver(server.getAppiumService().getUrl(), iosCapabilities);
        return SelfHealingDriver.create(iosDriver);
    }

    @Bean
    @ConditionalOnProperty(name = "device", havingValue = "ios")
    @Primary
    public SelfHealingDriver iosDriver() {
        if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
        server.getAppiumService().start();
        server.getAppiumService().clearOutPutStreams();
        XCUITestOptions desiredCapabilities = new XCUITestOptions()
                .setDeviceName(iosName)
                .setUdid(iosUdid)
                .setIsHeadless(iosHeadless)
                .setPlatformVersion(iosPlatform)
                .setPlatformName("IOS");
        iosDriver = new IOSDriver(server.getAppiumService().getUrl(), desiredCapabilities);
        return SelfHealingDriver.create(iosDriver);
    }


    public void doThrottle() {
        ChromiumNetworkConditions networkConditions = new ChromiumNetworkConditions();
        networkConditions.setOffline(false);
        networkConditions.setLatency(Duration.ofMillis(5));
        networkConditions.setDownloadThroughput(100000);//how fast download is (in bps)
        networkConditions.setUploadThroughput(100000);//how fast upload is (in bps)
        chromeDriver.setNetworkConditions(networkConditions);
    }

    public void GetStatusNetwork(String browserType) {
        //Lihat Di lognya
        // Source nya Chrome Network ada di sini:
        // https://chromedevtools.github.io/devtools-protocol/tot/Network/
        DevTools devTools;
        switch (browserType.toLowerCase()) {
            case "chrome":
                devTools = chromeDriver.getDevTools();
                break;
            case "firefox":
                devTools = firefoxDriver.getDevTools();
                break;
            default:
                throw new NotFoundException("browser not found");
        }

//        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // part untuk mendapatkan response dari webnya
        // tanda ini " -> " adalah Lambda di gunakan untuk menerima handler

        devTools.addListener(Network.requestWillBeSent(), request ->
        {
            System.out.println("=========== Network Status Request ===========");
            Request req = request.getRequest();
            System.out.println("This is Request URL : " + req.getUrl());
            System.out.println("Request Method => " + request.getRequest().getMethod());
            System.out.println("Request Headers => " + request.getRequest().getHeaders().toString());
            System.out.println("==============================================");
            System.out.println(req.getHeaders().get("Authorization"));
        });

        devTools.addListener(Network.responseReceived(), response ->
        {
            System.out.println("=========== Network Status Response ===========");
            Response res = response.getResponse();
            //System.out.println(res.getUrl());
            System.out.println("Response Url => " + res.getUrl());
            System.out.println("This is Response status: " + res.getStatus());
            System.out.println("Response Headers => " + res.getHeaders().toString());
            System.out.println("Response MIME Type => " + res.getMimeType().toString());
            System.out.println("==============================================");
        });
    }

    public static List<String> getResponseHeader() {
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        final RequestId[] requestIds = new RequestId[1];
        List<String> responseBody = new ArrayList<>();
        devTools.addListener(Network.responseReceived(), responseReceived -> {
            if (responseReceived.getResponse().getUrl().contains("gokomodo.co")) {
                System.out.println("URL: " + responseReceived.getResponse().getUrl());
                System.out.println("Status: " + responseReceived.getResponse().getStatus());
                System.out.println("Type: " + responseReceived.getType().toJson());
                responseReceived.getResponse().getHeaders().toJson().forEach((k, v) -> System.out.println((k + ":" + v)));
                requestIds[0] = responseReceived.getRequestId();
                System.out.println("Response Body: \n" + devTools.send(Network.getResponseBody(requestIds[0])).getBody() + "\n");
                responseBody.add(devTools.send(Network.getResponseBody(requestIds[0])).getBody());
            }
        });
        return responseBody;
    }

    public void setMaxPageLoadTimeMethod(int timeInSeconds, WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
    }

    private void browserConfig(WebDriver driver) {
        if (maxPageLoadTime > 0) {
            setMaxPageLoadTimeMethod(maxPageLoadTime, driver);
        }
        if (!device.toLowerCase().contains("android") || !device.toLowerCase().contains("ios")) {
            try {
                switch (deviceSize) {
                    case "auto" -> driver.manage().window().maximize();
                    case "QHD" -> {
                        Dimension dimension = new Dimension(2560, 1440);
                        driver.manage().window().setSize(dimension);
                    }
                    case "FHD" -> {
                        Dimension dimension = new Dimension(1920, 1080);
                        driver.manage().window().setSize(dimension);
                    }
                    case "HD+" -> {
                        Dimension dimension = new Dimension(1600, 900);
                        driver.manage().window().setSize(dimension);
                    }
                    case "HD" -> {
                        Dimension dimension = new Dimension(1280, 800);
                        driver.manage().window().setSize(dimension);
                    }
                    default -> throw new Exception("Wrong device size settings");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
