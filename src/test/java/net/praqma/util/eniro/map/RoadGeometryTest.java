package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import org.apache.commons.httpclient.URIException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 23:13
 */
public class RoadGeometryTest {

    @Test
    public void test1() throws IOException {
        JsonArray points = RoadGeometry.get( "Ejbyvej, 2610" );

        System.out.println( points );
    }
}
