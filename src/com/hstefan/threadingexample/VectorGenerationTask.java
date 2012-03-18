package com.hstefan.threadingexample;

import java.util.Random;

import android.os.AsyncTask;
import android.widget.Button;

public class VectorGenerationTask extends AsyncTask<VectorGenerationData, Void, Float[]> {
	private int m_numElements;
	private Button m_bRun;
	private OnTaskFinishedListener<Float[]> m_finListener;
	
	@Override
	protected Float[] doInBackground(VectorGenerationData... data) {
		m_numElements = data[0].getVectorLength();
		m_bRun = data[0].getButton();
		m_bRun.setClickable(false);
		m_bRun.setPressed(false);
		
		Float[] ret = new Float[m_numElements*2]; //lol at my lazyness
		Random r = new Random();
		for(int i = 0; i < m_numElements*2; ++i) {
			ret[i] = r.nextFloat()*r.nextInt(30);
		}
		
		return ret;
	}
	
	@Override
	protected void onPostExecute(Float[] result) {
		m_bRun.setClickable(true);
		m_bRun.setPressed(true);
		m_finListener.onTaskFinish(result);
	}	
}
