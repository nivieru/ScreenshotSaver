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

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import android.content.Context;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import android.content.SharedPreferences;
import java.io.IOException;
import android.util.Log;
import android.content.ComponentName;
import android.content.pm.PackageManager;

public class ToggleScreenshotSaver extends Activity {
	SharedPreferences prefs = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("info.nivieru.ScreenshotSaver", MODE_PRIVATE);
		if (prefs.getBoolean("firstrun", true)) {
			try {
				InputStream ins = this.getResources().openRawResource (R.raw.fb2png);
				byte[] buffer = new byte[ins.available()];
				ins.read(buffer);
				ins.close();
				FileOutputStream fos = this.openFileOutput("fb2png", Context.MODE_PRIVATE);
				fos.write(buffer);
				fos.close();

				String pathTofb2png = getApplicationContext().getFilesDir() + "/fb2png";
				Process process = Runtime.getRuntime().exec("/system/bin/chmod 744 " + pathTofb2png);
				process.waitFor();

			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			File screenshotDir = new File("/media/screensavers/ScreenshotSaver");
			screenshotDir.mkdirs();

			prefs.edit().putBoolean("firstrun", false).commit();
			Log.d("ScreenshotSaver","first run ");

			//File file = getFileStreamPath ("fb2png");
			//file.setExecutable(true);
		}

		//boolean active = Scheduler.scheduleScreenshots(this);
		ComponentName receiver = new ComponentName(this, Scheduler.class);
		PackageManager pm = this.getPackageManager();

		if(prefs.getBoolean("active", true)){
			Scheduler.cancelScreenshots(this);
			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
					PackageManager.DONT_KILL_APP);
			prefs.edit().putBoolean("active", false).commit();
			Toast.makeText(this, R.string.scrsht_canceled, Toast.LENGTH_LONG).show();
		}else{
			pm.setComponentEnabledSetting(receiver,
					PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
					PackageManager.DONT_KILL_APP);
			Scheduler.scheduleScreenshots(this);
			prefs.edit().putBoolean("active", true).commit();
			Toast.makeText(this, R.string.scrsht_scheduled, Toast.LENGTH_LONG).show();
		}
		finish();
	}
}
