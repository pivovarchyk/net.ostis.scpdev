package net.ostis.scpdev.ui.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class SCPStackTraceView extends ViewPart 
{
	public static final String ID = "net.ostis.scpdev.SCPStackTraceView";
	
	private TreeViewer mStackTree = null;

	@Override
	public void createPartControl(Composite parent)
	{
		mStackTree = new TreeViewer(parent);
		mStackTree.add("child1", "child3");
		
	}

	@Override
	public void setFocus() 
	{
		mStackTree.getControl().setFocus();
		
	}

}
