package crayonsTestbench;

import static org.junit.Assert.*;

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
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TabSheetElement;
import com.vaadin.testbench.elements.TextAreaElement;
import com.vaadin.testbench.elements.TextFieldElement;
import com.vaadin.testbench.elements.WindowElement;

public class CourseCreationTest extends TestBenchTestCase {

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	@Before
	public void setup() throws Exception {
		String osName = System.getProperty("os.name");
		osName = osName.substring(0, osName.indexOf(' '));
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
		getDriver().quit();
	}

	@Test
	public void testCreateAndDeleteCourse() {

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

		// 5. Enter "Algebra" into the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Algebra");

		// 6. Enter "Algebra is one of the broad parts of mathematics." into the
		// Course Description field
		$(TextAreaElement.class).first().setValue(
				"Algebra is one of the broad parts of mathematics.");

		// 7. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 8. Assert that the course was added to the tab sheet
		boolean include = false;
		List<String> captions = $(TabSheetElement.class).first()
				.getTabCaptions();
		captions.remove(0);
		for (String caption : captions) {
			if (caption.equals("Algebra"))
				include = true;
		}
		assertTrue(include);

		// 9. Click "Modify Course"
		$(ButtonElement.class).caption(lang.getString("ModifyCourse")).first()
				.click();

		// 10. Clear the Course Title field
		$$(WindowElement.class).caption("").$(TextFieldElement.class).first()
				.clear();

		// 11. Enter "Biology" into the Course Title field
		$$(WindowElement.class).caption("").$(TextFieldElement.class).first()
				.setValue("Biology");

		// 12. Clear the Course Description field
		$(TextAreaElement.class).first().clear();

		// 13. Enter "Biology is the study of life." into the Course Description
		// field
		$(TextAreaElement.class).first().setValue(
				"Biology is the study of life.");

		// 14. Click "Save" to save the changes
		$(ButtonElement.class).caption(lang.getString("Save")).first().click();

		// 15. Click "Delete Course" to delete the course
		$(ButtonElement.class).caption(lang.getString("DeleteCourse")).first()
				.click();
	}

	@Test
	public void testCreateTwoCoursesWithEqualNames() {

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

		// 5. Enter "Physics" into the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Physics");

		// 6. Enter "Physics is the natural science." into the Course
		// Description field
		$(TextAreaElement.class).first().setValue(
				"Physics is the natural science.");

		// 7. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 8. Open the first tab to create a new course
		$(TabSheetElement.class).first().openTab(0);

		// 9. Enter "Physics" into the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Physics");

		// 10. Enter "Physics is the natural science." into the Course
		// Description field
		$(TextAreaElement.class).first().setValue(
				"Physics is the natural science.");

		// 11. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 12. Assert that the user gets a notification that the course name
		// already in use
		NotificationElement notification = $(NotificationElement.class).first();
		assertEquals(lang.getString("CourseAlreadyExists"),
				notification.getCaption());

		// 13. Open the last tab - the tab with "Physics" course
		$(TabSheetElement.class).first().openTab("Physics");

		// 14. Click "Delete Course" to delete the course
		$(ButtonElement.class).caption(lang.getString("DeleteCourse")).first()
				.click();
	}

	@Test
	public void testCreateCourseWithEmptyFields() {
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

		// 5. Enter "Physics" into the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first()
				.setValue("Physics");

		// 6. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 7. Assert that the user gets a notification that the course
		// description cannot be empty
		assertEquals(
				String.format(lang.getString("RequiredField"),
						lang.getString("CourseDescription")),
				$(NotificationElement.class).first().getCaption());

		// 8. Enter "Physics is the natural science." into the Course
		// Description field
		$(TextAreaElement.class).first().setValue(
				"Physics is the natural science.");

		// 9. Clear the Course Title field
		$(TabSheetElement.class).$(TextFieldElement.class).first().clear();

		// 10. Click "CreateCourse"
		$(ButtonElement.class).caption(lang.getString("CreateCourse")).first()
				.click();

		// 11. Assert that the user gets a notification that the course title
		// cannot be empty
		assertEquals(
				String.format(lang.getString("RequiredField"),
						lang.getString("CourseTitle")),
				$(NotificationElement.class).first().getCaption());
	}
}
