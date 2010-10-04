/**
 * 
 */
package com.ilikeblues.android.pathTracker.listeners;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

/**
 * @author jafernan
 *
 */
public class MapsStartListener implements View.OnClickListener {

	/**
	 * 
	 */
	public MapsStartListener() {
		// TODO Auto-generated constructor stub
	}

	public void onClick(View v) {
		try {
			final Intent myIntent = new Intent(
					android.content.Intent.ACTION_VIEW,
					Uri.parse("geo:38.899533,-77.036476"));
			
			v.getContext().startActivity(myIntent);

		} catch (Exception e) {
			((TextView)v).setText(e.getLocalizedMessage());
		}
	}

}
