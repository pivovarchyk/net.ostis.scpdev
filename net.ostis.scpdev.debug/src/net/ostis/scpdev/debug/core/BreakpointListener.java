package net.ostis.scpdev.debug.core;

import net.ostis.scpdev.ui.view.SCPBreakpointView;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ui.PlatformUI;

public class BreakpointListener implements IBreakpointListener
{

	private SCPBreakpointView mBreakpointView;
	
	public BreakpointListener() 
	{
		mBreakpointView = (SCPBreakpointView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(SCPBreakpointView.ID);
		if (mBreakpointView != null)
			System.out.println("Breakpoint view found");
		else 
			System.out.println("Breakpoint view not found");
	}
	
	@Override
	public void breakpointAdded(IBreakpoint breakpoint) 
	{
		mBreakpointView.addBreakpoint(breakpoint);
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		mBreakpointView.removeBreakpoint(breakpoint);
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta)
	{
		Object obj = delta.getAttribute(IBreakpoint.ENABLED);
//		delta.getAttribute(Ibreakpoint.)
		mBreakpointView.changeBreakpointState(breakpoint, Boolean.valueOf(obj.toString()));
	}

}
