package com.glackemi.alyusra;



import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


/**
 * Created by Alex Boey on 8/12/2016.
 */
public class Util {

    public static void launchSTK(Context activity) {



        Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.android.stk");
        if (intent == null) {
            try {
                Intent intent1 = new Intent();
                intent1.addFlags(0x10000000);
                intent1.addCategory("android.intent.category.LAUNCHER");
                intent1.setAction("android.intent.action.MAIN");
                intent1.setType("text/plain");
                intent1.setComponent(new ComponentName("com.android.stk", "com.android.stk.StkLauncherActivity"));
                activity.startActivity(intent1);
                return;
            } catch (ActivityNotFoundException activitynotfoundexception) {
            }
        } else if (intent == null) {

            try {
                Intent intent2 = new Intent();
                intent2.addFlags(0x10000000);
                intent2.addCategory("android.intent.category.LAUNCHER");
                intent2.setAction("android.intent.action.MAIN");
                intent2.setType("text/plain");
                intent2.setComponent(new ComponentName("com.android.stk", "com.android.stk.StkMain"));
                activity.startActivity(intent2);
                return;
            } catch (ActivityNotFoundException activitynotfoundexception1) {
            }


        } else if (intent == null) {
            Intent intent3 = new Intent();
            intent3.addFlags(0x10000000);
            intent3.addCategory("android.intent.category.LAUNCHER");
            intent3.setAction("android.intent.action.MAIN");
            intent3.setType("text/plain");
            intent3.setComponent(new ComponentName("com.android.stk", "com.android.stk.StkLauncherActivity"));
            activity.startActivity(intent3);

        } else if (intent == null) {

            Intent intent4 = new Intent();
            intent4.addFlags(0x10000000);
            intent4.addCategory("android.intent.category.LAUNCHER");
            intent4.setAction("android.intent.action.MAIN");
            intent4.setType("text/plain");
            intent4.setComponent(new ComponentName("com.android.stk", "com.android.stk.StkMain"));
            activity.startActivity(intent4);

        } else if (intent == null) {
            Intent launchIntent5 = activity.getPackageManager().getLaunchIntentForPackage("com.mediatek.StkSelection");
            activity.startActivity(launchIntent5);


        } else {
            activity.startActivity(intent);
        }
        return;
    }



    public static void Toast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    public static void startActivity(Context context , Class<?> activity){

        Intent viewIntent = new Intent(context , activity);
        viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(viewIntent);
    }



    public static String getMyPhoneNumber(Context context){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    public static String getMy10DigitPhoneNumber(Context context){
        String s = getMyPhoneNumber(context);
        return s != null && s.length() > 2 ? s.substring(1) : null;
    }

    public static String getOperator(Context context){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId();
    }



    public static class ProfileImageProcessor {

        public static Bitmap bitmap;


        //loads the image from shared pref
        public static Bitmap LoadImage(Context context){

            String previouslyEncodedImage = MySharedPreference.getValue(context,"image_data");
            if( !previouslyEncodedImage.equalsIgnoreCase("") ){
                byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            }
            return  bitmap;
        }

        //Saving the image in shared pref
        public static Bitmap saveImage(Context context, Uri selectedImageURI ){
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImageURI, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            if(bitmap != null && !bitmap.isRecycled())
            {
                bitmap = null;
            }
            bitmap = BitmapFactory.decodeFile(filePath);
            //imageView.setBackgroundResource(0);

            //decode the file
            Bitmap realImage = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            System.out.print("#######" + encodedImage);

            //save the image
            MySharedPreference.save(context ,"image_data" , encodedImage);
            return  bitmap;

        }


        public static String defaultProfile(){

            return "https://cdn0.iconfinder.com/data/icons/user-administrator-web-ui-app/100/user-08-128.png";
        }


    }

    public static class MySharedPreference {

        public static void save(Context context , String key , String value){
            SharedPreferences prefs= context.getSharedPreferences("com.coretec.msacco.pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= prefs.edit();
            editor.putString(key,value);
            editor.apply();

        }

        public static String getValue(Context context , String key){
            //if 1st time , register the user
            SharedPreferences prefs = context.getSharedPreferences("com.coretec.msacco.pref", Context.MODE_PRIVATE);
            return prefs.getString(key , "0");
        }
    }

    public static boolean  isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    //Check Internet Connection
    private static Util instance = new Util();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static Util getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }


    public static void alertMasseage(final Context context ,String title, String message ,final boolean finish){
        AlertDialog.Builder alertadd = new AlertDialog.Builder(
                context);

        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.alert_registration, null);
        TextView title1 = (TextView) view. findViewById(R.id.title1);
        TextView message1 =(TextView) view.findViewById(R.id.message);
        title1.setText(title);
        message1.setText(message);


        alertadd.setView(view);
        alertadd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                dlg.dismiss();
                if (finish) {
                    ((Activity) context).finish();
                }
            }
        });



        alertadd.show();

    }


}
