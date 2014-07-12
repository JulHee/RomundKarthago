package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 12.07.14
 * Year    : 2014
 */
public class Logger {

    FileWriter fileWriter;
    File file;
    Date date;


    public Logger() {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yy.HH-mm-ss");
            date = new Date();
            String time = df.format(date);
            this.file = new File("log", time + "_RuK.log");
            System.out.println("Der Log wird nach: "+file.getAbsolutePath()+" geschrieben");
            System.out.println(" ");
            this.fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return file.getName();
    }

    public void log(String log) {
        Date date = new Date();
        //getTime() returns current time in milliseconds
        long time = date.getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp ts = new Timestamp(date.getTime());
        if (log != null) {
            try {
                fileWriter.write("[" + ts.toString() + "] : " + log + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
