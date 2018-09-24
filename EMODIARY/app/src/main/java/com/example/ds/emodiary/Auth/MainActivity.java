package com.example.ds.emodiary.Auth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ds.emodiary.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    Button btn;
    private Context mContext;
    final String TAG = "KeyHash";
    private SessionCallback sessionCallback;
    private FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        ActionBar bar = getSupportActionBar();
        bar.hide();


        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder

                (GoogleSignInOptions.DEFAULT_SIGN_IN)

                .requestIdToken(getString(R.string.default_web_client_id))

                .requestEmail()

                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .enableAutoManage(this, this )

                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)

                .build();

        mAuth = FirebaseAuth.getInstance();

        Button login_btn_google= (Button) findViewById(R.id.google_login);

        login_btn_google.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Log.v("알림", "구글 LOGIN");

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

                startActivityForResult(signInIntent, RC_SIGN_IN);

            }

        });

    }

    public void onStart() {

        super.onStart();

        // 활동을 초기화할 때 사용자가 현재 로그인되어 있는지 확인합니다.

        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return ;
        }

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                // Google Sign In was successful, authenticate with Firebase

                Log.v("알림", "google sign 성공, FireBase Auth.");

                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);

            } else {

                // Google Sign In failed, update UI appropriately

                Log.v("알림", result.isSuccess() +" Google Sign In failed. Because : " + result.getStatus().toString());

                // ...

            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.v("알림", "ONCOMPLETE");

                        // If sign in fails, display a message to the user. If sign in succeeds

                        // the auth state listener will be notified and logic to handle the

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Log.v("알림", "!task.isSuccessful()");

                            Toast.makeText(MainActivity.this, "인증에 실패하였습니다.", Toast.LENGTH_SHORT).show();

                        }else {

                            Log.v("알림", "task.isSuccessful()");

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(MainActivity.this, "FireBase 아이디 생성이 완료 되었습니다", Toast.LENGTH_SHORT).show();

                        }

                    }

                });


    }


    public void request(){
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("error", "Session Closed Error is " + errorResult.toString());
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile result) {
                Toast.makeText(MainActivity.this, "사용자 이름은 " + result.getNickname(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            request();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d("error", "Session Fail Error is " + exception.getMessage().toString());
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.v("알림", "onConnectionFailed");

    }


    // 로그아웃

    public void signOut() {

        mGoogleApiClient.connect();

        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override

            public void onConnected(@Nullable Bundle bundle) {

                mAuth.signOut();

                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override

                        public void onResult(@NonNull Status status) {

                            if (status.isSuccess()) {

                                Log.v("알림", "로그아웃 성공");

                                setResult(1);

                            } else {

                                setResult(0);

                            }

                            finish();

                        }

                    });

                }

            }

            @Override

            public void onConnectionSuspended(int i) {

                Log.v("알림", "Google API Client Connection Suspended");

                setResult(-1);

                finish();

            }

        });

    }


    //카카오버튼
    public void onKakaoButtonClicked(View view) {

    }

}