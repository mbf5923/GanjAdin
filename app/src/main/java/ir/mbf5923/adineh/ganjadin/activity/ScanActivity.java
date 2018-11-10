package ir.mbf5923.adineh.ganjadin.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.androidnetworking.error.ANError;
import com.google.zxing.Result;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import org.json.JSONException;
import org.json.JSONObject;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.Config;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


private ZXingScannerView.ResultHandler xxx;
    private static Activity sp;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        sp = this;
        xxx=this;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here


        String scaned = rawResult.getText();

        if (scaned.startsWith(Config.qrperfix)) {
            NetworkManager.getInstance().checkscaned(new NetworkListener() {
                @Override
                public void onResult(JSONObject result) {
                    try {
                        if (result.getInt("id") == 1) {
                            //not end challenge
                            Alerter.create(sp)
                                    .setDuration(3000)
                                    .setTitle("پایان مرحله")
                                    .setText("این مرحله رو با موفقیت گذروندی حالا بریم مرحله بعد.")
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                                    .setIcon(R.drawable.successicon)
                                    .enableSwipeToDismiss()
                                    .setOnHideListener(new OnHideAlertListener() {
                                        @Override
                                        public void onHide() {
                                            Intent intent = new Intent(sp, ChallengeActivity.class);
                                            startActivity(intent);
                                            mScannerView.stopCamera();
                                            sp.finish();
                                        }
                                    })
                                    .show();
                        } else if (result.getInt("id") == 2) {
                            //end challenge but not winner
                            Alerter.create(sp)
                                    .setDuration(3000)
                                    .setTitle("پایان مسابقه")
                                    .setText("شما مسابقه را با موفقیت به پایان رساندید اما متاسفانه قبل از شما بازیکن دیگری برنده مسابقه شده است.")
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                                    .setIcon(R.drawable.successicon)
                                    .enableSwipeToDismiss()
                                    .setOnHideListener(new OnHideAlertListener() {
                                        @Override
                                        public void onHide() {
                                            mScannerView.stopCamera();
                                            sp.finish();
                                        }
                                    })
                                    .show();
                        } else if (result.getInt("id") == 3) {
                            //is winner
                            Alerter.create(sp)
                                    .setDuration(3000)
                                    .setTitle("پایان مسابقه")
                                    .setText("تبریک میگیم، شما برنده مسابقه این هفته ما شدید.")
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                                    .setIcon(R.drawable.successicon)
                                    .enableSwipeToDismiss()
                                    .setOnHideListener(new OnHideAlertListener() {
                                        @Override
                                        public void onHide() {

                                            Intent intent = new Intent(sp, WinnerActivity.class);
                                            startActivity(intent);
                                            mScannerView.stopCamera();
                                            sp.finish();
                                        }
                                    })
                                    .show();
                        } else {
                            Alerter.create(sp)
                                    .setDuration(5000)
                                    .setTitle("خطا")
                                    .setText(result.getString("msg"))
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                                    .setIcon(R.drawable.erroricon)
                                    .enableSwipeToDismiss()
                                    .setOnHideListener(new OnHideAlertListener() {
                                        @Override
                                        public void onHide() {
                                            mScannerView.resumeCameraPreview(xxx);
                                        }
                                    })
                                    .show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError error) {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("خطا")
                            .setText("ارتباط اینترنت خود را چک نمایید")
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                            .setIcon(R.drawable.erroricon)
                            .enableSwipeToDismiss()
                            .show();
                    mScannerView.resumeCameraPreview(ScanActivity.this);
                }
            }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken(), scaned);

        } else {
            mScannerView.resumeCameraPreview(this);
        }


        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(sp, ChallengeActivity.class);
        startActivity(intent);
        mScannerView.stopCamera();
        sp.finish();
    }


}
