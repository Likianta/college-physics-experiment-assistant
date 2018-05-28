package com.likianta.cpea;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.likianta.cpea.databinding.InstrumentsActivityBinding;

/**
 * Created by Likianta_Dodoora on 2018/4/2 0002.
 */

public class InstrumentsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        InstrumentsActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.instruments_activity);
//        binding.setInstrument();
    
    }
    
}
