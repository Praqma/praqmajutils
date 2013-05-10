package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 23:04
 */
public class RoadGeometry {

    public static final String url = "http://map.krak.dk/api/geocode";

    public static JsonArray get( String roadName ) throws EniroMapException {

        HttpClient client = new DefaultHttpClient();

        HttpGet get = null;

        try {
            URIBuilder builder = new URIBuilder( url );
            builder.setParameter( "country", "dk" );
            builder.setParameter( "contentType", "json" );
            builder.setParameter( "hits", "25" );
            builder.setParameter( "geometry", "1" );
            builder.setParameter( "type", "STREET" );
            builder.setParameter( "name", roadName );
            get = new HttpGet( builder.build() );
        } catch( URISyntaxException e ) {
            throw new EniroMapException( e );
        }

        HttpResponse response = null;
        try {
            response = client.execute( get );
        } catch( IOException e ) {
            throw new EniroMapException( e );
        }
        HttpEntity entity = response.getEntity();
        if( entity != null ) {

            JsonElement jelement = null;
            try {
                BufferedReader br = new BufferedReader( new InputStreamReader( entity.getContent(), "UTF-8" ) );
                String jsonLine = br.readLine();
                jelement = new JsonParser().parse(jsonLine);
            } catch( IOException e ) {
                throw new EniroMapException( e );
            }

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
        } else {
            throw new EniroMapException( "No response: " + response.getStatusLine().toString() );
        }


    }
}
