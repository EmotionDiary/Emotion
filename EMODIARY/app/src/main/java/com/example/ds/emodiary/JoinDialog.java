package com.example.ds.emodiary;

import android.content.Context;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by jihu0 on 2018-06-26.
 */

public class JoinDialog extends Dialog {

    Button Join;

    public JoinDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_dialog);
        Join = (Button)findViewById(R.id.Join);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
