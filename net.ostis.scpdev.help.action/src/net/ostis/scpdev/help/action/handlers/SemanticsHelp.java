package net.ostis.scpdev.help.action.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.ui.IWorkbenchWindow;
//import org.eclipse.ui.PartInitException;
//import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.swt.program.Program;

public class SemanticsHelp extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		//Program.launch("java -classpath d:\\development\\tools\\eclipse_4.2_JUNO\\plugins\\org.eclipse.help.base_3.6.100.v201209141800.jar org.eclipse.help.standalone.Infocenter -eclipsehome d:\\development\\tools\\eclipse_4.2_JUNO -port 8081 -command start");
		//Program.launch("http://localhost:8081/help");
		Program.launch("http://ims.ostis.net/");
		return null;
	}
}
