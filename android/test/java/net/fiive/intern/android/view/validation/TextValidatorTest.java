package net.fiive.intern.android.view.validation;

import android.content.Context;
import net.fiive.intern.android.view.alerts.AlertHelper;
import net.fiive.intern.android.view.alerts.ErrorAlertInfo;
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

		ErrorAlertInfo alertInfoA = createAlertInfoForString("foo");
		ErrorAlertInfo alertInfoB = createAlertInfoForString("bar");

		Assert.assertTrue(validator.validateTextIsFilled("foo", alertInfoA));
		Assert.assertTrue(validator.validateTextIsFilled("f", alertInfoB));
		Mockito.verifyNoMoreInteractions(mockedHelper);
	}

	@Test
	public void testInvalidText() {
		TextValidator validator = new TextValidator(context);
		AlertHelper mockedHelper = Mockito.mock(AlertHelper.class);
		validator.mockAlertHelper(mockedHelper);

		ErrorAlertInfo alertInfoA = createAlertInfoForString("foo");
		ErrorAlertInfo alertInfoB = createAlertInfoForString("bar");


		Assert.assertFalse(validator.validateTextIsFilled(null, alertInfoA));
		Mockito.verify(mockedHelper).showErrorAlert(context, alertInfoA, null);

		Assert.assertFalse(validator.validateTextIsFilled("", alertInfoB));
		Mockito.verify(mockedHelper).showErrorAlert(context, alertInfoB, null);

		Mockito.verifyNoMoreInteractions(mockedHelper);
	}

	private ErrorAlertInfo createAlertInfoForString(String string) {
		return new ErrorAlertInfo(string, string, string);
	}
}
