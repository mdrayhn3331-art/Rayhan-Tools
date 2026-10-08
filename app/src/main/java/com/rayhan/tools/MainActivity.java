package com.rayhan.tools;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import java.util.Locale;

public class MainActivity extends Activity {
    LinearLayout box;
    TextView result;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        findViewById(R.id.calculator).setOnClickListener(v -> calculator());
        findViewById(R.id.converter).setOnClickListener(v -> converter());
        findViewById(R.id.timer).setOnClickListener(v -> timer());
        findViewById(R.id.device).setOnClickListener(v -> deviceInfo());
        findViewById(R.id.qr).setOnClickListener(v -> soon("QR Scanner / Generator"));
        findViewById(R.id.pdf).setOnClickListener(v -> soon("Image → PDF"));
        findViewById(R.id.compress).setOnClickListener(v -> soon("Image Compressor"));
    }

    void soon(String name) {
        new AlertDialog.Builder(this).setTitle(name)
            .setMessage("এই ফিচারটি পরের build-এ যোগ করা হবে।")
            .setPositiveButton("OK", null).show();
    }

    void calculator() {
        LinearLayout l = form();
        EditText a = input("প্রথম সংখ্যা");
        EditText op = input("অপারেটর: +  -  ×  ÷");
        EditText c = input("দ্বিতীয় সংখ্যা");
        l.addView(a); l.addView(op); l.addView(c);
        result = new TextView(this); result.setTextSize(20); result.setPadding(0,18,0,0); l.addView(result);
        new AlertDialog.Builder(this).setTitle("Calculator").setView(l)
            .setPositiveButton("Calculate", (d,w) -> {
                try {
                    double x=Double.parseDouble(a.getText().toString().trim());
                    double y=Double.parseDouble(c.getText().toString().trim());
                    String o=op.getText().toString().trim();
                    double z=o.equals("-")?x-y:o.equals("×")||o.equals("*")?x*y:o.equals("÷")||o.equals("/")?x/y:x+y;
                    Toast.makeText(this,"Result: "+z,Toast.LENGTH_LONG).show();
                } catch(Exception e){ Toast.makeText(this,"সঠিক সংখ্যা দিন",Toast.LENGTH_SHORT).show(); }
            }).setNegativeButton("Cancel",null).show();
    }

    void converter() {
        LinearLayout l=form();
        EditText v=input("Value");
        Spinner s=new Spinner(this);
        String[] units={"Kilometer → Mile","Mile → Kilometer","Kilogram → Pound","Pound → Kilogram","Celsius → Fahrenheit","Fahrenheit → Celsius"};
        s.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,units));
        l.addView(v); l.addView(s);
        new AlertDialog.Builder(this).setTitle("Unit Converter").setView(l)
            .setPositiveButton("Convert",(d,w)->{
                try {
                    double x=Double.parseDouble(v.getText().toString().trim()), z=x;
                    int p=s.getSelectedItemPosition();
                    if(p==0)z=x*0.621371; if(p==1)z=x*1.609344;
                    if(p==2)z=x*2.2046226218; if(p==3)z=x*0.45359237;
                    if(p==4)z=x*9/5+32; if(p==5)z=(x-32)*5/9;
                    Toast.makeText(this,String.format(Locale.US,"Result: %.4f",z),Toast.LENGTH_LONG).show();
                }catch(Exception e){Toast.makeText(this,"সঠিক value দিন",Toast.LENGTH_SHORT).show();}
            }).setNegativeButton("Cancel",null).show();
    }

    void timer() {
        LinearLayout l=form();
        EditText sec=input("Seconds");
        l.addView(sec);
        new AlertDialog.Builder(this).setTitle("Countdown Timer").setView(l)
            .setPositiveButton("Start",(d,w)->{
                try {
                    long ms=Long.parseLong(sec.getText().toString().trim())*1000L;
                    new CountDownTimer(ms,1000){ public void onTick(long x){ } public void onFinish(){
                        Toast.makeText(MainActivity.this,"⏰ Time is up!",Toast.LENGTH_LONG).show();
                    }}.start();
                    Toast.makeText(this,"Timer started",Toast.LENGTH_SHORT).show();
                }catch(Exception e){Toast.makeText(this,"সঠিক seconds দিন",Toast.LENGTH_SHORT).show();}
            }).setNegativeButton("Cancel",null).show();
    }

    void deviceInfo() {
        String text="Model: "+Build.MANUFACTURER+" "+Build.MODEL+
            "\nAndroid: "+Build.VERSION.RELEASE+
            "\nSDK: "+Build.VERSION.SDK_INT+
            "\nDevice: "+Build.DEVICE+
            "\nBrand: "+Build.BRAND;
        new AlertDialog.Builder(this).setTitle("Device Info").setMessage(text)
            .setPositiveButton("OK",null).show();
    }

    LinearLayout form() {
        LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(30,10,30,10); return l;
    }
    EditText input(String hint) {
        EditText e=new EditText(this); e.setHint(hint); e.setSingleLine(true); return e;
    }
}
