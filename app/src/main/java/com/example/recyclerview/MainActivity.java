package com.example.recyclerview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements
        DeleteDialogFragment.OnDeleteDialogFragmentInteractionListener
{

    static final int REQUEST_IMAGE_CAPTURE = 2 ;
    final private int currentItemPosition = -1;
   // public static final String taskExtra = "taskExtra" ;
    String photoPath ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivityForResult(intent, 1);
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
        Intent intent = new Intent(getApplicationContext(), ContactInfoActivity.class);
        intent.putExtra("TAG", i);
        startActivityForResult(intent, 3);
    }


    private void showDeleteDialog() {
        DeleteDialogFragment.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1) {
            if (resultCode == 1) {
                Toast.makeText(this, "Person added succesfully", Toast.LENGTH_SHORT).show();
                ((ContactList) getSupportFragmentManager().findFragmentById(R.id.listFrag)).notifyDataChange();

            } else if (resultCode == 0) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        } else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Intent i = new Intent(getApplicationContext(), AddContact.class) ;
            i.putExtra("PIC_PATH", photoPath);
            startActivityForResult(i, 1);


        }

    }

    @Override
    public void onDialogPositiveClick(DeleteDialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < ApplicationClass.people.size()) {
            Toast.makeText(this, "positive click" , Toast.LENGTH_SHORT).show();

//            if( removeItem(currentItemPosition) ) {
//                ((ContactList) getSupportFragmentManager().findFragmentById(R.id.listFrag)).notifyDataChange();
//            }
        }
    }

    @Override
    public void onDialogNegativeClick(DeleteDialogFragment dialog) {
        Toast.makeText(this, "negative click" , Toast.LENGTH_SHORT).show();

    }

    private boolean removeItem(int index){
        Toast.makeText(this, "usunięto: "+ index, Toast.LENGTH_SHORT).show();

        return true;
    }


    public void delete_row(View view) {
        showDeleteDialog();
    }
}