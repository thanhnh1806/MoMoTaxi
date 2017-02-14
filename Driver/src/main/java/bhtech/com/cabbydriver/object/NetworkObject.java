package bhtech.com.cabbydriver.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import bhtech.com.cabbydriver.R;
import bhtech.com.cabbydriver.services.VolleyServices;

/**
 * Created by thanh_nguyen02 on 23/12/2015.
 */
public class NetworkObject {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static boolean isNetworkConnect(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public interface onCallApi {
        void Success(JSONObject jsonObject);

        void Failure(ErrorObj error);
    }

    static ErrorObj error = new ErrorObj();

    public static void callAPI(Context context, String endPoint, String method, final String keyLog,
                               final Map<String, List<String>> header,
                               final Map<String, List<String>> parameters,
                               final onCallApi onFinish) {

        String url = ContantValuesObject.DOMAIN + endPoint;
        Log.e("url", url);

        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    Log.w("Responses_" + keyLog, result);
                    JSONObject jsonObject = new JSONObject(result);
                    onFinish.Success(jsonObject);
                } catch (Exception e1) {
                    if (e1.getMessage() != null) {
                        if (!e1.getMessage().equals("println needs a message")) {
                            error.errorMessage = e1.getMessage();
                            onFinish.Failure(error);
                        }
                    }
                }
            }
        };

        if (isNetworkConnect(context)) {
            if (header != null && parameters != null) {
                Log.w("Header", header.toString());
                Log.w("Params", parameters.toString());
                Ion.with(context).load(method, url).addHeaders(header)
                        .setBodyParameters(parameters).asString()
                        .setCallback(callback);
            } else if (parameters == null) {
                Log.w("Header", header.toString());
                Ion.with(context).load(method, url).addHeaders(header).asString()
                        .setCallback(callback);
            } else if (header == null) {
                Log.w("Params", parameters.toString());
                Ion.with(context).load(method, url).setBodyParameters(parameters).asString()
                        .setCallback(callback);
            }
        } else {
            error.setError(ErrorObj.NETWORK_DISCONNECT);
            error.errorMessage = context.getString(R.string.please_check_your_network);
            onFinish.Failure(error);
        }
    }

    public static void callAPI(Context context, final String url, final String keyLog, final onCallApi onFinish) {
        Ion.with(context).load(url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    Log.w("Url_" + keyLog, url);
                    Log.w("Json_" + keyLog, result);
                    JSONObject jsonObject = new JSONObject(result);
                    onFinish.Success(jsonObject);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    error.setError(ErrorObj.UNKNOWN_ERROR);
                    onFinish.Failure(error);
                }
            }
        });
    }

    public interface MakeImageRequestFinish {
        void Success(Bitmap bitmap);

        void Failure(ErrorObj error);
    }

    public static void imageRequest(Context context, String url, final MakeImageRequestFinish finish) {
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        finish.Success(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        ErrorObj error = new ErrorObj();
                        error.setError(ErrorObj.VOLLEY_IMAGE_REQUEST_ERROR, volleyError.getMessage());
                        finish.Failure(error);
                    }
                });
        VolleyServices.getInstance(context).addToRequestQueue(request);
    }
}
