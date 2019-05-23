package com.example.recyclerview;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddContact extends AppCompatActivity {


    String i, name, date, description, photoPath = null ;
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
            pic = PicUtils.decodePic(photoPath,
                    200,
                    200) ;
            imPic.setImageBitmap(pic);

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
        } else if (!validateDate(etDate.getText().toString())) {
            Toast.makeText(this, "Please enter a valid date! (format DD/MM/YYYY)", Toast.LENGTH_SHORT).show();
        } else {
            String delim = ";";
            String line, fileName;
            name = etName.getText().toString();
            date = etDate.getText().toString();
            description = etDesc.getText().toString();

            i = getCount("counter2.txt") ;
            if(i == null || i=="0")
                i = "1" ;
            else {
                int temp  = Integer.parseInt(i);
                temp ++ ;
                i = String.valueOf(temp);
            }
            fileName = "file" + i + ".txt";
            if(flag){
                ApplicationClass.people.add(new Person(i, name, date, description, photoPath));
                line = i + delim + name + delim + date + delim + description + delim + photoPath ;

            } else{
                ApplicationClass.people.add(new Person(i, name, date, description));
                line = i + delim + name + delim + date + delim + description ;
            }

            if (saveStringToFile(fileName, line)) {
                incrementCount("counter.txt");
                incrementCount("counter2.txt");
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

    private String getCount (String fileName) {
        String line ;
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(fileInputStream.getFD()));
            if((line = reader.readLine())!= null) {
                return line;
            }else{
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
    }

    private void incrementCount(String fileName) {

        String line;
        int i ;
        line = getCount(fileName);
        if(line != null) {
            i = Integer.parseInt(line);
            i++;
            line = "" + i ;
        }else{
            line = "1" ;
        }
        saveStringToFile(fileName,  line);
    }


    private boolean validateDate(String date){
        String regex = "^(1[0-2]|0[1-9])/(3[01]"
                + "|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex) ;
        Matcher matcher = pattern.matcher((CharSequence)date);
        return matcher.matches() ;



    }
}
