package net.ostis.scpdev.ui.model;

import org.eclipse.debug.ui.console.FileLink;



public class BreakpointModel 
{
	private boolean mIsBreakpointActivated;
	
	private String mBreakpointName;
	
	private FileLink mLink;
	
	public BreakpointModel(String name, FileLink link, boolean active)
	{
		mBreakpointName = name;
		mLink = link;
		mIsBreakpointActivated = active;
	}
	
	public BreakpointModel(String name, FileLink link)
	{
		mBreakpointName = name;
		mLink = link;
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
	
	public void setState(boolean state)
	{
		mIsBreakpointActivated = state;
	}
	
	public void setName(String name)
	{
		mBreakpointName = name;
	}
	
	public FileLink getLink()
	{
		return mLink;
	}
	
	public void setLink(FileLink link)
	{
		mLink = link;
	}
}
