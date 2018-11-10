package ir.mbf5923.adineh.ganjadin.tools;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import ir.mbf5923.adineh.ganjadin.R;
import ir.mbf5923.adineh.ganjadin.network.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by MBF5923 on 05/11/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        NetworkManager.getInstance(getApplicationContext());
        SHPManager.getInstance().setSharedPreferences(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lale.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
