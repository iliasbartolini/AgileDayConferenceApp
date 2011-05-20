package it.agileday;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dEU4a2tIR18yeWtGekg5WUZkZTItQ2c6MQ",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class AgileDayApplication extends Application {

	@Override
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
	}
}
