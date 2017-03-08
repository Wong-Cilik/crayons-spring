package crayonsTestbench;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.parallel.ParallelTest;

public class MyAppTest extends ParallelTest {

	private static final String URL = "http://localhost";
	private static final String PORT = "8080";

	@Before
	public void setup() throws Exception {
		setDriver(new ChromeDriver());
		// Open the WebPage
		getDriver().get(URL + ":" + PORT);
	}

	@After
	public void tearDown() throws Exception {
		// close the browser window
		getDriver().quit();
	}

	@Test
	public void test() {
		// Get a reference to the button
		ButtonElement button = $(ButtonElement.class).first();

		// Simulate button click;
		button.click();

		// Get text field value;
		String actualValue = $(LabelElement.class).first().getText();

		// Check that the value is not empty
		Assert.assertFalse(actualValue.isEmpty());
	}

}
