package edu.usf.cse.salvatierra.sensorreading;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView accelerationView;
    private SensorManager sensorManager;

    private GraphicalView mChart;

    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find View
       // accelerationView = (TextView) findViewById(R.id.accelerationView);
        // Real sensor Manager

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (mChart == null){
            initChart();
            mChart = ChartFactory.getCubeLineChartView(this, mDataset, mRenderer,0.3f);
            layout.addView(mChart);
        }
        else {
            mChart.repaint();
        }

        // Register sensors listeners
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

    }

    private void initChart() {
        mCurrentSeries = new XYSeries("Sample Data");
        mDataset.addSeries(mCurrentSeries); /* Makes Current Series (data to plot) part of the Dataset */
        mCurrentRenderer = new XYSeriesRenderer(); /* XY type of chart */
        mRenderer.addSeriesRenderer(mCurrentRenderer);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float xA = event.values[0];
        float yA = event.values[1];
        ///float zA = event.values[2];

        //String accelerationOut = String.format("X: %.2f Y: %.2f Z: %.2f", xA, yA, zA);
        //accelerationView.setText(accelerationOut);
        mCurrentSeries.add(xA, yA);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




}
