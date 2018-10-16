//package com.nouveta.payme;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
////import com.blikoon.qrcodescanner.QrCodeActivity;
//import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
//import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class TicketSoko extends AppCompatActivity  {
//
//    Button scan, validate;
//    ProgressBar progressBar;
//    EditText ticket_number;
//    String PayBillNumber;
//
//    ImageView logo, valid, invalid;
//    private static final int REQUEST_CODE_QR_SCAN = 101;
//
//    String   dataToPrintCustomer,description,event_company,phone_number,day;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ticket_soko);
//
//        TextView cash = (TextView) findViewById(R.id.cash);
//        cash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Cash.class));
//            }
//        });
//
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        logo = (ImageView) findViewById(R.id.logo);
//        valid = (ImageView) findViewById(R.id.valid);
//        invalid = (ImageView) findViewById(R.id.invalid);
//
//        scan = (Button) findViewById(R.id.scan);
//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            readQR();
//            }
//        });
//        validate = (Button) findViewById(R.id.validate);
//        validate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ticket_number.getText().toString().matches("")){
//                    Toast.makeText(TicketSoko.this, "PLEASE SCAN TICKET", Toast.LENGTH_SHORT).show();
//                }else {
//                    validateTicket();
//                }
//
//            }
//        });
//
//
//
//        ticket_number = (EditText) findViewById(R.id.ticket_number);
//
//    }
//
//    /*private  void readQR(){
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
//            logo.setVisibility(View.VISIBLE);
//            invalid.setVisibility(View.GONE);
//            valid.setVisibility(View.GONE);
//            Intent i = new Intent(TicketSoko.this,QrCodeActivity.class);
//            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
//
//        }else{
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.CAMERA},
//                    1);
//        }
//
//    }*/
//
//
//
//    public void validateTicket(){
//        logo.setVisibility(View.VISIBLE);
//        invalid.setVisibility(View.GONE);
//        valid.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
//        final Map<String,String> params = new HashMap<String, String>();
//        params.put("TransactionType","validateTicket");
//        params.put("ticket_number",ticket_number.getText().toString());
//
//        API.POST(TicketSoko.this, params, new API.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                progressBar.setVisibility(View.GONE);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if(jsonObject.getBoolean("success")){
//                        logo.setVisibility(View.GONE);
//                        invalid.setVisibility(View.GONE);
//                        valid.setVisibility(View.VISIBLE);
//
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//
//                        description =jsonObject1.getString("description");
//                        event_company =jsonObject1.getString("event_company");
//                        phone_number =jsonObject1.getString("phone_number");
//                        day =jsonObject1.getString("day");
//
//                        dataToPrintCustomer="\n________________________________\n" +
//                                "$big$Mercedes-Benz Club Kenya\n" +
//                                "$small$TicketSoko\n" +
//                                "$intro$Ref: "+ticket_number+"\n" +
//                                "Day: "+day+"\n" +
//                                "PMT: "+phone_number+" \n" +
//                                "Desc: "+description+"\n" +
//                                "Date: "+getCurrentTimeStamp()+" \n "+
//                                "________________________________$intro$$intro$$cut$$intro$";
////                        sendDataToBTPrinter(TicketSoko.this,dataToPrintCustomer,"10:00:E8:6B:E2:5A");
//
//
//
//                    }else {
//                        logo.setVisibility(View.GONE);
//                        invalid.setVisibility(View.VISIBLE);
//                        valid.setVisibility(View.GONE);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(resultCode != Activity.RESULT_OK)
//        {
//            Log.d("QR","COULD NOT GET A GOOD RESULT.");
//            if(data==null)
//                return;
//            //Getting the passed result
//            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
//            if( result!=null)
//            {
//                AlertDialog alertDialog = new AlertDialog.Builder(TicketSoko.this).create();
//                alertDialog.setTitle("Scan Error");
//                alertDialog.setMessage("QR Code could not be scanned");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            }
//            return;
//
//        }
//        if(requestCode == REQUEST_CODE_QR_SCAN)
//        {
//            if(data==null)
//                return;
//            //Getting the passed result
//            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
//            Log.d("QR","Have scan result in your app activity :"+ result);
//            ticket_number.setText(result);
//            AlertDialog alertDialog = new AlertDialog.Builder(TicketSoko.this).create();
//            alertDialog.setTitle("Scan result");
//            alertDialog.setMessage(result);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//
//        }
//    }
//
//
//    public static void sendDataToBTPrinter(Context context , String textoToSend, String BtDevice) {
//        System.out.println("Printing....");
//        Intent intentPrint = new Intent();
//        intentPrint.setAction(Intent.ACTION_SEND);
//        intentPrint.putExtra(Intent.EXTRA_TEXT, textoToSend);
//        intentPrint.putExtra("printer_type_id", "4");// For bluetooth
//        intentPrint.putExtra("printer_bt_adress", BtDevice);
//        intentPrint.setType("text/plain");
//        System.out.println("sendDataToBTPrinter Start Intent");
//        context.startActivity(intentPrint);
//    }
//
//    public static String getCurrentTimeStamp(){
//        try {
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String currentDateTime = dateFormat.format(new Date()); // Find todays date
//
//            return currentDateTime;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            return null;
//        }
//    }
//
//
//
//}
