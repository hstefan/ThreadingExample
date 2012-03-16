package com.hstefan.threadingexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class ThreadingExampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.main);
        
    	Button bRun = (Button)findViewById(R.id.bRun);
        new VectorGenerationTask().execute(new VectorGenerationData(300000, bRun));
        
        super.onCreate(savedInstanceState);
    }
}