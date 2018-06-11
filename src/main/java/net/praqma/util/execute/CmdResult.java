package net.praqma.util.execute;

import edu.umd.cs.findbugs.annotations.*;

import java.util.List;

@SuppressFBWarnings("URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD")
public class CmdResult {
	public StringBuffer stdoutBuffer = null;
	public List<String> stdoutList = null;

	public StringBuffer errorBuffer = null;
	public List<String> errorList = null;

	public CmdResult() {

	}
}
