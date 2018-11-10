package ir.mbf5923.adineh.ganjadin.tools;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by MBF5923 on 2/28/17.
 */

public class SHPManager {
    private static SHPManager instance = null;
    private static SharedPreferences SharedPref;
    private SharedPreferences.Editor editor;

    private SHPManager() {
    }

    public static SHPManager getInstance() {
        if (instance == null)
            instance = new SHPManager();
        return instance;
    }

    public void setSharedPreferences(Context context) {
        SharedPref = context.getSharedPreferences(Config.Appname, Context.MODE_PRIVATE);
        editor = SharedPref.edit();
    }

    public void SetShowIntro(int show){
        editor.putInt("showintro", show);
        editor.apply();
    }

    public int GetShowIntro(){
        return SharedPref.getInt("showintro", 0);

    }

    public void SetToken(String token){
        editor.putString("token",token);
        editor.apply();
    }

    public String GetToken(){
        return SharedPref.getString("token", "");
    }

    public void SetPhone(String phone){
        editor.putString("phone",phone);
        editor.apply();
    }

    public String GetPhone(){
        return SharedPref.getString("phone", "");
    }


    public void SetByteSend(long bytesend){
        editor.putLong("bytesend", bytesend);
        editor.apply();
    }

    public long GetByteSend(){
        return SharedPref.getLong("bytesend", 0);

    }

    public void SetByteRec(long byterec){
        editor.putLong("byterec", byterec);
        editor.apply();
    }

    public long GetByteRec(){
        return SharedPref.getLong("byterec", 0);

    }

    public void SetShowcase(int step){
        editor.putInt("step", step);
        editor.apply();
    }

    public int GetShowcase(){
        return SharedPref.getInt("step", 0);
    }



    public void cleardata(){
        editor.putString("phone","");
        editor.putString("token","");
        editor.apply();
    }


}
