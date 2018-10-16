package com.glackemi.alyusra;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

/*import com.blikoon.qrcodescanner.QrCodeActivity;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MakeOrder extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    ArrayAdapter<String> adapter ;
    String order;
    Spinner  spinner;
    Spinner  spinner1;
    Spinner  spinner2;
    ArrayList<CharSequence> arrayList = new ArrayList<>();
    EditText phone, amount,mobileNotification;
    Button ordernow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeorder);

        ordernow = (Button) findViewById(R.id.pay);
        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MakeOrder.this,"Order Posted!",Toast.LENGTH_LONG).show();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, arrayList);
//        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        order = String.valueOf(parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }

}
