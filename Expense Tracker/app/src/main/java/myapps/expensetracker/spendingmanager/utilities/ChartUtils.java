package myapps.expensetracker.spendingmanager.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.util.ArrayList;

import myapps.expensetracker.spendingmanager.R;

public class ChartUtils {


    public static void LineChartInitialization(Context ctx,LineChart lineChart, Typeface typeface){




        lineChart.setViewPortOffsets(0, 0, 0, 0);
//        lineChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);

        XAxis x = lineChart.getXAxis();
        x.setEnabled(false);

        YAxis y = lineChart.getAxisLeft();
        y.setTypeface(typeface);
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLUE);


        lineChart.getAxisRight().setEnabled(false);

        // add data


        // lower max, as cubic runs significantly slower than linear

        lineChart.getLegend().setEnabled(false);

        lineChart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        lineChart.invalidate();
    }

    public static void setLineChartData(ArrayList<String> xAxis, int count, float range, LineChart lineChart, Typeface typeface) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, 100));
        }

        final ArrayList<String> xLabel = new ArrayList<>();
        xLabel.add("9");
        xLabel.add("15");
        xLabel.add("21");
        xLabel.add("27");
        xLabel.add("33");

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });


//            XAxis bottomAxis = lineChart.getXAxis();
//            bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//            bottomAxis.setDrawGridLines(false);
//            bottomAxis.setValueFormatter(new IAxisValueFormatter() {
//                @Override
//                public String getFormattedValue(float value, AxisBase axis) {
//                    return xLabel.get((int)value);
//                }
//            });
            // create a data object with the data sets
//            LineData lineData = new LineData(xAxis, set1);
            LineData data = new LineData(set1);




            data.setValueTypeface(typeface);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart.setData(data);
        }
    }
}
