package gui.panels.subcontrolpanels;

import gui.panels.ControlPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ShutDownPanel extends JPanel implements ActionListener{
	List<JButton> shutDownButtons = new ArrayList<JButton>();
	
	ControlPanel parent;
	
	public ShutDownPanel(ControlPanel parent) {
		this.parent = parent;
		
		setLayout(new GridLayout(5,2));
		
		for(int i = 0; i < 14; i++) {
			shutDownButtons.add(new JButton("Shutdown " + (i+1)));
			add(shutDownButtons.get(i));
			shutDownButtons.get(i).addActionListener(this);
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 14; i++) {
			if(e.getSource() == shutDownButtons.get(i)) {
				shutDown(i);
			}
		}
	}
	
	public void shutDown(int i) {
		//need to implement methods for shutdown here;
		if(i == 0); //CF 1
		else if(i == 1);//CF 2
		else if(i == 2);//CF 3
		else if(i == 3);//CF 4
		else if(i == 4);//CF 5
		else if(i == 5);//CF 6
		else if(i == 6);//CF 7
		else if(i == 7);//CF 8
		else if(i == 8);//CF 9
		else if(i == 9);//CF 10
		else if(i == 10);//CF 11
		else if(i == 11);//CF 12
		else if(i == 12);//CF 13
		else if(i == 13);//CF 14
		
	}
}
