package net.fiive.intern.android.test.view;

import android.test.AndroidTestCase;
import android.widget.EditText;
import net.fiive.intern.android.view.validation.TextValidator;

public class TextValidatorTest extends AndroidTestCase {

	private TextValidator validator;
	private EditText editText;

	public void testTruth() {
		assertTrue(true);
	}

	public void setUp() {
		this.validator = new TextValidator();
		this.editText = new EditText(this.getContext());
	}

	public void testValidateNotEmpty_validText() {
		String notEmptyText = "a";
		editText.setText(notEmptyText);

		boolean validated = validator.validateTextNotEmpty(editText, "Text should not be empty");
		assertTrue(validated);
		assertNull(editText.getError());
	}

	public void testValidateNotEmpty_invalidText() {
		editText.setText("");

		boolean validated = validator.validateTextNotEmpty(editText, "Text should be empty");
		assertFalse(validated);
		assertEquals("Text should be empty", editText.getError());
	}

	public void testValidateNotEmpty_nullText() {
		editText.setText(null);

		boolean validated = validator.validateTextNotEmpty(editText, "Null Text");
		assertFalse(validated);
	}
}


