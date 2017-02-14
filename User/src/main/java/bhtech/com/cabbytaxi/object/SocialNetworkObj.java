package bhtech.com.cabbytaxi.object;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thanh_nguyen on 29/02/2016.
 */
public class SocialNetworkObj {
    public static final int GOOGLE_PLUS_SIGN_IN = 9001;
    private static GoogleApiClient mGoogleApiClient;
    private static GoogleSignInOptions gso;

    public static Intent getGoogleSignInIntent(Context context, FragmentActivity fragment) {
        Intent signInIntent = Auth.GoogleSignInApi
                .getSignInIntent(SocialNetworkObj.getGoogleApiClient(context, fragment));
        return signInIntent;
    }

    private static GoogleApiClient getGoogleApiClient(Context context, FragmentActivity fragment) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragment, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.e("GoogleApiClient", "ConnectionFailed");
                        }
                    }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        }

        return mGoogleApiClient;
    }

    public static void printKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("bhtech.com.cabbytaxi",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("SHA: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
