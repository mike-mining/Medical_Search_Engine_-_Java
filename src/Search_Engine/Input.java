package Search_Engine;

import javax.swing.JOptionPane;

public class Input {
	Display display;
	Search search;
	
	public static void Inputs(){
		Display.searchWord = Display.textfield.getText();
		if(Display.searchWord.length()>2)
		{
			Display.listModel.removeAllElements();
			try {
				Search.theResult.clear();
				Search.search(Display.searchWord);
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"Input Error: "+e1,"Input Error",JOptionPane.PLAIN_MESSAGE);	
			}
			
		}else{ if(Display.searchWord.isEmpty()) {JOptionPane.showMessageDialog(null,"Text Field is Empty","Input Error",JOptionPane.PLAIN_MESSAGE); }
		else{JOptionPane.showMessageDialog(null,"Search Term is too short","Input Error",JOptionPane.PLAIN_MESSAGE);
		}
	}
		
		try {
			if (Search.topDocHits.totalHits == 0){
				JOptionPane.showMessageDialog(null,"Found " + Search.thisDoc.length + " hits. For Search Term: "+Display.searchWord,"Feedback",JOptionPane.PLAIN_MESSAGE);
			}
			for(int i=0;i<Search.theResult.size();i++){//Search.thisDoc.length;i++) {
				//System.out.println(Search.theResult.get(i));
				Display.listModel.addElement(i+1+": "+Search.theResult.get(i));
				Display.hiddenPanel.setVisible(true);
			}
			System.out.println(Search.theResult);
		}catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error: "+e1,"Error",JOptionPane.PLAIN_MESSAGE);
		}
	}
}

