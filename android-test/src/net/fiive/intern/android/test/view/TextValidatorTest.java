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

	public void testTextMaxLength_zeroLength() {
		editText.setText("");

		boolean validated = validator.validateTextMaxLength(editText, 1, "Null Text");
		assertTrue(validated);
		assertNull(editText.getError());
	}

	public void testTextMaxLength_maxLength() {
		editText.setText("10 Letters");
		boolean validated = validator.validateTextMaxLength(editText, 10, "Should not appear");
		assertTrue(validated);
		assertNull(editText.getError());
	}

	public void testTextMaxLength_maxLengthPlusOne() {
		editText.setText("9 Letters");
		String message = "Text should have at most 8 letters";
		boolean validated = validator.validateTextMaxLength(editText, 8, message);
		assertFalse(validated);
		assertEquals(message, editText.getError().toString());
	}

	public void testMaxLength_maxLengthEqualsZero() {
		editText.setText("");
		assertTrue(validator.validateTextMaxLength(editText, 0, "null message"));
		editText.setText("a");
		assertFalse(validator.validateTextMaxLength(editText, 0, "null message"));
	}

	public void testMaxLength_errorChangeToNotNullWhenInvalidThenChangesBackToNullWhenValid() {
		editText.setText("hello");
		String message = "foo";
		assertTrue(validator.validateTextMaxLength(editText, 6, message));
		assertNull(editText.getError());

		assertFalse(validator.validateTextMaxLength(editText, 4, message));
		assertEquals(message, editText.getError().toString());

		assertTrue(validator.validateTextMaxLength(editText, 5, message));
		assertNull(editText.getError());
	}



}


