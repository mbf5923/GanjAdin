package ir.mbf5923.adineh.ganjadin.models;

import android.graphics.Bitmap;

import com.github.bassaer.chatmessageview.model.IChatUser;

public class supportusers_model implements IChatUser {
    private Integer id;
    private String name;
    private Bitmap icon;

    public supportusers_model(int id, String name, Bitmap icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String getId() {
        return this.id.toString();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Bitmap getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
