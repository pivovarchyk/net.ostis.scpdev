package net.ostis.scpdev.ui.model;

import java.util.List;
import java.util.ArrayList;

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
	
	public void addBreakpoint(String name, boolean active)
	{
		mBreakpointsList.add(new BreakpointModel(name, active));
	}
	
	public List<BreakpointModel> getBreakpointsList()
	{
		return mBreakpointsList;
	}
}
