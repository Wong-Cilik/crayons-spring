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
		
		user = new CrayonsUser(firstName, lastName, email, password, language, permission, true, true, false, false, authorities);
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
	
	@Test(expected=IllegalArgumentException.class)
	public void setInvalidFirstName() {
		user.setFirstName("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setNullFirstName() {
		user.setFirstName(null);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
