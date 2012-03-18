package com.hstefan.threadingexample;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ThreadingExampleActivity extends Activity implements OnTaskFinishedListener<Float[]>{
	/** Called when the activity is first created. */
	private Float[] m_u, m_v;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.main);
        
    	Button bRun = (Button)findViewById(R.id.bRun);
        new VectorGenerationTask().execute(new VectorGenerationData(300000, bRun, this));
        
        super.onCreate(savedInstanceState);
    }

	@Override
	public void onTaskFinish(Float[][] result) {
		Runtime rTime  = Runtime.getRuntime();
		m_u = result[0];
		m_v = result[1];
		
		Executor tExec = Executors.newFixedThreadPool(rTime.availableProcessors(), null);
	}
}