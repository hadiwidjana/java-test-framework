package com.satria.javatestframework.FE;

import com.satria.javatestframework.utils.Integration.GoogleAPI.AppDistribution;
import com.satria.javatestframework.utils.SpringAnnotations.BaseTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.gecko.options.GeckoOptions;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.network.Network;
import org.openqa.selenium.devtools.v110.network.model.Request;
import org.openqa.selenium.devtools.v110.network.model.RequestId;
import org.openqa.selenium.devtools.v110.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.Duration;
import java.util.*;


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
    @Value("${android.avd}")
    private String androidAvd;
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


    @Bean
    public WebDriver createDriver() throws IOException {
        String downloadFileLocation;
        if (System.getProperty("os.name").equals("Windows 10")) {
            downloadFileLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\downloadedFiles";
        } else {
            downloadFileLocation = System.getProperty("user.dir") + "/src/test/resources/downloadedFiles";
        }
        HashMap<String, Object> chromePrefs;
        switch (device.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFileLocation);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeDriver = new ChromeDriver(chromeOptions);
                browserConfig(chromeDriver);
                return chromeDriver;
            }
            case "chrome_debug" -> {
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFileLocation);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeDriver = new ChromeDriver(chromeOptions);
                browserConfig(chromeDriver);
                GetStatusNetwork("chrome");
                return chromeDriver;
            }
            case "chrome_throttle" -> {
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFileLocation);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeDriver = new ChromeDriver(chromeOptions);
                browserConfig(chromeDriver);
                doThrottle();
                return chromeDriver;
            }
            case "chrome_headless" -> {
                System.setProperty("webdriver.chrome.driver", "linuxchromedriver");
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFileLocation);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("enable-automation");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--disable-browser-side-navigation");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeDriver = new ChromeDriver(chromeOptions);
                browserConfig(chromeDriver);
                return chromeDriver;
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().arch64().setup();
                firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.download.folderList", 2);
                firefoxProfile.setPreference("browser.download.dir", downloadFileLocation);
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/zip");
                firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProfile(firefoxProfile);
                firefoxDriver = new FirefoxDriver(firefoxOptions);
                browserConfig(firefoxDriver);
                return firefoxDriver;
            }

            case "firefox_headless" -> {
                WebDriverManager.firefoxdriver().arch64().setup();
                firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.download.folderList", 2);
                firefoxProfile.setPreference("browser.download.dir", downloadFileLocation);
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/zip");
                firefoxOptions = new FirefoxOptions();
                firefoxOptions.setProfile(firefoxProfile);
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--profile-root");
//                firefoxOptions.setBinary("/snap/firefox/current/firefox.launcher");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxDriver = new FirefoxDriver(firefoxOptions);
                browserConfig(firefoxDriver);
                return firefoxDriver;
            }

            case "ie" -> {
                WebDriverManager.iedriver().setup();
                internetExplorerDriver = new InternetExplorerDriver();
                browserConfig(internetExplorerDriver);
                return internetExplorerDriver;
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                edgeDriver = new EdgeDriver();
                browserConfig(edgeDriver);
                return edgeDriver;
            }
            case "safari" -> {
                WebDriverManager.safaridriver();
                safariDriver = new SafariDriver();
                browserConfig(safariDriver);
                return safariDriver;
            }
            case "chrome_android" -> {
                if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
                server.getAppiumService().start();
                server.getAppiumService().clearOutPutStreams();
                UiAutomator2Options androidCapabilities = new UiAutomator2Options()
                        .setDeviceName(androidName)
                        .setAvd(androidAvd)
                        .setIsHeadless(androidHeadless)
                        .setPlatformName("Android")
                        .withBrowserName("Chrome");
                androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), androidCapabilities);
                return androidDriver;
            }
            case "firefox_android" -> {
                if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
                server.getAppiumService().start();
                server.getAppiumService().clearOutPutStreams();
                WebDriverManager.firefoxdriver().arch64().setup();
                Map<String, Object> firefoxOptions1 = new HashMap<>();
                firefoxOptions1.put("androidPackage", "org.mozilla.firefox");
                firefoxOptions1.put("androidActivity", "org.mozilla.gecko.BrowserApp");
                firefoxOptions1.put("androidDeviceSerial", androidUdid);
                GeckoOptions androidFirefoxCapabilities = new GeckoOptions()
                        .setAutomationName("Gecko")
                        .setPlatformName("mac")
                        .withBrowserName("firefox").setMozFirefoxOptions(firefoxOptions1);
                androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), androidFirefoxCapabilities);
                return androidDriver;
            }
            case "safari_ios" -> {
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
                return iosDriver;
            }
            case "android" -> {
                if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
                server.getAppiumService().start();
                server.getAppiumService().clearOutPutStreams();
                UiAutomator2Options desiredCapabilities = new UiAutomator2Options()
                        .setDeviceName(androidName)
                        .setIsHeadless(androidHeadless)
                        .setUdid(androidUdid)
                        .setPlatformName("Android")
//                        .setMockLocationApp(Location)
                        .setApp(appDistribution.releaseBinary)
                        .setAppActivity(androidActivity)
                        .setAppPackage(androidPackage)
                        .autoGrantPermissions();
                androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), desiredCapabilities);
                return androidDriver;
            }
            case "android_emulator" -> {
                if (server.getAppiumService().isRunning()) server.getAppiumService().stop();
                server.getAppiumService().start();
                server.getAppiumService().clearOutPutStreams();
                UiAutomator2Options desiredCapabilities = new UiAutomator2Options()
                        .setAvd(androidAvd)
                        .setDeviceName(androidName)
                        .setIsHeadless(androidHeadless)
                        .setUdid(androidUdid)
                        .setPlatformName("Android")
                        .setApp(appDistribution.releaseBinary)
                        .setAppActivity(androidActivity)
                        .setAppPackage(androidPackage)
                        .autoGrantPermissions();
                androidDriver = new AndroidDriver(server.getAppiumService().getUrl(), desiredCapabilities);
                return androidDriver;
            }
            case "ios", "ios_emulator" -> {
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
                return iosDriver;
            }
            default -> {
                return null;
            }
        }
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
