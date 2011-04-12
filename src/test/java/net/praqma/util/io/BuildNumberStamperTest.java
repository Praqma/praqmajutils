/**
 * 
 */
package net.praqma.util.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author JsSu
 * 
 */
public class BuildNumberStamperTest {

	@Before
	public void setUp() throws Exception {
		File file = new File("c:\\temp.cpp");
		file.createNewFile();
		FileOutputStream fop = new FileOutputStream(file);
		
		String s = "\"0_38_0_1539\"; //buildnumber.fourlevel";
		fop.write(s.getBytes());
		
		fop.flush();		
		fop.close();
		
		File nfile = new File("c:\\not_valid_temp.cpp");
		nfile.createNewFile();
		FileOutputStream nfop = new FileOutputStream(nfile);
		
		String ns = "This is the first line\nthis is the second line\n and so on, and so on\n";
		nfop.write(ns.getBytes());
		
		nfop.flush();		
		nfop.close();
	}

	@After
	public void tearDown() throws Exception {
		File file = new File("c:\\temp.cpp");
		file.delete();
		
		File nfile = new File("c:\\not_valid_temp.cpp");
		nfile.delete();
	}	
	
	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeStringStringStringString() {

		File file = new File("c:\\temp.cpp");

		try {
			
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", "1", "0", "25");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = false;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0_1_0_25\".*", strline)) {
					retval = true;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeStringStringStringStringString() {
		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", "1", "0", "25", ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = false;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = true;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeNULLStringStringStringString() {

		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode(null, "1", "0", "25", ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeStringNULLStringStringString() {

		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", null, "0", "25", ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeStringStringStringNULLString() {

		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", "1", null, "25", ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeStringStringStringStringNULL() {

		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", "1", "0", null, ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testStampIntoCodeNotValidFile() {

		File file = new File("c:\\not_valid_temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0", "1", "0", "25", ".");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	/**
	 * Test method for 
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String)}
	 */
	@Test
	public void stampIntoCodeString (){
		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode("0.1.0.25");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = false;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = true;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	/**
	 * Test method for 
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String)}
	 */
	@Test
	public void stampIntoCodeNULL (){
		File file = new File("c:\\temp.cpp");

		try {
			
			BuildNumberStamper stamp = new net.praqma.util.io.BuildNumberStamper(
					file);
			stamp.stampIntoCode(null);

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}
	
	/**
	 * Test method for 
	 * {@link net.praqma.util.io.BuildNumberStamper#stampIntoCode(java.lang.String)}
	 */
	@Test
	public void stampIntoCodeSingleParameterNotValidFile (){
		File file = new File("c:\\not_valid_temp.cpp");

		try {
			
			BuildNumberStamper stamp = new BuildNumberStamper(
					file);
			stamp.stampIntoCode("0.1.0.25");

			FileInputStream fstream = new FileInputStream(file
					.getAbsolutePath().toString());
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			boolean retval = true;
			String strline;
			while ((strline = reader.readLine()) != null) {
				if (Pattern.matches("\"0.1.0.25\".*", strline)) {
					retval = false;
				}
			}
			assertTrue(retval);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}	
	}

}
