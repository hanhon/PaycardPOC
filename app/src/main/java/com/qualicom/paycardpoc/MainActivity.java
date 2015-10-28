package com.qualicom.paycardpoc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.qualicom.emvpaycard.EmvPayCardException;
import com.qualicom.emvpaycard.business.CardData;
import com.qualicom.emvpaycard.controller.GetProcessingOptionsController;
import com.qualicom.emvpaycard.controller.PayCardController;
import com.qualicom.emvpaycard.controller.ReadRecordController;
import com.qualicom.emvpaycard.controller.SelectController;
import com.qualicom.emvpaycard.data.GetProcessingOptionsResponse;
import com.qualicom.emvpaycard.data.ReadResponse;
import com.qualicom.emvpaycard.data.SelectResponse;
import com.qualicom.emvpaycard.utils.ByteString;
import com.qualicom.emvpaycard.utils.PayCardUtils;

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

            if (PayCardUtils.isValidEmvPayCard(tag)) {
                if (PayCardUtils.isTypeAPayCard(tag))
                    toast = Toast.makeText(this, "This is a valid NFC type A payment card.", Toast.LENGTH_SHORT);
                if (PayCardUtils.isTypeBPayCard(tag))
                    toast = Toast.makeText(this, "This is a valid NFC type A payment card.", Toast.LENGTH_SHORT);

                PayCardController payCardController = new PayCardController(tag);
                payCardController.connect();
                SelectController selectController = new SelectController(payCardController);
                SelectResponse pseResponse = selectController.selectPSE();
                Log.i("PSE RESPONSE", pseResponse.toString());
                SelectResponse ddfResponse = selectController.selectDDF(SelectController.PPSE);
                Log.i("DDF RESPONSE", ddfResponse.toString());
                String appId = ddfResponse.getFciTemplate().getFciProprietaryTemplate().getIssuerDiscretionaryData().getApplicationTemplateData().get(0).getAdfName();
                SelectResponse appResponse = selectController.selectADF(ByteString.hexStringToByteArray(appId)); //Mastercard.
                Log.i("APP RESPONSE", appResponse.toString());

                GetProcessingOptionsController gpoController = new GetProcessingOptionsController(payCardController);
                GetProcessingOptionsResponse gpoResponse = gpoController.getApplicationProfile("8300");
                Log.i("GPO RESPONSE", gpoResponse.toString());

                ReadRecordController readRecordController = new ReadRecordController(payCardController);
                ReadResponse readResponse = null;
                if (gpoResponse.isSuccessfulResponse()) {
                    readResponse = readRecordController.readRecord(
                            gpoResponse.getApplicationFileLocator().getFirstRecordNum(),
                            gpoResponse.getApplicationFileLocator().getShortFileIdentifier(),
                            (byte) (gpoResponse.getApplicationFileLocator().getLastRecordNum() - gpoResponse.getApplicationFileLocator().getFirstRecordNum())
                    );
                    Log.i("RR RESPONSE", readResponse.toString());
                } else {
                    readResponse = readRecordController.readRecord(
                            (byte)01,
                            (byte)01,
                            (byte)00);
                }

                if (readResponse.isSuccessfulResponse()) {
                    CardData cardData = new CardData(readResponse.getApplicationData(),ddfResponse.getFciTemplate().getFciProprietaryTemplate().getIssuerDiscretionaryData().getApplicationTemplateData().get(0));
                    Log.i("DATA", cardData.toString());
                    TextView textView = (TextView)findViewById(R.id.content_text);
                    textView.setText(cardData.toString());
                } else {
                    TextView textView = (TextView)findViewById(R.id.content_text);
                    textView.setText(readResponse.getStatusWord() + " " + readResponse.getStatusMessage());
                }


                payCardController.disconnect();

            } else
                toast = Toast.makeText(this, "This is an invalid or unknown payment card.", Toast.LENGTH_SHORT);
            toast.show();

        }
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
