package ir.mbf5923.adineh.ganjadin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.example.myloadingbutton.MyLoadingButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jaredrummler.android.device.DeviceName;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import me.toptas.fancyshowcase.FancyShowCaseView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private MyLoadingButton btnstart, btnretry;
    private String token, phone;
    private Activity sp;
    private BoomMenuButton bmb;
    private SmoothProgressBar pbProgress;
    private TextView tvinfo;
    private String phonebrand, phonemodel, uniqid, simoperator;
    private int version;
    private RelativeLayout relstart, relretry, relmenu;
    private AlertDialog updatealert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = this;

        relmenu = findViewById(R.id.relmenu);
        btnretry = findViewById(R.id.btnretry);
        btnretry.setMyButtonClickListener(() -> {
            btnretry.showLoadingButton();
            checklogin();
        });
        relstart = findViewById(R.id.relstart);
        relretry = findViewById(R.id.relretry);

        pbProgress = findViewById(R.id.pbProgress);
        if (SHPManager.getInstance().GetShowIntro() == 0) {
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            this.finish();
        }
        token = SHPManager.getInstance().GetToken();
        bmb = findViewById(R.id.bmb);
        menubuilder();
        btnstart = findViewById(R.id.btnstart);
        btnstart.setMyButtonClickListener(() -> {

            if (!token.equals("")) {
                checkchallengedate();
            } else {
                Alerter.create(sp)
                        .setDuration(5000)
                        .setTitle("حساب کاربری")
                        .setText("برای شرکت توی مسابقه باید ثبت نام کنی و یا اگه قبلا ثبت نام کردی وارد حساب کاربریت بشی.")
                        .setContentGravity(Gravity.END)
                        .setBackgroundColorInt(Color.parseColor("#FFB64F"))
                        .setIcon(R.drawable.warningicon)
                        .enableSwipeToDismiss()
                        .show();
                btnstart.showErrorButton();
            }


        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (updatealert != null) {
            updatealert.dismiss();
        }
        bmb.setVisibility(View.GONE);
        btnretry.showNormalButton();
        relstart.setVisibility(View.GONE);
        relretry.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
        checklogin();

    }

    private void checklogin() {

        NetworkManager.getInstance().checklogin(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                checkloginresponse(result);

            }

            @Override
            public void onError(ANError error) {
                token = "";
                phone = "";
                btnretry.showErrorButton();
                relretry.setVisibility(View.VISIBLE);
                Alerter.create(sp)
                        .setDuration(5000)
                        .setTitle("خطا")
                        .setText(NetworkManager.getVollyErrorType(error))
                        .setContentGravity(Gravity.END)
                        .setBackgroundColorInt(Color.parseColor("#FF1D4C"))
                        .setIcon(R.drawable.erroricon)
                        .enableSwipeToDismiss()
                        .show();
                pbProgress.setVisibility(View.GONE);
                menubuilder();
            }


        }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken());
    }

    private void checkloginresponse(JSONObject object) {

        try {

            if (object.getInt("islogin") == 1) {
                token = SHPManager.getInstance().GetToken();
                phone = SHPManager.getInstance().GetPhone();
                getinfo();
            } else {
                SHPManager.getInstance().cleardata();
                token = "";
                phone = "";
            }

            if (object.getInt("inchallenge") == 1) {
                btnstart.setButtonLabel("ادامه");
                tvinfo = findViewById(R.id.tvinfo);
                tvinfo.setText("برای ادامه مسابقه بر روی ادامه کلیک کنید");
                btnstart.setMyButtonClickListener(() -> {
                    btnstart.showNormalButton();
                    Intent intent = new Intent(sp, ChallengeActivity.class);
                    startActivity(intent);
                });
            } else {
                btnstart.setButtonLabel("شروع");
                tvinfo = findViewById(R.id.tvinfo);
                tvinfo.setText("برای ورود به مسابقه بر روی شروع کلیک کنید");
                btnstart.setMyButtonClickListener(() -> {
                    if (!token.equals("")) {
                        checkchallengedate();
                    } else {
                        Alerter.create(sp)
                                .setDuration(5000)
                                .setTitle("حساب کاربری")
                                .setText("برای شرکت توی مسابقه باید ثبت نام کنی و یا اگه قبلا ثبت نام کردی وارد حساب کاربریت بشی.")
                                .setContentGravity(Gravity.END)
                                .setBackgroundColorInt(Color.parseColor("#FFB64F"))
                                .setIcon(R.drawable.warningicon)
                                .enableSwipeToDismiss()
                                .show();
                        btnstart.showErrorButton();
                    }
                });
            }
            pbProgress.setVisibility(View.GONE);
            relstart.setVisibility(View.VISIBLE);
            relretry.setVisibility(View.GONE);
            versioncheck(object.getInt("versioncode"), object.getInt("mustupdate"), object.getString("updatemessage"), object.getString("appurl"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        menubuilder();
    }

    private void menubuilder() {
        bmb.clearBuilders();
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        String accname = "", accinfo = "";
        if (!token.equals("")) {
            accname = "حساب کاربری";
            accinfo = "نمایش و تغییر اطلاعات حساب شما";
        } else {
            accname = "ثبت نام/ورود";
            accinfo = "ثبت نام و یا ورد به حساب کاربری";
        }
        HamButton.Builder builder = new HamButton.Builder()
                .normalImageRes(R.drawable.accounticon)
                .imagePadding(new Rect(0, 15, 0, 15))
                .normalText(accname)
                .subNormalText(accinfo)
                .normalColor(Color.parseColor("#669900"))
                .textGravity(Gravity.RIGHT)
                .subTextGravity(Gravity.RIGHT);
        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                if (!token.equals("")) {
                    Intent intent = new Intent(sp, AccountActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(sp, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        bmb.addBuilder(builder);
        builder = new HamButton.Builder()
                .normalImageRes(R.drawable.faqicon)
                .imagePadding(new Rect(0, 15, 0, 15))
                .normalText("سوالات رایج")
                .subNormalText("آشنایی بیشتر با مسابقه و روند اجرا")
                .normalColor(Color.parseColor("#FF8800"))
                .textGravity(Gravity.RIGHT)
                .subTextGravity(Gravity.RIGHT);
        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent intent = new Intent(sp, FAQActivity.class);
                startActivity(intent);
            }
        });
        bmb.addBuilder(builder);

        builder = new HamButton.Builder()
                .normalImageRes(R.drawable.newsicon)
                .imagePadding(new Rect(0, 15, 0, 15))
                .normalText("خبرها")
                .subNormalText("اخبار و اطلاعیه ها رو اینجا بخونید")
                .normalColor(Color.parseColor("#34bdb7"))
                .textGravity(Gravity.RIGHT)
                .subTextGravity(Gravity.RIGHT);
        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent intent = new Intent(sp, NewsActivity.class);
                startActivity(intent);
            }
        });
        bmb.addBuilder(builder);

        builder = new HamButton.Builder()
                .normalImageRes(R.drawable.supporticon)
                .imagePadding(new Rect(0, 15, 0, 15))
                .normalText("از من بپرس")
                .subNormalText("برای ارتباط مستقیم با کارشناسان ما در ساعات اداری")
                .normalColor(Color.parseColor("#466DFE"))
                .textGravity(Gravity.RIGHT)
                .subTextGravity(Gravity.RIGHT);
        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent intent = new Intent(sp, SupportActivity.class);
                startActivity(intent);
            }
        });
        bmb.addBuilder(builder);
        builder = new HamButton.Builder()
                .normalImageRes(R.drawable.abouticon)
                .imagePadding(new Rect(0, 15, 0, 15))
                .normalText("درباره ما")
                .subNormalText("با ما بیشتر آشنا بشید")
                .normalColor(Color.parseColor("#992EFF"))
                .textGravity(Gravity.RIGHT)
                .subTextGravity(Gravity.RIGHT);
        builder.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent intent = new Intent(sp, AboutActivity.class);
                startActivity(intent);
            }
        });
        bmb.addBuilder(builder);
        bmb.setVisibility(View.VISIBLE);


        new FancyShowCaseView.Builder(sp)
                .focusOn(relmenu)
                .title("از این قسمت به منوی برنامه دسترسی پیدا کنید")
                .titleGravity(Gravity.CENTER)
                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER | Gravity.CENTER)
                .enableAutoTextPosition()
                .closeOnTouch(true)
                .showOnce("menu")
                .build()
                .show();

    }


    private void checkchallengedate() {
        NetworkManager.getInstance().checkchallengedate(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                try {
                    if (result.getInt("id") == 1) {
                        btnstart.showNormalButton();
                        Intent intent = new Intent(sp, ChallengeInfoActivity.class);
                        startActivity(intent);
                    } else {
                        Alerter.create(sp)
                                .setDuration(5000)
                                .setTitle("پیام سیستم")
                                .setText(result.getString("msg"))
                                .setContentGravity(Gravity.END)
                                .setBackgroundColorInt(Color.parseColor("#FFB64F"))
                                .setIcon(R.drawable.warningicon)
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

            }
        }, phone, token);
    }


    private void getinfo() {

        DeviceName.with(sp).request((DeviceName.DeviceInfo info, Exception error) -> {


            phonebrand = info.manufacturer;
            phonemodel = info.marketName;
            version = Build.VERSION.SDK_INT;
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (manager != null) {
                simoperator = manager.getNetworkOperatorName();
            }

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(sp, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    uniqid = instanceIdResult.getToken();
                    NetworkManager.getInstance().saveuserphone(new NetworkListener() {
                        @Override
                        public void onError(ANError error) {

                        }
                    }, phone, token, phonebrand, phonemodel, version + "", simoperator, uniqid);

                }
            });
            // "Galaxy S8+"
            // FYI: We are on the UI thread.


        });


    }

    private void versioncheck(int version, int mustupdate, String updatemessage,String appurl) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            if (version > versionCode) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(sp,
                        R.style.AlertDialogCustom));
                if (mustupdate == 0) {
                    alertDialogBuilder.setCancelable(true);
                }else {
                    alertDialogBuilder.setCancelable(false);
                }

                alertDialogBuilder.setTitle("بروزرسانی");
                alertDialogBuilder.setMessage(updatemessage);

                alertDialogBuilder.setPositiveButton("بروزرسانی", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //sp.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + sp.getPackageName())));
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appurl));
                        startActivity(browserIntent);
                        dialog.cancel();
                    }
                });
                updatealert = alertDialogBuilder.show();
                TextView titleView = updatealert.findViewById(sp.getResources().getIdentifier("alertTitle", "id", "android"));
                if (titleView != null) {
                    titleView.setGravity(Gravity.RIGHT);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmb.reboom();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
