package com.hstefan.threadingexample;

import android.os.AsyncTask;
import android.util.Log;

public class DotProductAsyncTask extends AsyncTask<Integer, Void, Float> {

	@Override
	protected Float doInBackground(Integer... index) {
		Float res = 0.f;
		DotProductThreadingSingleton l_inst = DotProductThreadingSingleton.getInstance();
		Float u[] = l_inst.getVectorU();
		Float v[] = l_inst.getVectorV();
		assert(index.length == 2);
		assert(u != null && v != null);
		
		for(int i = index[0]; i < index[1]; ++i) {
			res += u[i]*v[i];
		}
		
		return res;
	}
	
	@Override
	protected void onPostExecute(Float result) {
		Log.d("Thread", "Reached post execution state. Partial result: " + result);
		DotProductThreadingSingleton.getInstance().threadEnded(result);
	}
}
