package net.praqma.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.praqma.util.debug.Logger;


public class BuildNumberStamper
{
	private File src = null;
	private File dst = null;
	
	private final Pattern rx_major_pattern    = Pattern.compile( "(\\s*)\".*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.major.*$)" );
	private final Pattern rx_minor_pattern    = Pattern.compile( "(\\s*)\".*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.minor.*$)" );
	private final Pattern rx_patch_pattern    = Pattern.compile( "(\\s*)\".*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.patch.*$)" );
	private final Pattern rx_sequence_pattern = Pattern.compile( "(\\s*)\".*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.sequence.*$)" );
	
	//private final Pattern rx_sequence_4lvl    = Pattern.compile( "(=\\s*)\"(\\d+_\\d+_\\d+_\\d+)*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.fourlevel.*$)" );
	private final Pattern rx_sequence_4lvl    = Pattern.compile( "(\\s*)\".*\"(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.fourlevel.*$)" );
	
	/* Alternate versions */
	private final Pattern rx_alt_major_pattern    = Pattern.compile( "(\\s*)\\d+(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.major.*$)" );
	private final Pattern rx_alt_minor_pattern    = Pattern.compile( "(\\s*)\\d+(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.minor.*$)" );
	private final Pattern rx_alt_patch_pattern    = Pattern.compile( "(\\s*)\\d+(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.patch.*$)" );
	private final Pattern rx_alt_sequence_pattern = Pattern.compile( "(\\s*)\\d+(\\s*;*\\s*[\\/#]{2,2}\\s*buildnumber\\.sequence.*$)" );
	
	/* Maven four level */
	private final Pattern rx_maven_sequence_4lvl  = Pattern.compile( "^(\\s*)<version>.*</version>(\\s*)$" );
	
	private static final Logger logger = Logger.getLogger();

	private static final String linesep = System.getProperty( "line.separator" );
	
	public BuildNumberStamper( File src ) throws IOException
	{
		this.src = src;
		this.dst = File.createTempFile( "praqma_", ".tmp" );
	}
	
	/**
	 * Stamp a four level version number into a version file
	 * @param flvl A string representing the four level version, "major.minor.patch.sequence" 
	 * @return The number of occurrences
	 * @throws IOException
	 */
	public int stampIntoCode( String flvl ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new FileReader( src ) );
		FileWriter writer = new FileWriter( this.dst );
		
		String s = "";
		
		int number = 0;
		
		while( ( s = reader.readLine() ) != null )
		{
			/* Stamp 4level */
			if( flvl != null )
			{
				Matcher m = rx_sequence_4lvl.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + flvl + "\"$2" );
					number++;
					logger.debug( "flvl used" );
				}
				
				/* Maven */
				Matcher mm = rx_maven_sequence_4lvl.matcher( s );
				if( mm.find() )
				{
					s = mm.replaceFirst( "$1<version>" + flvl + "</version>$2" );
					number++;
					logger.debug( "Maven flvl used" );
				}
			}
			
			/* Write back */
			writer.write( s + linesep );
		}
		
		writer.close();
		reader.close();
		
		copyFile( this.dst, this.src );
		
		return ( number == 0 ? 0 : 1 );
	}
	
	
	/**
	 * Stamps a file
	 * @param major
	 * @param minor
	 * @param patch
	 * @param sequence
	 * @return 0 if the files had zero occurrences or 1 if it had any
	 * @throws IOException
	 */
	public int stampIntoCode( String major, String minor, String patch, String sequence ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new FileReader( src ) );
		FileWriter writer = new FileWriter( this.dst );
		
		String s = "";
		
		String flvl = null;
		if( major != null && minor != null && patch != null && sequence != null )
		{
			flvl = major + "_" + minor + "_" + patch + "_" + sequence;
			logger.debug( "flvl=" + flvl );
		}
		else
		{
			logger.debug( "flvl not set" );
		}
		
		int number = 0;
		
		while( ( s = reader.readLine() ) != null )
		{
			/* Stamp major */
			if( major != null )
			{
				Matcher m = rx_major_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + major + "\"$2" );
					number++;					
				}

				m = rx_alt_major_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1" + major + "$2" );
					number++;					
				}
			}
			
			/* Stamp minor */
			if( minor != null )
			{
				Matcher m = rx_minor_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + minor + "\"$2" );
					number++;					
				}
				
				m = rx_alt_minor_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1" + minor + "$2" );
					number++;					
				}
			}
			
			/* Stamp patch */
			if( patch != null )
			{
				Matcher m = rx_patch_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + patch + "\"$2" );
					number++;					
				}
				
				m = rx_alt_patch_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1" + patch + "$2" );
					number++;					
				}
			}
			
			/* Stamp sequence */
			if( sequence != null )
			{
				Matcher m = rx_sequence_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + sequence + "\"$2" );
					number++;					
				}
				
				m = rx_alt_sequence_pattern.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1" + sequence + "$2" );
					number++;					
				}
			}
			
			/* Stamp 4level */
			if( flvl != null )
			{
				Matcher m = rx_sequence_4lvl.matcher( s );
				if( m.find() )
				{
					s = m.replaceFirst( "$1\"" + flvl + "\"$2" );
					number++;
					logger.debug( "flvl used" );
				}
				
				/* Maven */
				Matcher mm = rx_maven_sequence_4lvl.matcher( s );
				if( mm.find() )
				{
					s = mm.replaceFirst( "$1<version>" + flvl + "</version>$2" );
					number++;
					logger.debug( "Maven flvl used" );
				}
			}
			
			/* Write back */
			writer.write( s + linesep );
		}
		
		writer.close();
		reader.close();
		
		copyFile( this.dst, this.src );
		
		return ( number == 0 ? 0 : 1 );
	}
	
	public static void copyFile( File sourceFile, File destFile ) throws IOException
	{
		if ( !destFile.exists() )
		{
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		
		try
		{
			source = new FileInputStream( sourceFile ).getChannel();
			destination = new FileOutputStream( destFile ).getChannel();
			destination.transferFrom( source, 0, source.size() );
		}
		finally
		{
			if ( source != null )
			{
				source.close();
			}
			if ( destination != null )
			{
				destination.close();
			}
		}
	}

}
