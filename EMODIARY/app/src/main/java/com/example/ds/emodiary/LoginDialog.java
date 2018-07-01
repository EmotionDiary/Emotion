package com.example.ds.emodiary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;

public class LoginDialog extends Dialog {
    private Button loginBtn, cancelBtn;
    Button findPassword;

    public LoginDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);

        findPassword = (Button) findViewById(R.id.findPassword);
        final SpannableString content = new SpannableString("비밀번호 찾기");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        findPassword.setText(content);

        cancelBtn = (Button)findViewById(R.id.Cancel2);
        loginBtn = (Button)findViewById(R.id.Login);
        final Dialog dialog = this;

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),HomeActivity.class);
                getContext().startActivity(intent);
            }
        });
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //비밀번호찾기 다이얼로그 연결
                FindPasswordDialog fp = new FindPasswordDialog(getContext());
                fp.setTitle("비밀번호 찾기");
                fp.show();
            }
        });
    }
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

}
