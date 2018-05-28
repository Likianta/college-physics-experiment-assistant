package com.likianta.cpea;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Likianta_Dodoora on 2018/4/21 0021.
 */
public class TableData {
    final int TABLE1 = 0;
    final int TABLE2 = 1;
    final int SELECTOR1 = 0;
    final int SELECTOR2 = 1;
    final int SELECTOR3 = 2;
    private final String[] valueX = {
            "0.00",
            "0.10",
            "0.20",
            "0.30",
            "0.40",
            "0.50",
            "0.60",
            "0.70",
            "0.80",
            "0.85",
            "0.90",
            "0.95",
            "1.00"
    };
    private int currentTableId = TABLE1;
    private int[] currentSelectorId = {SELECTOR1, SELECTOR1};
    private String[][][] values = new String[2][3][16];
    private EditText[][] cells = new EditText[2][16];
    private TextView[][] selectors = new TextView[2][3];
    
    public void setValues(String[][] values) {
        this.values[currentTableId] = values;
    }
    
    public TextView[] getSelectors() {
        return selectors[currentTableId];
    }
    
    public void setSelectors(TextView[] selectors) {
        this.selectors[currentTableId] = selectors;
    }
    
    public int getCurrentTableId() {
        return currentTableId;
    }
    
    public void setCurrentTableId(int currentTableId) {
        this.currentTableId = currentTableId;
    }
    
    public String getTableTitle() {
        switch (this.currentTableId) {
            case TABLE1:
                return "表1 滑线电阻的限流特性研究测量数据";
            case TABLE2:
                return "表2 滑线电阻的分压特性研究测量数据";
            default:
                return null;
        }
    }
    
    public String getTableLabel() {
        switch (this.currentTableId) {
            case TABLE1:
                return "当前为表格 1";
            case TABLE2:
                return "当前为表格 2";
            default:
                return null;
        }
    }
    
    public String getValueLabel() {
        switch (this.currentTableId) {
            case TABLE1:
                return "通过负载电阻的\n电流 I";
            case TABLE2:
                return "负载电阻两端的\n电压 U";
            default:
                return null;
        }
    }
    
    public int getCurrentSelectorId() {
        return currentSelectorId[currentTableId];
    }
    
    public void setCurrentSelectorId(int currentSelectorId) {
        this.currentSelectorId[currentTableId] = currentSelectorId;
    }
    
    public EditText[] getCells() {
        return cells[currentTableId];
    }
    
    public void setCells(EditText[] cells) {
        this.cells[currentTableId] = cells;
    }
    
    public String[] getValueX() {
        return valueX;
    }
    
    public float getValueByFloat(int index) {
        String s = values[currentTableId][currentSelectorId[currentTableId]][index];
        if (s == null) {
            return 0.00f;
        } else {
            return Float.parseFloat(s);
        }
    }
    
    public void setValue(String value, int index) {
        this.values[currentTableId][currentSelectorId[currentTableId]][index] = value;
    }
    
    public String[] getValues() {
        return values[currentTableId][currentSelectorId[currentTableId]];
    }
    
    public void setValues(String[] values) {
        this.values[currentTableId][currentSelectorId[currentTableId]] = values;
    }
    
}
