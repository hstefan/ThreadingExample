package com.hstefan.threadingexample;

import android.os.AsyncTask;

public class DotProductAsyncTask extends AsyncTask<Float[], Void, Float> {

	@Override
	protected Float doInBackground(Float[]... vecs) {
		Float res = 0.f;
		
		assert(vecs.length == 2 && vecs[0].length == vecs[1].length);
		
		for(int i = 0; i < vecs[0].length; ++i) {
			res += vecs[0][i]*vecs[1][i];
		}
		
		return res;
	}
	
	@Override
	protected void onPostExecute(Float result) {
		super.onPostExecute(result);
	}
}
