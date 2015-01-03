package com.example.diabetes;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.database.sqlite.*;

public class MainActivity extends Activity {

	SQLiteDatabase db;
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
		
		// TODO: remove this... used just for test purposes!
		db.execSQL("INSERT INTO InsoulinTypes VALUES (1,'Novo Novorapid', 15, 67, 240);");	// Actually, keep this, it has real data.
		db.execSQL("INSERT INTO InsoulinDose (insoulinType, dosage) VALUES (1, 14);");
		db.execSQL("INSERT INTO BloodGlucose VALUES ('2015-01-02 00:00:00', 123);");
		db.execSQL("INSERT INTO BloodGlucose VALUES (datetime('now'), 234);");
		showMessage("Success", "Records Inserted!");
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
