package net.praqma.cli;

/**
 * @author cwolfgang
 */
public class MemoryInfo extends CLI {

    public static void main( String[] args ) throws Exception {
        MemoryInfo s = new MemoryInfo();
        s.perform( args );
    }

    @Override
    public void perform( String[] arguments ) throws Exception {
        long maxBytes = Runtime.getRuntime().maxMemory();
        System.out.println( "Max memory  : " + toMegaBytes( maxBytes ) );

        long totalBytes = Runtime.getRuntime().totalMemory();
        System.out.println( "Total memory: " + toMegaBytes( totalBytes ) );

        long freeBytes = Runtime.getRuntime().freeMemory();
        System.out.println( "Free memory : " + toMegaBytes( freeBytes ) );
    }

    public static String toMegaBytes( long bytes ) {
        return bytes / 1024 / 1024 + "MB";
    }
}
