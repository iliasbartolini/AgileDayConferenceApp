package it.agileday.util;

import android.os.Handler;

public class TaskRunner {
	public static <T> void run(final Task<T> task) {
		final Handler handler = new Handler();
		new Thread() {
			@Override
			public void run() {
				try {
					final T ret = task.run();
					handler.post(new Runnable() {
						@Override
						public void run() {
							task.success(ret);
						}
					});
				} catch (final Exception e) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							task.failure(e);
						}
					});
				}
			}
		}.start();
	}
}
