package com.example.diabetes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PlotActivity extends Activity {

	private HashMap<Date, Integer> values;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot);
		
		// Create a couple arrays of y-values to plot:
		List<Number> series1Numbers = new ArrayList<Number>();
		List<Number> series2Numbers = new ArrayList<Number>();

		values = (HashMap<Date, Integer>) getIntent().getSerializableExtra("valuesHashMap");
		Iterator<Map.Entry<Date, Integer>> entries = values.entrySet().iterator();
		while (entries.hasNext()) {
		    Entry<Date, Integer> entry = entries.next();
		    series1Numbers.add(entry.getKey().getSeconds());	// TODO: I should get the full date. Also change the plotting for date.
		    series1Numbers.add(entry.getValue());
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
		setContentView(R.layout.activity_plot);
		// initialize our XYPlot reference:
		XYPlot plot = (XYPlot) findViewById(R.id.plot);
		// Turn the above arrays into XYSeries':
		XYSeries series1 = new SimpleXYSeries(series1Numbers, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Dates");
		// same as above
		XYSeries series2 = new SimpleXYSeries(series2Numbers, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Blood Glucose");
		// Create a formatter to use for drawing a series using LineAndPointRenderer and configure it from xml:
		LineAndPointFormatter series1Format = new LineAndPointFormatter();
		//series1Format.setPointLabelFormatter(new PointLabelFormatter());
		//series1Format.configure(getApplicationContext(), R.xml.line_point_formatter_with_plf1);
		// add a new series' to the xyplot:
		plot.addSeries(series1, series1Format);
		// same as above:
		LineAndPointFormatter series2Format = new LineAndPointFormatter();
		//series2Format.setPointLabelFormatter(new PointLabelFormatter());
		//series2Format.configure(getApplicationContext(), R.xml.line_point_formatter_with_plf2);
		plot.addSeries(series2, series2Format);
		// reduce the number of range labels
		plot.setTicksPerRangeLabel(3);
		plot.getGraphWidget().setDomainLabelOrientation(-45);
		Button buttonClosePlot = (Button) findViewById(R.id.buttonClosePlot);
		buttonClosePlot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
