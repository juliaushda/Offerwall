package com.example.offerwall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.offerwall.models.ModelA;
import com.example.offerwall.models.ModelB;
import com.example.offerwall.requests.ServerAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static ServerAPI serverAPI;
    private Retrofit retrofit;
    private List<ModelA> modelsA = new ArrayList<>();
    private List<String> ObjectsIds;
    private ModelB modelB;
    private String text;
    private String url;
    private int maxNumber,number=0;

    private TextView textView;
    private WebView webView;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        textView.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.INVISIBLE);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    if(number<modelsA.size()){
                        getDetailsB(number);
                        number++;
                        System.out.println(number);
                    }else{
                        number=0;
                        getDetailsB(number);
                    }
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, "Turn on internet", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isNetworkConnected()){
            getDetailsA();
        }else{
            Toast toast = Toast.makeText(MainActivity.this, "Turn on internet", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @SuppressWarnings("unchecked")
    private void getDetailsA() {
        retrofit = Builder.getRetrofit();
        serverAPI = retrofit.create(ServerAPI.class);
        Call call = serverAPI.getData();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                modelsA = (List<ModelA>) response.body();
                if (response.body() != null) {
                    modelsA = (List<ModelA>) response.body();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast toast = Toast.makeText(MainActivity.this, "There is an error with request", Toast.LENGTH_SHORT);
                toast.show();
                t.printStackTrace();
            }
        });

    }
    @SuppressWarnings("unchecked")
    private void getDetailsB(int number) {
        retrofit = Builder.getRetrofit();
        serverAPI = retrofit.create(ServerAPI.class);
        Call call = serverAPI.getObject(modelsA.get(number).getId());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    modelB = (ModelB) response.body();
                    if(modelB.getType().equals("text")){
                        textView.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.INVISIBLE);
                        text = modelB.getContents();
                        textView.setText(text);
                    }else{
                        textView.setVisibility(View.INVISIBLE);
                        webView.setVisibility(View.VISIBLE);
                        url = modelB.getUrl();
                        webView.loadUrl(url);
                        System.out.println(url);
                    }
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast toast = Toast.makeText(MainActivity.this, "There is an error with request", Toast.LENGTH_SHORT);
                toast.show();
                t.printStackTrace();
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
