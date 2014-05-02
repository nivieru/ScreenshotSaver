/*
   Copyright (c) 2014 Niv Ierushalmi

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
   */

package info.nivieru.ScreenshotSaver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class Scheduler extends BroadcastReceiver {
	private static final int PERIOD=110000;

	@Override
	public void onReceive(Context ctxt, Intent i) {
		scheduleScreenshots(ctxt);
	}

	static void scheduleScreenshots(Context ctxt) {
		AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
		Intent i=new Intent(ctxt, TakeScreenshot.class);
	//	boolean alarmUp = (PendingIntent.getService(ctxt, 0, i, PendingIntent.FLAG_NO_CREATE) != null);
	//	if (alarmUp)
	//	{
	//		PendingIntent pi=PendingIntent.getService(ctxt, 0, i, 0);
	//		mgr.cancel(pi);
	//		pi.cancel();
	//	}else{
			PendingIntent pi=PendingIntent.getService(ctxt, 0, i, 0);
			mgr.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + PERIOD, PERIOD, pi);
	//	}		      
	//	return !alarmUp;
	}
	static void cancelScreenshots(Context ctxt) {
		AlarmManager mgr=(AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
		Intent i=new Intent(ctxt, TakeScreenshot.class);
		PendingIntent pi=PendingIntent.getService(ctxt, 0, i, 0);
		mgr.cancel(pi);
		pi.cancel();
	}
}
