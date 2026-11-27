package Search_Engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Display extends JFrame  implements ActionListener, KeyListener, ListSelectionListener {

	JPanel pospanel = new JPanel();
	JPanel panel1 = new JPanel();
	static JPanel hiddenPanel = new JPanel();
	JButton button = new JButton("Search");
	static JTextField textfield = new JTextField("",30);			//The Search box
	static DefaultListModel listModel = new DefaultListModel();
	JList list = new JList(listModel);
	JScrollPane listScroll = new JScrollPane(list);

	int height = 600;
	int width = 800;
	static int algorithm = 0;
	String linkMe; //string to send to browser
	static String searchWord; //string to search
	static String subSite; //User submitted site
	static String[] strings = {"TF-IDF Algorithm", "K-Means Algorithm","Cosine Similarity Algorithm"};
	JRadioButton[] switchMe = new JRadioButton[strings.length];
	ButtonGroup group = new ButtonGroup();
	JPanel buttons = new JPanel();
	Search search;
	Browse browse;

	public Display(){

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.addListSelectionListener(this);
		listScroll.setPreferredSize(new Dimension(750,450));

		this.setSize(width,height);
		this.setLocationRelativeTo(null);
		this.setName("Search Engine");
		this.setVisible(true);
		this.setTitle("Medical Search Engine");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(pospanel);
		pospanel.setLayout(new BorderLayout());		//The Outer Panel
		panel1.setBackground(Color.white);
		hiddenPanel.setBackground(Color.white);
		pospanel.add(panel1, BorderLayout.CENTER);	//The Textbox & Button Panel
		pospanel.add(hiddenPanel, BorderLayout.SOUTH);	//The Results box Panel
		hiddenPanel.setVisible(false);

		panel1.add(textfield);
		panel1.add(button);
		panel1.add(buttons);
		hiddenPanel.add(listScroll);

		changer();				//Radiobutton changer

		/* Menu Bar */
		JMenuBar menubar = new JMenuBar();

		this.setJMenuBar(menubar);
		menubar.setBackground(Color.white);

		JMenu submitText = new JMenu("Submit Website");
		menubar.add(submitText);

		JMenuItem submit = new JMenuItem("OK");
		submitText.add(submit);

		JMenu help = new JMenu("Help");
		menubar.add(help);

		JMenuItem about = new JMenuItem("About");
		help.add(about);

		JMenuItem tutorial = new JMenuItem("Tutorial");
		help.add(tutorial);

		about.addActionListener(new aboutAction());
		submit.addActionListener(new submitAction());
		tutorial.addActionListener(new tutAction());

		button.addActionListener(this);
		textfield.addKeyListener(this);
	}

	public static void main (String [] args){
		new Display();
	}

	public void changer()
	{
		for (int i= 0; i < strings.length; i++) 
		{
			switchMe[i] = new JRadioButton(strings[i].toString());
			switchMe[i].setActionCommand(String.valueOf(i));
			switchMe[i].addActionListener(this);
			switchMe[i].setBackground(Color.WHITE);
			group.add(switchMe[i]);
			buttons.add(switchMe[i]);
			buttons.setBackground(Color.WHITE);
		}
		switchMe[0].setSelected(true);
	}

	class submitAction implements ActionListener {
		public void actionPerformed (ActionEvent e){
			submitLink.submitLinks();
		}
	}

	class aboutAction implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null,"This is a Medical-Focused Search Engine created by Michael Adeniran using Java. "
					+"\n19th February 2012 - Present."
					+"\nThis Search Engine features the TF-IDF, K-Means & Cosine Similarity algorithms"
					,"About",JOptionPane.PLAIN_MESSAGE);
		}
	}

	class tutAction implements ActionListener {
		public void actionPerformed (ActionEvent e){
			JOptionPane.showMessageDialog(null,"Simply enter your search phrase into the textbox provided and click the search button."
					+"\n Additionally you can select an algorithm of your choosing." ,"Tutorial",JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==switchMe[0])
		{		algorithm = 0;		System.out.println("TF-IDF Algorithm");	}
		if(a.getSource()==switchMe[1])
		{		algorithm = 1;		System.out.println("K-Mean Algorithm");	}
		if(a.getSource()==switchMe[2])
		{		algorithm = 2;		System.out.println("Cosine Similarity Algorithm");	}
		if(a.getSource()==button){
			Input.Inputs();
		}	
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			Input.Inputs();
		}
	}

	public void valueChanged(ListSelectionEvent l) {
		//if the changes to selection are false
		if(l.getValueIsAdjusting() == false)	 
		{
			if(list.getSelectedIndex() == -1){	//no selection has be made (get position -1 in list)
			}else{
				linkMe = Search.theResult.get(list.getSelectedIndex());
				Browse.openLink(linkMe);
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}