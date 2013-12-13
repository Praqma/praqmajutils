package net.praqma.util.net.url;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.SystemUtils;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 21:40
 */
public class UrlBuilder {
    private String domain;
    private int port = 0;
    private String protocol = "http://";
    private StringBuilder url = new StringBuilder();
    private StringBuilder parms = new StringBuilder();

    public UrlBuilder() {}

    public UrlBuilder( String domain ) {
        this.domain = domain;
    }

    public UrlBuilder setDomain( String domain ) {
        this.domain = domain;

        return this;
    }

    public UrlBuilder setPort( int port ) {
        this.port = port;

        return this;
    }

    public UrlBuilder setProtocol( String protocol ) {
        if( !protocol.endsWith( "/" ) ) {
            if( !protocol.endsWith( "//" ) ) {
                if( !protocol.endsWith( "://" ) ) {
                    this.protocol = protocol + "://";
                } else {
                    this.protocol = protocol + "//";
                }
            } else {
                this.protocol = protocol + "/";
            }
        } else {
            this.protocol = protocol;
        }

        return this;
    }

    public UrlBuilder addSubPage( String page ) {
        this.url.append( page );

        /* Make sure urls ends with "/" */
        if( !page.endsWith( "/" ) ) {
            this.url.append( "/" );
        }

        return this;
    }

    public <T> UrlBuilder addKeyValue( String key, T value ) {

        this.parms.append( key );
        this.parms.append( "=" );
        this.parms.append( value );
        this.parms.append( "&" );

        return this;
    }


    public <T> UrlBuilder addKeyValues( String key, List<T> values ) {
        int size = values.size(), c = 0;

        this.parms.append( key );
        this.parms.append( "=" );

        for( T value : values ) {
            this.parms.append( value );
            c++;

            if( c < size ) {
                this.parms.append( ";" );
            }
        }
        this.parms.append( "&" );

        return this;
    }

    public <T> UrlBuilder addKeyValues( String key, T ... values ) {
        this.parms.append( key );
        this.parms.append( "=" );
        int c = 0;

        for( T value : values ) {
            this.parms.append( value );
            c++;

            if( c < values.length ) {
                this.parms.append( ";" );
            }
        }
        this.parms.append( "&" );

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( protocol );
        sb.append( domain );
        if( port > 0 ) {
            sb.append( ":" );
            sb.append( port );
        }
        sb.append( "/" );
        if( url.length() > 0 ) {
            sb.append( url );
        }
        if( parms.length() > 0 ) {
            sb.replace( sb.length() - 1, sb.length(), "?" );
            sb.append( parms );
            sb.setLength( sb.length() - 1 );
        }
        return sb.toString();
    }
    
    /**
     * Cross platform method to resolve the hostname.
     * @return the hostname. Can be null if not succesful
     */
    public static String getLocalComputerName() {
        
        String compName = null;
        
        if(SystemUtils.IS_OS_UNIX) {
            try {
                compName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException ex) { }
        } else {
            compName = System.getenv("COMPUTERNAME");
        }
        
        return compName;
    }
}
