package JavaDemo.Integrations.GoogleAPI;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import JavaDemo.Integrations.Encryption.Encryption;
import JavaDemo.Integrations.SpringBoot.PropertiesReader;
import io.appium.java_client.gecko.GeckoDriver;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import static JavaDemo.Integrations.Utils.ReadAndWriteFile.readString;
import static JavaDemo.Integrations.Utils.ReadAndWriteFile.writeString;
import static io.restassured.RestAssured.given;

@BaseTest
public class AppDistribution {

    private WebDriver webDriver;
    private String clientId;
    private String clientSecret;
    private String authURL;
    private String browserCode[];
    public URL releaseBinary;
    @Autowired
    private PropertiesReader pr;

    @PostConstruct
    public void initAppDistribution() throws Exception {
//        getBearerAccessToken();
        refreshToken();
        switch (pr.getDevice()) {
            case "android", "android_emulator" -> {
                if (!pr.getAndroidAppUrl().equals("")) releaseBinary = new URL(pr.getAndroidAppUrl());
                else if (pr.getAndroidReleaseId().equals(""))
                    releaseBinary = getReleaseLatest(pr.getAndroidProjectId(), pr.getAndroidAppId());
                else
                    releaseBinary = getRelease(pr.getAndroidProjectId(), pr.getAndroidAppId(), pr.getAndroidReleaseId());
            }
            case "ios", "ios_emulator" -> {
                if (!pr.getAndroidAppUrl().equals("")) releaseBinary = new URL(pr.getAndroidAppUrl());
                else if (pr.getIosReleaseId().equals(""))
                    releaseBinary = getReleaseLatest(pr.getIosProjectId(), pr.getIosAppId());
                else releaseBinary = getRelease(pr.getIosProjectId(), pr.getIosAppId(), pr.getIosReleaseId());
            }
            default -> {
            }
        }
    }

    public URL getReleaseBinary() {
        return releaseBinary;
    }

    private void initDriver() throws IOException, ParseException {
        getCredential();
        //[--disable-web-security, '--user-data-dir', '--allow-running-insecure-content' ]
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        webDriver = new ChromeDriver(chromeOptions);
        authURL = pr.getGoogleAuthUri() + pr.getGoogleAuthPath() + "?scope=" + pr.getGoogleCloudScope() + "&auth_url=" + pr.getGoogleAuthUri() + pr.getGoogleAuthPath() + "&client_id=" + clientId +
                "&response_type=" + pr.getGoogleHeaderResponsetype() + "&redirect_uri=" + pr.getGoogleRedirectUri() + "&state=" + pr.getGoogleHeaderType();
        System.out.println(authURL);
    }

    private void getCredential() throws IOException, ParseException {
        // Get credential
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("src/main/resources/credentials.json");
        JSONObject cred = (JSONObject) parser.parse(reader);
        JSONObject installed = (JSONObject) cred.get("installed");
        clientId = installed.get("client_id").toString();
        clientSecret = installed.get("client_secret").toString();
    }

    private void getCodeThroughBrowserAuthentication() throws Exception {
        initDriver();
        webDriver.get(authURL);
        webDriver.findElement(By.cssSelector("input[type='email']")).sendKeys(Encryption.decryptData(pr.getGoogleEmail()));
        webDriver.findElement(By.xpath("//span[text()='Next']")).click();
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("input[type='password']")).sendKeys(Encryption.decryptData(pr.getGooglePassword()));
        webDriver.findElement(By.xpath("//span[text()='Next']")).click();
        Thread.sleep(3000);
        try {
            webDriver.findElement(By.xpath("//span[text()='Allow']")).click();
        } catch (WebDriverException e) {
            // do nothing
        }
        Thread.sleep(3000);
        //http://localhost:8888/Callback?state=empty&code=4/0AfJohXnRd0faCclm3Lnm0KSelH8Dl-sNV3PKRQnbvwfp-pZ4T4cow5ygWPVT5E9_-nwt-w&scope=https://www.googleapis.com/auth/cloud-platform
        String[] arr = webDriver.getCurrentUrl().split("&code=");
        browserCode = arr[1].split("&scope=");
        webDriver.quit();
    }

    private void getBearerAccessToken() throws Exception {
//        getCodeThroughBrowserAuthentication();
        RestAssured.baseURI = "https://www.googleapis.com";
        Response res = given().urlEncodingEnabled(false)
//                .queryParam("code", browserCode[0])
                .queryParam("code", "4/0AfJohXlyjFC0BVMYqs3oN74ZBQak1YfOyP1L0kDiW_KtvN_du3XuAdortfo7T9SddHGOyQ")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", pr.getGoogleRedirectUri())
                .queryParam("grant_type", "authorization_code").
                when()
                .post("/oauth2/v4/token").
                then()
                .assertThat()//.statusCode(200)
                .extract().response();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(res.getBody().prettyPrint());
        writeString("tokens/StoredCredentialToken", (String) json.get("access_token"));
        writeString("tokens/StoredCredentialRefresh", (String) json.get("refresh_token"));
    }

    private void refreshToken() throws IOException, ParseException {
        getCredential();
        RestAssured.baseURI = "https://www.googleapis.com";
        Response res = given().urlEncodingEnabled(false)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", readString("tokens/StoredCredentialRefresh").trim())
                .queryParam("access_type", "offline")
                .when()
                .post("/oauth2/v4/token")
                .then()
                .assertThat().statusCode(200).extract().response();
        JsonPath json = res.jsonPath();
        writeString("tokens/StoredCredentialToken", json.get("access_token"));
        Log.info("Google OAuth 2.0: Token has been refreshed.");
    }

    public URL getReleaseLatest(String projectId, String appId) throws Exception {
        System.out.println(readString("tokens/StoredCredentialToken").trim());
        Response res = given()
                .auth()
                .preemptive()
                .oauth2(readString("tokens/StoredCredentialToken").trim())
                .when()
                .request(Method.GET, String.format("https://firebaseappdistribution.googleapis.com/v1/projects/%s/apps/%s/releases/", projectId, appId));
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(res.getBody().prettyPrint());
        JSONArray releases = (JSONArray) response.get("releases");
        JSONObject latest = (JSONObject) releases.get(0);
        return new URL(latest.get("binaryDownloadUri").toString());
    }

    public URL getRelease(String projectId, String appId, String releaseId) throws Exception {
        System.out.println(readString("tokens/StoredCredentialToken").trim());
        Response res = given()
                .auth()
                .preemptive()
                .oauth2(readString("tokens/StoredCredentialToken").trim())
                .when()
                .request(Method.GET, String.format("https://firebaseappdistribution.googleapis.com/v1/projects/%s/apps/%s/releases/%s", projectId, appId, releaseId));
        JsonPath json = res.jsonPath();
        JsonPath release = json.get("releases");
        System.out.println(release);
        return new URL(json.get("binaryDownloadUri"));
    }


}
