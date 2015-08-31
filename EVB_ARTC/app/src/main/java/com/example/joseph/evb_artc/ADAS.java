package com.example.joseph.evb_artc;

import android.app.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;


/**
 * Created by joseph on 2015/8/17.
 */
public class ADAS extends Activity {


    public MediaPlayer mediaPlayer;
    public SurfaceHolder adas_surfaceHolder;
    public SurfaceView adas_playersurface;
    public String videoSrc = "rtsp://192.168.1.254/xxxx.mp4";

    public ImageView imageView_InitRow, imageView_Euslope;
    public Button btnUp, btnText, btnDown;

    public ToggleButton toggleButton_Euslope_InitRow,togglebtn_ADAS;

    public static int stateTop_Euslope,stateRight_Euslope,stateBottom_Euslope,stateLeft_Euslope;
    public static int stateTop_InitRow,stateRight_InitRow,stateBottom_InitRow,stateLeft_InitRow;
    public static int lineWidth_Euslope,lineWidth_InitRow;

    public SeekBar seekBar_Euslope,seekBar_InitRow;

    public Spinner spinnerLDW_FCW_sensitivity;
    public String[] str_LDW_FCW_sensitivity = {"低", "中", "高"};
    public ArrayAdapter adapterLDW_FCW_sensitivity;

    //------TCP----
    private ArrayList<String> arrayList;
    private TCPClient mTcpClient;
    public Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adas);
        findviews();


        adas_surfaceHolder = adas_playersurface.getHolder();
        //surfaceHolder.addCallback(this);
        adas_surfaceHolder.addCallback(adas_callback);

        //<string name="str_url1">http://www.google.com</string>
        // <string name="str_url2">http://192.168.1.254</string>
        // <string name="str_url3">http://192.168.1.254/NOVATEK/MOVIE</string>


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(960, 540);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adas_playersurface.setLayoutParams(layoutParams);


/*

        LayoutParams lp = adas_playersurface.getLayoutParams();
        lp.height = 540;//1080
        lp.width = 960;//1920
        adas_playersurface.setLayoutParams(lp);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
*/


    }

    private void findviews() {
        spinnerLDW_FCW_sensitivity = (Spinner) findViewById(R.id.spinnerLDW_FCW_sensitivity);
       spinnerLDW_FCW_sensitivity.setSelection(0, true);
        adapterLDW_FCW_sensitivity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_LDW_FCW_sensitivity);
        adapterLDW_FCW_sensitivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLDW_FCW_sensitivity.setAdapter(adapterLDW_FCW_sensitivity);

        adas_playersurface = (SurfaceView) findViewById(R.id.adas_playersurface);
        imageView_InitRow = (ImageView) findViewById(R.id.imageView_InitRow);
        imageView_Euslope = (ImageView) findViewById(R.id.imageView_Euslope);

        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);

        btnText = (Button) findViewById(R.id.btnText);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(ADAS.this, "InitRow : " + imageView_InitRow.getTop() + " " + getInitRowParameter(imageView_InitRow.getTop()) + "\n" + "Euslope : " + imageView_Euslope.getTop() + " " + getEuslopeParameter(imageView_Euslope.getTop()), Toast.LENGTH_SHORT).show();
                Toast.makeText(ADAS.this, "藍線InitRow : " + stateTop_InitRow + " " + getInitRowParameter(imageView_InitRow.getTop())
                        + "\n" + "紅線Euslope : " + stateTop_Euslope + " " + getEuslopeParameter(imageView_Euslope.getTop())
                        , Toast.LENGTH_SHORT).show();

            }
        });

        btnSend = (Button) findViewById(R.id.btnSend);
        toggleButton_Euslope_InitRow = (ToggleButton) findViewById(R.id.toggleButton_Euslope_InitRow);
        toggleButton_Euslope_InitRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        stateBottom_Euslope=imageView_Euslope.getBottom();//save Euslope state
                stateLeft_Euslope=imageView_Euslope.getLeft();
                stateRight_Euslope=imageView_Euslope.getRight();
                stateTop_Euslope=imageView_Euslope.getTop();


                stateBottom_InitRow=imageView_InitRow.getBottom();//save InitRow state
                stateLeft_InitRow=imageView_InitRow.getLeft();
                stateRight_InitRow=imageView_InitRow.getRight();
                stateTop_InitRow=imageView_InitRow.getTop();
