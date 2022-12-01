package com.example.myemissions;

import android.content.Context;
import android.util.Log;

import java.io.FileWriter;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class FileInterface {
    String dataFilePath;
    String settingsFilePath;
    String workingDirectory;

    File dataFile;
    FileWriter dataWrite;
    Scanner dataRead;
    File settingsFile;
    FileWriter settingsWrite;
    Scanner settingsRead;

    EmissionCalculator calc = new EmissionCalculator();

    //Constructor. Pass in context using 'this' and pass in our current user
    //using the username passed around through Intent.
    public FileInterface(Context context, String username) {
        workingDirectory = context.getFilesDir().getPath();
        dataFilePath = workingDirectory + "/data_" + username + ".txt";
        settingsFilePath = workingDirectory + "/settings_" + username + ".txt";

        dataFile = new File(dataFilePath);
        settingsFile = new File(settingsFilePath);

        Log.i("DEBUG", workingDirectory);

        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            }
            catch (Exception ex){
                ex.getStackTrace();
            }
        }

        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
            }
            catch (Exception ex){
                ex.getStackTrace();
            }
        }
    }

    //Writes emissions data to file. First parameter is an emission type from
    //EmissionCalculator class, second parameter is the relevant data for that
    //type, such as miles for vehicles, or kilowatt-hours for energy, etc. Last
    //parameter is the given unit.
    //File format is as follows, with tab characters as spaces:
    //Date:MM/DD/YYYY   Time:HH:MM A    Type:emission_type  Value:0.00
    public void addNewEmission(EmissionCalculator.Source source, double data, String unit) {
        try{dataWrite = new FileWriter(dataFile, true);}
        catch(Exception ex){ex.getStackTrace();};
        String date = java.text.DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        String time = java.text.DateFormat.getTimeInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        try {
            dataWrite.write("Date:" + date + "\t" + "Time:" + time + "\t" + "Source:" + source.name + "\t" + "Value:" + data + "\t" + "Unit:" + unit + "\t\n");
            dataWrite.flush();

        }
        catch (Exception ex){
            ex.getStackTrace();
        }
        try{dataWrite.close();}
        catch(Exception ex){ex.getStackTrace();};
    }

    //Returns an ArrayList of strings containing all the emissions data
    //from a specific passed in date, sorted from least to most recent.
    public ArrayList<String> getEmissionsFromDate(String date) {
        ArrayList<String> emissions = new ArrayList<String>();
        String buffer;
        try{dataRead = new Scanner(dataFile);}
        catch(Exception ex){ex.getStackTrace();};
        while(dataRead.hasNextLine()) {
            buffer = dataRead.nextLine();
            if (buffer.substring(5, 5 + date.length()).equals(date)) {
                emissions.add(0, buffer);
            }
        }
        try{dataRead.close();}
        catch(Exception ex){ex.getStackTrace();};
        return emissions;
    }

    //Calls above function, but with the current date as of running the app.
    //Called in Home page activity.
    public ArrayList<String> getEmissionsFromCurrentDate() {
        String currentDate = java.text.DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
        return this.getEmissionsFromDate(currentDate);
    }

    //Places entirety of the emissions data into an ArrayList. To be called
    //when 'See all' is selected on the home page.
    public ArrayList<String> getAllEmissions() {
        try{dataRead = new Scanner(dataFile);}
        catch(Exception ex){ex.getStackTrace();};
        ArrayList<String> emissions = new ArrayList<String>();
        String buffer;

        while(dataRead.hasNextLine()) {
            buffer = dataRead.nextLine();
            emissions.add(0 ,buffer);
        }
        try{dataRead.close();}
        catch(Exception ex){ex.getStackTrace();};
        return emissions;
    }
}
