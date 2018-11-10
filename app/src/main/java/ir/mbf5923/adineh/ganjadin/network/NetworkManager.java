package ir.mbf5923.adineh.ganjadin.network;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import ir.mbf5923.adineh.ganjadin.tools.Config;
import ir.mbf5923.adineh.ganjadin.tools.SHPManager;


public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager instance = null;


    private NetworkManager(Context context) {


    }


    public static synchronized void getInstance(Context context) {
        if (null == instance)
            instance = new NetworkManager(context);

    }

    public static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

//    public static void requestscompelete(int mode) {
//        if(mode==1){
//            Config.numofreqcompelete++;
//            if (Config.numofreq == Config.numofreqcompelete) {
//                Config.numofreq = 0;
//                Config.numofreqcompelete = 0;
//                BaseActivity.loadingmanage(0,"");
//                Config.haserror=0;
//            }
//        }else {
//            Config.numofreq = 0;
//            Config.numofreqcompelete = 0;
//            Config.haserror=1;
//            BaseActivity.loadingmanage(1,"");
//        }
//
//    }
//
//    public static void requestscompelete(int mode,String msg) {
//        if(mode==1){
//            Config.numofreqcompelete++;
//            if (Config.numofreq == Config.numofreqcompelete) {
//                Log.e("okwith",Config.numofreq+"");
//                Config.numofreq = 0;
//                Config.numofreqcompelete = 0;
//                BaseActivity.loadingmanage(0,msg);
//                Config.haserror=0;
//            }
//        }else {
//            Config.numofreq = 0;
//            Config.numofreqcompelete = 0;
//            BaseActivity.loadingmanage(1,msg);
//            Config.haserror=1;
//        }
//
//    }


    public static String getVollyErrorType(ANError error) {
        String msg = "";
        switch (error.getErrorCode()) {
            case 0:
                msg = "دسترسی به اینترنت وجود ندارد";
                break;
        }
        return error.getErrorDetail() + "";
    }

    public void login(final NetworkListener listener, final String phone) {
        AndroidNetworking.post(Config.WebServiceUrl + "login")
                .addBodyParameter("phone", phone)
                .setTag("login")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });
    }


    public void verification(final NetworkListener listener, final String phone, final String code) {
        AndroidNetworking.post(Config.WebServiceUrl + "verification")
                .addBodyParameter("phone", phone)
                .addBodyParameter("code", code)
                .setTag("verification")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });
    }


    public void checklogin(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "checklogin")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("checklogin")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void saveprofile(final NetworkListener listener,  String phone,  String token,String name,  String family ,String nationalcode) {
        AndroidNetworking.post(Config.WebServiceUrl + "saveprofile")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .addBodyParameter("name", name)
                .addBodyParameter("family", family)
                .addBodyParameter("nationalcode", nationalcode)
                .setTag("saveprofile")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }

    public void loadprofile(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadprofile")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("loadprofile")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }



    public void loadcharge(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadcharge")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("loadcharge")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadchallengehistory(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadchallengehistory")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("loadchallengehistory")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void checkchallengedate(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "checkchallengedate")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("checkchallengedate")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadchallengeinfo(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadchallengeinfo")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("loadchallengeinfo")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }

    public void startchallenge(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "startchallenge")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("startchallenge")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadtask(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadtask")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("loadtask")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void checkinchallenge(final NetworkListener listener, final String phone, final String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "checkinchallenge")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .setTag("checkinchallenge")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void checkscaned(final NetworkListener listener, final String phone, final String token,String qrcode) {
        AndroidNetworking.post(Config.WebServiceUrl + "checkscaned")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .addBodyParameter("qrcode", qrcode)
                .setTag("checkscaned")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void saveuserphone(final NetworkListener listener, final String phone, final String token,String phonebrand,String phonemodel,String version,String simoperator,String uniqid) {
        AndroidNetworking.post(Config.WebServiceUrl + "saveuserphone")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .addBodyParameter("phonebrand", phonebrand)
                .addBodyParameter("phonemodel", phonemodel)
                .addBodyParameter("version", version)
                .addBodyParameter("simoperator", simoperator)
                .addBodyParameter("uniqid", uniqid)
                .setTag("saveuserphone")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadfaq(final NetworkListener listener,int offset) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadfaq")
                .setTag("loadfaq")
                .addBodyParameter("offset", Integer.toString(offset))
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }

    public void savechat(final NetworkListener listener,String phone,String token,String question) {
        AndroidNetworking.post(Config.WebServiceUrl + "savechat")
                .setTag("savechat")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)
                .addBodyParameter("question", question)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadchats(final NetworkListener listener,String phone,String token) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadchats")
                .setTag("loadchats")
                .addBodyParameter("phone", phone)
                .addBodyParameter("token", token)

                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


    public void loadabout(final NetworkListener listener) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadabout")
                .setTag("loadabout")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }

    public void loadnews(final NetworkListener listener,int offset) {
        AndroidNetworking.post(Config.WebServiceUrl + "loadnews")
                .setTag("loadnews")
                .addBodyParameter("offset", Integer.toString(offset))
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override

                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        long crntbt = SHPManager.getInstance().GetByteRec();
                        crntbt = bytesReceived + crntbt;
                        SHPManager.getInstance().SetByteRec(crntbt);
                        crntbt = SHPManager.getInstance().GetByteSend();
                        crntbt = bytesSent + crntbt;
                        SHPManager.getInstance().SetByteSend(crntbt);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        listener.onResult(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listener.onError(error);
                    }
                });

    }


}
