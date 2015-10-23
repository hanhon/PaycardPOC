package com.qualicom.paycardpoc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.controller.PayCardController;
import com.qualicom.emvpaycard.controller.ReadRecordController;
import com.qualicom.emvpaycard.controller.SelectController;
import com.qualicom.emvpaycard.model.SelectResponse;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.EmvCommand;
import com.qualicom.emvpaycard.utils.EmvPayCardUtils;
import com.qualicom.emvpaycard.enums.EmvCommandEnum;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TECH_NFCA = "android.nfc.tech.NfcA";
    public static final String TECH_NFCB = "android.nfc.tech.NfcB";
    public static final String TECH_UNKNOWN = "???";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        try {
            handleIntent(intent);
        } catch (EmvPayCardException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            handleIntent(intent);
        } catch (EmvPayCardException e) {
            e.printStackTrace();
        }
    }

    private void handleIntent(Intent intent) throws EmvPayCardException {
        if (intent != null && NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tag = (Tag)intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast toast = null;

            if (EmvPayCardUtils.isValidEmvPayCard(tag)) {
                if (EmvPayCardUtils.isTypeAPayCard(tag))
                    toast = Toast.makeText(this, "This is a valid NFC type A payment card.", Toast.LENGTH_SHORT);
                if (EmvPayCardUtils.isTypeBPayCard(tag))
                    toast = Toast.makeText(this, "This is a valid NFC type A payment card.", Toast.LENGTH_SHORT);

                PayCardController payCardController = new PayCardController(tag);
                payCardController.connect();
                SelectController selectController = new SelectController(payCardController);
                SelectResponse response = selectController.selectPSE();
                Log.i("RESPONSE", response.toString());
                response = selectController.selectDDF(SelectController.PPSE);
                Log.i("RESPONSE", response.toString());
                String appId = response.getFciTemplate().getFciProprietaryTemplate().getIssuerDiscretionaryData().get(0).getAdfName();
                response = selectController.selectADF(ByteString.hexStringToByteArray(appId)); //Mastercard.
                Log.i("RESPONSE", response.toString());
//                ReadRecordController readRecordController = new ReadRecordController(payCardController);
//                readRecordController.readPSERecord();
                payCardController.disconnect();

            } else
                toast = Toast.makeText(this, "This is an invalid or unknown payment card.", Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    private String printByteStream(byte[] stream) {
        StringBuffer buffer = new StringBuffer();
        for (byte b : stream) {
            buffer.append(byteToHexString(b) + " ");
        }
        return buffer.toString();
    }

    private String byteToHexString(byte b) {
        String hexString = Integer.toHexString(b & 0xff);
        if (hexString.length() == 1) hexString = "0" + hexString;
        return hexString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
