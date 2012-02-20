package net.fiive.intern.android.view.validation;

import android.content.Context;
import net.fiive.intern.android.view.alerts.AlertHelper;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TextValidatorTest {

	private Context context;

	@BeforeClass
	public void mockContext() {
		context = Mockito.mock(Context.class);
	}



	@Test
	public void testValidText() {
		TextValidator validator = new TextValidator(context);
		AlertHelper mockedHelper = Mockito.mock(AlertHelper.class);
		validator.mockAlertHelper(mockedHelper);

		Assert.assertTrue( validator.validateTextIsFilled("foo", "foo"));
		Assert.assertTrue( validator.validateTextIsFilled("f", "f"));
		Mockito.verifyNoMoreInteractions(mockedHelper);
	}

	@Test
	public void testInvalidText() {
		TextValidator validator = new TextValidator(context);
		AlertHelper mockedHelper = Mockito.mock(AlertHelper.class);
		validator.mockAlertHelper(mockedHelper);

		Assert.assertFalse(validator.validateTextIsFilled(null, "1"));
		Mockito.verify(mockedHelper).showErrorAlert(context, "1", null);

		Assert.assertFalse( validator.validateTextIsFilled("", "2"));
		Mockito.verify(mockedHelper).showErrorAlert(context, "2", null);

		Mockito.verifyNoMoreInteractions(mockedHelper);
	}
}
