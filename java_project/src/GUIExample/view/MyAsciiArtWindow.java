package GUIExample.view;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import view.viewGUI.BasicWindow;


public class MyAsciiArtWindow extends BasicWindow implements View{


	String fileName;
	Text t;
	


	public MyAsciiArtWindow(String title, int width, int height) {
		super(title, width, height);
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		
		Button openFile=new Button(shell, SWT.PUSH);
		openFile.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));		
		openFile.setText("open image file");
		openFile.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.png", "*.gif", ".jpg", "*.bmp" };
				fd.setFilterExtensions(filterExt);
				fileName = fd.open();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		t=new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,2));
		
		Button convert=new Button(shell, SWT.PUSH);
		convert.setText("convert to ASCII art");
		convert.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		convert.addSelectionListener(new SelectionListener() {
			
			@Override			
			public void widgetSelected(SelectionEvent arg0) {
				if(fileName!=null){
					c.convert(fileName);
					
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	}

	@Override
	public void display(String fileName) {
		try{
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			String line;
			t.setText("");
			while((line=in.readLine())!=null){
				System.out.println(line);
				t.append(line+"\n");
			}
		}catch(Exception e){
			
		}
	}

}
