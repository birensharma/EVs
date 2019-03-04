package com.example.evehicle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Custom extends AppCompatActivity {
    private final int BULB_POWER=60;
    private final int FAN_POWER=30;
    private final int AC_POWER=1500;
    private final int OVEN_POWER=500;
    private final int FRIDGE_POWER=150;
    private EditText bulb,fan,ac,oven,fridge;
    private Button submit;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        bulb=findViewById(R.id.bulb);
        fan=findViewById(R.id.fans);
        ac=findViewById(R.id.ac);
        oven=findViewById(R.id.oven);
        fridge=findViewById(R.id.fridge);
        tv=findViewById(R.id.info);
        submit=findViewById(R.id.submit);

        final EditText[] ed=new EditText[]{bulb,fan,ac,oven,fridge};
        final int[] totalPower=new int[] {BULB_POWER,FAN_POWER,AC_POWER,OVEN_POWER,FRIDGE_POWER};
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum=0,val,i=0;
                for(EditText e : ed ){
                    if(e.getText().toString().equals(""))
                        val=0;
                    else
                        val=Integer.parseInt(e.getText().toString());
                    sum+=val*totalPower[i];
                    i++;
                }
                //Toast.makeText(Custom.this, "Total power : "+sum, Toast.LENGTH_LONG).show();
                tv.setText("Total power Required:\n"+sum+'W');
            }
        });

    }
}
