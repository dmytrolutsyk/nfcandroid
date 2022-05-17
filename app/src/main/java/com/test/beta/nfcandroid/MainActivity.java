package com.test.beta.nfcandroid;

import androidx.annotation.RequiresApi;
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
import android.widget.ListView;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity implements IsoDepTransceiver.OnMessageReceived, NfcAdapter.ReaderCallback {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/
    private NfcAdapter nfcAdapter;
    private ListView listView;
    private IsoDepAdapter isoDepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        isoDepAdapter = new IsoDepAdapter(getLayoutInflater());
        listView.setAdapter(isoDepAdapter);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /*@Override
    public void onResume() {
        super.onResume();
        IntentFilter writeTagFilters[];
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        writeTagFilters = new IntentFilter[] { tagDetected };

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(nfcAdapter==null)
        {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        }
        nfcAdapter.enableForegroundDispatch(this, pendingIntent,       writeTagFilters, null);

    }*/
    @Override
    public void onResume() {
        super.onResume();
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
                null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        super.onPause();
        nfcAdapter.disableReaderMode(this);
    }

    public void onTagDiscovered(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);
        IsoDepTransceiver transceiver = new IsoDepTransceiver(isoDep, (IsoDepTransceiver.OnMessageReceived) this);
        Thread thread = new Thread(transceiver);
        thread.start();
    }

    public void onMessage(final byte[] message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                isoDepAdapter.addMessage(new String(message));
            }
        });
    }

    public void onError(Exception exception) {
        onMessage(exception.getMessage().getBytes());
    }
}