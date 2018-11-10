package ir.mbf5923.adineh.ganjadin.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import com.irozon.justbar.BarItem;
import com.irozon.justbar.JustBar;
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

public class AccountActivity extends AppCompatActivity {
    private MyLoadingButton btnsave, btncharge;
    private EditText etname, etfamily, etnationalcode;
    private String name, family, nationalcode;
    private Activity sp;
    private RelativeLayout relacount, relcharge,relhistory;
    private View curentview;
    private int requiercharge = 0, currentcharge = 0;
    private TextView tvrequiercharge, tvcurrentcharge,tvnumofyouin,tvnumofchallenge;
    private SmoothProgressBar pbProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        sp = this;
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v) -> {
            sp.finish();
        });
        pbProgress=findViewById(R.id.pbProgress);
        etname = findViewById(R.id.etname);
        etfamily = findViewById(R.id.etfamily);
        etnationalcode = findViewById(R.id.etnationalcode);
        tvrequiercharge = findViewById(R.id.tvrequiercharge);
        tvcurrentcharge = findViewById(R.id.tvcurrentcharge);
        tvnumofyouin = findViewById(R.id.tvnumofyouin);
        tvnumofchallenge = findViewById(R.id.tvnumofchallenge);

        NetworkManager.getInstance().loadprofile(new NetworkListener() {

            @Override
            public void onResult(JSONObject result) {
                loadprofileresponse(result);
            }

            @Override
            public void onError(ANError error) {
                pbProgress.setVisibility(View.GONE);
            }
        }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken());
        JustBar menubar = findViewById(R.id.menubar);
        relacount = findViewById(R.id.relacount);
        relcharge = findViewById(R.id.relcharge);
        relhistory = findViewById(R.id.relhistory);
        curentview = relacount;
        menubar.setOnBarItemClickListener((BarItem barItem, int position) -> {
            switch (position) {
                case 0:
                    YoYo.with(Techniques.FlipOutX)
                            .duration(250)
                            .repeat(0)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    curentview.setVisibility(View.GONE);
                                    relacount.setVisibility(View.VISIBLE);
                                    curentview = relacount;
                                    YoYo.with(Techniques.FlipInX)
                                            .duration(250)
                                            .repeat(0)
                                            .playOn(relacount);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })

                            .playOn(curentview);
                    break;
                case 1:
                    YoYo.with(Techniques.FlipOutX)
                            .duration(250)
                            .repeat(0)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    curentview.setVisibility(View.GONE);
                                    relcharge.setVisibility(View.VISIBLE);
                                    curentview = relcharge;
                                    YoYo.with(Techniques.FlipInX)
                                            .duration(250)
                                            .repeat(0)
                                            .playOn(relcharge);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })

                            .playOn(curentview);
                    break;


                case 2:
                    YoYo.with(Techniques.FlipOutX)
                            .duration(250)
                            .repeat(0)
                            .withListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    curentview.setVisibility(View.GONE);
                                    relhistory.setVisibility(View.VISIBLE);
                                    curentview = relhistory;
                                    YoYo.with(Techniques.FlipInX)
                                            .duration(250)
                                            .repeat(0)
                                            .playOn(relhistory);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            })

                            .playOn(curentview);
                    break;
            }
        });

        btnsave = findViewById(R.id.btnsave);
        btncharge = findViewById(R.id.btncharge);

        btnsave.setMyButtonClickListener(() -> {
            name = etname.getText().toString();
            family = etfamily.getText().toString();
            nationalcode = etnationalcode.getText().toString();
            NetworkManager.getInstance().saveprofile(new NetworkListener() {
                @Override
                public void onResult(JSONObject result) {
                    saveprofileresponse(result);
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
                    btnsave.showErrorButton();
                }
            }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken(), name, family, nationalcode);
        });

        btncharge.setMyButtonClickListener(() -> {
            if (requiercharge > currentcharge) {

            } else {
                Alerter.create(sp)
                        .setDuration(5000)
                        .setTitle("خطا")
                        .setText("اعتبار شما جهت شرکت در مسابقه کافی است و نیازی به افزایش اعتبار نیست")
                        .setContentGravity(Gravity.END)
                        .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                        .setIcon(R.drawable.erroricon)
                        .enableSwipeToDismiss()
                        .show();
                btncharge.showErrorButton();
            }
        });
    }

    private void loadprofileresponse(JSONObject result) {

        try {
            if (result.getInt("id") == 1) {
                etname.setText(result.getString("name"));
                etfamily.setText(result.getString("family"));
                etnationalcode.setText(result.getString("nationalcode"));
                loadcharge();
            } else {
                pbProgress.setVisibility(View.GONE);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveprofileresponse(JSONObject result) {
        try {
            if (result.getInt("id") == 1) {
                Alerter.create(sp)
                        .setDuration(3000)
                        .setTitle("تایید")
                        .setText("تغییرات با موفقیت ذخیره شد")
                        .setContentGravity(Gravity.END)
                        .setBackgroundColorInt(Color.parseColor("#00DCBF"))
                        .setIcon(R.drawable.successicon)
                        .enableSwipeToDismiss()
                        .setOnHideListener(new OnHideAlertListener() {
                            @Override
                            public void onHide() {

                            }
                        })
                        .show();
                btnsave.showNormalButton();
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
                btnsave.showErrorButton();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loadcharge() {
        NetworkManager.getInstance().loadcharge(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {

                try {
                    if (result.getInt("id") == 1) {
                        currentcharge = result.getInt("currentcharge");
                        requiercharge = result.getInt("requiercharge");
                        tvcurrentcharge.setText("اعتبار حساب شما:"+currentcharge+" تومان");
                        tvrequiercharge.setText("اعتبار مورد نیاز:"+requiercharge+" تومان");
                        loadchallengehistory();
                    } else {
                        pbProgress.setVisibility(View.GONE);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError error) {

            }
        }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken());
    }


    private void loadchallengehistory() {
        NetworkManager.getInstance().loadchallengehistory(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {

                try {
                    if (result.getInt("id") == 1) {
                        pbProgress.setVisibility(View.GONE);
                        tvnumofyouin.setText("تعداد مسابقات شرکت کرده:"+result.getString("numofyouin"));
                        tvnumofchallenge.setText("تعداد مسابقات برگزار شده:"+result.getString("numofchallenge"));
                    } else {
                        pbProgress.setVisibility(View.GONE);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
