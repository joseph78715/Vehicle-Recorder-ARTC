package com.example.joseph.evb_artc;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;


import java.io.IOException;


public class MainActivity extends Activity {


    public Button btnToSetting,btnToADAS,btnToFile,btnToAbout;
    public static String url_EVB_FileList="http://192.168.1.254";
    public static String url_EVB_video="http://192.168.1.254/NOVATEK/MOVIE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        //<string name="str_url1">http://www.google.com</string>
        // <string name="str_url2">http://192.168.1.254</string>
        // <string name="str_url3">http://192.168.1.254/NOVATEK/MOVIE</string>
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        btnToADAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ADAS.class);
                startActivity(intent);
            }
        });
        btnToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,File.class);
                startActivity(intent);
            }
        });

        btnToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
            }
        });
        btnToAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });

    }


    private void findViews() {

        btnToSetting =(Button)findViewById(R.id.btnToSetting);
        btnToADAS=(Button)findViewById(R.id.btnToADAS);
        btnToFile=(Button)findViewById(R.id.btnToFile);
        btnToAbout=(Button)findViewById(R.id.btnToAbout);

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

