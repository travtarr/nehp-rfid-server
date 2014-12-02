package com.nehp.rfid_system.reader;

public class Launcher {
	
	public static void main(String[] args) throws Exception {
		new Launcher().run(args);
	}

	public void run(String[] args){
		final ReaderApp app;
		
		app = new ReaderApp("RFID Tracker");
		

		app.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				app.exitApp();
			};
		});
		
		app.setSize(480, 640);
		app.setVisible(true);
	}
}
