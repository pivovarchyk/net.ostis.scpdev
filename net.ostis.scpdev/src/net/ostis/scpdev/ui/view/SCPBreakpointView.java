package net.ostis.scpdev.ui.view;

import net.ostis.scpdev.ui.model.BreakpointModel;
import net.ostis.scpdev.ui.model.BreakpointModelProvider;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

public class SCPBreakpointView extends ViewPart 
{
	public static final String ID = "net.ostis.scpdev.SCPBreakPointView";
	private BreakpointModelProvider mBreakpointListProvider;
	private HashMap<BreakpointModel, IBreakpoint> mMapModel2Breakpoint;
	private CheckboxTableViewer mBreakPointList;

	public SCPBreakpointView()
	{
		super();
		mMapModel2Breakpoint = new HashMap<BreakpointModel, IBreakpoint>();
	}
	
	@Override
	public void createPartControl(Composite parent) 
	{
		mBreakpointListProvider = BreakpointModelProvider.INSTANCE;
		mBreakPointList = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		mBreakPointList.setLabelProvider(new ColumnLabelProvider()
				{
					private final Image Active = new Image(Display.getDefault(), SCPBreakpointView.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "icons/breakpointActive.png");
					
					private final Image Inactive = new Image(Display.getDefault(), SCPBreakpointView.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "icons/breakpointInactive.png");
					
					@Override
					public String getText(Object element)
					{
						BreakpointModel breakpoint = (BreakpointModel)element;
						return breakpoint.getName();
					}
					
					@Override
					public Image getImage(Object element)
					{
						BreakpointModel breakpoint = (BreakpointModel) element;
						return breakpoint.getActivationState() ? Active : Inactive;
					}
				}
				);
		mBreakPointList.setContentProvider(new ArrayContentProvider());
		mBreakPointList.setInput(mBreakpointListProvider.getBreakpointsList());
		mBreakPointList.addCheckStateListener(new ICheckStateListener()
			{
			
			@Override
			public void checkStateChanged(CheckStateChangedEvent event)
			{
				BreakpointModel element =((BreakpointModel)event.getElement()); 
				element.setState(event.getChecked());
				
		    	try 
		    	{
		    		IBreakpoint breakpoint = mMapModel2Breakpoint.get(element);
					breakpoint.setEnabled(event.getChecked());
				} 
		    	catch (CoreException e)
		    	{
					e.printStackTrace();
				}
				refreshList();
				System.out.println("state: " + event.getChecked() + " " + ((BreakpointModel)event.getElement()).getName());
			}
		});
		mBreakPointList.getControl().addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyReleased(KeyEvent e)
			{
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.keyCode == SWT.DEL)
				{
					BreakpointModel element = (BreakpointModel)((IStructuredSelection)mBreakPointList.getSelection()).getFirstElement();
					if (element == null)
						return;
					IBreakpoint breakpoint = mMapModel2Breakpoint.get(element);
					try 
					{
						// нужно удалить брейкпоинт через менеджер, а он уже оповестит все подписанные обработчики
						// необходимо, чтобы не было замкнутых циклов
						DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
					} 
					catch (CoreException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		mBreakPointList.addDoubleClickListener(new IDoubleClickListener()
		{
			
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				BreakpointModel element = (BreakpointModel)((IStructuredSelection)event.getSelection()).getFirstElement();
				element.getLink().linkActivated();
			}
		});
		
		createList();
		// необходимо сформировать список активных брейкпоинтов и чекнуть сооответствующие им элементы в таблице
		LinkedList<BreakpointModel> list = new LinkedList<BreakpointModel>();
		List<BreakpointModel> allBreakpoints = mBreakpointListProvider.getBreakpointsList();
		for (BreakpointModel element : allBreakpoints)
			if (element.getActivationState())
				list.add(element);
		mBreakPointList.setCheckedElements(list.toArray());
	}

	@Override
	public void setFocus() 
	{
		
	}
	
	public void addBreakpoint(IBreakpoint breakpoint)
	{
		try 
		{
			String name = (String)breakpoint.getMarker().getAttribute(IMarker.MESSAGE);
			String path = breakpoint.getMarker().getResource().getLocation().toString();
			ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
			int lineNumber = ((Integer)breakpoint.getMarker().getAttribute(IMarker.LINE_NUMBER)).intValue();
			File file = new File(path);
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI());
			FileLink link = new FileLink(files[0], null, -1, -1, lineNumber);
			boolean state = Boolean.valueOf(breakpoint.getMarker().getAttribute(IBreakpoint.ENABLED).toString());
			BreakpointModel element = new BreakpointModel(name, link, state);
			mMapModel2Breakpoint.put(element, breakpoint);
			mBreakpointListProvider.addBreakpoint(element);
		} catch (CoreException e)
		{
			e.printStackTrace();
		}
		refreshList();
	}
	
	public void removeBreakpoint(IBreakpoint breakpoint)
	{
		Entry<BreakpointModel, IBreakpoint> needRemove = getEntryForValue(breakpoint);
	    if (needRemove == null)
	    	return;
        mMapModel2Breakpoint.remove(needRemove.getKey());
		try 
		{
	        String name = breakpoint.getMarker().getAttribute(IMarker.MESSAGE).toString();
			mBreakpointListProvider.removeBreakpoint(name);
		} 
		catch (CoreException e)
		{
			e.printStackTrace();
		}
	    refreshList();
	}
	
	public void changeBreakpointState(IBreakpoint breakpoint, boolean state)
	{
		Entry<BreakpointModel, IBreakpoint> needModify = getEntryForValue(breakpoint);
	    if (needModify == null)
	    	return;
	    // set inversive state, because we have previous breakpoint state
    	needModify.getKey().setState(!state);

		refreshList();
	}
	
	private void createList()
	{
		IBreakpoint[] breakList = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for (IBreakpoint breakpoint : breakList)
			addBreakpoint(breakpoint);
		refreshList();
	}
	
	private void refreshList()
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			
			@Override
			public void run() 
			{
				mBreakPointList.refresh();
			}
		});
	}
	
	private Entry<BreakpointModel, IBreakpoint> getEntryForValue(IBreakpoint value)
	{
		Set<Entry<BreakpointModel, IBreakpoint>> set = mMapModel2Breakpoint.entrySet();
	    for (Entry<BreakpointModel, IBreakpoint> entry : set)
	    {
	        if (value.equals(entry.getValue()))
	        	return entry;
	    }
    	return null;

	}

}
