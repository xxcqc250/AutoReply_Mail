package com.behavior.ming_yi.Behavior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ShowResponseData extends AppCompatActivity {
    TextView res_txv,error_txv=null;

    // 所有方法的textView
    TextView txv_tfidf,txv_kcem,txv_CFN_Vertex_Weight,txv_CFN_Vertex_Weight_TFIDF,txv_CFN_Vertex_Degree,txv_CFN_PageRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_response_data);

        res_txv=(TextView)findViewById(R.id.response_txv);
        error_txv=(TextView)findViewById(R.id.error_txv);

        txv_tfidf=(TextView)findViewById(R.id.txv_1);
        txv_kcem=(TextView)findViewById(R.id.txv_2);
        txv_CFN_Vertex_Weight=(TextView)findViewById(R.id.txv_3);
        txv_CFN_Vertex_Weight_TFIDF=(TextView)findViewById(R.id.txv_4);
        txv_CFN_Vertex_Degree=(TextView)findViewById(R.id.txv_5);
        txv_CFN_PageRank=(TextView)findViewById(R.id.txv_6);


        Intent it =getIntent();
//        String response = it.getStringExtra("response");
        // 取得B2T回傳的sentence
        ArrayList response_list = it.getStringArrayListExtra("response");
        for (int i=0;i<response_list.size();i++){
            String sentence=null;
            try{
                JSONObject jsonObject = new JSONObject(response_list.get(i).toString());
                sentence = jsonObject.getString("sentence");
                if(jsonObject.getString("method").equals("tfidf")){
                    txv_tfidf.setText("Tfidf : \n"+sentence);
                }
                else if(jsonObject.getString("method").equals("kcem")){
                    txv_kcem.setText("Kcem : \n"+sentence);
                }
                else if(jsonObject.getString("method").equals("CFN-Vertex-Weight")){
                    txv_CFN_Vertex_Weight.setText("CFN-Vertex-Weight : \n"+sentence);
                }
                else if(jsonObject.getString("method").equals("CFN-Vertex-Weight-TFIDF")){
                    txv_CFN_Vertex_Weight_TFIDF.setText("CFN-Vertex-Weight-TFIDF : \n"+sentence);
                }
                else if(jsonObject.getString("method").equals("CFN-Vertex-Degree")){
                    txv_CFN_Vertex_Degree.setText("CFN-Vertex-Degree : \n"+sentence);
                }
                else if(jsonObject.getString("method").equals("CFN-PageRank")){
                    txv_CFN_PageRank.setText("CFN-PageRank : \n"+sentence);
                }
            }catch (JSONException ex){
                String error_msg=null;
                try{
                    JSONObject jsonObject = new JSONObject(response_list.get(i).toString());
                    error_msg=jsonObject.getString("error");
                    error_txv.setText("error_msg : \n"+error_msg);
                }catch (JSONException ex2){
                    error_txv.setText("Server端發生問題");
                }
            }

        }



//        String sentence=null,error_msg=null;
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            sentence = jsonObject.getString("sentence");
//        }catch (JSONException e){
//            try{
//                JSONObject jsonObject = new JSONObject(response);
//                error_msg=jsonObject.getString("data");
//            }catch (JSONException ex){
//                error_msg="好像沒有回傳資料或是有問題喔";
//            }
////            Log.e("BinHong","資料格式錯誤");
//        }
//        res_txv.setText(sentence);
//        error_txv.setText(error_msg);

    }
}
