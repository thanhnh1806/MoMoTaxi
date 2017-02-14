package bhtech.com.cabbytaxi.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.List;

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.ContantValuesObject;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.NetworkObject;
import bhtech.com.cabbytaxi.object.SharedPreference;
import bhtech.com.cabbytaxi.object.StringObject;
import bhtech.com.cabbytaxi.object.TaxiRequestObj;
import bhtech.com.cabbytaxi.object.UserObj;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by thanh_nguyen02 on 16/12/2015.
 */
public class LoginModel implements LoginInterface.Datasource {
    private String username, password;
    private Context context;
    private boolean isLoginWithTwitter = false;
    private boolean buttonLoginEnable = true;
    public List<String> permissions = new ArrayList<String>();
    private boolean isUserAcceptAllPermission = true;

    public LoginModel(Context context) {
        this.context = context;
    }

    public boolean isUserAcceptAllPermission() {
        return isUserAcceptAllPermission;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setRememberMe(Context context, boolean isChecked) {
        SharedPreference.set(context, ContantValuesObject.REMEMBER_ME, String.valueOf(isChecked));
    }

    public boolean isRememberMe(Context context) {
        return Boolean.parseBoolean(SharedPreference.get(context, ContantValuesObject.REMEMBER_ME, String.valueOf(false)));
    }

    public boolean isLoginWithTwitter() {
        return isLoginWithTwitter;
    }

    public void setLoginWithTwitter(boolean loginWithTwitter) {
        isLoginWithTwitter = loginWithTwitter;
    }

    @Override
    public void setFacebookLoginResult(LoginResult loginResult) {
        UserObj.getInstance().setFbUserId(loginResult.getAccessToken().getUserId());
        UserObj.getInstance().setFbAccessToken(loginResult.getAccessToken().getToken());
        Log.w("AccessToken", loginResult.getAccessToken().getToken());
        UserObj.getInstance().setFbExpirationDate(String.valueOf(loginResult.getAccessToken().getExpires()));
        UserObj.getInstance().setFbRefreshDate(String.valueOf(loginResult.getAccessToken().getLastRefresh()));
    }

    public int getCurrentRequestId() {
        try {
            return Integer.parseInt(SharedPreference.get(context, ContantValuesObject.CURRENT_REQUEST_ID,
                    ContantValuesObject.REQUEST_ID_NULL));
        } catch (Exception e) {
            return Integer.parseInt(ContantValuesObject.REQUEST_ID_NULL);
        }
    }

    public void setButtonLoginEnable(boolean b) {
        buttonLoginEnable = b;
    }

    public boolean isButtonLoginEnable() {
        return buttonLoginEnable;
    }

    public void isUserAcceptAllPermission(boolean b) {
        isUserAcceptAllPermission = b;
    }

    public interface OnLoginGooglePlus {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginGooglePlus(Context context, final OnLoginGooglePlus onFinish) {
        isLoginWithTwitter = false;
        UserObj.getInstance().loginGoogle(context, new UserObj.OnLoginGoogle() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(Error error) {
                onFinish.Failure(error);
            }
        });
    }

    public interface OnLoginFacebook {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginFacebook(Context context, final OnLoginFacebook OnFinish) {
        isLoginWithTwitter = false;
        UserObj.getInstance().loginFacebook(context, new UserObj.OnLoginFacebook() {
            @Override
            public void Success() {
                OnFinish.Success();
            }

            @Override
            public void Failure(Error error) {
                OnFinish.Failure(error);
            }
        });

    }

    public interface OnGetCurrentRequest {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getCurrentRequest(final Context context, final OnGetCurrentRequest onFinish) {
        TaxiRequestObj.getInstance().getCurrentRequest(context, new TaxiRequestObj.onGetCurrentRequest() {
            @Override
            public void Success() {
                onFinish.Success();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                onFinish.Failure(error);
            }
        });
    }


    public interface OnFinishExecute {
        void onFinish(boolean result);
    }

    public void login(Context context, final OnFinishExecute onFinishExecute) {
        isLoginWithTwitter = false;
        UserObj.getInstance().setUsername(username);
        UserObj.getInstance().setPassword(password);
        UserObj.getInstance().login(context, new LoginInterface.OnFinishExecute() {
            @Override
            public void onFinish(boolean result) {
                if (result) {
                    onFinishExecute.onFinish(true);
                } else {
                    onFinishExecute.onFinish(false);
                }
            }
        });

    }

    public interface OnLoginTwitter {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void loginTwitter(Context context, OnLoginTwitter onFinish) {
        isLoginWithTwitter = true;
        new LoginTwitterTask(context, onFinish).execute();
    }

    public class LoginTwitterTask extends AsyncTask<String, Void, String> {
        private OnLoginTwitter onFinish;
        private Dialog dialog;
        private ProgressDialog progressBar;
        private WebView webView;
        private String oauthURL, verifier;
        private Context context;
        private Twitter twitter;
        private RequestToken requestToken;
        private String TWITTER_CONSUMER_KEY;
        private String TWITTER_CONSUMER_SECRET;

        public LoginTwitterTask(Context context, OnLoginTwitter onFinish) {
            this.context = context;
            this.onFinish = onFinish;
            twitter = new TwitterFactory().getInstance();
            TWITTER_CONSUMER_KEY = context.getString(R.string.TWITTER_CONSUMER_KEY);
            TWITTER_CONSUMER_SECRET = context.getString(R.string.TWITTER_CONSUMER_SECRET);
            twitter.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
        }

        @Override
        protected void onPreExecute() {
            progressBar = new ProgressDialog(context);
            progressBar.setMessage("Connecting...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setCancelable(false);
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                requestToken = twitter.getOAuthRequestToken();
                oauthURL = requestToken.getAuthorizationURL();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return oauthURL;
        }

        @Override
        protected void onPostExecute(String oauthUrl) {
            if (oauthUrl != null) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.login_twitter_webview);
                webView = (WebView) dialog.findViewById(R.id.webview);
                webView.canGoBack();
                webView.cancelLongPress();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(oauthUrl);
                webView.setWebViewClient(new WebViewClient() {
                    boolean authComplete = false;

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.dismiss();
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        Log.w("Url", url);
                        if (url.contains(context.getString(R.string.oauth_verifier))
                                && authComplete == false) {
                            authComplete = true;
                            Uri uri = Uri.parse(url);
                            verifier = uri.getQueryParameter(context.getString(R.string.oauth_verifier));
                            dialog.dismiss();
                            progressBar.show();
                            //revoke access token asynctask
                            new AccessTokenGetTask(context, onFinish, twitter, requestToken, verifier
                                    , progressBar).execute();
                        } else if (url.contains(context.getString(R.string.denied))) {
                            dialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.permission_is_denied),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                dialog.setCancelable(true);
            }
        }
    }

    public class AccessTokenGetTask extends AsyncTask<String, String, Void> {
        private Context context;
        private OnLoginTwitter onFinish;
        private AccessToken accessToken;
        private Twitter twitter;
        private RequestToken requestToken;
        private String verifier;
        private ProgressDialog progressBar;

        public AccessTokenGetTask(Context context, OnLoginTwitter onFinish, Twitter twitter,
                                  RequestToken requestToken, String verifier, ProgressDialog progressBar) {
            this.context = context;
            this.onFinish = onFinish;
            this.twitter = twitter;
            this.requestToken = requestToken;
            this.verifier = verifier;
            this.progressBar = progressBar;
        }

        @Override
        protected Void doInBackground(String... args) {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                twitter.showUser(accessToken.getUserId());
                UserObj.getInstance().setTwToken(accessToken.getToken());
                UserObj.getInstance().setTwTokenSecret(accessToken.getTokenSecret());
                UserObj.getInstance().loginTwitter(context, new UserObj.OnLoginTwitter() {
                    @Override
                    public void Success() {
                        progressBar.dismiss();
                        onFinish.Success();
                    }

                    @Override
                    public void Failure(Error error) {
                        onFinish.Failure(error);
                    }
                });
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public boolean hasAccountLogin(Context context) {
        String authToken = SharedPreference.get(context, ContantValuesObject.AUTHTOKEN, "");
        if (!StringObject.isNullOrEmpty(authToken)) {
            UserObj.getInstance().setUser_token(authToken);
            return true;
        } else {
            return false;
        }
    }

    public interface OnGetUserObj {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void getUserObj(Context context, final OnGetUserObj onFinish) {
        Error error = new Error();
        if (NetworkObject.isNetworkConnect(context)) {
            UserObj.getInstance().reLogin(context, new UserObj.OnReLogin() {
                @Override
                public void Success() {
                    onFinish.Success();
                }

                @Override
                public void Failure(Error error) {
                    onFinish.Failure(error);
                }
            });
        } else {
            error.setError(Error.NETWORK_DISCONNECT);
            onFinish.Failure(error);
        }
    }
}