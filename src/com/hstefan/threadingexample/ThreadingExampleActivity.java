package com.hstefan.threadingexample;

import java.util.Arrays;
import java.util.concurrent.Executor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThreadingExampleActivity extends Activity implements OnTaskFinishedListener<Float[]>, OnClickListener {

	/** Called when the activity is first created. */
	private Float[] m_u, m_v;
	private AsyncTask<Float[], Void, Float>[] m_Tasks;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.main);
        
    	Button bRun = (Button)findViewById(R.id.bRun);
    	bRun.setOnClickListener((android.view.View.OnClickListener) this);
        new VectorGenerationTask().setOnFinishListener(this).execute(
        	new VectorGenerationData(300000, bRun, this));
        
        super.onCreate(savedInstanceState);
    }

	@Override
	public void onTaskFinish(Float[][] result) {
		Runtime rTime  = Runtime.getRuntime();
		m_u = result[0];
		m_v = result[1];
		
		int sliceSz = m_u.length/rTime.availableProcessors();
		int numSlices = m_u.length/sliceSz;
		
		if(m_Tasks == null) {
			m_Tasks = new DotProductAsyncTask[numSlices];
		}
	}

	@Override
	public void onClick(View v) {
		Runtime rTime  = Runtime.getRuntime();
		calculateDotProduct(rTime.availableProcessors());
	}
	
	private void calculateDotProduct(int numThreads) {
		int sliceSz = m_u.length/numThreads;
		int numSlices = m_u.length/sliceSz;
		Executor tExec = AsyncTask.THREAD_POOL_EXECUTOR;
		
		for(int slice = 0; slice < numSlices; slice++) {
			Log.d("Thread", "Spawning thread #" + slice);
			m_Tasks[slice] = new DotProductAsyncTask().executeOnExecutor(tExec, 
					Arrays.copyOfRange(m_u, slice*sliceSz, slice*sliceSz + sliceSz),
					Arrays.copyOfRange(m_v, slice*sliceSz, slice*sliceSz + sliceSz));
		}
	}
}