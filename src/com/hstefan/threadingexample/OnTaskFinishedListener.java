package com.hstefan.threadingexample;

public interface OnTaskFinishedListener<T> {
	void onTaskFinish(T result);
}
