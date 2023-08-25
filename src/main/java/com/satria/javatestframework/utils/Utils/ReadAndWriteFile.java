package com.satria.javatestframework.utils.Utils;



import com.satria.javatestframework.utils.Logger.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadAndWriteFile {

    public static void writeString(String fileLocation, String stringData) {
        try (FileWriter file = new FileWriter(fileLocation)){
            file.write(stringData);
            file.flush();
            file.close();
//            waitFileToExist(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitFileToExist(String fileLocation) throws InterruptedException {
        File file = new File(fileLocation);
        while(!file.exists()){
            Thread.sleep(1000);
        }
    }

    public static String readString(String fileLocation) {
        char[] text = new char[1000];
        try (FileReader file = new FileReader(fileLocation)) {
            file.read(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(text);
    }


    public static void deleteFile(String fileName) {
        File myObj = new File(fileName);
        if (myObj.delete()) {
            Log.info("Deleted the file: " + myObj.getName());
        } else {
            Log.info("Failed to delete the file.");
        }
    }

    public static String generateJsonName(Class cls,String description){
        return cls.getSimpleName()+"_"+description+".json";
    }

}
