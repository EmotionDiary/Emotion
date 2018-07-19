package com.example.ds.emodiary;

import android.content.Context;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by jihu0 on 2018-06-26.
 */

public class JoinDialog extends Dialog {
    Button Join;
    EditText name, id, password, passwordCheck, phone;

    public JoinDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_dialog);

        name = (EditText)findViewById(R.id.editName);
        id = (EditText)findViewById(R.id.editId);
        password = (EditText)findViewById(R.id.editPassword);
        passwordCheck = (EditText)findViewById(R.id.editPasswordCheck);
        phone = (EditText)findViewById(R.id.editPhone);

        Join = (Button)findViewById(R.id.Join);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //유효성 검사
                if(!Patterns.EMAIL_ADDRESS.matcher(id.getText().toString()).matches()){
                    Toast.makeText(getContext(), "아이디가 이메일 형식이 아닙니다",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
