package com.example.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DeletePerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int i = intent.getIntExtra("PERSON" ,0);
        deletePerson(i);

    }

    private void deletePerson(int i) {

        String personId = ApplicationClass.people.get(i).getId();
        String fileName = "file"+personId+".txt";
        ApplicationClass.people.remove(i);
        if(saveStringToFile(fileName, "")){
            decrementCount();
            setResult(1);
            finish();
        } else{
            setResult(0);
            finish();
        }
    }

    private boolean saveStringToFile(String fileName, String line) {

        try(
                FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE)){
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutputStream.getFD()));
            writer.write(line);
            writer.newLine();
            writer.close();

            return true ;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getCount () {
        String line ;
        try {
            FileInputStream fileInputStream = openFileInput("counter.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fileInputStream.getFD()));
            if((line = reader.readLine())!= null) {
                return line;
            }else{
                return null ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
    }

    private void decrementCount() {

        String line ;
        int i ;
        line = getCount();
        if(line != null) {
            i = Integer.parseInt(line);
            i--;
            line = "" + i ;
        }else{
            line = "0" ;
        }
        saveStringToFile("counter.txt",line) ;
    }


}
