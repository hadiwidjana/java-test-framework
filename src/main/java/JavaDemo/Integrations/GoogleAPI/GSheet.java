package JavaDemo.Integrations.GoogleAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static JavaDemo.Integrations.GoogleAPI.GDrive.getGDriveFileId;
import static JavaDemo.Integrations.GoogleAPI.GDrive.getGDriveFileList;

@Component
public class GSheet extends GoogleAPI {

    @Value("${spring.profiles.active}")
    String environmentName;

    private static Sheets initSheetService() {
        final NetHttpTransport HTTP_TRANSPORT;
        Sheets service = null;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return service;
    }


    public static String createSpreadsheet(String title) throws IOException, GeneralSecurityException {

        Sheets service = initSheetService();

        // Create new spreadsheet with a title
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle(title));
        spreadsheet = service.spreadsheets().create(spreadsheet)
                .setFields("spreadsheetId")
                .execute();
        // Prints the new spreadsheet id
        System.out.println("Spreadsheet Created! ID: " + spreadsheet.getSpreadsheetId());
        return spreadsheet.getSpreadsheetId();
    }

    /**
     * Returns a range of values from a spreadsheet.
     *
     * @param spreadsheetId - Id of the spreadsheet.
     * @param range         - Range of cells of the spreadsheet.
     * @return Values in the range
     * @throws IOException - if credentials file not found.
     */
    public static ValueRange getSpreadsheetValues(String spreadsheetId, String range) {

        // Create the sheets API client
        Sheets service = initSheetService();

        ValueRange result = null;
        try {
            // Gets the values of the cells in the specified range.
            result = service.spreadsheets().values().get(spreadsheetId, range).execute();
            int numRows = result.getValues() != null ? result.getValues().size() : 0;
            System.out.printf("%d rows retrieved.", numRows);
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n", spreadsheetId);
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Sets values in a range of a spreadsheet.
     *
     * @param spreadsheetId    - Id of the spreadsheet.
     * @param range            - Range of cells of the spreadsheet.
     * @param valueInputOption - Determines how input data should be interpreted.
     * @param values           - List of rows of values to input.
     * @return spreadsheet with updated values
     * @throws IOException - if credentials file not found.
     */
    public static UpdateValuesResponse updateSpreadsheetValues(String spreadsheetId,
                                                               String range,
                                                               String valueInputOption,
                                                               List<List<Object>> values) {

        Sheets service = initSheetService();

        UpdateValuesResponse result = null;
        try {
            // Updates the values in the specified range.
            ValueRange body = new ValueRange()
                    .setValues(values);
            result = service.spreadsheets().values().update(spreadsheetId, range, body)
                    .setValueInputOption(valueInputOption)
                    .execute();
            System.out.printf("%d cells updated.", result.getUpdatedCells());
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n", spreadsheetId);
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    public static String initTestDataSpreadsheet(String spreadsheetName) {
        //create spreadsheet if it's not exist
        String spreadsheetId = "";
        try {
            if (getGDriveFileList().contains(spreadsheetName)) {
                spreadsheetId = getGDriveFileId(spreadsheetName);
            } else {
                spreadsheetId = createSpreadsheet(spreadsheetName);
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //
        return spreadsheetId;
    }

}
