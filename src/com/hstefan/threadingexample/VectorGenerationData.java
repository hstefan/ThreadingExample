package com.hstefan.threadingexample;

import android.widget.Button;

public class VectorGenerationData {
	private int m_aLen;
	private Button bRun;
	
	public VectorGenerationData(int data, Button bRun) {
		super();
		this.m_aLen = data;
		this.bRun = bRun;
	}
	
	public int getVectorLenght() {
		return m_aLen;
	}
	public Button getbRun() {
		return bRun;
	}
}