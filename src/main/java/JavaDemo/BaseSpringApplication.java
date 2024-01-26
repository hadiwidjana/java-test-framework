package JavaDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static JavaDemo.Integrations.Encryption.Encryption.decryptData;
import static JavaDemo.Integrations.GoogleAPI.GDrive.getGDriveFileList;

@SpringBootApplication
public class BaseSpringApplication {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        SpringApplication.run(BaseSpringApplication.class, args);
    }
}
