/*
 * This source file is part of OSTIS (Open Semantic Technology for Intelligent
 * Systems) For the latest info, see http://www.ostis.net
 *
 * Copyright (c) 2010 OSTIS
 *
 * OSTIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * OSTIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSTIS. If not, see <http://www.gnu.org/licenses/>.
 */

package net.ostis.scpdev.perspectives;

import net.ostis.scpdev.ui.view.*;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;


/**
 * @brief ќписание перспективы дл€ работы в отладочном режиме
 * @author Eugene Kharkunov
 * 
 **/
public class SCPdevDebugPerspective implements IPerspectiveFactory
{
	static public final String ID = "net.ostis.scpdev.perspectives.scpdevdebug";
	
	public SCPdevDebugPerspective()
	{
		super();
		//! TODO: регистрируетс€ только при создании перспективы
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(new IPerspectiveListener()
		{
			@Override
			public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId)
			{
				System.out.println(changeId);
			}
			
			@Override
			public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective)
			{
				//! TODO: нужно сделать обновление списка брейкпоинтов
				System.out.println("Perspective activate: ");
				System.out.println(perspective.getDescription());
				
			}
		});
		
	}
	
	public void createInitialLayout(IPageLayout layout)
	{
		String editorArea = layout.getEditorArea();

		IFolderLayout topLeft = layout.createFolder("topleft", IPageLayout.TOP, 0.25f, editorArea);
		topLeft.addView(SCPDebuggerConsoleView.ID);
		
		IFolderLayout topRight = layout.createFolder("topright", IPageLayout.RIGHT, 0.6f, "topleft");
		topRight.addView(SCPStackTraceView.ID);
		topRight.addView(SCPBreakpointView.ID);
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.6f, editorArea);
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.15f, editorArea);
		left.addView(IPageLayout.ID_PROJECT_EXPLORER);
		
	    layout.addActionSet("org.eclipse.debug.ui.launchActionSet"); // NON-NLS-1
	    layout.addActionSet("org.eclipse.debug.ui.debugActionSet"); // NON-NLS-1
	    layout.addActionSet("org.eclipse.debug.ui.profileActionSet"); // NON-NLS-1
	}
}
