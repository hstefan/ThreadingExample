package com.hstefan.threadingexample;

import java.util.Arrays;
import java.util.concurrent.Executor;

import com.hstefan.threadingexample.DotProductThreadingSingleton.OnDotProductCalculationListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadingExampleActivity extends Activity implements OnTaskFinishedListener<Float[]>, 
	OnClickListener, OnDotProductCalculationListener {

	/** Called when the activity is first created. */
	private Float[] m_u, m_v;
	private AsyncTask<Float[], Void, Float>[] m_Tasks;
	private DotProductThreadingSingleton m_dotInstance;
	private boolean firstTime = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.main);
        
    	Button bRun = (Button)findViewById(R.id.bRun);
    	bRun.setOnClickListener((android.view.View.OnClickListener) this);
        new VectorGenerationTask().setOnFinishListener(this).execute(
        	new VectorGenerationData(300000, bRun, this));
        m_dotInstance = DotProductThreadingSingleton.getInstance();
        m_dotInstance.setOnDotProductCalculationListener(this);
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
		if(firstTime) {
			calculateDotProduct(1);
		} else {
			Runtime rTime  = Runtime.getRuntime();
			calculateDotProduct(rTime.availableProcessors());
		}
	}
	
	private void calculateDotProduct(int numThreads) {
		int sliceSz = m_u.length/numThreads;
		int numSlices = m_u.length/sliceSz;
		Executor tExec = AsyncTask.THREAD_POOL_EXECUTOR;
		m_dotInstance.setNumThreads(numThreads);
		m_dotInstance.startTimer();
		for(int slice = 0; slice < numSlices; slice++) {
			Log.d("Thread", "Spawning thread #" + slice);
			m_Tasks[slice] = new DotProductAsyncTask().executeOnExecutor(tExec, 
					Arrays.copyOfRange(m_u, slice*sliceSz, slice*sliceSz + sliceSz),
					Arrays.copyOfRange(m_v, slice*sliceSz, slice*sliceSz + sliceSz));
		}
	}

	@Override
	public void onDotProductCalculation(float res) {
		TextView tView = (TextView) findViewById(R.id.tvResult);
		StringBuilder sBuilder = new StringBuilder();
		if(firstTime) {
			sBuilder.append("Program took ");
			sBuilder.append(m_dotInstance.getElapsedTime());
			sBuilder.append("ms to calculate dot product with 1 thread(s) running.");
		} else {
			sBuilder.append("Program took ");
			sBuilder.append(m_dotInstance.getElapsedTime());
			sBuilder.append("ms to calculate dot product with");
			sBuilder.append(Runtime.getRuntime().availableProcessors());
			sBuilder.append(" thread(s) running.");
		}
		tView.setText(sBuilder);
		firstTime = !firstTime;
	}
}