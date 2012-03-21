package com.hstefan.threadingexample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import com.hstefan.threadingexample.DotProductThreadingSingleton.OnDotProductCalculationListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadingExampleActivity extends Activity implements VectorGenerationTask.OnTaskFinishedListener<Float[]>, 
	OnClickListener, OnDotProductCalculationListener {

	/** Called when the activity is first created. */
	private Float[] m_u, m_v;
	private List<AsyncTask<Integer, Void, Float>> m_Tasks;
	private DotProductThreadingSingleton m_dotInstance;
	private int m_numThreadsLRun;
	
	public static final int VEC_SIZE = 80000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.main);
        
    	Button bRun = (Button)findViewById(R.id.bRun);
    	bRun.setClickable(false);
    	bRun.setOnClickListener((android.view.View.OnClickListener) this);
    	Toast.makeText(this, "Generating vector", Toast.LENGTH_LONG).show();
        new VectorGenerationTask().setOnFinishListener(this).execute(
        	new VectorGenerationData(VEC_SIZE, bRun, this));
        m_dotInstance = DotProductThreadingSingleton.getInstance();
        m_dotInstance.setOnDotProductCalculationListener(this);
        m_Tasks = new ArrayList<AsyncTask<Integer,Void,Float>>();
        m_numThreadsLRun = 0;
        
        super.onCreate(savedInstanceState);
    }

	@Override
	public void onTaskFinish(Float[][] result) {
		Toast.makeText(this, "Vector generated", Toast.LENGTH_LONG).show();
		Button bRun = (Button)findViewById(R.id.bRun);
		bRun.setClickable(true);
		m_u = result[0];
		m_v = result[1];
		m_dotInstance.setVectorUVector(m_u);
		m_dotInstance.setVectorVVector(m_v);
	}

	@Override
	public void onClick(View v) {
		EditText eThreadNum = (EditText) findViewById(R.id.eNumThreads);
		Editable eEntry = eThreadNum.getText();
		if(eEntry.toString().isEmpty()) {
			eEntry.append("0");
		}
		
		int numThreads = Integer.parseInt(eEntry.toString());
		
		if(numThreads == 0) {
			numThreads = Runtime.getRuntime().availableProcessors();
		}
		
		calculateDotProduct(numThreads);
	}
	
	private void calculateDotProduct(int numThreads) {
		Log.d("MethodCall", "calculateDotProduct");
		int sliceSz = m_u.length/numThreads;
		int numSlices = m_u.length/sliceSz;
		Executor tExec = AsyncTask.THREAD_POOL_EXECUTOR;
		m_dotInstance.setNumThreads(numThreads);
		m_dotInstance.startTimer();
		m_Tasks.clear();
		for(int slice = 0; slice < numSlices; ++slice) {
			Log.d("Thread", "Spawning thread #" + slice);
			assert((slice*sliceSz >= 0) && (slice*sliceSz + sliceSz <= m_u.length));
			m_Tasks.add(new DotProductAsyncTask().executeOnExecutor(tExec, 
					slice*sliceSz, (slice*sliceSz) + sliceSz));
		}
		m_numThreadsLRun = numThreads;
	}

	@Override
	public void onDotProductCalculation(float res) {
		Log.d("MethodCall", "onDotProductCalculation");
		TextView tView = (TextView) findViewById(R.id.tvResult);
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("Program took ");
		sBuilder.append(m_dotInstance.getElapsedTime());
		sBuilder.append("ms to calculate dot product with ");
		sBuilder.append(m_numThreadsLRun);
		sBuilder.append(" thread(s) running.");
		tView.setText(sBuilder);
	}
	
	
}