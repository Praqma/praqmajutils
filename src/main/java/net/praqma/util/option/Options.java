package net.praqma.util.option;

import net.praqma.logging.LoggingUtil;
import net.praqma.logging.PraqmaticLogFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Option has a longName and an optional shortName. The longName is prefixed
 * with double dashes, "--" and followed by an optional equal sign, "=".<br>
 * The shortName is prefixed with a single dash, "-".<br>
 * Options can have an arbitrary number of values, for example, --option=a b c
 * d, --option a b e f, -o a b "c d e f"<br>
 * <br>
 * An Option is initialized:<br>
 * Option o1 = new Option( "major", "m", false, 1,
 * "The major version of the change set to stamp" );<br>
 * <br>
 * The Option is then set into the Options. Parsed. And checked.<br>
 * The Option's can then be used to extract option values from the CLI.
 * 
 * @author wolfgang
 * 
 */
public class Options {
	private static Logger logger = Logger.getLogger( Options.class.getName() );
	
	private List<Option> options = new ArrayList<Option>();
	private String syntax = "";
	private String header = "";
	private String description = "";

	private String version = "";

	private Option ohelp = null;
	private Option oversion = null;
	private Option overbose = null;
	private Option odebug = null;
	private Option otemplate = null;
	private Option ologfile = null;
    private Level used = Level.INFO;

	private boolean verbose = false;

	public static final String linesep = System.getProperty( "line.separator" );

    private Option otiming = null;
    private long millis;

	/* CONSTRUCTORS */

	public Options() {
		initialize();
	}

	public Options( String version ) {
		this.version = version;
		initialize();
        millis = System.currentTimeMillis();
	}
	
	private void initialize() {
        LoggingUtil.changeLoggerFormat( new PraqmaticLogFormatter() );
        
        registerShutdownHook();
	}
	
