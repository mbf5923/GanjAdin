package ir.mbf5923.adineh.ganjadin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.example.myloadingbutton.MyLoadingButton;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import org.json.JSONException;
import org.json.JSONObject;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChallengeInfoActivity extends AppCompatActivity {
    private SmoothProgressBar pbProgress;
    private Activity sp;
    private TextView tvdate,tvpoincnt,tvusers,tvreqcharge,tvtimeend,tvprice;
    private MyLoadingButton btnstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_info);
        sp=this;
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v) -> {
            sp.finish();
        });
        btnstart=findViewById(R.id.btnstart);
        pbProgress=findViewById(R.id.pbProgress);
        tvdate=findViewById(R.id.tvdate);
        tvpoincnt=findViewById(R.id.tvpoincnt);
        tvusers=findViewById(R.id.tvusers);
        tvreqcharge=findViewById(R.id.tvreqcharge);
        tvtimeend=findViewById(R.id.tvtimeend);
        tvprice=findViewById(R.id.tvprice);
        NetworkManager.getInstance().loadchallengeinfo(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                pbProgress.setVisibility(View.GONE);
                try {
                    if(result.getInt("id")==1) {
                        tvdate.setText(result.getString("date"));
                        tvpoincnt.setText(result.getString("poincnt") + " مرحله");
                        tvusers.setText(result.getString("users") + " نفر");
                        tvreqcharge.setText(result.getString("reqcharge") + " تومان");
                        tvtimeend.setText(result.getString("timeend"));
                        tvprice.setText(result.getString("price"));
                    }else {
                        Alerter.create(sp)
                                .setDuration(5000)
                                .setTitle("خطا")
                                .setText(result.getString("msg"))
                                .setContentGravity(Gravity.END)
                                .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                                .setIcon(R.drawable.erroricon)
                                .enableSwipeToDismiss()
                                .show();
                        pbProgress.setVisibility(View.GONE);
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
                pbProgress.setVisibility(View.GONE);
            }
        },SHPManager.getInstance().GetPhone(),SHPManager.getInstance().GetToken());


        btnstart.setMyButtonClickListener(()-> {
            NetworkManager.getInstance().startchallenge(new NetworkListener() {
                @Override
                public void onResult(JSONObject result) {

                    try {
                        if(result.getInt("id")==1){
                            Alerter.create(sp)
                                    .setDuration(3000)
                                    .setTitle("تایید")
                                    .setText("شما با موفقیت وارد شدید.")
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                                    .setIcon(R.drawable.successicon)
                                    .enableSwipeToDismiss()
                                    .setOnHideListener(new OnHideAlertListener() {
                                        @Override
                                        public void onHide() {
                                            Intent intent=new Intent(sp,ChallengeActivity.class);
                                            startActivity(intent);
                                            sp.finish();
                                        }
                                    })
                                    .show();
                        }else {
                            Alerter.create(sp)
                                    .setDuration(5000)
                                    .setTitle("خطا")
                                    .setText(result.getString("msg"))
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                                    .setIcon(R.drawable.erroricon)
                                    .enableSwipeToDismiss()
                                    .show();
                            btnstart.showErrorButton();
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
                    btnstart.showErrorButton();
                }
            },SHPManager.getInstance().GetPhone(),SHPManager.getInstance().GetToken());
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
