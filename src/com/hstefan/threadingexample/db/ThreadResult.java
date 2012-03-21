package com.hstefan.threadingexample.db;

import java.util.Date;

public class ThreadResult {
	private int m_nThreads;
	private long m_procTime;
	private Date m_date;
	
	public ThreadResult(int nThreads, long procTime, Date date) {
		super();
		this.m_nThreads = nThreads;
		this.m_procTime = procTime;
		this.m_date = date;
	}

	
	public int getNThreads() {
		return m_nThreads;
	}

	public void setNThreads(int nThreads) {
		this.m_nThreads = nThreads;
	}

	public long getProcTime() {
		return m_procTime;
	}

	public void setProcTime(long procTime) {
		this.m_procTime = procTime;
	}

	public Date getDate() {
		return m_date;
	}

	public void setDate(Date date) {
		this.m_date = date;
	}
}
