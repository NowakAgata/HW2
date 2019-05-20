package com.example.recyclerview;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class AddContact extends AppCompatActivity {


    String name, date, description, photoPath = null ;
    EditText etName, etDate, etDesc ;
    Bitmap pic ;
    ImageView imPic;
    boolean flag ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_contact);

        etName = findViewById(R.id.name);
        etDate = findViewById(R.id.birthDate);
        etDesc = findViewById(R.id.description) ;
        imPic = findViewById(R.id.imageView);



        Intent i = getIntent();
        photoPath = i.getStringExtra("PIC_PATH") ;
        if(photoPath != null){
//            int width = imPic.getMaxWidth();
//            int height = imPic.getMaxHeight();
            pic = PicUtils.decodePic(photoPath,
                    200,
                    200) ;
            imPic.setImageBitmap(pic);
            Toast.makeText(this, "ustawiam fotkę "+photoPath, Toast.LENGTH_SHORT).show();
            flag = true ;
        }else {
            imPic.setImageResource(R.drawable.avatar_2);
            flag = false;
        }
    }

    public void add_contact(View view) {

        if(etName.getText().toString().isEmpty() || etDate.getText().toString().isEmpty()
                || etDesc.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter all data!", Toast.LENGTH_SHORT).show();
        }else {
            String delim = ";";
            String line, fileName;
            name = etName.getText().toString();
            date = etDate.getText().toString();
            description = etDesc.getText().toString();
            if(flag){
                ApplicationClass.people.add(new Person(name, date, description, photoPath));
                line = name + delim + date + delim + description + delim + photoPath ;

            } else{
                ApplicationClass.people.add(new Person(name, date, description));
                line = name + delim + date + delim + description ;
            }
            fileName = "file" + getCount() + ".txt";
            if (saveStringToFile(fileName, line)) {
                incrementCount();
                setResult(1);
                finish();
            } else {
                setResult(0);
                finish();
            }
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

    private void incrementCount() {

        String line ;
        int i ;
        line = getCount();
        if(line != null) {
            i = Integer.parseInt(line);
            i = i+1;
            line = "" + i ;
        }else{
            line = "0" ;
        }
        saveStringToFile("counter.txt",line) ;
    }


}
