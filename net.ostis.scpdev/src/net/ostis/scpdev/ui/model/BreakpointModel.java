package net.ostis.scpdev.ui.model;

public class BreakpointModel 
{
	private boolean mIsBreakpointActivated;
	
	private String mBreakpointName;
	
	public BreakpointModel(String name, boolean active)
	{
		mBreakpointName = name;
		mIsBreakpointActivated = active;
	}
	
	public BreakpointModel(String name)
	{
		mBreakpointName = name;
		mIsBreakpointActivated = true;
	}
	
	public String getName()
	{
		return mBreakpointName;		
	}
	
	public boolean getActivationState()
	{
		return mIsBreakpointActivated;
	}
}
