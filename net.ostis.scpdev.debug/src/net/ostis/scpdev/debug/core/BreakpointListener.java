package net.ostis.scpdev.debug.core;

import net.ostis.scpdev.ui.view.SCPBreakpointView;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

public class BreakpointListener implements IBreakpointListener
{

	private IViewDescriptor mBreakpointView;
	
	public BreakpointListener() 
	{
		mBreakpointView = PlatformUI.getWorkbench().getViewRegistry().find(SCPBreakpointView.ID);
	}
	
	@Override
	public void breakpointAdded(IBreakpoint breakpoint) 
	{
		
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		
	}

}
