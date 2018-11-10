package ir.mbf5923.adineh.ganjadin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.adapters.news_adapter;
import ir.mbf5923.adineh.ganjadin.models.news_model;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NewsActivity extends AppCompatActivity {
    private Activity sp;
    private news_adapter adapter;
    private List<news_model> model = new ArrayList<>();
    private int offset = 0;
    private SmoothProgressBar pbProgress;
    private boolean isnotif = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Bundle args = getIntent().getExtras();
        if (args != null) {
            if(args.getString("notif_id")!=null){
                isnotif = true;
            }
        }
        sp = this;
        pbProgress = findViewById(R.id.pbProgress);
        RelativeLayout relback = findViewById(R.id.relback);
        relback.setOnClickListener((View v) -> {
            if (isnotif) {
                Intent intent = new Intent(sp, MainActivity.class);
                startActivity(intent);
                sp.finish();
            } else {
                sp.finish();
            }

        });
        RecyclerView rvfaq = findViewById(R.id.rvfaq);
        adapter = new news_adapter(model) {
            @Override
            public void load() {
                if (model.size() > 11) {
                    offset += 12;
                    loadfaq();
                }
            }
        };

        adapter.setHasStableIds(true);
        adapter.setOnItemClickListener(new news_adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                showanswerpopup(v, model.get(position).getText());

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(sp, LinearLayoutManager.VERTICAL, false);
        rvfaq.setLayoutManager(layoutManager);
        rvfaq.setAdapter(adapter);
        loadfaq();
    }

    private void loadfaq() {
        if (offset == 0) {
            model.clear();
        }

        NetworkManager.getInstance().loadnews(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                pbProgress.setVisibility(View.GONE);
                try {

                    if (result.getInt("id") == 1) {

                        JSONArray array = result.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            news_model movie = new news_model(array.getJSONObject(i).getString("titr"), array.getJSONObject(i).getString("text"), array.getJSONObject(i).getString("time"), array.getJSONObject(i).getString("id"));
                            model.add(movie);
                        }
                        adapter.notifyDataSetChanged();
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
        }, offset);
    }


    private void showanswerpopup(View anchorView, String answer) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_answer, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.animationonetest);


        TextView txtforgotbtn = popupView.findViewById(R.id.tvanswer);
        txtforgotbtn.setText(answer);


        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];
        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);
        //WindowManager winMan = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //int anchorScreen = winMan.getDefaultDisplay().getHeight();
        //int offset = anchorScreen / 2;
        //popupWindow.setHeight(offset);
        //Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.CENTER,
                Gravity.CENTER, 0);
        //popupWindow.showAsDropDown(anchorView,0, -125);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
