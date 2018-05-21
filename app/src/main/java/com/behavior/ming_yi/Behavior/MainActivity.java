package com.behavior.ming_yi.Behavior;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.behavior.ming_yi.Accessibility.MyAccessibility;
import com.behavior.ming_yi.SQLite.Database;
import com.behavior.ming_yi.SQLite.Item;
import com.behavior.ming_yi.Upload.UploadSyncTask;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends Activity {
    private String TAG = "Mysqlit";
    public static Dialog dialog;

    ArrayAdapter listAdapter = null;
    Database db;

//    Button Getsqlit = null;
    Button Uploadsqlit = null;
    Button Setting = null;
    Button del_table = null;
    ListView DataList = null;
    TextView checkDataState = null;
    public static TextView res_txv=null;
    public static String sssss="asddfg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {WRITE_EXTERNAL_STORAGE,
                            READ_EXTERNAL_STORAGE},
                    1);
        }
        db = new Database(MainActivity.this);
        Intent i = new Intent(this, MyAccessibility.class);
        startService(i);

//        Getsqlit = (Button) findViewById(R.id.btn_get);
        Uploadsqlit = (Button) findViewById(R.id.btn_Upload);
        Setting = (Button) findViewById(R.id.btn_setting);
        DataList = (ListView) findViewById(R.id.lv_datalist);
        checkDataState = (TextView) findViewById(R.id.tv_checkstate);
        res_txv = (TextView) findViewById(R.id.response_txv);
        del_table = (Button)findViewById(R.id.del_btn);

        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        DataList.setAdapter(listAdapter);


//        Getsqlit.setOnClickListener(getclick);
        Uploadsqlit.setOnClickListener(uploadData);
        Setting.setOnClickListener(SettingAccessibility);
        del_table.setOnClickListener(delclick);

        checkDataState.setText("Token: "+db.getToken());



    }
    /*Button.OnClickListener uploadData = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = ProgressDialog.show(MainActivity.this,
                    "傳送資料中", "請稍等...", true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(MainActivity.this.isNetworkConnected()){
                            UploadSyncTask upload = new UploadSyncTask(MainActivity.this);
                            upload.execute();
                        }else{
                            Toast.makeText(MainActivity.this, "沒有網路", Toast.LENGTH_LONG).show();
                        }
                        Thread.sleep(50000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        dialog.dismiss();
                    }
                }
            }).start();
        }
    };*/

    Button.OnClickListener uploadData = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = ProgressDialog.show(MainActivity.this,
                    "正在取得資料", "請稍等...", true);
            if(MainActivity.this.isNetworkConnected()){
                UploadSyncTask upload = new UploadSyncTask(MainActivity.this);
                upload.execute();
            }else{
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "沒有網路", Toast.LENGTH_LONG).show();
            }

        }
    };

    Button.OnClickListener getclick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            listAdapter.clear();
            checkDataState.setText("總筆數/已上傳\n" + db.checkUploadData());
            for(Item i :db.getAll()){
                Log.i(TAG,"App: " + i.appname.toString());
                Log.i(TAG,"Context: " + i.data.toString());
                Log.i(TAG,"Time: " + i.time.toString());
                Log.i(TAG,"===================================================");

                listAdapter.add("[App]:\n" + i.appname.toString()+"\n\n[Context]:\n" + i.data.toString()+"\n\n[Event]:\n"+ i.event +"\n\n[Time]:\n" + i.time.toString());
//                listAdapter.add("[App]: "+i.appname.toString()+"\n[Time]: "+i.time.toString());
            }
//            db.close();
        }
    };

    Button.OnClickListener delclick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            listAdapter.clear();
            db = new Database(MainActivity.this);
            db.deleteAll();
            db.close();
        }
    };

    Button.OnClickListener SettingAccessibility = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent goToSettings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            goToSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(goToSettings);
        }
    };

    private boolean isNetworkConnected() {
        // 判斷是否有跟網路連接
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("SD","Gat SD card ");
                } else {
                    MainActivity.this.finish();
                }
           return;
        }
    }
}
