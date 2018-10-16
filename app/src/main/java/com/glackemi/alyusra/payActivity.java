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

//import com.blikoon.qrcodescanner.QrCodeActivity;
import com.glackemi.alyusra.R;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class payActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button pay;
    ProgressBar progressBar;
    Spinner  spinner;
    Spinner spinner1;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter ;
    EditText phone, amount,mobileNotification;
    String PayBillNumber;
    Button login;
    int me;

    SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        pay = (Button) findViewById(R.id.pay);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pay.setOnClickListener(this);
        pay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //login();
                return true;
            }
        });

       /* ImageView qr = (ImageView) findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),payActivity.class));
            }
        });
        ImageView more= (ImageView) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(payActivity.this,"coming soon!",Toast.LENGTH_LONG).show();
            }
        });*/

        spinner = (Spinner) findViewById(R.id.spinner);
        phone = (EditText) findViewById(R.id.phone);
        amount = (EditText) findViewById(R.id.amount);

       adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, arrayList);
        //adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //spinner.setOnItemSelectedListener(this);


        if(!Util.MySharedPreference.getValue(this,"company").equalsIgnoreCase("0"))
            getOrders(Util.MySharedPreference.getValue(this,"company"));



        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                if(message.contains("Payment") || message.contains("payment"))
                    Util.alertMasseage(payActivity.this,"PAYMENT RECEIVED",message,false);
            }
        });

    }
    public void authenticate(final String username, String password){
        progressBar.setVisibility(View.VISIBLE);
        final Map<String,String> params = new HashMap<String, String>();
        params.put("function","login");
        params.put("email",username.trim());
        params.put("password",password.trim());
        APIPAYME.POST(payActivity.this, params, new APIPAYME.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("success")){
                        JSONObject object2 = jsonObject.getJSONObject("data");
                        Util.MySharedPreference.save(payActivity.this,"company",object2.getString("company"));
                        Util.MySharedPreference.save(payActivity.this,"login","true");
                        JSONArray jsonArray = jsonObject.getJSONArray("data2");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayList.add(String.valueOf(object.getString("order_id")));
                        }
                        //spinner.setAdapter(adapter);

                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getBusinessesPayBill(String company){
        progressBar.setVisibility(View.VISIBLE);
        final Map<String,String> params = new HashMap<String, String>();
        params.put("function","getBusinessesPayBill");
        params.put("company",company);

        APIPAYME.POST(payActivity.this, params, new APIPAYME.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("success")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayList.add(String.valueOf(object.getString("order_id")));
                        }
                       // spinner.setAdapter(adapter);
                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public void getOrders(String company){
        progressBar.setVisibility(View.VISIBLE);
        final Map<String,String> params = new HashMap<String, String>();
        params.put("function","getBusinessesPayBill");
        params.put("company",company);

        API.POST(payActivity.this, params, new API.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getBoolean("success")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayList.add(String.valueOf(object.getString("order_id")));
                        }
                         spinner.setAdapter(adapter);
                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
    public void CustomerPayBillOnline(){
        progressBar.setVisibility(View.VISIBLE);
        final Map<String,String> params = new HashMap<String, String>();
        params.put("function","CustomerPayBillOnline");
        params.put("PayBillNumber","175555");
        params.put("Amount",amount.getText().toString());
        params.put("PhoneNumber",phone.getText().toString());
        params.put("mobileNotification",Util.MySharedPreference.getValue(payActivity.this,"mobileNotification"));
        params.put("AccountReference",String.valueOf(getRandomNumber(1000,9999)));
        params.put("TransactionDesc","PAYMEAPP");
        APIPAYME.POST(payActivity.this, params, new APIPAYME.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                progressBar.setVisibility(View.GONE);
                Util.alertMasseage(payActivity.this,"PUSH SENT","Payment request sent to customer.",false);
                amount.setText("");
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(Util.MySharedPreference.getValue(this,"login").equalsIgnoreCase("true")){
            readSMSPERM();
        }else{

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PayBillNumber =String.valueOf(parent.getItemAtPosition(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private  void readSMSPERM(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            CustomerPayBillOnline();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_SMS},
                    1);
        }

    }


}
