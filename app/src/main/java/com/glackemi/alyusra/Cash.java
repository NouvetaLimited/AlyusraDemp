//package com.glackemi.alyusra;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
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
//import android.widget.Toast;
//
////import com.blikoon.qrcodescanner.QrCodeActivity;
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
//
//public class Cash extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
//
//    Button pay;
//    ProgressBar progressBar;
//    Spinner spinner;
//    ArrayList<String> arrayList = new ArrayList<>();
//    ArrayAdapter<String> adapter;
//    EditText phone, amount, mobileNotification;
//    String desc;
//    String   dataToPrintCustomer,description,event_company,phone_number,day;
//    ImageView imageView2;
//    ImageView imageView3;
//    int let;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.cash);
//        let = 4025;
//        imageView2 = (ImageView) findViewById(R.id.imageView2);
//        imageView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 {
//                    dataToPrintCustomer = "\n________________________________\n" +
//                            "$big$ KENYA DREAM AWARD \n" +
//                            "$small$Test\n" +
//                            "$intro$Ref:" + let + "\n" +
//                            "Day: TODAY\n" +
//                            "AMT: 500 \n" +
//                            "DECRIPTION: VIP \n" +
//                            "Date: " + getCurrentTimeStamp() + " \n " +
//                            "________________________________$intro$$intro$$cut$$intro$";
//                    sendDataToBTPrinter(Cash.this, dataToPrintCustomer, "10:00:E8:6B:E2:5A");
//                }
//            }
//        });
//        imageView3 = (ImageView) findViewById(R.id.imageView3);
//        imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                {
//                    dataToPrintCustomer = "\n________________________________\n" +
//                            "$big$ KENYA DREAM AWARD \n" +
//                            "$small$Test\n" +
//                            "$intro$Ref:" + let + "\n" +
//                            "Day: TODAY\n" +
//                            "AMT: 300 \n" +
//                            "DECRIPTION: REGULAR \n" +
//                            "Date: " + getCurrentTimeStamp() + " \n " +
//                            "________________________________$intro$$intro$$cut$$intro$";
//                    sendDataToBTPrinter(Cash.this, dataToPrintCustomer, "10:00:E8:6B:E2:5A");
//                }
//            }
//        });
//        pay = (Button) findViewById(R.id.pay);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        pay.setOnClickListener(this);
//        pay.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                cashTicket();
//                return true;
//            }
//        });
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Cash.this, "Long Press to Print", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        spinner = (Spinner) findViewById(R.id.spinner);
//        phone = (EditText) findViewById(R.id.phone);
//        amount = (EditText) findViewById(R.id.amount);
//
//        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_dropdown_item, arrayList);
//        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spinner.setOnItemSelectedListener(this);
//
//
//
//            getBusinessesPayBill(Util.MySharedPreference.getValue(this, "company"));
//
//
//
//
//    }
//
//
//
//
//    public void getBusinessesPayBill(String company) {
//        progressBar.setVisibility(View.VISIBLE);
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("function", "cashOptions");
//        params.put("id", "11");
//
//        API.POST(Cash.this, params, new API.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                progressBar.setVisibility(View.GONE);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.getBoolean("success")) {
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            arrayList.add(String.valueOf(object.getString("name")+"-"+object.getString("price")+"-"+object.getString("day")));
//                        }
//                        spinner.setAdapter(adapter);
//                    } else {
//                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
//
//
//    public void cashTicket() {
//        progressBar.setVisibility(View.VISIBLE);
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("function", "cashTicket");
//        params.put("description",desc);
//        params.put("amount", amount.getText().toString());
//        params.put("day", phone.getText().toString());
//
//        API.POST(Cash.this, params, new API.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                progressBar.setVisibility(View.GONE);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.getBoolean("success")) {
//
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//
//                        description =jsonObject1.getString("description");
//                        event_company =jsonObject1.getString("event_company");
//                        phone_number =jsonObject1.getString("phone_number");
//                        day =jsonObject1.getString("day");
//
//                        dataToPrintCustomer="\n________________________________\n" +
//                                "$big$"+event_company+" \n" +
//                                "$small$TicketSoko\n" +
//                                "$intro$Ref: "+jsonObject1.getString("ticket_number")+"\n" +
//                                "Day: "+day+"\n" +
//                                "PMT: "+phone_number+" \n" +
//                                "Desc: "+description+"\n" +
//                                "Date: "+getCurrentTimeStamp()+" \n "+
//                                "________________________________$intro$$intro$$cut$$intro$";
//                        sendDataToBTPrinter(Cash.this,dataToPrintCustomer,"10:00:E8:6B:E2:5A");
//                    } else {
//                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }
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
//
//
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String array[]=String.valueOf(parent.getItemAtPosition(position)).split("-");
//        desc =String.valueOf(array[0]);
//        amount.setText(String.valueOf(array[1]));
//        phone.setText(String.valueOf(array[2]));
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//
//
//}
