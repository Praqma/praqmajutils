package net.praqma.util.option;

import java.util.ArrayList;
import java.util.List;

import net.praqma.util.debug.Logger;

/**
 * An Option has a longName and an optional shortName. The longName is prefixed with double dashes, "--" and followed by an optional equal sign, "=".<br>
 * The shortName is prefixed with a single dash, "-".<br>
 * Options can have an arbitrary number of values, for example, --option=a b c d, --option a b e f, -o a b "c d e f"<br>
 * <br>
 * An Option is initialized:<br>
 * Option o1 = new Option( "major", "m", false, 1, "The major version of the change set to stamp" );<br>
 * <br>
 * The Option is then set into the Options. Parsed. And checked.<br>
 * The Option's can then be used to extract option values from the CLI.
 * 
 * @author wolfgang
 *
 */
public class Options
{
	public List<Option> options = new ArrayList<Option>();
	public String syntax = "";
	
	private Logger logger = Logger.getLogger();
	
	public Options( )
	{
	}
	
	public Options( String syntax )
	{
		this.syntax = syntax;
	}
	
	public void setOption( Option option )
	{
		options.add( option );
	}
	
	public void parse( String[] args )
	{
		String currentStr = null;
		Option current = null;
		
		for( int i = 0 ; i < args.length ; i++ )
		{
			
			/* New option */
			if( args[i].startsWith( "-" ) )
			{
				
				/* Of the form --option */
				if( args[i].startsWith( "-", 1 ) )
				{
					
					currentStr = args[i].substring( 2 );
					current = null;
					String[] val = currentStr.split( "=", 2 );

					if( val.length == 2 )
					{
						currentStr = val[0];
					}
					
					for( Option o : options )
					{
						if( currentStr.equalsIgnoreCase( o.longName ) )
						{
							current = o;
							if( val.length == 2 )
							{
								o.addValue( val[1] );
							}

							
							o.setUsed();
						}
					}
				}
				/* Single char option of the form -o */
				else
				{
					currentStr = args[i].substring( 1 );
					current = null;
					for( Option o : options )
					{
						if( currentStr.equalsIgnoreCase( o.shortName ) )
						{
							current = o;
							o.setUsed();
						}
					}
				}
			}
			else
			{
				if( current != null )
				{
					//options.get( current ).values.add( args[i] );
					current.addValue( args[i] );
				}
			}
		}
	}
	
	public void checkOptions() throws Exception
	{
		//List<String> errors = new ArrayList<String>();
		String errors = "";
		
		for( Option o : options )
		{
			if( o.required && !o.used )
			{
				errors += o.longName + " is not used and is not optional.\n";
			}
			
			if( o.arguments != o.values.size() && o.used )
			{
				errors += "Incorrect arguments for option " + o.longName + ". " + o.arguments + " required.\n";
			}
		}
		
		if( errors.length() > 0 )
		{
			throw new Exception( errors );
		}
	}
	
	public void print()
	{
		System.out.println( "Printing " + options.size() + " option" + ( options.size() == 1 ? "" : "s" ) );
		
		for( Option o : options )
		{
			System.out.print( o.longName + ": " );
			//System.out.print( ( o.required ? "Required " : "Optional " ) );
			System.out.print( ( o.used ? "Used " : "Unused " ) );
			if( o.values.size() > 0 )
			{
				System.out.print( "Values:" );
				int c = 0;
				for( String s : o.values )
				{
					c++;
					System.out.print( " [" + c + "] " + s );
				}
				
			}
			System.out.println( "" );
		}
	}
	
	public void display()
	{
		System.out.println( "Usage: " + this.syntax + "\n" );
		
		for( Option o : options )
		{
			System.out.print( "  --" + o.longName );
			
			if( o.shortName.length() > 0 )
			{
				System.out.print( new String(new char[15 - o.longName.length()]).replace("\0", " ") + "-" + o.shortName );
			}
			
			System.out.print( "\t" + ( o.required ? "Required" : "Optional" ) );
			
			System.out.println( "\t" + o.description );
		}
	}
	
	
}
