package com.likianta.cpea;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.likianta.cpea.databinding.DataPage1Binding;

/**
 * Created by Likianta_Dodoora on 2018/4/3 0003.
 */

public class DataPage1 extends Fragment implements View.OnClickListener, View
        .OnFocusChangeListener {
    private TableData tableData = new TableData();
    private DataPage1Binding tableBinding;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.data_page1, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBinding(view);
        initLoading();
        addFocusChangeListener();
        addClickListeners();
    }
    
    private void initLoading() {
        loadTableDescription();
        activateSelector();
        // TODO: (SharedPreferences) load values which were saved last time
        
    }
    
    private void loadTableDescription() {
        tableBinding.tableTitle.setText(tableData.getTableTitle());
        tableBinding.tableLabel.setText(tableData.getTableLabel());
        tableBinding.valueLabel.setText(tableData.getValueLabel());
    }
    
    private void activateSelector() {
        for (int i = 0; i < tableData.getSelectors().length; i++) {
            if (i == tableData.getCurrentSelectorId()) {
                tableData.getSelectors()[i].setBackgroundResource(R.drawable
                        .data_page1_selector_on);
                tableData.getSelectors()[i].setTextColor(this.getResources().getColor(R.color
                        .colorTextFocused));
                tableData.getSelectors()[i].getPaint().setFakeBoldText(true);
            } else {
                tableData.getSelectors()[i].setBackgroundResource(R.drawable
                        .data_page1_selector_off);
                tableData.getSelectors()[i].setTextColor(this.getResources().getColor(R.color
                        .colorTextUnfocused));
                tableData.getSelectors()[i].getPaint().setFakeBoldText(false);
            }
        }
        
        loadTableData();
    }
    
    private void loadTableData() {
        for (int i = 0; i < tableData.getValues().length; i++) {
            tableData.getCells()[i].setText(tableData.getValues()[i]);
        }
    }
    
    private void addFocusChangeListener() {
        for (EditText editText : tableData.getCells()) {
            // There are 16 views
            editText.setOnFocusChangeListener(this);
        }
    }
    
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        // Scheme a: register a text watcher (easy but not recommend)
        // Scheme b: activate the view who was just lost focus (need some justifications)
        int index;
        index = searchViewInArray(tableData.getCells(), view);
        if (index != -1 && hasFocus) {
            final int finalIndex = index;
            ((EditText) view).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int
                        i2) {
                    
                }
                
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
                }
                
                @Override
                public void afterTextChanged(Editable editable) {
                    tableData.setValue(editable.toString(), finalIndex);
                }
            });
        }
    }
    
    private int searchViewInArray(View[] views, View view) {
        for (int i = 0; i < views.length; i++) {
            if (views[i] == view) {
                return i;
            }
        }
        return -1;
    }
    
    private void initBinding(View view) {
        tableBinding = DataBindingUtil.bind(view);
        assert tableBinding != null;
        
        for (int i = 1; i >= 0; i--) {
            tableData.setCurrentTableId(i);
            
            tableData.setCells(new EditText[]{
                    tableBinding.row0.valueY,
                    tableBinding.row1.valueY,
                    tableBinding.row2.valueY,
                    tableBinding.row3.valueY,
                    tableBinding.row4.valueY,
                    tableBinding.row5.valueY,
                    tableBinding.row6.valueY,
                    tableBinding.row7.valueY,
                    tableBinding.row8.valueY,
                    tableBinding.row9.valueY,
                    tableBinding.row10.valueY,
                    tableBinding.row11.valueY,
                    tableBinding.row12.valueY,
                    tableBinding.status1,
                    tableBinding.status2,
                    tableBinding.status3
            });
            
            tableData.setSelectors(new TextView[]{
                    tableBinding.selector1,
                    tableBinding.selector2,
                    tableBinding.selector3,
            });
        }
        
        // Extra: static text filled by valueX in each cell
        TextView[] textViews = {
                tableBinding.row0.valueX,
                tableBinding.row1.valueX,
                tableBinding.row2.valueX,
                tableBinding.row3.valueX,
                tableBinding.row4.valueX,
                tableBinding.row5.valueX,
                tableBinding.row6.valueX,
                tableBinding.row7.valueX,
                tableBinding.row8.valueX,
                tableBinding.row9.valueX,
                tableBinding.row10.valueX,
                tableBinding.row11.valueX,
                tableBinding.row12.valueX
        };
        for (int i = 0; i < tableData.getValueX().length; i++) {
            textViews[i].setText(tableData.getValueX()[i]);
        }
    }
    
    private void addClickListeners() {
        for (TextView view : tableData.getSelectors()) {
            view.setOnClickListener(this);
        }
        
        tableBinding.tableSwitch.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View selector) {
        int index = searchViewInArray(tableData.getSelectors(), selector);
        if (index != -1) {
            tableData.setCurrentSelectorId(index);
            activateSelector();
            return;
        }
        
        if (selector.getId() == R.id.table_switch) {
            switch (tableData.getCurrentTableId()) {
                case 0:
                    tableData.setCurrentTableId(1);
                    break;
                case 1:
                    tableData.setCurrentTableId(0);
                    break;
            }
            initLoading();
        }
    }
    
    public float[][][] extractChartData() {
        float[][][] data = new float[2][3][13];
        
        int tableId = tableData.getCurrentTableId();
        int selectorId = tableData.getCurrentSelectorId();
        for (int i = 0; i < data.length; i++) {
            tableData.setCurrentTableId(i);
            for (int j = 0; j < data[i].length; j++) {
                tableData.setCurrentSelectorId(j);
                for (int k = 0; k < data[i][j].length; k++) {
                    data[i][j][k] = tableData.getValueByFloat(k);
                }
            }
        }
        tableData.setCurrentTableId(tableId);
        tableData.setCurrentSelectorId(selectorId);
        
        return data;
    }
}
