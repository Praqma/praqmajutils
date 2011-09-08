package net.praqma.util.structure.changeset;

import java.io.File;

public class ChangeSetElement {
	private File file;
	private String version;
	private Status status = Status.UNCHANGED;
	
	public enum Status {
		UNCHANGED,
		CHANGED,
		ADDED,
		DELETED
	}
	
	public ChangeSetElement( File file, String version ) {
		this.file = file;
		this.version = version;
	}
	
	public ChangeSetElement( File file, String version, Status status ) {
		this.file = file;
		this.version = version;
		this.status = status;
	}

	public File getFile() {
		return file;
	}

	public void setFile( File file ) {
		this.file = file;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus( Status status ) {
		this.status = status;
	}
}
