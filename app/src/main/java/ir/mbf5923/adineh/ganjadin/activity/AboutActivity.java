package ir.mbf5923.adineh.ganjadin.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {
private TextView tvtext;
    private Activity sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        sp = this;
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v) -> {
            sp.finish();
        });
        tvtext=findViewById(R.id.tvtext);
        NetworkManager.getInstance().loadabout(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                try {
                    tvtext.setText(result.getString("text"));
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
        });
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
