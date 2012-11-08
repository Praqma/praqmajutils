package net.praqma.util.eniro.map;

/**
 * User: cwolfgang
 * Date: 07-11-12
 * Time: 20:12
 */
public class EniroMapException extends Exception {
    public EniroMapException( String m ) {
        super( m );
    }

    public EniroMapException( Exception e ) {
        super( e );
    }

    public EniroMapException( String m, Exception e ) {
        super( m, e );
    }
}
