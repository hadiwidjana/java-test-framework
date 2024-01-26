package JavaDemo.Integrations.SpringBoot;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertiesReader {
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
    @Value("${device}")
    private String device;

}
