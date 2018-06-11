package net.praqma.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PackageUtils {

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws Exception 
	 */
	public static List<Class<?>> getClasses( String packageName, String regexFilter ) throws Exception {
		Pattern regex = null;
		if( regexFilter != null ) {
			regex = Pattern.compile( regexFilter );
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace( '.', '/' );
		Enumeration<URL> resources = classLoader.getResources( path );

		List<String> dirs = new ArrayList<String>();
		while( resources.hasMoreElements() ) {
			URL resource = resources.nextElement();
			dirs.add( resource.getFile() );
		}

		TreeSet<String> classes = new TreeSet<String>();
		for( String directory : dirs ) {
			classes.addAll( findClasses( directory, packageName, regex ) );
		}

		ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
		for( String clazz : classes ) {
			classList.add( Class.forName( clazz ) );
		}

		return classList;
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	public static TreeSet<String> findClasses( String path, String packageName, Pattern regex ) throws Exception {
		TreeSet<String> classes = new TreeSet<String>();
		if (path.startsWith("file:") && path.contains("!")) {
			String[] split = path.split("!");
			URL jar = new URL(split[0]);
			ZipEntry entry;
			try (ZipInputStream zip = new ZipInputStream(jar.openStream())) {
				while ((entry = zip.getNextEntry()) != null) {
					if (entry.getName().endsWith(".class")) {
						String className = entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
						if (className.startsWith(packageName) && (regex == null || regex.matcher(className).matches())) {
							classes.add(className);
						}
					}
				}
			}
		}
		File dir = new File(path);
		if (!dir.exists()) {
			return classes;
		}
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file.getAbsolutePath(), packageName + "." + file.getName(), regex));
				} else if (file.getName().endsWith(".class")) {
					String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
					if (regex == null || regex.matcher(className).matches()) classes.add(className);
				}
			}
		}
		return classes;
	}
}
