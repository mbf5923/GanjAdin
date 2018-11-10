package ir.mbf5923.adineh.ganjadin.models;

public class news_model {
    private String titr, text,time,id;

    public news_model(String titr, String text,String time,String id) {
        this.titr = titr;
        this.text = text;
        this.time = time;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getTitr() {
        return titr;
    }
}
