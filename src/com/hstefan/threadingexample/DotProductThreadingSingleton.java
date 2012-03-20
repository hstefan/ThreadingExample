package com.hstefan.threadingexample;

import java.util.Calendar;

public class DotProductThreadingSingleton {
	static private DotProductThreadingSingleton _instance = null;
	
	private long m_timer;
	private int m_numThreads;
	private int m_runningThreads;
	private float m_acc;
	private OnDotProductCalculationListener m_listener;
	private Float m_u[];
	private Float m_v[];
	
	private DotProductThreadingSingleton() {   }
	 
    public static synchronized DotProductThreadingSingleton getInstance() {
            if (_instance == null) {
                    _instance = new DotProductThreadingSingleton();
            }
            return _instance;
    }
    
    public void startTimer() {
    	m_timer =  Calendar.getInstance().getTimeInMillis();
    }
    
    public long getElapsedTime() {
    	return Calendar.getInstance().getTimeInMillis() - m_timer;
    }
    
    public synchronized void setNumThreads(int numThreads) {
    	m_numThreads = numThreads;
    	m_runningThreads = m_numThreads;
    }
    
    public synchronized void threadEnded(float partialRes) {
    	m_runningThreads--;
    	m_acc += partialRes;
    	if(m_runningThreads == 0) {
    		if(m_listener != null) 
    			m_listener.onDotProductCalculation(m_acc);
    	}
    }
    
    public void setOnDotProductCalculationListener(OnDotProductCalculationListener lst) {
    	m_listener = lst;
    }
    
    public void setVectorUVector(Float[] u) {
    	m_u = u;
    }
    
    public void setVectorVVector(Float[] v) {
    	m_u = v;
    }
    
    public Float[] getVectorU() {
    	return m_u;
    }
    
    public Float[] getVectorV() {
    	return m_v;
    }
    
    public interface OnDotProductCalculationListener {
    	void onDotProductCalculation(float res);
    }
    
}