*/

                if (toggleButton_Euslope_InitRow.isChecked()) {
                    Toast.makeText(ADAS.this, "移動紅線 Euslope", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ADAS.this, "移動藍線 InitRow", Toast.LENGTH_SHORT).show();


                }

                imageView_Euslope.layout(stateLeft_Euslope,
                        stateTop_Euslope,
                        stateRight_Euslope,
                        stateBottom_Euslope);
                imageView_InitRow.layout(stateLeft_InitRow,
                        stateTop_InitRow,
                        stateRight_InitRow,
                        stateBottom_InitRow);


            }
        });
        togglebtn_ADAS = (ToggleButton) findViewById(R.id.togglebtn_ADAS);
        togglebtn_ADAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (togglebtn_ADAS.isChecked()) {
                    if (mTcpClient != null) {
                        mTcpClient.sendMessage("ADAS 1");
                    }


                } else {
                    if (mTcpClient != null) {
                        mTcpClient.sendMessage("ADAS 0");
                    }


                }

            }
        });



        seekBar_Euslope=(SeekBar)findViewById(R.id.seekBar_Euslope);
        seekBar_InitRow=(SeekBar)findViewById(R.id.seekBar_InitRow);
        seekBar_Euslope.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stateTop_Euslope = progress;
                stateBottom_Euslope = lineWidth_Euslope + stateTop_Euslope;

                imageView_Euslope.layout(stateLeft_Euslope,
                        stateTop_Euslope,
                        stateRight_Euslope,
                        stateBottom_Euslope);

