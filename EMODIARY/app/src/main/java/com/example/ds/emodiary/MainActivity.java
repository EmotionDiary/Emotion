package com.example.ds.emodiary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {
        Button btn;
    private Context mContext;
    final String TAG = "KeyHash";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btn = (Button) findViewById(R.id.loginbutton);
            SpannableString content = new SpannableString("기존 계정으로 로그인");
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            btn.setText(content);

            ActionBar bar = getSupportActionBar();
            bar.hide();

            /*getHashKey(mContext);*/
        }

    /*public static String getHashKey(Context context){ //해시키 구하기 실패 ㅠㅠ
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature:info.signatures){
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        }catch (Exception e){
            Log.e("name not found",e.toString());
        }
        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }*/

        //카카오버튼
        public void onKakaoButtonClicked(View view) {

        }

        //회원가입버튼
        public void onJoinButtonClicked(View view) {
            JoinDialog join = new JoinDialog(this);
            join.setTitle("회원가입");
            join.show();
        }

        //기존계정으로 로그인 버튼
        public void onLoginButtonClicked(View view) {
            LoginDialog login = new LoginDialog(this);
            login.setTitle("로그인");
            login.show();
        }
}
