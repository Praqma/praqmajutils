package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 23:04
 */
public class RoadGeometry {

    public static String url = "http://map.krak.dk/api/geocode";


    public static JsonArray get( String roadName ) throws IOException, EniroMapException {
        JsonObject data = new JsonObject();

        data.addProperty( "country", "dk" );
        data.addProperty( "name", roadName );
        data.addProperty( "contentType", "json" );
        data.addProperty( "hits", 25 );
        data.addProperty( "geometry", true );
        data.addProperty( "type", "street" );

        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod( url );
        get.setQueryString( URIUtil.encodeQuery( "country=dk&contentType=json&hits=25&geometry=1&type=STREET&name=" + roadName ) );


        int returnCode = client.executeMethod( get );
        if( returnCode == HttpStatus.SC_NOT_IMPLEMENTED ) {
            throw new IllegalStateException( "Return code " + returnCode );
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader( get.getResponseBodyAsStream() ) );
            String jsonLine = br.readLine();
            JsonElement jelement = new JsonParser().parse(jsonLine);

            if( jelement.getAsJsonObject().getAsJsonObject( "search" ).getAsJsonObject( "geocodingResponse" ).getAsJsonArray( "locations" ) == null ) {
                throw new EniroMapException( "No locations for " + roadName );
            }

            JsonObject geometry = null;
            try {
                geometry = jelement.getAsJsonObject().getAsJsonObject( "search" ).getAsJsonObject( "geocodingResponse" ).getAsJsonArray( "locations" ).get( 0 ).getAsJsonObject().getAsJsonObject( "geometry" );
            } catch( Exception e ) {
                throw new EniroMapException( "Unable to get geometry from result", e );
            }

            JsonArray coords = geometry.getAsJsonArray( "coordinates" );

            return coords;
        }


    }
}
