package com.hstefan.threadingexample.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ResultDbOpenHelper extends SQLiteOpenHelper {
	
	public final static String DATABASE_NAME = "run_results.db";
	public final static int DATABASE_VERSION = 1;
	
	//Table
	public final static String RESULT_TABLE = "result";
	public final static String RESULT_DATE = "date";
	public final static String RESULT_NUMTHREADS = "num_threads";
	public final static String RESULT_PROCTIME = "proc_time";
	
	public ResultDbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE ");
		query.append(RESULT_TABLE);
		query.append(" (");
		query.append(BaseColumns._ID);
		query.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		query.append(RESULT_DATE);
		query.append(" INTEGER, ");
		query.append(RESULT_NUMTHREADS);
		query.append(" INTEGER, ");
		query.append(RESULT_PROCTIME);
		query.append(" INTEGER)");
		
		Log.d("ResultsData", "onCreate" + query.toString());
		db.execSQL(query.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
