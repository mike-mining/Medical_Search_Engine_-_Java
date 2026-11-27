package Search_Engine;

import javax.swing.JOptionPane;

public class Browse {
	Display display;
	
	public static void openLink(String linkme) {
		//tests to see if java desktop api is supported on this computer
		if(!java.awt.Desktop.isDesktopSupported()) {	
			System.out.println("Desktop is not supported on this computer");
			JOptionPane.showMessageDialog(null,"Desktop is not supported on this computer","Error",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
		
		//obtains the default web browse on the current computer
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();	
		
		//determines if the browser supports the java browse action
		if(!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) { 	
			System.out.println("Cannot find desktop default browser ");
			JOptionPane.showMessageDialog(null,"Cannot find desktop default browser ","Error",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}

		try {
			//creates a URI (Uniform Resource Identifier) from a string
			java.net.URI link = new java.net.URI(linkme);	
			//opens the URI the default web browser
			desktop.browse(link);							
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}