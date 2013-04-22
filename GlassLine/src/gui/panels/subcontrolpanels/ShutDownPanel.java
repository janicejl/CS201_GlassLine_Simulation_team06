package gui.panels.subcontrolpanels;

import gui.panels.ControlPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ShutDownPanel extends JPanel implements ActionListener{
	List<JButton> cfShutDownButtons;
	List<JButton> workstationShutDownButtons;
	
	ControlPanel parent;
	JPanel conveyorShutDownPanel;
	JPanel workstationShutDown;
	
	public ShutDownPanel(ControlPanel parent) {
		//DECLARATIONS
		this.parent = parent;
		cfShutDownButtons = new ArrayList<JButton>();
		workstationShutDownButtons = new ArrayList<JButton>();
		conveyorShutDownPanel = new JPanel();
		workstationShutDown = new JPanel();
		
		//LAYOUTS
		setLayout(new GridBagLayout());
		setupConveyorShutDownPanel();
		setupWorkstationShutDownPanel();
		
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(conveyorShutDownPanel, gbc);
		gbc.gridx++;
		add(workstationShutDown, gbc);
		
	}

	private void setupConveyorShutDownPanel() {
		// TODO Auto-generated method stub
		conveyorShutDownPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		//ADDING COMPONENTS TO CONVEYOR SHUTDOWN PANEL
		gbc.gridx = 0;
		gbc.gridwidth = 4;
		conveyorShutDownPanel.add(new JLabel("Shutdown Conveyor Family"), gbc);
		gbc.weightx = 5;
		gbc.gridy++;
		gbc.gridwidth = 1;
		for(int i = 0; i < 14; i++) {
			cfShutDownButtons.add(new JButton((""+(i+1))));
			cfShutDownButtons.get(i).addActionListener(this);
			if(i == 7)  { gbc.gridy = 0; gbc.gridx++; gbc.weightx = 1; }
			gbc.gridy++;
			conveyorShutDownPanel.add(cfShutDownButtons.get(i), gbc);
		}
	}
	
	private void setupWorkstationShutDownPanel() {
		// TODO Auto-generated method stub
		workstationShutDown.setLayout(new BoxLayout(workstationShutDown, BoxLayout.Y_AXIS));
		
		//ADDING COMPONENTS TO WORKSTATION SHUTDOWN PANEL
		for(int i = 0; i < 3; i++) {
			workstationShutDownButtons.add(new JButton("Workstation Shutdown " + (i+1)));
			workstationShutDownButtons.get(i).addActionListener(this);
			workstationShutDown.add(workstationShutDownButtons.get(i));
		}
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 14; i++) {
			if(e.getSource() == cfShutDownButtons.get(i)) {
				conveyorShutDown(i);
			}
		}
	}
	
	public void conveyorShutDown(int i) {
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
