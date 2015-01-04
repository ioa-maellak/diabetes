package com.example.diabetes;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	String dt;
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Creating database and tables
		db=openOrCreateDatabase("diabetes", Context.MODE_PRIVATE, null);
		
		// TODO: remove thsi... used just for test purposes!
		db.execSQL("DROP TABLE IF EXISTS BloodGlucose;");
		db.execSQL("DROP TABLE IF EXISTS InsoulinDose;");
		db.execSQL("DROP TABLE IF EXISTS InsoulinTypes;");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS InsoulinTypes (insoulinID integer, name varchar(50), ActingStartMinutes integer, ActingPeakMinutes integer, durationMinutes integer, PRIMARY KEY (insoulinID));");
		db.execSQL("CREATE TABLE IF NOT EXISTS InsoulinDose (givenAt TEXT DEFAULT CURRENT_DATETIME, insoulinType integer, dosage double precision, FOREIGN KEY (insoulinType) REFERENCES InsoulinTypes(insoulinID), PRIMARY KEY (givenAt, insoulinType) );");
		db.execSQL("CREATE TABLE IF NOT EXISTS BloodGlucose (measuredAt TEXT DEFAULT CURRENT_DATETIME, glucoseValue smallint, PRIMARY KEY (measuredAt));");
		
		Button buttonBloodMeasurements = (Button) findViewById(R.id.buttonBloodMeasurements);
		buttonBloodMeasurements.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: have a look over here: http://examples.javacodegeeks.com/android/core/ui/alertdialog/android-prompt-user-input-dialog-example/ and here http://www.mkyong.com/android/android-prompt-user-input-dialog-example/ and remove this... used just for test purposes!
//				db.execSQL("INSERT INTO InsoulinTypes VALUES (1,'Novo Novorapid', 15, 67, 240);");	// Actually, keep this, it has real data.
//				db.execSQL("INSERT INTO InsoulinDose (insoulinType, dosage) VALUES (1, 14);");
//				db.execSQL("INSERT INTO BloodGlucose VALUES ('2015-01-02 00:00:00', 123);");
				dt="";
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				// get blood_glucose_form.xml view
				LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
				// set blood_glucose_form.xml to be the layout file of the alertdialog builder
				final View promptView = layoutInflater.inflate(R.layout.blood_glucose_form, null);
				((EditText) promptView.findViewById(R.id.datetime)).setText(dateFormat.format(date));
				alertDialogBuilder.setView(promptView);
				alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dt = ((EditText) promptView.findViewById(R.id.datetime)).getText().toString();
						flag=true;
						dialog.dismiss();
					}
				});
				alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						flag=true;
						dialog.cancel();
					}
				});
				alertDialogBuilder.setCancelable(false);	// TODO: find what is wrong with modal dialogs!!!
				AlertDialog alertD = alertDialogBuilder.create();	// create an alert dialog
				alertD.show();
				showMessage("Worked", dt);
			}
		});
		
		Button buttonNutritionInformation = (Button) findViewById(R.id.buttonNutritionInformation);
		buttonNutritionInformation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMessage("Success", "Nutrition info clicked!");
			}
		});
		
		Button buttonWorkoutInformation = (Button) findViewById(R.id.buttonWorkoutInformation);
		buttonWorkoutInformation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMessage("Success", "Workout info clicked!");
			}
		});
		
		Button buttonClose = (Button) findViewById(R.id.buttonClose);
		buttonClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					db.close();
					finish();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showMessage(String title,String message)
	{
		Builder builder=new Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}
	
	protected void onUpdate(Bundle savedInstanceState) {
		// TODO: Add code for updating database. 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