	private void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook( new Thread() {
			@Override
			public void run() {
				shutdown( otiming.used, millis );
			}
		} );
	}

	protected void shutdown( boolean used, long millis ) {
        try {
            if( used ) {
                long now = System.currentTimeMillis();
                double t = ( Math.floor( (double)( now - millis ) ) ) / 1000;
                System.out.println( "Time taken: " + t + "s" );
            }

        } catch( Throwable e ) {
            e.printStackTrace();
        }
	}

	public void setDefaultOptions() {
		ohelp = new Option( "help", "h", false, 0, "Display help" );
		oversion = new Option( "version", null, false, 0, "Print the version" );
		overbose = new Option( "verbose", null, false, 0, "Verbose" );
		odebug = new Option( "debug", null, false, 0, "Debug" );
		otemplate = new Option( "template", null, false, 1, "Output template" );
		ologfile = new Option( "logfile", null, false, -1, "Set a file to log to" );
        otiming = new Option( "timing", null, false, 0, "Enable timing" );

		this.setOption( ohelp );
		this.setOption( oversion );
		this.setOption( overbose );
		this.setOption( odebug );
        this.setOption( otiming );
		this.setOption( otemplate );
		this.setOption( ologfile );
	}

	public void setSyntax( String syntax ) {
		this.syntax = syntax;
	}

	public void setDescription( String desc ) {
		this.description = desc;
	}

	public void setHeader( String header ) {
		this.header = header;
	}

	public void setOption( Option option ) {
		options.add( option );
	}

	public void parse( String[] args ) {
		String currentStr = null;
		Option current = null;

		for( int i = 0; i < args.length; i++ ) {

			/* New option */
			if( args[i].startsWith( "-" ) ) {

				/* Of the form --option */
				if( args[i].startsWith( "-", 1 ) ) {

					currentStr = args[i].substring( 2 );
					current = null;
					String[] val = currentStr.split( "=", 2 );

					if( val.length == 2 ) {
						currentStr = val[0];
					}

					for( Option o : options ) {
						if( currentStr.equalsIgnoreCase( o.longName ) ) {
							current = o;
							if( val.length == 2 ) {
								o.addValue( val[1] );
							}

							o.setUsed();
						}
					}
				}
				/* Single char option of the form -o */
				else {
					currentStr = args[i].substring( 1 );
					current = null;
					for( Option o : options ) {
						if( currentStr.equals( o.shortName ) ) {
							current = o;
							o.setUsed();
						}
					}
				}
			} else {
				if( current != null ) {
					// options.get( current ).values.add( args[i] );
					current.addValue( args[i] );
				}
			}
		}
		
		this.verboseUsed();
		
		setTemplate();
		
        if( isVerbose() ) {
        	LoggingUtil.changeLoggerLevel( Level.CONFIG );
            used = Level.CONFIG;
        } else {
            LoggingUtil.changeLoggerLevel( Level.INFO );
            used = Level.INFO;
        }
        
        if( odebug.isUsed() ) {
            LoggingUtil.changeLoggerLevel( Level.ALL );
            used = Level.ALL;
        }
	}

	private void helpUsed() {
		if( ohelp != null && ohelp.used ) {
			this.display();
			System.exit( 0 );
		}
	}

	private void versionUsed() {
		if( oversion != null && oversion.used ) {
			System.out.println( this.version );
			System.exit( 0 );
		}
	}

	private void verboseUsed() {
		if( overbose != null && overbose.used ) {
			this.verbose = true;
		}
	}
	
	public void setTemplate() {
		if( otemplate.isUsed() ) {
            LoggingUtil.changeLoggerFormat( new PraqmaticLogFormatter( otemplate.getString() ) );
		}
	}
	
	private void logfileUsed() {
        if( ologfile.isUsed() ) {
            File file = new File( ologfile.getString() );
            try {
                OutputStream out = new FileOutputStream( file );
                LoggingUtil.addOutput( out, new PraqmaticLogFormatter() );
                LoggingUtil.changeLoggerLevel( used );
            } catch( FileNotFoundException e ) {
                e.printStackTrace();
            }
        }
	}

	public void checkOptions() throws Exception {
		this.helpUsed();
		this.versionUsed();
		this.logfileUsed();

		String errors = "";

		for( Option o : options ) {
			if( o.required && !o.used ) {
				errors += o.longName + " is not used and is not optional." + linesep;
			}

			if( o.arguments != o.values.size() && o.arguments > -1 && o.used ) {
				errors += "Incorrect arguments for option " + o.longName + ". " + o.arguments + " required." + linesep;
			}
		}

		if( errors.length() > 0 ) {
			throw new Exception( errors );
		}
	}

	/**
	 * @deprecated since 0.1.8
	 * @return
	 */
	public boolean verbose() {
		return this.verbose;
	}

	public boolean isVerbose() {
		return this.verbose;
	}

	public void print() {
		System.out.println( "Printing " + options.size() + " option" + ( options.size() == 1 ? "" : "s" ) );

		for( Option o : options ) {
			System.out.print( o.longName + ": " );
			// System.out.print( ( o.required ? "Required " : "Optional " ) );
			System.out.print( ( o.used ? "Used " : "Unused " ) );
			if( o.values.size() > 0 ) {
				System.out.print( "Values:" );
				int c = 0;
				for( String s : o.values ) {
					c++;
					System.out.print( " [" + c + "] " + s );
				}

			}
			System.out.println( "" );
		}
	}

	public void display() {
		if( this.header.length() > 0 ) {
			System.out.println( this.header + linesep );
		}

		System.out.println( "Usage: " + this.syntax + linesep );

		for( Option o : options ) {
			System.out.print( "  --" + o.longName );

			if( o.shortName != null && o.shortName.length() > 0 ) {
				System.out.print( new String( new char[15 - o.longName.length()] ).replace( "\0", " " ) + "-" + o.shortName );
			} else {
				System.out.print( new String( new char[16 - o.longName.length()] ).replace( "\0", " " ) );
			}

			System.out.print( "\t" + ( o.required ? "Required" : "Optional" ) );

			System.out.println( "\t" + o.description );
		}

		if( this.description.length() > 0 ) {
			System.out.println( linesep + this.description );
		}
	}

	public String toString() {
		return "";
	}

}
