package ir.mbf5923.adineh.ganjadin.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.anastr.flattimelib.CountDownTimerView;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChallengeActivity extends AppCompatActivity {
    private SmoothProgressBar pbProgress;
    private TextView tvtasklevel, tvtaskname, tvtaskinfo, btnscan, tvtimer;
    private Activity sp;
    private CountDownTimerView mHourGlass;
    private RelativeLayout reltask, reltimer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        sp = this;
        tvtimer = findViewById(R.id.tvtimer);
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v) -> {
            sp.finish();
        });
        reltask = findViewById(R.id.reltask);
        reltimer = findViewById(R.id.reltimer);
        mHourGlass = findViewById(R.id.mHourGlass);
        pbProgress = findViewById(R.id.pbProgress);
        tvtasklevel = findViewById(R.id.tvtasklevel);
        tvtaskname = findViewById(R.id.tvtaskname);
        tvtaskinfo = findViewById(R.id.tvtaskinfo);
        btnscan = findViewById(R.id.btnscan);
        btnscan.setClickable(false);


        btnscan.setOnClickListener((View v) -> {
            Intent intent = new Intent(sp, ScanActivity.class);
            startActivity(intent);
            sp.finish();
        });
    }


    private void loadtask() {
        pbProgress.setVisibility(View.VISIBLE);
        reltimer.setVisibility(View.GONE);
        reltask.setVisibility(View.GONE);
        NetworkManager.getInstance().loadtask(new NetworkListener() {

            @Override
            public void onResult(JSONObject result) {

                pbProgress.setVisibility(View.GONE);
                try {
                    if (result.getInt("id") == 1) {
                        reltask.setVisibility(View.VISIBLE);
                        tvtasklevel.setText(numtotext(result.getInt("tasknumber")));
                        tvtaskname.setText(result.getString("taskname"));
                        tvtaskinfo.setText(result.getString("taskinfo"));
                        btnscan.setClickable(true);
                    } else {
                        if (result.getLong("timeleft") > 0) {

                            countDownTimer = new CountDownTimer(result.getLong("timeleft"), 1000) {

                                public void onTick(long millisUntilFinished) {
                                    int totalSecs = (int) millisUntilFinished / 1000;
                                    int minutes = (totalSecs % 3600) / 60;
                                    int seconds = totalSecs % 60;
                                    String timeString = String.format("%02d:%02d", minutes, seconds);
                                    try {
                                        tvtimer.setText(timeString + " تا شروع " + numtotext(result.getInt("tasknumber")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        tvtimer.setText(timeString);
                                    }
                                    //progressBar.setProgress((int)(millisUntilFinished / 1000));
                                }

                                public void onFinish() {
                                }
                            }.start();

                            reltimer.setVisibility(View.VISIBLE);
                            mHourGlass.start(result.getLong("timeleft"));


                            mHourGlass.setOnTimeFinish(() -> {
                                pbProgress.setVisibility(View.VISIBLE);
                                reltimer.setVisibility(View.GONE);
                                loadtask();

                            });
                            Alerter.create(sp)
                                    .setDuration(5000)
                                    .setTitle("پیام سرور")
                                    .setText(result.getString("msg"))
                                    .setContentGravity(Gravity.END)
                                    .setBackgroundColorInt(Color.parseColor("#FFB64F"))
                                    .setIcon(R.drawable.warningicon)
                                    .enableSwipeToDismiss()
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
                                    .show();
                        }

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

            }
        }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken());
    }

    private String numtotext(int tnumber) {
        String snumber = "";
        switch (tnumber) {
            case 1:
                snumber = "مرحله اول";
                break;
            case 2:
                snumber = "مرحله دوم";
                break;
            case 3:
                snumber = "مرحله سوم";
                break;
            case 4:
                snumber = "مرحله چهارم";
                break;
            case 5:
                snumber = "مرحله پنجم";
                break;

            case 6:
                snumber = "مرحله ششم";
                break;
            case 7:
                snumber = "مرحله هفتم";
                break;
            case 8:
                snumber = "مرحله هشتم";
                break;
            case 9:
                snumber = "مرحله نهم";
                break;
            case 10:
                snumber = "مرحله دهم";
                break;
            case 11:
                snumber = "مرحله یازدهم";
                break;
            case 12:
                snumber = "مرحله دوازدهم";
                break;
            case 13:
                snumber = "مرحله سیزدهم";
                break;
            case 14:
                snumber = "مرحله چهاردهم";
                break;
            case 15:
                snumber = "مرحله پامزدهم";
                break;
            case 16:
                snumber = "مرحله شانزدهم";
                break;
            case 17:
                snumber = "مرحله هفدهم";
                break;
            case 18:
                snumber = "مرحله هجدهم";
                break;
            case 19:
                snumber = "مرحله نوزدهم";
                break;
            case 20:
                snumber = "مرحله بیستم";
                break;
        }
        return snumber;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHourGlass.stop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        mHourGlass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadtask();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
