package bhtech.com.cabbytaxi.SNSSetting;

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

import bhtech.com.cabbytaxi.R;
import bhtech.com.cabbytaxi.object.Error;
import bhtech.com.cabbytaxi.object.UserObj;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by thanh_nguyen on 04/02/2016.
 */
public class SNSSettingModel implements SNSSettingInterface.Datasource {

    @Override
    public void setFacebookLoginResult(LoginResult loginResult) {
        UserObj.getInstance().setFbUserId(loginResult.getAccessToken().getUserId());
        UserObj.getInstance().setFbAccessToken(loginResult.getAccessToken().getToken());
        UserObj.getInstance().setFbExpirationDate(String.valueOf(loginResult.getAccessToken().getExpires()));
        UserObj.getInstance().setFbRefreshDate(String.valueOf(loginResult.getAccessToken().getLastRefresh()));
    }

    public interface OnSettingFacebook {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingFacebook(Context context, final OnSettingFacebook OnFinish) {
        UserObj.getInstance().settingFacebook(context, new UserObj.OnSettingFacebook() {
            @Override
            public void Success() {
                OnFinish.Success();
            }

            @Override
            public void Failure(bhtech.com.cabbytaxi.object.Error error) {
                OnFinish.Failure(error);
            }
        });
    }

    public interface OnSettingGooglePlus {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingGooglePlus(Context context, final OnSettingGooglePlus onFinish) {
        UserObj.getInstance().settingGoogle(context, new UserObj.OnSettingGoogle() {
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

    public interface OnSettingTwitter {
        void Success();

        void Failure(bhtech.com.cabbytaxi.object.Error error);
    }

    public void settingTwitter(Context context, OnSettingTwitter onFinish) {
        new LoginTwitterTask(context, onFinish).execute();
    }

    public class LoginTwitterTask extends AsyncTask<String, Void, String> {
        private OnSettingTwitter onFinish;
        private Dialog dialog;
        private ProgressDialog progressBar;
        private WebView webView;
        private String oauthURL, verifier;
        private Context context;
        private Twitter twitter;
        private RequestToken requestToken;
        private String TWITTER_CONSUMER_KEY;
        private String TWITTER_CONSUMER_SECRET;

        public LoginTwitterTask(Context context, OnSettingTwitter onFinish) {
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
                dialog.setContentView(R.layout.login_twitter_webview);
                webView = (WebView) dialog.findViewById(R.id.webview);
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
        private OnSettingTwitter onFinish;
        private AccessToken accessToken;
        private Twitter twitter;
        private RequestToken requestToken;
        private String verifier;
        private ProgressDialog progressBar;

        public AccessTokenGetTask(Context context, OnSettingTwitter onFinish, Twitter twitter,
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
                UserObj.getInstance().settingTwitter(context, new UserObj.OnSettingTwitter() {
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
}
