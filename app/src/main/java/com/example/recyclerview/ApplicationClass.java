package com.example.recyclerview;

import android.app.Application;

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
        String line,id,  name, date, desc, pic ,  fileName ;

        String[] allLine;

        int count = getCount() ;
        int i = 1, c=0;
        while (c < count ){
            fileName = "file" + i + ".txt";
            try {
                FileInputStream fileInputStream = openFileInput(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(fileInputStream.getFD()));
                if((line = reader.readLine()) != null && !line.equals("")) {
                    allLine = line.split(delim);
                    id = String.valueOf(i) ;
                    name = allLine[1];
                    date = allLine[2];
                    desc = allLine[3];
                    try {
                        pic = allLine[4];
                        people.add(new Person(id,name, date, desc, pic));
                        c++ ;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        people.add(new Person(id,name, date, desc));
                        c++ ;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

     public int getCount () {
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
