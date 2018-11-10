package ir.mbf5923.adineh.ganjadin.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.myloadingbutton.MyLoadingButton;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

import org.json.JSONException;
import org.json.JSONObject;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private MyLoadingButton btnlogin, btnconfirm;
    private EditText etnumber,etcode;
    private String number,code;
    private Activity sp;
    private RelativeLayout rellogin;
    private RelativeLayout relconfirm;
    private CountDownTimer countDownTimer;
    private TextView txttimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = this;
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v)-> {
            sp.finish();
        });
        txttimer = findViewById(R.id.txttimer);
        rellogin = findViewById(R.id.rellogin);
        relconfirm = findViewById(R.id.relconfirm);
        etnumber = findViewById(R.id.etnumber);
        etcode = findViewById(R.id.etcode);
        btnconfirm=findViewById(R.id.btnconfirm);
        btnconfirm.setProgressLoaderColor(Color.WHITE);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setProgressLoaderColor(Color.WHITE);
        btnlogin.setMyButtonClickListener(new MyLoadingButton.MyLoadingButtonClick() {
            @Override
            public void onMyLoadingButtonClick() {
                number = etnumber.getText().toString();
                if (!number.equals("")) {
                    if (number.length() == 11) {
                        NetworkManager.getInstance().login(new NetworkListener() {


                            @Override
                            public void onResult(JSONObject result) {
                                loginresponse(result);
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
                                btnlogin.showErrorButton();
                            }


                        }, number);
                    } else {
                        Alerter.create(sp)
                                .setDuration(5000)
                                .setTitle("خطا")
                                .setText("شماره تلفن همراه وارد شده صحیح نیست.")
                                .setContentGravity(Gravity.END)
                                .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                                .setIcon(R.drawable.erroricon)
                                .enableSwipeToDismiss()
                                .show();
                        btnlogin.showErrorButton();
                    }
                } else {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("خطا")
                            .setText("شماره تلفن همراه خود را وارد نمایید.")
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                            .setIcon(R.drawable.erroricon)
                            .enableSwipeToDismiss()
                            .show();
                    btnlogin.showErrorButton();
                }
            }
        });


        btnconfirm.setMyButtonClickListener(new MyLoadingButton.MyLoadingButtonClick() {
            @Override
            public void onMyLoadingButtonClick() {
                code = etcode.getText().toString();
                if (!code.equals("")) {
                    if (code.length() == 4) {
                        NetworkManager.getInstance().verification(new NetworkListener() {


                            @Override
                            public void onResult(JSONObject result) {
                                verificationresponse(result);
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
                                btnlogin.showErrorButton();
                            }


                        }, number,code);
                    } else {
                        Alerter.create(sp)
                                .setDuration(5000)
                                .setTitle("خطا")
                                .setText("کد تایید وارد شده صحیح نیست.")
                                .setContentGravity(Gravity.END)
                                .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                                .setIcon(R.drawable.erroricon)
                                .enableSwipeToDismiss()
                                .show();
                        btnconfirm.showErrorButton();
                    }
                } else {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("خطا")
                            .setText("کد تایید را وارد نمایید.")
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                            .setIcon(R.drawable.erroricon)
                            .enableSwipeToDismiss()
                            .show();
                    btnconfirm.showErrorButton();
                }
            }
        });
    }

    private void verificationresponse(JSONObject object) {


            try {

                if (object.getInt("id") == 1) {
                    SHPManager.getInstance().SetPhone(number);
                    SHPManager.getInstance().SetToken(object.getString("token"));
                    countDownTimer.cancel();
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
                                    sp.finish();
                                }
                            })
                            .show();

                }else {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("خطا")
                            .setText(object.getString("msg"))
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                            .setIcon(R.drawable.erroricon)
                            .enableSwipeToDismiss()
                            .show();
                    btnlogin.showErrorButton();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


    }

    private void loginresponse(JSONObject object) {


            try {

                if (object.getInt("id") == 1) {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("تایید")
                            .setText("کد تایید ورود به شماره شما ارسال گردید.")
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                            .setIcon(R.drawable.successicon)
                            .enableSwipeToDismiss()
                            .show();
                    YoYo.with(Techniques.SlideOutDown)
                            .duration(500)
                            .repeat(0)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    rellogin.setVisibility(View.GONE);
                                    relconfirm.setVisibility(View.VISIBLE);
                                    YoYo.with(Techniques.SlideInUp)
                                            .duration(500)
                                            .repeat(0)
                                            .playOn(relconfirm);
                                    btnlogin.showNormalButton();
                                    countDownTimer = new CountDownTimer(300000, 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            int totalSecs = (int) millisUntilFinished / 1000;
                                            int minutes = (totalSecs % 3600) / 60;
                                            int seconds = totalSecs % 60;
                                            String timeString = String.format("%02d:%02d", minutes, seconds);
                                            txttimer.setText(timeString);
                                            //progressBar.setProgress((int)(millisUntilFinished / 1000));
                                        }

                                        public void onFinish() {
                                            txttimer.setText("درخواست مجدد");
                                            txttimer.setClickable(true);
                                            txttimer.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    txttimer.setText("");

                                                    YoYo.with(Techniques.SlideOutUp)
                                                            .duration(500)
                                                            .repeat(0)
                                                            .withListener(new Animator.AnimatorListener() {
                                                                @Override
                                                                public void onAnimationStart(Animator animation) {

                                                                }

                                                                @Override
                                                                public void onAnimationEnd(Animator animation) {
                                                                    relconfirm.setVisibility(View.GONE);
                                                                    rellogin.setVisibility(View.VISIBLE);

                                                                    YoYo.with(Techniques.SlideInDown)
                                                                            .duration(500)
                                                                            .repeat(0)
                                                                            .playOn(rellogin);
                                                                }

                                                                @Override
                                                                public void onAnimationCancel(Animator animation) {

                                                                }

                                                                @Override
                                                                public void onAnimationRepeat(Animator animation) {

                                                                }
                                                            })
                                                            .playOn(relconfirm);
                                                    countDownTimer.cancel();
                                                }
                                            });
                                        }
                                    }.start();
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })
                            .playOn(rellogin);
                }else {
                    Alerter.create(sp)
                            .setDuration(5000)
                            .setTitle("خطا")
                            .setText(object.getString("msg"))
                            .setContentGravity(Gravity.END)
                            .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                            .setIcon(R.drawable.erroricon)
                            .enableSwipeToDismiss()
                            .show();
                    btnlogin.showErrorButton();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
