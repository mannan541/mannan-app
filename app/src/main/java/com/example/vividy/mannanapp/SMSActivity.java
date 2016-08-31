package com.example.vividy.mannanapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by VIVIDY on 4/25/2016.
 */
public class SMSActivity extends AppCompatActivity {

    Button smsBtn;
    EditText smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        smsText = (EditText) findViewById(R.id.smsText);

        smsBtn = (Button) findViewById(R.id.smsmessage);
        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smsManager();
            }
        });
    }

    public void smsManager(){

        BroadcastReceiver smsSentReceiver = null;
        String phone="03044422122".toString();
        String message="" +smsText.getText().toString() ;
        PendingIntent piSent= PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phone, null, message, piSent, null);
        Toast.makeText(getApplicationContext(), "Message Sent to \n"+phone, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Your message is: \n"+message, Toast.LENGTH_LONG).show();

        registerReceiver(smsSentReceiver, new IntentFilter("sms sent"));

        registerReceiver(new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("sent"));

    }

}
