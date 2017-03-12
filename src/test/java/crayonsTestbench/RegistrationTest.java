package crayonsTestbench;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import com.crayons_2_0.service.LanguageService;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.NativeSelectElement;
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.PasswordFieldElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class RegistrationTest extends TestBenchTestCase {
    
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
        getDriver().quit();
    }

    @Test
    public void testNewUser() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
    }
    
    @Test
    public void testEmailExist() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the email address already in use
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(lang.getString("EmailAlreadyExists"), notification.getCaption());
    }
    
    @Test
    public void testWrongEmail() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();
        
        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the email is not valid
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(lang.getString("EmailIsNotValid"), notification.getCaption());
    }
    
    @Test
    public void testShortPassword() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("new@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("1234");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the password is too short
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(String.format(lang.getString("ShouldBeAtLeastNCharactersLong"),
                lang.getString("Password"), 6), notification.getCaption());
    }
    
    @Test
    public void testLongPasswort() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456123456123456123456123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the password is too long
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(String.format(lang.getString("ShouldBeAtMostNCharactersLong"),
                lang.getString("Password"), 15), notification.getCaption());
    }
    
    @Test
    public void testFirstNameFieldEmpty() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Leave the First Name field empty
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the password is too long
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(String.format(lang.getString("RequiredField"),
                lang.getString("FirstName")), notification.getCaption());
    }
    
    @Test
    public void testLastNameFieldEmpty() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Leave the Last Name field empty
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("client@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the password is too long
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(String.format(lang.getString("RequiredField"),
                lang.getString("LastName")), notification.getCaption());
    }
    
    @Test
    public void testEmailIsTooLong() {
        
        // 1. Click "Register" to navigate to the register form 
        $(ButtonElement.class).caption(lang.getString("Register")).first().click();

        // 2. Enter "Max" into the First Name field
        $(TextFieldElement.class).get(0).setValue("Max");
        
        // 3. Enter "Mustermann" into the Last Name field
        $(TextFieldElement.class).get(1).setValue("Mustermann");
        
        // 4. Enter "client@web.de" into the Email field
        $(TextFieldElement.class).get(2).setValue("clientclientclientclientclient@web.de");
        
        // 5. Enter "123456" into the Password field        
        $(PasswordFieldElement.class).first().setValue("123456");
        
        // 6. Choose "English" in the "Select Your Language" drop-down list
        $(NativeSelectElement.class).caption(lang.getString("SelectYourLanguage"))
                .first().setValue(lang.getString("English"));
           
        // 7. Click "Register" to finish the registration
        $(ButtonElement.class).caption(lang.getString("CreateUser")).first().click();
        
        // 8. Assert that the user gets a notification that the password is too long
        NotificationElement notification =
                $(NotificationElement.class).first();
        assertEquals(String.format(lang.getString("ShouldBeAtMostNCharactersLong"),
                lang.getString("Email"), 30), notification.getCaption());
    }
}
