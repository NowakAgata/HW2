package com.example.recyclerview;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ApplicationClass extends Application {

    public static ArrayList<Person> people;

    @Override
    public void onCreate() {
        super.onCreate();
        people = new ArrayList<Person>();
        String delim = ";";
        String line, name, date, desc, pic ,  fileName ;
        String[] allLine;
        int count = getCount() ;
        for (int i=0; i < count; i++ ){
            fileName = "file" + i + ".txt";
            try {
                FileInputStream fileInputStream = openFileInput(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(fileInputStream.getFD()));
                if((line = reader.readLine()) != null) {
                    allLine = line.split(delim);
                    name = allLine[0];
                    date = allLine[1];
                    desc = allLine[2];
                    try {
                        pic = allLine[3];
                        people.add(new Person(name, date, desc, pic));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        people.add(new Person(name, date, desc));
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private int getCount () {
        String line ;
        try {
            FileInputStream fileInputStream = openFileInput("counter.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fileInputStream.getFD()));
            if((line = reader.readLine())!= null) {
                return Integer.parseInt(line);
            }else{
                return 0 ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;

        } catch (IOException e) {
            e.printStackTrace();
            return 0 ;
        }
    }

}
