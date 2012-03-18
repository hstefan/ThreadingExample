package com.hstefan.threadingexample;

import java.util.Arrays;
import java.util.concurrent.Executor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

public class ThreadingExampleActivity extends Activity implements OnTaskFinishedListener<Float[]>{
	/** Called when the activity is first created. */
	private Float[] m_u, m_v;
	private AsyncTask<Float[], Void, Float>[] m_Tasks;
	
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
		
		Executor tExec = AsyncTask.THREAD_POOL_EXECUTOR;
		int sliceSz = m_u.length/rTime.availableProcessors();
		int numSlices = m_u.length/sliceSz;
		
		if(m_Tasks == null) {
			m_Tasks = new DotProductAsyncTask[numSlices];
		}
		
		for(int slices = 0; slices < numSlices; slices++) {
			m_Tasks[slices] = new DotProductAsyncTask().executeOnExecutor(tExec, 
					Arrays.copyOfRange(m_u, slices*sliceSz, slices*sliceSz + sliceSz),
					Arrays.copyOfRange(m_v, slices*sliceSz, slices*sliceSz + sliceSz));
		}
	}
}