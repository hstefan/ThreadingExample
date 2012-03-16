package com.hstefan.threadingexample;

import android.widget.Button;

public class VectorGenerationData {
	private int m_aLen;
	private Button m_bRun;
	private OnTaskFinishedListener<Float[]> m_FinListener;
	
	public VectorGenerationData(int aLen, Button bRun, OnTaskFinishedListener<Float[]> finListener) {
		super();
		this.m_aLen = aLen;
		this.m_bRun = bRun;
		this.m_FinListener = finListener;
	}
	
	public int getVectorLength() {
		return m_aLen;
	}
	
	public Button getButton() {
		return m_bRun;
	}
	
	public void attachListener(OnTaskFinishedListener<Float[]> listener) {
		m_FinListener = listener;
	}
	
	public OnTaskFinishedListener<Float[]> getListener() {
		return m_FinListener;
	}
}