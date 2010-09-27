package it.agileday.util;

public interface Task<T> {
	T run();

	void success(T ret);

	void failure(Exception e);
}