package net.ostis.scpdev.ui.view;

import java.io.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SCPDebuggerConsoleView extends ViewPart
{
	public static final String ID = "net.ostis.scpdev.SCPDebuggerConsoleView";
	
	private static Text mDebuggerOutput = null;
	private static Text mDebuggerCommand = null;
	
	@Override
	public void createPartControl(Composite parent)
	{
		GridLayout mainLayout = new GridLayout(2, false);
		parent.setLayout(mainLayout);
		mDebuggerOutput = new Text(parent, SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		Label commandLabel = new Label(parent, SWT.HORIZONTAL);
		commandLabel.setText("Debugger command:");
		mDebuggerCommand = new Text(parent, SWT.SINGLE | SWT.BORDER);
		mDebuggerCommand.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mDebuggerCommand.addKeyListener(new KeyListener()
		{	
			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		GridData data = new GridData();
		data.widthHint = 180;
		mDebuggerCommand.setLayoutData(data);
		data = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
		mDebuggerOutput.setLayoutData(data);
	}

	@Override
	public void setFocus() 
	{
		mDebuggerOutput.setFocus();
	}

	public static void redirectStream(final InputStream in)
	{
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run()
			{
				String str;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				try {
					System.out.println("Redirect begin");
					while((str = reader.readLine()) != null)
						writeToConsole(str);
					System.out.println("Redirect end");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public static void writeToConsole(final String str)
	{
		try
		{
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run()
				{
					mDebuggerOutput.append(str + "\r\n");
				}
			});
		}
		catch(NullPointerException e)
		{
		}
	}
	
}
