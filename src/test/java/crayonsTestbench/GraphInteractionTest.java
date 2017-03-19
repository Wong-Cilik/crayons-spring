package crayonsTestbench;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.ComboBoxElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TabSheetElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class GraphInteractionTest extends TestBenchTestCase {

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	@Before
	public void setup() throws Exception {
		String osName = System.getProperty("os.name");
		osName = osName.substring(0, osName.indexOf(' '));
		System.out.println(osName);
		if (osName.equals("Windows")) {
			System.setProperty("webdriver.chrome.driver",
					"src/main/resources/chromedriver.exe");
		} else if (osName.equals("Mac")) {
			System.setProperty("webdriver.chrome.driver",
					"src/main/resources/chromedriver");
		}
		setDriver(TestBench.createDriver(new ChromeDriver()));
		// Open the WebPage
		getDriver().get("http://localhost:8080");

		driver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		// close the browser window
		Thread.sleep(5000);
		getDriver().quit();
	}

	@Test
	public void navigateToGrapheditor() {

		// 1. Enter "client@web.de" into the Email field
		$(TextFieldElement.class).caption(lang.getString("Login") + ": ")
				.first().setValue("client@web.de");

		// 2. Enter "123456" into the password field
		$(PasswordFieldElement.class)
				.caption(lang.getString("Password") + ": ").first()
				.setValue("123456");

		// 3. Click "Login"
		$(ButtonElement.class).caption(lang.getString("Login")).first().click();

		// 4. Click "Authorlibrary" to navigate to the author library
		$(ButtonElement.class).caption(lang.getString("Authorlibrary")).first()
				.click();

		// 5. Enter "GraphTest" into the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("GraphTest");

		// 6. Enter "Testing the graph." into the Course Description field
		$(TextAreaElement.class).first().setValue("Testing the graph");

		// 7. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 8. Assert that the course was added to the tab sheet
		boolean include = false;
		List<String> captions = $(TabSheetElement.class).first()
				.getTabCaptions();
		captions.remove(0);
		for (String caption : captions) {
			if (caption.equals("GraphTest"))
				include = true;
		}
		assertTrue(include);

		// 9. Click "Grapheditor"
		$(ButtonElement.class).caption(lang.getString("GraphEditor")).first()
				.click();

		// 10. Click "Add unit"
		$(ButtonElement.class).caption("Add unit").first().click();

		// 11. Click "Add unit"
		$(TextFieldElement.class).caption("Unit title").first()
				.setValue("TestUnit");

		// 12. Select Start Unit
		$(ComboBoxElement.class).caption("Select the previous unit").first()
				.selectByText("Start");

		// 13. Select End Unit
		$(ComboBoxElement.class).caption("Select the next unit").first()
				.selectByText("End");

		// 14. Click "Add unit"
		$(TextFieldElement.class).caption("Unit title").first()
				.setValue("TestUnit2");

		// 15. Select Start Unit
		$(ComboBoxElement.class).caption("Select the previous unit").first()
				.selectByText("TestUnit");

		// 16. Select End Unit
		$(ComboBoxElement.class).caption("Select the next unit").first()
				.selectByText("End");

		// 17. Click ""
		$(ButtonElement.class).caption("Modify connections").first().click();

		// 18. Click "Modify Connections"
		$(ButtonElement.class).caption("Modify connections").first().click();

	}

}