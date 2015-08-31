package com.example.joseph.evb_artc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by joseph on 2015/8/17.
 */
public class File extends Activity {
    public Button btnEVB_FileList,btnEVB_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file);

        findviews();

    }


    private void goToUrl(String url)
    {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private void findviews() {
        btnEVB_FileList=(Button)findViewById(R.id.btnEVB_FileList);
        btnEVB_video=(Button)findViewById(R.id.btnEVB_video);

        btnEVB_FileList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(MainActivity.url_EVB_FileList);
            }
        });

        btnEVB_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl(MainActivity.url_EVB_video);
            }
        });
    }
}
