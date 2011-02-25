package net.praqma.util;

/**
 * @author jssu
 * 
 */
public class Version
{
	private static final String major    = "0"; // buildnumber.major
	private static final String minor    = "1"; // buildnumber.minor
	private static final String patch    = "5"; // buildnumber.patch
	private static final String sequence = "XX"; // buildnumber.sequence
	
	public static final  String version  = major + '.' + minor + '.' + patch + '.' + sequence;
}
