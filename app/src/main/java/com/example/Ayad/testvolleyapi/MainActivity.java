package com.example.Ayad.testvolleyapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Vector;

import volley.singleton.MySingleton;

public class MainActivity extends AppCompatActivity {
    private Button downLoad;
    private TextView musuem;
    private ProgressDialog progressBar;
    private ImageLoader mImageLoader;
    private NetworkImageView mNetworkImageView;
    final Vector <String> monumentsNames=new Vector<>();
    HashMap<String, String> params ;


    private static final String IMAGE_URL =
            "http://www.arabcont.com/projects/Images/A%20(18).jpg";
//    private static String url = "https://api.kudan.eu/CMS/JSON/test.json";
//    private static String url = "http://192.168.43.197:8084/ElmoezWebService/rest/elmoez/testJersyWithArray";

    private static String url = "http://192.168.43.197:8084/ElmoezWebService/services/elmoez/signUp";


    private static final String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downLoad = (Button) findViewById(R.id.download);
        musuem = (TextView) findViewById(R.id.museumName);
        params = new HashMap<String, String>();


        // Get the NetworkImageView that will display the image.
        mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);

        // Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(this).getImageLoader();



        // Set the URL of the image that should be loaded into this view, and
        // specify the ImageLoader that will be used to make the request.

        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);
                progressBar=ProgressDialog.show(MainActivity.this,"Processing","Please Wait",true,false);

//                getGson();
//                getGsonArray();

                params.put("firstName", "mohammed");
                params.put("lastName", "ayad");
                params.put("email","mohammedayad611@gmail.com");
                params.put("password","123");
                params.put("repassword","123");
                params.put("image","ayad.jpg");

                setGson();


            }
        });


    }

    public void getGson(){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.dismiss();
                        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);

                        try {
                            musuem.setText("Response: " + response.getString("lastUpdated"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("errrrrrrrrrror",error.getMessage());

                    }
                });

        jsObjRequest.setTag(TAG);
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
    public void getGsonArray(){
        JsonArrayRequest jreq = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                String name = jo.getString("monumentsName");
                                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG);
                                monumentsNames.add(name);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        progressBar.dismiss();
                        musuem.setText("Response: " + monumentsNames.get(0));



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        jreq.setTag(TAG);
        MySingleton.getInstance(this).addToRequestQueue(jreq);



    }

    public void setGson(){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url,new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.dismiss();
                        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);

                        try {
                            musuem.setText("Response: " + response.getString("state"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("errrrrrrrrrror",error.getMessage());

                    }
                });

        jsObjRequest.setTag(TAG);
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MySingleton.getInstance(this).getRequestQueue().cancelAll(TAG);
        Log.i("inffffffffffffffffffformation","request canceled");

    }
}
