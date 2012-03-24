package com.hstefan.threadingexample;

import com.hstefan.threadingexample.task.Task;

import android.os.AsyncTask;
import android.widget.Button;

public class VectorGenerationTask extends Task<Integer, Float[][]> {
	private int m_numElements;

	@Override
	public Float[][] onRun(Integer... params) {
		m_numElements = params[0];
		
		Float[][] ret = new Float[2][m_numElements];
		for(int i = 0; i < m_numElements; ++i) {
			ret[0][i] = (float) i;
		}
		
		ret[1] = ret[0];
		return ret;
	}
}
