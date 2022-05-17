package com.test.beta.nfcandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.cardemulation.HostApduService;
import android.nfc.tech.IsoDep;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "JDR HostCardEmulation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.app_name);

        Button setNdef = (Button) findViewById(R.id.set_ndef_button);
        setNdef.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           //
                                           // Technically, if this is past our byte limit,
                                           // it will cause issues.
                                           //
                                           // TODO: add validation
                                           //
                                           TextView getNdefString = (TextView) findViewById(R.id.ndef_text);
                                           String test = getNdefString.getText().toString();

                                           Intent intent = new Intent(view.getContext(), MyHostApduService.class);
                                           intent.putExtra("ndefMessage", test);
                                           startService(intent);
                                       }
                                   }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}