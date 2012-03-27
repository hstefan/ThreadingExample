package com.hstefan.threadingexample;

import com.hstefan.threadingexample.task.Task;

import android.os.AsyncTask;
import android.util.Log;

public class DotProductAsyncTask extends Task<Integer, Float> {

	@Override
	public Float onRun(Integer... params) {
		Float res = 0.f;
		DotProductThreadingSingleton l_inst = DotProductThreadingSingleton.getInstance();
		Float u[] = l_inst.getVectorU();
		Float v[] = l_inst.getVectorV();
		assert(params.length == 2);
		assert(u != null && v != null);
		
		int diff = params[1] - params[0];
		for(int i  = 0; i < diff; ++i) {
			for(int j = params[0]; j < params[1]; ++j) {
				res += u[j]*v[j];
			}
		}
		return res;
	}
}
