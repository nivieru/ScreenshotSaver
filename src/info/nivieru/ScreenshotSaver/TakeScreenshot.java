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

import android.app.IntentService;
import android.content.Intent;
import java.io.OutputStream;
import java.io.IOException;
import java.lang.InterruptedException;
//import android.util.Log;

public class TakeScreenshot extends IntentService {
	public TakeScreenshot() {
		super("ScheduledScrsht");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try
		{
			String pathTofb2png = getApplicationContext().getFilesDir() + "/fb2png";
			String[] command = {pathTofb2png, "/media/screensavers/ScreenshotSaver/screenshot.png"};
			Process fb2png = Runtime.getRuntime().exec(command, null,null);
			fb2png.waitFor();
	//		Log.d("ScreenshotSaver", "Screenshot taken");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
