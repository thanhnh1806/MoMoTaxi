package bhtech.com.cabbytaxi.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;


/**
 * Created by thanh_nguyen02 on 11/12/2015.
 */
public class NetworkServices {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    private static final String DOMAIN = ContantValuesObject.DOMAIN;

    public interface onCallApi {
        void onFinish(boolean isSuccess, JSONObject jsonObject);
    }

    public interface onCallApiJson {
        void onFinish(JsonObject jsonObject);
    }

    public static void callAPI(Context context, String endPoint, String method,
                               final Map<String, List<String>> header,
                               final Map<String, List<String>> parameters,
                               final onCallApi callApi) {

        String url = ContantValuesObject.DOMAIN + endPoint;
        Log.e("url", url);
        FutureCallback<String> callback = new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    Log.w("JSONObject", result);
                    JSONObject jsonObject = new JSONObject(result);
                    callApi.onFinish(true, jsonObject);
                } catch (JSONException e1) {
                    callApi.onFinish(false, null);
                } catch (Exception e2) {
                    callApi.onFinish(false, null);
                }
            }
        };

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
    }

    public static void callAPI(Context context, String endPoint, String method,
                               final Map<String, List<String>> header,
                               JsonObject json, final onCallApiJson callApi) {
        String url = ContantValuesObject.DOMAIN + endPoint;
        Log.e("url", url);

        Ion.with(context).load(method, url).addHeaders(header).setJsonObjectBody(json).asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.w("JSONObject", String.valueOf(result));
                        callApi.onFinish(result);
                    }
                });
    }

    public static void callAPI(Context context, final String url, final String keyLog, final onCallApi callApi) {
        Ion.with(context).load(url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                try {
                    Log.w("Url_" + keyLog, url);
                    Log.w("Json_" + keyLog, result);
                    JSONObject jsonObject = new JSONObject(result);
                    callApi.onFinish(true, jsonObject);
                } catch (Exception e1) {
                    callApi.onFinish(false, null);
                }
            }
        });
    }

    public interface OnUploadImage {
        void onFinish(JSONObject jsonObject);
    }

    public static void uploadImage(Context context, final String endPoint, final String keyLog,
                                   Map<String, List<String>> header, File file, final OnUploadImage onFinish) {

        String url = ContantValuesObject.DOMAIN + endPoint;
        Log.e("url", url);
        Ion.with(context).load(POST, url).addHeaders(header)
                .setMultipartFile("file", "image/jpeg", file)
                .asString().withResponse()
                .setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            Log.w("JSONObject" + keyLog, result.getResult());
                            JSONObject jsonObject = new JSONObject(result.getResult());
                            onFinish.onFinish(jsonObject);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public interface MakeImageRequestFinish {
        void Success(Bitmap bitmap);

        void Failure(bhtech.com.cabbytaxi.object.Error error);
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
                        bhtech.com.cabbytaxi.object.Error error = new Error();
                        error.setError(Error.VOLLEY_IMAGE_REQUEST_ERROR, volleyError.getMessage());
                        finish.Failure(error);
                    }
                });
        VolleyServices.getInstance(context).addToRequestQueue(request);
    }

    public interface MakeJsonObjectRequestFinish {
        void Success(JSONObject jsonObject);

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public static void makeJsonObjectRequest(final Context context, int method, final String url,
                                             final String keyLog,
                                             final Map<String, String> headers,
                                             final Map<String, String> params,
                                             final MakeJsonObjectRequestFinish finish) {
        final Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            Log.w("JsonObjectRequest_" + keyLog, url);
            JSONObject paramsJsonObject = new JSONObject(params);
            Log.w("JsonObjectRequest_" + keyLog, paramsJsonObject.toString());
            JsonObjectRequest request = new JsonObjectRequest(method, url, paramsJsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.w("JsonObjectRequest_" + keyLog, response.toString());
                            finish.Success(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("VolleyError", volleyError.toString());
                    if (volleyError.toString().equals("com.android.volley.TimeoutError")) {
                        error.setError(Error.VOLLEY_TIMEOUT_ERROR, context.getString(R.string.request_timeout));
                        finish.Failure(error);
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }
            };

            VolleyServices.getInstance(context).addToRequestQueue(request);
        } else {
            error.setError(Error.NETWORK_DISCONNECT, context.getString(R.string.please_check_your_network));
            finish.Failure(error);
        }
    }
}
