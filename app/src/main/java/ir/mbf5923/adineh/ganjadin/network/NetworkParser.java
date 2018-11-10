package ir.mbf5923.adineh.ganjadin.network;

import android.content.Context;


/**
 * Created by MBF5923 on 3/1/17.
 */

public class NetworkParser {
    private static NetworkParser instance = null;

    private NetworkParser(Context context) {
    }

    public static synchronized NetworkParser getInstance(Context context) {
        if (null == instance)
            instance = new NetworkParser(context);
        return instance;
    }

    public static synchronized NetworkParser getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkParser.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }


}
