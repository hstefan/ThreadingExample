package com.hstefan.threadingexample;

import com.hstefan.threadingexample.task.Task;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

public class DotProductAsyncTask extends Task<Integer, Float> {

	@Override
	public Float onRun(Integer... params) {
		Looper.myLooper().getThread().setPriority(Thread.MAX_PRIORITY);
		
		Float res = 0.f;
		DotProductThreadingSingleton l_inst = DotProductThreadingSingleton.getInstance();
		Float u[] = l_inst.getVectorU();
		Float v[] = l_inst.getVectorV();
		assert(params.length == 2);
		assert(u != null && v != null);

		for(int j = params[0]; j < params[1]; ++j) {
			res += u[j]*v[j];
		}
		return res;
	}
}
