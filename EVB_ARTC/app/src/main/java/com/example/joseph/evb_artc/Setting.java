package com.example.joseph.evb_artc;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by joseph on 2015/8/17.
 */
public class Setting extends Activity {
    public Spinner spinnerSensivity, spinnerCycle_Rec_Time, spinnerSleepTime,spinnerEV;
    public String[] str_time_length = {"3分鐘", "5分鐘", "10分鐘"};
    public String[] str_sensivity = {"低", "中", "高"};
    public String[] str_EV = {"+2.0", "+1.7", "+1.3","+1.0", "+0.7", "+0.3","0", "-1.3", "-0.7","-1.0", "-1.3", "-1.7","-2.0"};
    public ArrayAdapter adapterSensitivity, adapterTime_length,adapterEV;
    public TextView tvSensivity, tvSleepTime, tvCycle_Rec_length,tvEV,tvSOS,tvLDW_FCW;
    public EditText etPhoneNumber1,etMail1,etPhoneNumber2,etMail2;



    public Button btnSendMail1,btnSendSMS1,btnAlertDialogSendSMS;

    public AlertDialog.Builder alertDialogBuilder;
    public CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        findviews();

        adapterSensitivity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_sensivity);
        adapterSensitivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSensivity.setAdapter(adapterSensitivity);
        spinnerSensivity.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  parent	The AdapterView where the selection happened
                //  view	The view within the AdapterView that was clicked
                //  position	The position of the view in the adapter
                //  id	The row id of the item that is selected

                //    Toast.makeText(Setting.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2011&par=1");

                        break;
                    case 1:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2011&par=2");

                        break;
                    case 2:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2011&par=3");

                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        adapterTime_length = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_time_length);
        adapterTime_length.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCycle_Rec_Time.setAdapter(adapterTime_length);
        spinnerCycle_Rec_Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  parent	The AdapterView where the selection happened
                //  view	The view within the AdapterView that was clicked
                //  position	The position of the view in the adapter
                //  id	The row id of the item that is selected

                //     Toast.makeText(Setting.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2003&par=1");

                        break;
                    case 1:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2003&par=2");

                        break;
                    case 2:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=2003&par=3");

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSleepTime.setAdapter(adapterTime_length);
        spinnerSleepTime.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  parent	The AdapterView where the selection happened
                //  view	The view within the AdapterView that was clicked
                //  position	The position of the view in the adapter
                //  id	The row id of the item that is selected

                //  Toast.makeText(Setting.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=3007&par=1");

                        break;
                    case 1:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=3007&par=2");

                        break;
                    case 2:
                        goToUrl("http://192.168.1.254/?custom=1&cmd=3007&par=3");

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---EV
        adapterEV = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_EV);
        adapterEV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEV.setAdapter(adapterEV);
        spinnerEV.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  parent	The AdapterView where the selection happened
                //  view	The view within the AdapterView that was clicked
                //  position	The position of the view in the adapter
                //  id	The row id of the item that is selected

                //    Toast.makeText(Setting.this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                goToUrl("http://192.168.1.254/?custom=1&cmd=2005&par="+position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void findviews() {
        tvLDW_FCW=(TextView)findViewById(R.id.tvLDW_FCW);
        tvSensivity = (TextView) findViewById(R.id.tvSensivity);
        tvSleepTime = (TextView) findViewById(R.id.tvSleepTime);
        tvCycle_Rec_length = (TextView) findViewById(R.id.tvCycle_Rec_length);
        tvEV=(TextView)findViewById(R.id.tvEV);
        tvSOS=(TextView)findViewById(R.id.tvSOS);

        spinnerSensivity = (Spinner) findViewById(R.id.spinnerSensivity);
        spinnerSensivity.setSelection(0, true);


        spinnerCycle_Rec_Time = (Spinner) findViewById(R.id.spinnerCycle_Rec_Time);
        spinnerCycle_Rec_Time.setSelection(0, true);

        spinnerSleepTime = (Spinner) findViewById(R.id.spinnerSleepTime);
        spinnerSleepTime.setSelection(0, true);

        spinnerEV = (Spinner) findViewById(R.id.spinnerEV);
        spinnerEV.setSelection(0, true);

        etMail1=(EditText)findViewById(R.id.etMail1);
        etPhoneNumber1=(EditText)findViewById(R.id.etPhoneNumber1);
        etMail2=(EditText)findViewById(R.id.etMail2);
        etPhoneNumber2=(EditText)findViewById(R.id.etPhoneNumber2);

        btnSendMail1=(Button)findViewById(R.id.btnSendMail1);
        btnSendMail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Date now = new java.util.Date();
                String time=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);


                Intent intentMail = new Intent(Intent.ACTION_SEND);
                intentMail.setType("message/rfc822");
                intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{etMail1.getText().toString()});
                intentMail.putExtra(Intent.EXTRA_SUBJECT,"緊急通報");
                intentMail.putExtra(Intent.EXTRA_TEXT,"於"+time+"疑似發生事故 \n"+"地點如下 :　http://maps.google.com/maps?q=24.180072,120.624690");



                //intentMail.putExtra(Intent.EXTRA_STREAM, Uri.parse("/data/"+ "com.example.joseph.evb_artc" +"/res/"+"HealthDB.db"));
                //intentMail.setType("image/jpeg");

                try {
                    startActivity(Intent.createChooser(intentMail, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Setting.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSendSMS1=(Button)findViewById(R.id.btnSendSMS1);
        btnSendSMS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   smsManager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent)
                1.destinationAddress發送出去那兒的號碼，收訊息者號碼
                2.scAddress訊息服務中心號碼，填null即可
                3. text簡訊內容
                4. sentIntent訊息發送結果廣播
                5.deliveryIntent訊息到達廣播
             */

                SmsManager smsManager = SmsManager.getDefault();
                try {

                   /* smsManager.sendTextMessage("0978320715",
                            null, "於"+time+"疑似發生事故 \n"+"地點如下 : "+"http://maps.google.com/maps?q=24.060256,120.385684",
                            PendingIntent.getBroadcast(getApplicationContext(), 0,new Intent(), 0),
                            null);*/

                    smsManager.sendTextMessage(etPhoneNumber1.getText().toString(),
                            null, "緊急通報 \n"+"http://maps.google.com/maps?q=24.180072,120.624690",
                            PendingIntent.getBroadcast(getApplicationContext(), 0,new Intent(), 0),
                            null);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnAlertDialogSendSMS=(Button)findViewById(R.id.btnAlertDialogSendSMS);
        btnAlertDialogSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 countDownTimer = new CountDownTimer(11000,1000){

                    @Override
                    public void onFinish() {

                        SmsManager smsManager = SmsManager.getDefault();
                        try {

                            smsManager.sendTextMessage(etPhoneNumber1.getText().toString(),
                                    null, "緊急通報 \n"+"http://maps.google.com/maps?q=24.180072,120.624690",
                                    PendingIntent.getBroadcast(getApplicationContext(), 0,new Intent(), 0),
                                    null);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        Toast.makeText(Setting.this,(millisUntilFinished/1000) + "秒後將自動傳送簡訊通知",Toast.LENGTH_SHORT).show();


                        //if((millisUntilFinished/1000)>=10){
                        //    ((DialogInterface)alertDialogBuilder).cancel();
                        //}
                    }

                }.start();

                ImageView image = new ImageView(Setting.this);
                image.setImageResource(R.drawable.sos1);



                alertDialogBuilder= new AlertDialog.Builder(Setting.this);
                alertDialogBuilder.setTitle("緊急通報")
                        .setMessage("10秒後將自動傳送簡訊通知")
                        .setView(image)
                       // .setIcon(R.drawable.sos)
                        .setPositiveButton("確定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  Setting.this.finish(); //關閉程式
                                countDownTimer.cancel();

                                SmsManager smsManager = SmsManager.getDefault();
                                try {

                                    smsManager.sendTextMessage(etPhoneNumber1.getText().toString(),
                                            null, "緊急通報 \n" + "http://maps.google.com/maps?q=24.180072,120.624690",
                                            PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), 0),
                                            null);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                dialog.cancel();
                            }

                        })
                        .setNegativeButton("取消",new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countDownTimer.cancel();
                                dialog.cancel();

                            }
                        })
                        .setCancelable(false)//讓使用者不能按Back鍵取消
                        .show();	//Inherited Methods From class android.app.Dialog	; Start the dialog and display it on screen.







              /*  new AlertDialog.Builder(Setting.this)
                        .setTitle("緊急通報")
                        .setMessage("10秒後將自動傳送簡訊通知")
                        .setIcon(R.drawable.sos)
                        .setPositiveButton("確定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //  Setting.this.finish(); //關閉程式

                            }

                        })
                        .setNegativeButton("取消",new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)//讓使用者不能按Back鍵取消
                        .show();	//Inherited Methods From class android.app.Dialog	; Start the dialog and display it on screen.
             */


            }
        });

    }




    private void goToUrl(String url)
    {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }



}
