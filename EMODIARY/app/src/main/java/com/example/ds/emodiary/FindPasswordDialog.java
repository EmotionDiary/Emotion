package com.example.ds.emodiary;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;

public class FindPasswordDialog extends Dialog {
    Button authrnticate, cancel;

    public FindPasswordDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpassword_dialog);
        authrnticate = (Button)findViewById(R.id.authenticate);
        cancel = (Button)findViewById(R.id.cancel);
        final Dialog dialog = this;

        authrnticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인증 후 바뀐다이얼로그 세팅
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }
}
