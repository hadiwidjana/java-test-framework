package JavaDemo.BE;


import JavaDemo.Integrations.Logger.Log;
import JavaDemo.Integrations.Utils.ReadAndWriteFile;
import JavaDemo.Integrations.Encryption.Encryption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.ToNumberPolicy;
import io.qameta.allure.Attachment;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static JavaDemo.Integrations.GoogleAPI.GDrive.*;

@Lazy
@Component
public class XmlRpcBaseMethod extends RestBaseMethod {
    @Value("${OdooWebUrl}")
    protected String BASE_URL;
    @Value("${UserSetupAdminOdoo}")
    protected String USERNAME;
    @Value("${PasswordSetupAdminOdoo}")
    protected String PASSWORD;
    @Value("${odoo.rpc.db}")
    protected String DB;
    @Value("${odoo.rpc.common}")
    protected String odooRpcCommon;
    @Value("${odoo.rpc.object}")
    protected String odooRpcObject;


    public int login() throws XmlRpcException, MalformedURLException {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setEnabledForExtensions(true);
        config.setServerURL(new URL(BASE_URL + odooRpcCommon));
        client.setConfig(config);
        //Connect
        Object[] params = new Object[]{DB, Encryption.decryptData(USERNAME), Encryption.decryptData(PASSWORD)}; // Ok & simple
        Object uid = client.execute("login", params);
        if (uid instanceof Integer) return (int) uid;
        return -1;
    }

    public XmlRpcClient clientInit() throws MalformedURLException, XmlRpcException {
        XmlRpcClient client = new XmlRpcClient();
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setEnabledForExtensions(true);
        config.setServerURL(new URL(BASE_URL + odooRpcObject));
        client.setConfig(config);
        return client;
    }

    public Object queryParam(String json) {
        Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
        return gson.fromJson(JsonParser.parseString(json), Object.class);
    }

    public Object[] queryParamArray(String json) {
        return new Gson().fromJson(JsonParser.parseString(json), Object[].class);
    }

    public String parseResponse(Object[] response) {
        Gson gson = new Gson();
        return prettyPrint(gson.toJson(response));
    }

    public String parseResponse(Object response) {
        Gson gson = new Gson();
        return prettyPrint(gson.toJson(response));
    }

    @Attachment(value = "JSON Attachment", type = "text/json")
    public byte[] attachResponse(String response) {
        return response.getBytes();
    }

    public void uploadResponse(String response, String fileName) {
        //create json file
        ReadAndWriteFile.writeString(fileName + ".json", response);
        //delete gdrive old file
        try {
            deleteFile(fileName);
        } catch (NullPointerException e) {
            Log.infoYellow("No file with the same name found on GDrive");
        }
        //upload
        try {
            uploadGDrive(fileName + ".json", "text/json", "1QtA3EOXky_ym6eISsstjjdOgLI-X83X_");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //delete json
        ReadAndWriteFile.deleteFile(fileName + ".json");
    }

    public String getJson(String fileName) {
        //download
        downloadGdriveAndWriteFile(fileName+".json");
        //read
        String readJson = ReadAndWriteFile.readString(fileName + ".json");
        //delete json
        ReadAndWriteFile.deleteFile(fileName + ".json");
        return readJson;
    }


}
