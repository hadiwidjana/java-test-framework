package JavaDemo.Integrations.SpringBoot;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertiesReader {

    @Value("${spring.profiles.active}")
    private String environment;

    //device
    @Value("${device}")
    private String device;
    @Value("${device.headless}")
    private boolean deviceHeadless;
    @Value("${device.size}")
    private String deviceSize;

    //android
    @Value("${android.name}")
    private String androidName;
    @Value("${android.package}")
    private String androidPackage;
    @Value("${android.activity}")
    private String androidActivity;
    @Value("${android.udid}")
    private String androidUdid;
    @Value("${android.google.project.id}")
    private String androidProjectId;
    @Value("${android.google.app.id}")
    private String androidAppId;
    @Value("${android.google.release.id}")
    private String androidReleaseId;
    @Value("${android.drive.url}")
    private String androidDriveLink;

    //ios
    @Value("${ios.name}")
    private String iosName;
    @Value("${ios.platform}")
    private String iosPlatform;
    @Value("${ios.udid}")
    private String iosUdid;
    @Value("${ios.google.project.id}")
    private String iosProjectId;
    @Value("${ios.google.app.id}")
    private String iosAppId;
    @Value("${ios.google.release.id}")
    private String iosReleaseId;

    @Value("${highlight.element}")
    private boolean highlightElement;
    @Value("${maxPageLoadTime}")
    private int maxPageLoadTime;
    @Value("${screenshot.element}")
    private boolean screenshotElement;
    @Value("${record.mode}")
    private String recordMode;

    @Value("${google.auth.uri}")
    private String googleAuthUri;
    @Value("${google.auth.path}")
    private String googleAuthPath;
    @Value("${google.header.responsetype}")
    private String googleHeaderResponsetype;
    @Value("${google.header.type}")
    private String googleHeaderType;
    @Value("${google.redirect.uri}")
    private String googleRedirectUri;
    @Value("${google.cloud.scope}")
    private String googleCloudScope;
    @Value("${google.email}")
    private String googleEmail;
    @Value("${google.password}")
    private String googlePassword;


    @Value("${website.url}")
    private String websiteUrl;
    @Value("${user.email}")
    private String userEmail;
    @Value("${user.password}")
    private String userPassword;
    @Value("${user.firstname}")
    private String userFirstName;
    @Value("${user.lastname}")
    private String userLastName;
    @Value("${user.telephone}")
    private String userTelephone;

}
