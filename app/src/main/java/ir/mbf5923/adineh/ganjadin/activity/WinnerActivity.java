package ir.mbf5923.adineh.ganjadin.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import ir.mbf5923.adineh.ganjadin.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WinnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
//        VusikView vusik = findViewById(R.id.vusik);
//        int[]  myImageList = new int[]{R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5};
//        vusik
//
//                .setImages(myImageList)
//                .start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
