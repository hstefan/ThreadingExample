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
		
		for(int i = params[0]; i < params[1]; ++i) {
			res += u[i]*v[i];
		}
		
		return res;
	}
}
