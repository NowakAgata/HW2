package com.example.recyclerview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity
{

    static final int REQUEST_ADD_PERSON =1;
    static final int REQUEST_IMAGE_CAPTURE = 2 ;
    static final int REQUEST_DELETE_PERSON = 3;
    String photoPath ;
    int currentPerson =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void displayInfoTask(int person) {
        ContactInfoFragment contactInfoFragment = ((ContactInfoFragment) getSupportFragmentManager().findFragmentById(R.id.infoFrag));
        if(contactInfoFragment != null){
            contactInfoFragment.displayPerson(person);
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" ;
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        photoPath = image.getAbsolutePath();
        return image ;
    }

    public void add_contact(View view) {
        Intent intent = new Intent(getApplicationContext(), AddContact.class);
        startActivityForResult(intent, REQUEST_ADD_PERSON);
    }

    public void addPhoto(View view)  {
        Intent takePictureIntent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null ){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch(IOException e){

            }
            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        getString(R.string.myFileprovider),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void on_row_click(View view) {
        int i = (int) view.getTag();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            displayInfoTask(i);


        } else{
            Intent intent = new Intent(getApplicationContext(), ContactInfoActivity.class);
            intent.putExtra("TAG", i);
            startActivityForResult(intent, 2);
        }


    }

    public void delete_row(View view) {
        currentPerson = (int) view.getTag();
        showDeleteDialog();
    }

    public void showDeleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_dialog_tag));
        builder.setMessage(getString(R.string.delete_question));
        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(getApplicationContext(), DeletePerson.class) ;
                i.putExtra("PERSON", currentPerson);
                startActivityForResult(i, REQUEST_DELETE_PERSON);

            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String temp =  ApplicationClass.people.get(currentPerson).getName() ;
                Toast.makeText(getApplicationContext(), "Deleting person "+temp +" canceled", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_ADD_PERSON) {
            if (resultCode == 1) {
                Toast.makeText(this, "Person added succesfully", Toast.LENGTH_SHORT).show();
                ((ContactList) getSupportFragmentManager().findFragmentById(R.id.listFrag)).notifyDataChange();

            } else if (resultCode == 0) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        } else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Intent i = new Intent(getApplicationContext(), AddContact.class) ;
            i.putExtra("PIC_PATH", photoPath);
            startActivityForResult(i, REQUEST_ADD_PERSON);
        } else if(requestCode == REQUEST_DELETE_PERSON){
            if(resultCode == 1) {
                Toast.makeText(this, "Person deleted succesfully", Toast.LENGTH_SHORT).show();
                ((ContactList) getSupportFragmentManager().findFragmentById(R.id.listFrag)).notifyDataChange();

            } else if(resultCode==0){
                Toast.makeText(this, "Person is not deleted, something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

}