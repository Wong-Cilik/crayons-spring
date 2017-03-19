package junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;

public class CourseTest {

	// DummyUser
	private final String firstName = "Max";
	private final String lastName = "Mustermann";
	private final String email = "max@web.de";
	private final String password = "123456";
	private final String language = Language.German.toString();
	private final int permission = 2;
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	// CrayonsUser testUser = new CrayonsUser(firstName, lastName, email, password, language, permission, true, true, false, false, authorities);
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidTitle() {
		// Empty Title is not allowed
		String invalidTitle = "";
		
		Course course = new Course();
		course.setTitle(invalidTitle);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidTitle2() {
		// Title length > 30 is not allowed
		String invalidTitle = "abcdefghijklmnopqrstuvwxyz01234";
		
		Course course = new Course();
		course.setTitle(invalidTitle);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDescription() {
		// empty description is not allowed
		String invalidDescription = "";
		
		Course course = new Course();
		course.setDescription(invalidDescription);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidAuthor() {
		// NULL Author is not allowed
		
		Course course = new Course();
		course.setAuthor(null);
	}
	

	
}
