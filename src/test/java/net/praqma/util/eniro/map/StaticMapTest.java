package net.praqma.util.eniro.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: cwolfgang
 * Date: 06-11-12
 * Time: 00:14
 */
public class StaticMapTest {

    public static String json = "[[[12.0,55.0],[12.4,55.6]],[[12.1,55.1],[12.3,55.7]]]";
    public static List<Point> points = new ArrayList<Point>();

    @BeforeClass
    public static void before() {
        points.add( new Point( 0.0, 1.0 ) );
        points.add( new Point( 2.0, 3.0 ) );
    }

    @Test
    public void test1() {
        JsonArray obj = new JsonParser().parse( json ).getAsJsonArray();

        StaticMap map = new StaticMap().addLines( obj );

        System.out.println( map );

        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?g=12.0,55.0;12.4,55.6&g=12.1,55.1;12.3,55.7" ) );
    }

    @Test
    public void test2() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().addLine( points );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?g=1.0,0.0;3.0,2.0" ));
    }

    @Test
    public void test3() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setDimensions( 600, 400 );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?iwidth=600&iheight=400" ));
    }

    @Test
    public void test4() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setWidth( 600 );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?iwidth=600" ));
    }

    @Test
    public void test5() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setHeight( 400 );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?iheight=400" ));
    }

    @Test
    public void test6() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setBoundingBox( new Point( 1.0, 2.0 ), new Point( 3.0, 4.0 ) );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?bbox=2.0,1.0;4.0,3.0" ));
    }

    @Test
    public void test7() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setCenter( new Point( 0.0, 1.0 ) );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?cc=1.0,0.0" ));
    }

    @Test
    public void test8() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().setZoom( 1 );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?zoom=1" ) );
    }

    @Test
    public void test9() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().addSymbol( "name", "url" ).addPoint( new Point( 0.0, 0.1 ), "name" );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?name=url&p=0.1,0.0;name" ));
    }

    @Test
    public void test10() throws IOException, EniroMapException {
        StaticMap map = new StaticMap().addPoint( new Point( 0.0, 0.1 ) );
        System.out.println( map );
        assertThat( map.toString(), is( "http://kartor.eniro.se/api/statmap?p=0.1,0.0" ));
    }
}
