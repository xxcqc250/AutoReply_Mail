package com.behavior.ming_yi.Upload;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.behavior.ming_yi.Behavior.MainActivity;
import com.behavior.ming_yi.Behavior.ShowResponseData;
import com.behavior.ming_yi.SQLite.Database;
import com.behavior.ming_yi.SQLite.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ming-yi on 2017/5/8.
 */

public class UploadSyncTask extends AsyncTask<String,Void,String> {
    private Context context = null;
    private List<Item> uploadItems = null;
    private Database db = null;
    private String Token = null;
    public UploadSyncTask(Context context){
        this.context = context;
    }

    private String response_data=null;
    private ArrayList response_list=new ArrayList();

    @Override
    protected String doInBackground(String... params) {
        db = new Database(this.context);
        Token = db.getToken();
        uploadItems = db.SqlitetoUpload();
        int datanumber = uploadItems.size();
        int count = 0;


        String result = null;
        JSONArray entired_data = new JSONArray(); // 也可以不指定
        for(Item row : uploadItems){
            try {
                JSONObject data = new JSONObject();
                data.put("id",row.id);
                data.put("datetime",row.time);
                data.put("context", URLEncoder.encode(row.data, "UTF-8"));
                data.put("appname",row.appname);
                data.put("event",row.event);
                data.put("token",Token);

                if(this.postData(data.toString())){
                    db.UpdateUploadState(row);
                    count += 1;
                }

                // 把所有的context抓下來
                JSONObject context_data = new JSONObject(); // 放context的部分
                // 將特殊字元去除
                String context_str=row.data.replace("\n","").replace("\t","").replace("\b","").replace("\f","").replace("\r","").replace("\'","")
                        .replace("\"","").replace("\\","");
                // context放到JsonObject，並將資料encode
                context_data.put("context", URLEncoder.encode(context_str, "UTF-8"));
                // 再將JsonObject放入JsonArray
                entired_data.put(context_data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

//        //印出所有的context Log-----------------
//        if(this.printAllContext(entired_data.toString())){
//            Log.i("BinHong","OKKKK");
//        }else{
//            Log.e("BinHong","NOOOOOO");
//        }


        //把資料傳到B2T-----------------
        if(this.PostToB2T(entired_data.toString())){
            // 將processDialog移除
            MainActivity.dialog.dismiss();

            Log.i("BinHong" , "傳送OK");
            Intent it= new Intent();
            it.setClass(this.context,ShowResponseData.class);
//            it.putExtra("response",response_data);
            it.putExtra("response",response_list);
            for(int i=0;i<response_list.size();i++){
                Log.i("response",response_list.get(i).toString());
            }
            context.startActivity(it);
        }
        else {
            Log.e("BinHong" , "傳送fail");
        }
        if(datanumber > 0){
            result = "("+String.valueOf(datanumber)+"/"+String.valueOf(count)+")";
        }
        return result;
    }
    private boolean printAllContext(String context){
        boolean state =false;
        URL urls = null;
        try {
            urls = new URL("http://140.120.13.243:6680/PrintContextToTxt.php");// 測試POST
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json, */*;");
            conn.setReadTimeout(50000);
            conn.setConnectTimeout(10000);
            conn.setDoInput (true);
            conn.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write("Data="+context);
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.i("BinHong_printAllContext",response.toString());
                if("OK".equals(response.toString())) state = true;
            } else {
                Log.i("BinHong_printAllContext", "訪問失敗" + responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }
    private boolean PostToB2T(String context_log){
        boolean state =false;
        String[] method_list = {"tfidf","kcem","CFN-Vertex-Weight","CFN-Vertex-Weight-TFIDF","CFN-Vertex-Degree","CFN-PageRank"};

        for (String method : method_list) {
            try{
                URL urls = new URL("http://udiclab.cs.nchu.edu.tw/behavior2text/");// B2T位置
//            urls = new URL("http://140.120.13.242:27017/behavior2text/");// B2T測試位置
                HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json, */*;");
                conn.setReadTimeout(50000);
                conn.setConnectTimeout(10000);
                conn.setDoInput (true);
                conn.setDoOutput(true);

                Log.i("BinHong_data","user :"+URLDecoder.decode(context_log,"UTF-8"));
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("data="+context_log+"&method="+method+"&topN=3&clusterTopn=3");
                out.flush();
                out.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    Log.i("BinHong","200 OK");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    Log.i("BinHong","Response: "+response.toString());

                    state = true;
                    response_list.add(response.toString());
                } else {
                    Log.e("BinHong","訪問失敗"+responseCode);
                }
            } catch (MalformedURLException e) {
                Log.e("BinHong","發生錯誤1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("BinHong","發生錯誤2");
                Log.e("BinHong",e.toString());
                e.printStackTrace();
            }
        }
        return state;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s==null)
            Toast.makeText(this.context, "所有資料已經上傳完畢", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this.context, "上傳完畢 "+s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private boolean postData(String PostJsonData){
        boolean state = false;
        URL urls = null;
        try {
//            urls = new URL("http://140.120.13.243:6680/accessibility_ten.php");
//            urls = new URL("http://140.120.13.243:6680/PHPwrite.php");
            //urls = new URL("http://140.120.13.243:6680/IRI_test.php");// 給自己測試用
            //urls = new URL("http://140.120.13.243:6680/IRI_formal.php");// 給學姊用
            urls = new URL("http://140.120.13.243:6680/test_postAllData.php");// 測試POST
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json, */*;");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoInput (true);
            conn.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write("Data="+PostJsonData);
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.i("HTTP",response.toString());
                if("OK".equals(response.toString())) state = true;
            } else {
                Log.i("HTTP", "訪問失敗" + responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }



    public boolean doPost(String sURL, String data, String cookie,
                          String referer, String charset) {

        boolean doSuccess = false;
        java.io.BufferedWriter wr = null;
        try {

            URL url = new URL("http://udiclab.cs.nchu.edu.tw/behavior2text/");
            HttpURLConnection URLConn = (HttpURLConnection) url
                    .openConnection();

            URLConn.setDoOutput(true);
            URLConn.setDoInput(true);
            ((HttpURLConnection) URLConn).setRequestMethod("POST");
            URLConn.setUseCaches(false);
            URLConn.setAllowUserInteraction(true);
            HttpURLConnection.setFollowRedirects(true);
            URLConn.setInstanceFollowRedirects(true);

            URLConn
                    .setRequestProperty(
                            "User-agent",
                            "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "
                                    + "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)");
            URLConn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            URLConn.setRequestProperty("Accept-Language","zh-tw,en-us;q=0.7,en;q=0.3");
            URLConn.setRequestProperty("Accept-Charse","Big5,utf-8;q=0.7,*;q=0.7");
            if (cookie != null)
                URLConn.setRequestProperty("Cookie", cookie);
            if (referer != null)
                URLConn.setRequestProperty("Referer", referer);

            URLConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            URLConn.setRequestProperty("Content-Length", String.valueOf(data
                    .getBytes().length));

            java.io.DataOutputStream dos = new java.io.DataOutputStream(URLConn
                    .getOutputStream());
            dos.writeBytes(data);

            java.io.BufferedReader rd = new java.io.BufferedReader(
                    new java.io.InputStreamReader(URLConn.getInputStream(),
                            charset));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

            rd.close();
        } catch (java.io.IOException e) {
            doSuccess = false;

        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (java.io.IOException ex) {
                }
                wr = null;
            }
        }

        return doSuccess;
    }
}
