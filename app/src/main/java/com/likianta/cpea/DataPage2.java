package com.likianta.cpea;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.likianta.cpea.utils.LeastSquares;
import com.likianta.cpea.utils.lkLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/4/12 0012.
 */
public class DataPage2 extends Fragment {
    final float[] valueX = {
            0.00f,
            0.10f,
            0.20f,
            0.30f,
            0.40f,
            0.50f,
            0.60f,
            0.70f,
            0.80f,
            0.85f,
            0.90f,
            0.95f,
            1.00f
    };
    List<Entry> chart1K1Entries = new ArrayList<>();
    List<Entry> chart1K2Entries = new ArrayList<>();
    List<Entry> chart1K3Entries = new ArrayList<>();
    List<Entry> chart2K1Entries = new ArrayList<>();
    List<Entry> chart2K2Entries = new ArrayList<>();
    List<Entry> chart2K3Entries = new ArrayList<>();
    LineDataSet chart1K1Set = new LineDataSet(chart1K1Entries, "");
    LineDataSet chart1K2Set = new LineDataSet(chart1K2Entries, "");
    LineDataSet chart1K3Set = new LineDataSet(chart1K3Entries, "");
    LineDataSet chart2K1Set = new LineDataSet(chart2K1Entries, "");
    LineDataSet chart2K2Set = new LineDataSet(chart2K2Entries, "");
    LineDataSet chart2K3Set = new LineDataSet(chart2K3Entries, "");
    float[][][] lastChartData = new float[2][3][13];
    private LineChart chart1, chart2;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.data_page2, container, false);
        
        // 初始化绑定组件（目前只绑了chart1和chart2）
        initBindings(view);
        
        // 初始化图表外观（仅仅是把外观确定下来，不涉及数据加载）
        initChart(chart1);
        initChart(chart2);
        
        return view;
    }
    
    public void refreshChartData(float[][][] originalChartData) {
        /* 工作流程
         * 1. 参数传入来自page1的表格数据（全部数据，共有13x3x2=78个测量值）
         * 2. 当数据传过来后，需要进行检查：检查方法为判断它是否和上一次传过来的数据（lastChartData）相同
         *     1. 如果相同，则说明没有测量值更新，则直接return，离开函数
         *     2. 如果不相同，则继续下一步
         * 3. 首先在chartEntries中装载新的表格数据（每个Entry代表一个x坐标和y坐标的点）
         * 4. chartEntries绑定好后，再转入各自对应的LineDataSet中
         * 5. 6个LineDataSet装好后，前三个set装载到一个sets里面，sets再转化为data，就可以最终应用到chart1了
         * 6. 后三个set同理装到另一个sets里面，再转化为data，最终应用到chart2
         * 注：5、6两步是在loadChartData中完成的 */
        
        if (Arrays.deepEquals(lastChartData, originalChartData)) {
            lkLog.d("lastChartData == originalChartData");
            return;
        } else {
            lastChartData = originalChartData.clone();
            lkLog.d("lastChartData != originalChartData");
        }
        
        Entry[][][] leastSquareEntries = new Entry[2][3][13];
        
        Entry[][][] chartEntries = new Entry[2][3][13];
        for (int i = 0; i < chartEntries.length; i++) {
            for (int j = 0; j < chartEntries[i].length; j++) {
                for (int k = 0; k < chartEntries[i][j].length; k++) {
                    chartEntries[i][j][k] = new Entry(valueX[k], originalChartData[i][j][k]);
                    leastSquareEntries[i][j][k] = new Entry(valueX[k], LeastSquares.estimate
                            (valueX, originalChartData[i][j], valueX[k]));
                }
            }
        }
        
        List<LineDataSet> valueSetList = new ArrayList<>(6);
        List<LineDataSet> estimateSets = new ArrayList<>(6);
        
        int[] labelColor = {
                this.getResources().getColor(R.color.colorRed),
                this.getResources().getColor(R.color.colorTextNormal),
                this.getResources().getColor(R.color.colorBlue),
        };
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                valueSetList.add(new LineDataSet(Arrays.asList(chartEntries[i][j]), null));
                estimateSets.add(new LineDataSet(Arrays.asList(leastSquareEntries[i][j]), null));
            }
        }
        
        List<ILineDataSet> finalChart1DataSets = new ArrayList<>(6);
        List<ILineDataSet> finalChart2DataSets = new ArrayList<>(6);
        
        for (int i = 0; i < 3; i++) {
            
        // -------------------- Set --------------------
        // 1. 载入entries
        // 2. 设置为贝塞尔曲线模式
        // 3. 设置每个chart的每个selector下的曲线的颜色
        // TODO 未来会增加三种模式可选：贝塞尔曲线（默认），折线，最小二乘法拟合曲线
            // Incorrect
            /*valueSetList.get(i).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            valueSetList.get(i + 3).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);*/
            // Correct
            /*valueSetList.get(i).setMode(LineDataSet.Mode.CUBIC_BEZIER);
            valueSetList.get(i + 3).setMode(LineDataSet.Mode.CUBIC_BEZIER);*/
    
            // 给estimateSets设置为虚线
            // REFERENCE: 2018-05-19
            // http://www.mamicode.com/info-detail-927983.html
            
            valueSetList.get(i).setColor(labelColor[i]);
            valueSetList.get(i + 3).setColor(labelColor[i]);
            estimateSets.get(i).setColor(labelColor[i]);
            estimateSets.get(i).enableDashedLine(10f, 10f, 0);
            estimateSets.get(i + 3).setColor(labelColor[i]);
            estimateSets.get(i + 3).enableDashedLine(10f, 10f, 0);
            
            finalChart1DataSets.add(valueSetList.get(i));
            finalChart1DataSets.add(estimateSets.get(i));
            finalChart2DataSets.add(valueSetList.get(i + 3));
            finalChart2DataSets.add(estimateSets.get(i + 3));
        }
        
        LineData data = new LineData(finalChart1DataSets);
        chart1.setData(data);
        chart1.invalidate();
        
        data = new LineData(finalChart2DataSets);
        chart2.setData(data);
        chart2.invalidate();
        
        /*chart1K1Set = new LineDataSet(Arrays.asList(chartEntries[0][0]), null);
        chart1K2Set = new LineDataSet(Arrays.asList(chartEntries[0][1]), null);
        chart1K3Set = new LineDataSet(Arrays.asList(chartEntries[0][2]), null);
        chart2K1Set = new LineDataSet(Arrays.asList(chartEntries[1][0]), null);
        chart2K2Set = new LineDataSet(Arrays.asList(chartEntries[1][1]), null);
        chart2K3Set = new LineDataSet(Arrays.asList(chartEntries[1][2]), null);
        
        chart1K1Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart1K2Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart1K3Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart2K1Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart2K2Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        chart2K3Set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        
        chart1K1Set.setColor(this.getResources().getColor(R.color.colorRed));
        chart1K2Set.setColor(this.getResources().getColor(R.color.colorTextNormal));
        chart1K3Set.setColor(this.getResources().getColor(R.color.colorBlue));
        chart2K1Set.setColor(this.getResources().getColor(R.color.colorRed));
        chart2K2Set.setColor(this.getResources().getColor(R.color.colorTextNormal));
        chart2K3Set.setColor(this.getResources().getColor(R.color.colorBlue));*/
        
        // Load data to the charts
        /*loadChartData(chart1, chart1K1Set, chart1K2Set, chart1K3Set);
        loadChartData(chart2, chart2K1Set, chart2K2Set, chart2K3Set);*/
    }
    
    
    
    /*private void loadChartData(LineChart chart, List<LineDataSet> sets1, List<LineDataSet> sets2,
                               LineDataSet
            set3) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < sets1.size(); i++) {
            dataSets.add(sets1.get(i));
        }
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        LineData data = new LineData(dataSets);
        
        chart.setData(data);
        chart.invalidate(); // refresh chart
    }*/
    
    private void initChart(LineChart chart) {
        // MPAndroidChart Wiki（译）～ Part 1 - Android - 掘金
        // https://juejin.im/entry/593c8ff6ac502e006ce9f473
        
        // Init Instances
        chart.setEnabled(true);
        AxisBase base = chart.getAxis(YAxis.AxisDependency.LEFT);
        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();
        chart.getAxisRight().setEnabled(false);
        
        // Chart Appearance
        chart.setDescription(null);
        chart.setNoDataText("当前表格信息不完整，请至少完成一列测量数据");
        chart.setDragEnabled(false);
        chart.setDrawBorders(false);
        chart.setScaleEnabled(false);
        chart.setTouchEnabled(false);
        
        // Base Axis
        base.setDrawAxisLine(true);
        base.setDrawLabels(true);
        base.setTextColor(this.getResources().getColor(R.color.colorTextNormal));
        base.setTextSize(13);
        
        // valueX Axis
//        xAxis.setDrawLabels(true);
//        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(10, false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        
        // Y Axis
//        leftAxis.setDrawLabels(true);
//        leftAxis.setDrawAxisLine(true);
        leftAxis.setLabelCount(10, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        
        // Unknown usages but worth of trying
//        chart.getDescription().setText("x");
//        xAxis.setDrawGridLines(true);
//        yAxis.setDrawGridLines(true);
        
        // Data Set Appearance
        chart1K1Set.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart1K2Set.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart1K3Set.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart2K1Set.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart2K2Set.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart2K3Set.setAxisDependency(YAxis.AxisDependency.LEFT);
    }
    
    private void initBindings(View view) {
        chart1 = view.findViewById(R.id.data_page2_chart1);
        chart2 = view.findViewById(R.id.data_page2_chart2);
    }
    
}
