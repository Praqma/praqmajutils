package net.praqma.util.net.url;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 21:58
 */
public class UrlBuilderTest {

    @Test
    public void test1() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" );

        assertThat( ub.toString(), is( "http://www.praqma.net/" ) );
    }

    @Test
    public void test2() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).setPort( 8080 );

        assertThat( ub.toString(), is( "http://www.praqma.net:8080/" ) );
    }

    @Test
    public void test3() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).setProtocol( "https" );

        assertThat( ub.toString(), is( "https://www.praqma.net/" ) );
    }

    @Test
    public void test4() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).setProtocol( "https" ).setPort( 8080 );

        assertThat( ub.toString(), is( "https://www.praqma.net:8080/" ) );
    }

    @Test
    public void test5a() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addSubPage( "code" );

        assertThat( ub.toString(), is( "http://www.praqma.net/code/" ) );
    }

    @Test
    public void test5b() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addSubPage( "code" ).addSubPage( "cool" );

        assertThat( ub.toString(), is( "http://www.praqma.net/code/cool/" ) );
    }

    @Test
    public void test6() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValue( "shark", "haj" );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj" ) );
    }

    @Test
    public void test7a() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValue( "shark", "haj" ).addSubPage( "code" );

        assertThat( ub.toString(), is( "http://www.praqma.net/code?shark=haj" ) );
    }

    @Test
    public void test7b() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addSubPage( "code" ).addKeyValue( "shark", "haj" ).addSubPage( "cool" );

        assertThat( ub.toString(), is( "http://www.praqma.net/code/cool?shark=haj" ) );
    }

    @Test
    public void test8() {
        List<String> map = new LinkedList<String>();
        map.add( "haj" );
        map.add( "whale" );
        map.add( "hval" );
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", map );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj;whale;hval" ) );
    }

    @Test
    public void test9() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValue( "shark", 88 );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=88" ) );
    }

    @Test
    public void test10() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", "haj", "whale", "hval" );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj;whale;hval" ) );
    }

    @Test
    public void test10b() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", "haj", "whale", "hval" );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj;whale;hval" ) );
    }

    @Test
    public void test10c() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", "haj", "whale", "" );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj;whale;" ) );
    }

    @Test
    public void test10d() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", "haj", "whale" );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=haj;whale" ) );
    }

    @Test
    public void test10e() {
        UrlBuilder ub = new UrlBuilder( "www.praqma.net" ).addKeyValues( "shark", 1, 2, 3 );

        assertThat( ub.toString(), is( "http://www.praqma.net?shark=1;2;3" ) );
    }
}