/*
                tvE.setText(""+stateTop_Euslope);
                tvI.setText(""+stateTop_InitRow);*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar_InitRow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stateTop_InitRow = 540 - progress;
                stateBottom_InitRow = lineWidth_InitRow + stateTop_InitRow;


                imageView_InitRow.layout(stateLeft_InitRow,
                        stateTop_InitRow,
                        stateRight_InitRow,
                        stateBottom_InitRow);
             /*   tvE.setText(""+stateTop_Euslope);
                tvI.setText(""+stateTop_InitRow);*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    Callback adas_callback = new Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDisplay(adas_surfaceHolder);
                    mediaPlayer.setDataSource(videoSrc);
                    mediaPlayer.prepare();
                    //  mediaPlayer.prepareAsync();
                }


                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();

                    }
                });
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        stateTop_Euslope=imageView_Euslope.getTop();
        stateRight_Euslope=imageView_Euslope.getRight();
        stateBottom_Euslope=imageView_Euslope.getBottom();
        stateLeft_Euslope=imageView_Euslope.getLeft();

        stateTop_InitRow=imageView_InitRow.getTop();
        stateRight_InitRow=imageView_InitRow.getRight();
        stateBottom_InitRow=imageView_InitRow.getBottom();
        stateLeft_InitRow=imageView_InitRow.getLeft();

        lineWidth_Euslope=stateBottom_Euslope-stateTop_Euslope;
        lineWidth_InitRow=stateBottom_InitRow-stateTop_InitRow;


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(toggleButton_Euslope_InitRow.isChecked()){//flag_Euslope_InitRow
                   //red line euslope up

                   if( stateTop_Euslope>=3){
                       imageView_Euslope.layout(stateLeft_Euslope,
                               stateTop_Euslope - 3,
                               stateRight_Euslope,
                               stateBottom_Euslope - 3);

                       //update Euslope state
                       stateBottom_Euslope=imageView_Euslope.getBottom();
                       stateLeft_Euslope=imageView_Euslope.getLeft();
                       stateRight_Euslope=imageView_Euslope.getRight();
                       stateTop_Euslope=imageView_Euslope.getTop();

                       //keep InitRow state
                       imageView_InitRow.layout(stateLeft_InitRow,
                               stateTop_InitRow,
                               stateRight_InitRow,
                               stateBottom_InitRow);

                       //reset SeedBar position
                       seekBar_Euslope.setProgress(stateTop_Euslope);
                   }else{
                       Toast.makeText(ADAS.this,"已移至邊界不能再往上移動",Toast.LENGTH_SHORT).show();
                   }


               }else{
                   //blue line InitRow up
                   if(stateTop_InitRow>=3){
                       imageView_InitRow.layout(stateLeft_InitRow,
                               stateTop_InitRow - 3,
                               stateRight_InitRow,
                               stateBottom_InitRow - 3);


                       //update InitRow state
                       stateBottom_InitRow=imageView_InitRow.getBottom();
                       stateLeft_InitRow=imageView_InitRow.getLeft();
                       stateRight_InitRow=imageView_InitRow.getRight();
                       stateTop_InitRow=imageView_InitRow.getTop();
                       //keep Euslope state
                       imageView_Euslope.layout(stateLeft_Euslope,
                               stateTop_Euslope,
                               stateRight_Euslope,
                               stateBottom_Euslope);

                       //reset SeedBar position
                       seekBar_InitRow.setProgress(540-stateTop_InitRow);
                   }else{
                       Toast.makeText(ADAS.this,"已移至邊界不能再往上移動",Toast.LENGTH_SHORT).show();
                   }


               }




            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggleButton_Euslope_InitRow.isChecked()){//flag_Euslope_InitRow
                    //red line euslope down

                    if(stateTop_Euslope<=537){
                        imageView_Euslope.layout(stateLeft_Euslope,
                                stateTop_Euslope + 3,
                                stateRight_Euslope,
                                stateBottom_Euslope + 3);


                        //update Euslope state
                        stateBottom_Euslope=imageView_Euslope.getBottom();
                        stateLeft_Euslope=imageView_Euslope.getLeft();
                        stateRight_Euslope=imageView_Euslope.getRight();
                        stateTop_Euslope=imageView_Euslope.getTop();
                        //keep InitRow state
                        imageView_InitRow.layout(stateLeft_InitRow,
                                stateTop_InitRow,
                                stateRight_InitRow,
                                stateBottom_InitRow);
                        //reset SeedBar position
                        seekBar_Euslope.setProgress(stateTop_Euslope);
                    }else{
                        Toast.makeText(ADAS.this,"已移至邊界不能再往下移動",Toast.LENGTH_SHORT).show();
                    }



                }else{
                    //blue line InitRow down

                    if(stateTop_InitRow<=537){
                        imageView_InitRow.layout(stateLeft_InitRow,
                                stateTop_InitRow + 3,
                                stateRight_InitRow,
                                stateBottom_InitRow + 3);


                        //update InitRow state
                        stateBottom_InitRow=imageView_InitRow.getBottom();
                        stateLeft_InitRow=imageView_InitRow.getLeft();
                        stateRight_InitRow = imageView_InitRow.getRight();
                        stateTop_InitRow=imageView_InitRow.getTop();
                        //keep Euslope state
                        imageView_Euslope.layout(stateLeft_Euslope,
                                stateTop_Euslope,
                                stateRight_Euslope,
                                stateBottom_Euslope);

                        //reset SeedBar position
                        seekBar_InitRow.setProgress(540-stateTop_InitRow);
                    }else{
                        Toast.makeText(ADAS.this,"已移至邊界不能再往下移動",Toast.LENGTH_SHORT).show();
                    }

                }




            }
        });

        // ------TCP -----
        arrayList = new ArrayList<String>();




    /*    //relate the listView from java to the one created in xml
        mList = (ListView)findViewById(R.id.list);
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);
*/
        // connect to the server
        new connectTask().execute("");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String message = editText.getText().toString();
                String message_euslope = "euslope " + getEuslopeParameter(imageView_Euslope.getTop());
                String message_initRow = "initRow  " + getInitRowParameter(imageView_InitRow.getTop());

                //add the text in the arrayList
                arrayList.add("c: " + message_euslope);

                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message_euslope);
                    mTcpClient.sendMessage(message_initRow);
                }


            }
        });



        spinnerLDW_FCW_sensitivity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  parent	The AdapterView where the selection happened
                //  view	The view within the AdapterView that was clicked
                //  position	The position of the view in the adapter
                //  id	The row id of the item that is selected

                //    Toast.makeText(Setting.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                  //  mTcpClient.sendMessage("sensitivity "+(position + 1));
                    mTcpClient.sendMessage("sensitivity "+position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    int getEuslopeParameter(int euslopeGetTop) {
        return (540 - euslopeGetTop) * 2;

    }

    int getInitRowParameter(int initRowGetTop) {
        return (540 - initRowGetTop) * 2;
    }

    //-------TCP---------
    public class connectTask extends AsyncTask<String, String, TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);

         /*
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();

         */

        }
    }
}
