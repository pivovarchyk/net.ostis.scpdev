package net.ostis.scpdev.ui.view;

import net.ostis.scpdev.ui.model.BreakpointModel;
import net.ostis.scpdev.ui.model.BreakpointModelProvider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class SCPBreakpointView extends ViewPart 
{
	public static final String ID = "net.ostis.scpdev.SCPBreakPointView";

	public SCPBreakpointView()
	{
		super();
	}
	
	@Override
	public void createPartControl(Composite parent) 
	{
		TableViewer breakPointList = new TableViewer(parent, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		breakPointList.setLabelProvider(new ColumnLabelProvider()
				{
//					private final Image Active = new Image(Display.getDefault(), getClass().getResource("icons/breakpointActive.png").getFile());
					
//					private final Image Inactive = new Image(Display.getDefault(), getClass().getResource("icons/breakpointInactive.png").getFile());
					
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
						return null;//breakpoint.getActivationState() ? Active : Inactive;
					}
				}
				);
		breakPointList.setContentProvider(new ArrayContentProvider());
		breakPointList.setInput(BreakpointModelProvider.INSTANCE.getBreakpointsList());
	}

	@Override
	public void setFocus() 
	{
		
	}

}
