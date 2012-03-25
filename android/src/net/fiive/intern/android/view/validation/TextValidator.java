package net.fiive.intern.android.view.validation;

import android.widget.EditText;

public class TextValidator {

	public boolean validateTextNotEmpty(EditText field, String textMessage) {
		String text = field.getText().toString();
		boolean validated = text != null && !"".equals(text);
		if( !validated) {
			field.setError(textMessage);
		} else {
			field.setError(null);
		}
		return validated;
	}

}
