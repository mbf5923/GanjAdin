package ir.mbf5923.adineh.ganjadin.network;


import com.androidnetworking.error.ANError;

import org.json.JSONObject;

/**
 * Created by MBF5923 on 2/25/17.
 */

public interface NetworkListener {
    default void onResult(String result) {

    }

    default void onResult(JSONObject result) {

    }

    void onError(ANError error);

    default void onprogress(long bytesUploaded, long totalBytes) {

    }
}
