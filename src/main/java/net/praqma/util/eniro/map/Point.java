package net.praqma.util.eniro.map;

/**
 * User: cwolfgang
 * Date: 05-11-12
 * Time: 23:59
 */
public class Point {
    public double x;
    public double y;

    public Point(){}

    public Point( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    public static Point LonLat( double lon, double lat ) {
        return new Point( lat, lon );
    }

    public String toString() {
        return y+","+x;
    }
}
