package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.praqma.util.net.url.UrlBuilder;
import java.util.LinkedList;
import java.util.List;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 21:31
 */
public class StaticMap {
    //public static final String URL = "http://kartor.eniro.se/api/statmap";
    private UrlBuilder url = new UrlBuilder( "kartor.eniro.se" ).addSubPage( "api" ).addSubPage( "statmap" );

    public StaticMap setWidth( int width ) {
        url.addKeyValue( "iwidth", width );

        return this;
    }

    public StaticMap setHeight( int height ) {
        url.addKeyValue( "iheight", height );

        return this;
    }

    public StaticMap setDimensions( int width, int height ) {
        return setWidth( width ).setHeight( height );
    }

    public StaticMap setBoundingBox( Point lowerLeft, Point upperRight ) {
        url.addKeyValues( "bbox", lowerLeft, upperRight );

        return this;
    }

    public StaticMap setCenter( Point center ) {
        url.addKeyValue( "cc", center );

        return this;
    }

    public StaticMap setZoom( int value ) {
        url.addKeyValue( "zoom", value );

        return this;
    }

    public StaticMap addPoint( Point point ) {
        url.addKeyValue( "p", point );

        return this;
    }

    public StaticMap addPoint( Point point, String name ) {
        url.addKeyValues( "p", point.y+","+point.x, name );

        return this;
    }

    public StaticMap addPoint( double x, double y, String name ) {
        url.addKeyValues( "p", y + "," + x, name );

        return this;
    }

    public StaticMap addSymbol( String name, String path ) {
        url.addKeyValue( name, path );

        return this;
    }

    public StaticMap addLine( List<Point> points ) {
        url.addKeyValues( "g", points );

        return this;
    }


    public StaticMap addLines( JsonArray lines ) {
        for( JsonElement line : lines ) {
            JsonArray lineArray = line.getAsJsonArray();
            List<Point> points = new LinkedList<Point>();
            for( JsonElement segment : lineArray ) {
                JsonArray a = segment.getAsJsonArray();
                points.add( Point.LonLat( a.get( 0 ).getAsDouble(), a.get( 1 ).getAsDouble() ) );
            }

            addLine( points );
        }

        return this;
    }

    public String toString() {
        return url.toString();
    }

}
