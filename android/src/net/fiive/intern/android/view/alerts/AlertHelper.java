package net.fiive.intern.android.view.alerts;

import net.fiive.intern.android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.google.common.base.Preconditions;

public class AlertHelper {


	public void showErrorAlert(Context context, String message, final AlertCallback alertCallback) {
		Preconditions.checkNotNull(context, "Error: Can't show alert message without a context");
		Preconditions.checkNotNull(message, "Error: message cannot be null");

		   new AlertDialog.Builder(context).setMessage(message).setPositiveButton(R.string.continue_verb,
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					if ( alertCallback != null ) {
						alertCallback.notifyCallback();
					}
				}
			} );
	}


	public static abstract class AlertCallback {

		private void notifyCallback() {
			this.onCallback();
		}

		public abstract void onCallback();

	}

}
