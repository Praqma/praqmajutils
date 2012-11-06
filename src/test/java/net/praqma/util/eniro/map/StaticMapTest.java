package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.IOException;

/**
 * User: cwolfgang
 * Date: 06-11-12
 * Time: 00:14
 */
public class StaticMapTest {

    public static String json = "[[[12.448382,55.687213],[12.445217,55.687119]],[[12.444062,55.687089],[12.443021,55.687065]]]";

    @Test
    public void test1() {
        JsonArray obj = new JsonParser().parse( json ).getAsJsonArray();


        StaticMap map = new StaticMap().addLines( obj );

        System.out.println( map );
    }

    @Test
    public void test2() throws IOException {
        JsonArray array = RoadGeometry.get( "Ejbyvej, 2610" );
        StaticMap map = new StaticMap().addLines( array ).setDimensions( 640, 480 ).addPoint( new Point( 55.6870282047923, 12.4449100923736 ));
        System.out.println( map );

    }
}
