package com.likianta.cpea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Likianta_Dodoora on 2018/4/2 0002.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    
    Button accessInstruments;
    Button accessFormulas;
    Button accessData;
    Button accessReport;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        initButtons();
    }
    
    private void initButtons() {
        accessInstruments = findViewById(R.id.main_button1);
        accessFormulas = findViewById(R.id.main_button2);
        accessData = findViewById(R.id.main_button3);
        accessReport = findViewById(R.id.main_button4);
        
        accessInstruments.setOnClickListener(this);
        accessFormulas.setOnClickListener(this);
        accessData.setOnClickListener(this);
        accessReport.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.main_button1:
                intent = new Intent(this, InstrumentsActivity.class);
                break;
            case R.id.main_button2:
                intent = new Intent(this, FormulasActivity.class);
                break;
            case R.id.main_button3:
                intent = new Intent(this, DataActivity.class);
                break;
            case R.id.main_button4:
                intent = new Intent(this, ReportActivity.class);
                break;
            default:
                
                break;
        }
        startActivity(intent);
    }
    
    @Override
    public void onDestroy() {
        // TODO: save experiment data and report
        super.onDestroy();
    }
}
