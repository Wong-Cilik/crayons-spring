package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;

@RunWith(SpringJUnit4ClassRunner.class)
public class CrayonsUserTest {

	private CrayonsUser user;

	private final String firstName = "Max";
	private final String lastName = "Mustermann";
	private final String email = "max@web.de";
	private final String password = "123456";
	private final String language = Language.German.toString();
	private final int permission = 2;
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		authorities.add(new SimpleGrantedAuthority("CLIENT"));

		user = new CrayonsUser(firstName, lastName, email, password, language,
				permission, true, true, false, false, authorities);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void setValidFirstName() {
		String newFirstName = "Susi";
		user.setFirstName(newFirstName);
		assertEquals(user.getFirstName(), newFirstName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidFirstName() {
		user.setFirstName("");
	}

	/*
	 * @Test(expected=IllegalArgumentException.class) public void
	 * setNullFirstName() { user.setFirstName(null); }
	 */

	@Test
	public void setValidLastName() {
		String newLastName = "Kaufgern";
		user.setLastName(newLastName);
		assertEquals(user.getLastName(), newLastName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidLastName() {
		user.setFirstName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail1() {
		// without @
		user.setEmail("withoutAT");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail2() {
		// Without point
		user.setEmail("without@point");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail3() {
		// longer than 30
		user.setEmail("tooLooooooooooooooooooooooooooong@web.de");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail4() {
		// missing front
		user.setEmail("@web.de");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail5() {
		// missing between @ and point
		user.setEmail("max@.de");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail6() {
		// mission after point
		user.setEmail("max@web.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidMail7() {
		// without @
		user.setEmail("withoutAT.de");
	}

	@Test
	public void setValidPassword() {
		String newPassword = "654321";
		user.setPassword(newPassword);
		assertEquals(user.getPassword(), newPassword);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPassword1() {
		// Empty
		user.setPassword("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPassword2() {
		// to short ( <6 )
		user.setPassword("12345");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPassword3() {
		// to long ( >15 )
		user.setPassword("1111111111111116");
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPassword4() {
		// with spaces
		user.setPassword("123 456");
	}

	@Test
	public void setValidLanguage() {
		String newLanguage = Language.English.toString();
		user.setLanguage(newLanguage);
		assertEquals(user.getLanguage(), newLanguage);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidLanguage() {
		// NotImplementedLanguage
		user.setLanguage("Schwäbisch");
	}

	@Test
	public void setValidPermission() {
		int newPermission = 1;
		user.setPermission(newPermission);
		assertEquals(user.getPermission(), newPermission);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPermission1() {
		// < 0
		user.setPermission(-1);
		;
	}

	@Test(expected = IllegalArgumentException.class)
	public void setInvalidPermission2() {
		// > 2
		user.setPermission(3);
		;
	}

}
