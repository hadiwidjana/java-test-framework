package JavaDemo.Integrations.GoogleAPI;

import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.SpringBoot.BaseTest;
import JavaDemo.Integrations.Encryption.Encryption;
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

    @Value("${redirectUri}")
    private String redirectUri;
    @Value("${google.cloud.scope}")
    private String scope;
    @Value("${google.email}")
    private String username;
    @Value("${google.password}")
    private String password;
    private String authURL;
    private String browserCode[];
    @Value("${google.auth.uri}")
    private String baseUri;
    @Value("${google.auth.path}")
    private String resource;
    @Value("${google.header.responsetype}")
    private String responseType;
    @Value("${google.header.type}")
    private String state;
    public URL releaseBinary;

    @Value("${android.google.project.id}")
    private String androidProjectId;
    @Value("${android.google.app.id}")
    private String androidAppId;
    @Value("${android.google.release.id}")
    private String androidReleaseId;
    @Value("${android.drive.url}")
    private String androidDriveLink;
    @Value("${ios.google.project.id}")
    private String iosProjectId;
    @Value("${ios.google.app.id}")
    private String iosAppId;
    @Value("${ios.google.release.id}")
    private String iosReleaseId;
    @Value("${device}")
    private String device;

    @PostConstruct
    public void initAppDistribution() throws Exception {
//        getBearerAccessToken();
        refreshToken();
        switch (device){
            case "android","android_emulator"->{
                if (!androidDriveLink.equals("")) releaseBinary=new URL(androidDriveLink);
                else if (androidReleaseId.equals("")) releaseBinary=getReleaseLatest(androidProjectId,androidAppId);
                else releaseBinary=getRelease(androidProjectId,androidAppId,androidReleaseId);
            }
            case "ios","ios_emulator"->{
                if(iosReleaseId.equals("")) releaseBinary=getReleaseLatest(iosProjectId,iosAppId);
                else releaseBinary=getRelease(iosProjectId,iosAppId,iosReleaseId);
            }
            default -> {}
        }
    }



    private void initDriver() throws IOException, ParseException {
        getCredential();
        webDriver = new ChromeDriver();
        authURL = baseUri+resource+"?scope="+scope+"&auth_url="+baseUri+resource+"&client_id="+clientId+
                "&response_type="+responseType+"&redirect_uri="+redirectUri+"&state="+state;
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
        webDriver.findElement(By.cssSelector("input[type='email']")).sendKeys(Encryption.decryptData(username));
        webDriver.findElement(By.xpath("//span[text()='Next']")).click();
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("input[type='password']")).sendKeys(Encryption.decryptData(password));
        webDriver.findElement(By.xpath("//span[text()='Next']")).click();
        Thread.sleep(3000);
        try {
            webDriver.findElement(By.xpath("//span[text()='Allow']")).click();
        } catch (WebDriverException e){
            // do nothing
        }
        Thread.sleep(3000);
        String[] arr =  webDriver.getCurrentUrl().split("&code=");
        browserCode = arr[1].split("&scope=");
        webDriver.quit();
    }

    private void getBearerAccessToken() throws Exception {
        getCodeThroughBrowserAuthentication();
        RestAssured.baseURI="https://www.googleapis.com";
        Response res = given().urlEncodingEnabled(false)
//                .queryParam("code", browserCode[0])
                .queryParam("code","4/0AfJohXlWkvrD7ROlQxz9eM9mV5vexg_YMUTMfO6-WKt5krCMkOwBBSAEVR_GMY3JvyitmA")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("grant_type","authorization_code").
                when()
                .post("/oauth2/v4/token").
                then()
                .assertThat().statusCode(200).extract().response();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(res.getBody().prettyPrint());
        writeString("tokens/StoredCredentialToken", (String) json.get("access_token"));
        writeString("tokens/StoredCredentialRefresh",(String) json.get("refresh_token"));
    }
    private void refreshToken() throws IOException, ParseException {
        getCredential();
        RestAssured.baseURI="https://www.googleapis.com";
        Response res = given().urlEncodingEnabled(false)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type","refresh_token")
                .queryParam("refresh_token",readString("tokens/StoredCredentialRefresh").trim())
                .queryParam("access_type","offline")
                .when()
                .post("/oauth2/v4/token")
                .then()
                .assertThat().statusCode(200).extract().response();
        JsonPath json = res.jsonPath();
        writeString("tokens/StoredCredentialToken",json.get("access_token"));
        Log.info("Google OAuth 2.0: Token has been refreshed.");
    }

    public URL getReleaseLatest(String projectId, String appId) throws Exception {
        System.out.println(readString("tokens/StoredCredentialToken").trim());
        Response res = given()
                .auth()
                .preemptive()
                .oauth2(readString("tokens/StoredCredentialToken").trim())
                .when()
                .request(Method.GET, String.format("https://firebaseappdistribution.googleapis.com/v1/projects/%s/apps/%s/releases/",projectId,appId));
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(res.getBody().prettyPrint());
        JSONArray releases= (JSONArray) response.get("releases");
        JSONObject latest = (JSONObject) releases.get(0);
        return new URL(latest.get("binaryDownloadUri").toString());
    }

    public URL getRelease(String projectId, String appId,String releaseId) throws Exception {
        System.out.println(readString("tokens/StoredCredentialToken").trim());
        Response res = given()
                .auth()
                .preemptive()
                .oauth2(readString("tokens/StoredCredentialToken").trim())
                .when()
                .request(Method.GET, String.format("https://firebaseappdistribution.googleapis.com/v1/projects/%s/apps/%s/releases/%s",projectId,appId,releaseId));
        JsonPath json = res.jsonPath();
        JsonPath release = json.get("releases");
        System.out.println(release);
        return new URL(json.get("binaryDownloadUri"));
    }



}
