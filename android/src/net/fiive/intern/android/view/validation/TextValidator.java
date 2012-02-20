package net.fiive.intern.android.view.validation;

import android.content.Context;
import com.google.common.base.Preconditions;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.intern.android.view.alerts.ErrorAlertInfo;

public class TextValidator {

	private Context context;
	private AlertHelper alertHelper;

	public TextValidator(Context context) {
		Preconditions.checkNotNull(context);
		this.context = context;
		alertHelper = new AlertHelper();
	}

	public boolean validateTextIsFilled(String text, ErrorAlertInfo alertInfo) {
		boolean validated = text != null && !"".equals(text);
		if( !validated) {
			alertHelper.showErrorAlert(context, alertInfo, null);
		}
		return validated;
	}

	public void mockAlertHelper(AlertHelper helper) {
		this.alertHelper = helper;
	}

}
