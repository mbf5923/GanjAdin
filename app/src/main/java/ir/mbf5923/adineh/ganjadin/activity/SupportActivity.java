package ir.mbf5923.adineh.ganjadin.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidnetworking.error.ANError;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.models.supportusers_model;
import ir.mbf5923.adineh.ganjadin.network.NetworkListener;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;
import ir.mbf5923.adineh.ganjadin.utils.MyTimeFormatter;

public class SupportActivity extends AppCompatActivity {
    private ChatView mChatView;
    private supportusers_model me, ava;
    private Activity sp;
    private boolean isnotif = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        sp = this;
        Bundle args = getIntent().getExtras();
        if (args != null) {
            if(args.getString("notif_id")!=null){
                isnotif = true;
            }
        }
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


        mChatView = findViewById(R.id.chat_view);
        mChatView.setRightBubbleColor(Color.parseColor("#2ACB6E"));
        mChatView.setLeftBubbleColor(Color.parseColor("#FDDB70"));
        mChatView.setBackgroundColor(Color.TRANSPARENT);
        mChatView.setSendButtonColor(Color.parseColor("#64DD16"));
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("پیام خود را بنویسید...");
        mChatView.setInputTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mChatView.setAutoScroll(false);

        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);


        int myId = 0;
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        String myName = "من";
        me = new supportusers_model(myId, myName, myIcon);

        int avaId = 1;
        Bitmap avaIcon = BitmapFactory.decodeResource(getResources(), R.drawable.supportavatar);
        String avaName = "کارشناس";
        ava = new supportusers_model(avaId, avaName, avaIcon);


        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new message

                NetworkManager.getInstance().savechat(new NetworkListener() {
                    @Override
                    public void onResult(JSONObject result) {
                        try {
                            if (result.getInt("id") == 1) {

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
                }, SHPManager.getInstance().GetPhone(), SHPManager.getInstance().GetToken(), mChatView.getInputText());
                Calendar calendar = Calendar.getInstance();
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRight(true)
                        .setText(mChatView.getInputText())
                        .setSendTime(calendar)
                        .setSendTimeFormatter(new MyTimeFormatter())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
                //Reset edit text
                mChatView.setInputText("");

                //Receive message
//                 Message receivedMessage = new Message.Builder()
//                        .setUser(ava)
//                        .setRight(false)
//                        .setText("test")
//                        .build();
//                 mChatView.receive(receivedMessage);

                // This is a demo bot
                // Return within 3 seconds

            }

        });

        NetworkManager.getInstance().loadchats(new NetworkListener() {
            @Override
            public void onResult(JSONObject result) {
                try {

                    if (result.getInt("id") == 1) {

                        JSONArray array = result.getJSONArray("data");


                        for (int i = 0; i < array.length(); i++) {

                            Calendar calendar = Calendar.getInstance();

                            if (array.getJSONObject(i).getInt("mode") == 1) {
                                Message receivedMessage = new Message.Builder()
                                        .setUser(ava)
                                        .setSendTimeFormatter(new MyTimeFormatter())
                                        .setRight(false)
                                        .setSendTime(calendar)
                                        .setText(array.getJSONObject(i).getString("text"))
                                        .build();
                                mChatView.receive(receivedMessage);
                            } else {
                                Message message = new Message.Builder()
                                        .setUser(me)
                                        .setRight(true)
                                        .setSendTimeFormatter(new MyTimeFormatter())
                                        .setSendTime(calendar)
                                        .setText(array.getJSONObject(i).getString("text"))
                                        .hideIcon(true)
                                        .build();
                                //Set to chat view
                                mChatView.send(message);
                            }
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
}
