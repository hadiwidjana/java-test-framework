package com.satria.javatestframework.utils.Integration.GoogleAPI;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.satria.javatestframework.utils.Integration.GoogleAPI.GDrive.getGDriveFileId;
import static com.satria.javatestframework.utils.Integration.GoogleAPI.GDrive.getGDriveFileList;


@Component
public class GSheet extends GoogleAPI{

    @Value("${spring.profiles.active}")
    String environmentName;

    private static Sheets initSheetService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
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
    public static ValueRange getSpreadsheetValues(String spreadsheetId, String range) throws IOException, GeneralSecurityException {

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
                throw e;
            }
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
                                                    List<List<Object>> values)
            throws IOException, GeneralSecurityException {

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
                throw e;
            }
        }
        return result;
    }



    public static String initTestDataSpreadsheet(String spreadsheetName) throws GeneralSecurityException, IOException {
        //create spreadsheet if it's not exist
        String spreadsheetId = "";
        if(getGDriveFileList().contains(spreadsheetName)){
            spreadsheetId = getGDriveFileId(spreadsheetName);
        } else {
            spreadsheetId = createSpreadsheet(spreadsheetName);
        }

        //
        return spreadsheetId;
    }

}
