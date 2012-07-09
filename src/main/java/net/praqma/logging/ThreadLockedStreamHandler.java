package net.praqma.logging;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class ThreadLockedStreamHandler extends StreamHandler {

	protected OutputStream stream;
	protected long threadId;
	
	public ThreadLockedStreamHandler( OutputStream stream, Formatter formatter ) {
		super( stream, formatter );
		
		this.threadId = Thread.currentThread().getId();
	}
	
	@Override
	public void publish( LogRecord logRecord ) {
		if( threadId == Thread.currentThread().getId() ) {
			super.publish( logRecord );
			super.flush();
		} else {
			/* No op, not same thread */
		}
	}
}
