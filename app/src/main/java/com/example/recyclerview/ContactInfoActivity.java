package com.example.recyclerview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

    }

    public void ok_button(View view) {
        finish();
    }
}
