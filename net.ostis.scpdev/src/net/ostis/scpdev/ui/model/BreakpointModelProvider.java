package net.ostis.scpdev.ui.model;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.debug.ui.console.FileLink;

public enum BreakpointModelProvider
{
	INSTANCE;
	private List<BreakpointModel> mBreakpointsList;
	
	private BreakpointModelProvider()
	{
		mBreakpointsList = new ArrayList<BreakpointModel>();
	}
	
	public void addBreakpoint(BreakpointModel breakpoint)
	{
		mBreakpointsList.add(breakpoint);
	}
	
	public void addBreakpoint(String name, FileLink link, boolean active)
	{
		mBreakpointsList.add(new BreakpointModel(name, link, active));
	}
	
	public List<BreakpointModel> getBreakpointsList()
	{
		return mBreakpointsList;
	}
	
	public void removeBreakpoint(String name)
	{
		for (BreakpointModel element : mBreakpointsList)
		{
			if (name.equals(element.getName()))
			{
				mBreakpointsList.remove(element);
				break;
			}
		}
	}
	
	public void removeAllBreakpoint()
	{
		mBreakpointsList.clear();
	}
}
