package gui.panels.nonnormpanels;

import gui.panels.ControlPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class NonNormPanel extends JPanel implements ActionListener {

	ControlPanel parent;
	
	JComboBox nonNormSelector;
	
	JPanel bottomPanel = new JPanel();
	
	public NonNormPanel(ControlPanel c) {
		parent = c;
		
		nonNormSelector = new JComboBox();
		
		nonNormSelector.addItem("Select Non-Norm: ");
		nonNormSelector.addItem("Stop/Start Conveyor (0-14)");
		nonNormSelector.addItem("Inline Workstation Break/Restart");
		nonNormSelector.addItem("Workstation Broken/Fixed");
		nonNormSelector.addItem("Popup Jam/Unjam");
		nonNormSelector.addItem("Offline Workstation Change Speed");
		nonNormSelector.addItem("Disable/Enable Offline Workstation");
		nonNormSelector.addItem("Truck Breaks Down/Fixed");
		nonNormSelector.addItem("Glass Breaking Offline");
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.ipadx = 400;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(nonNormSelector, gbc);
		gbc.gridy++;
		add(bottomPanel, gbc);
		nonNormActionListener();
	}
	
	public void nonNormActionListener() {
		nonNormSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JComboBox option = (JComboBox) ae.getSource();
				
				String selected = (String) option.getSelectedItem();
				
				if(selected.equals("Stop/Start Conveyor (0-14)")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 14);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					conveyorSlider.setMinorTickSpacing(1);
					conveyorSlider.setPaintTicks(true);
					conveyorSlider.setSnapToTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Conveyor Numbers: 0-14"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					repaint();
					revalidate();
					
					
				}
				else if(selected.equals("Inline Workstation Break/Restart")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 6);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("2"));
					table.put(2, new JLabel("3"));
					table.put(3, new JLabel("8"));
					table.put(4, new JLabel("10"));
					table.put(5, new JLabel("11"));
					table.put(6, new JLabel("13"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Inline Conveyor Numbers:"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				else if(selected.equals("Workstation Broken/Fixed")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 6);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("2"));
					table.put(2, new JLabel("3"));
					table.put(3, new JLabel("8"));
					table.put(4, new JLabel("10"));
					table.put(5, new JLabel("11"));
					table.put(6, new JLabel("13"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Workstation Conveyor Numbers:"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				else if(selected.equals("Popup Jam/Unjam")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 2);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("1"));
					table.put(2, new JLabel("2"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Popup Index:"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				else if(selected.equals("Offline Workstation Change Speed")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 2);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("1"));
					table.put(2, new JLabel("2"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Popup Index"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				else if(selected.equals("Disable/Enable Offline Workstation")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 5);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("1"));
					table.put(2, new JLabel("2"));
					table.put(3, new JLabel("3"));
					table.put(4, new JLabel("4"));
					table.put(5, new JLabel("5"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Offline Workstation Index Index:"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				else if(selected.equals("Truck Breaks Down/Fixed")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
				}
				else if(selected.equals("Glass Breaking Offline")) {
					bottomPanel.removeAll();
					bottomPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					
					JSlider conveyorSlider = new JSlider(0, 5);
					JButton accept = new JButton("Accept");
					accept.addActionListener(this);
					
					Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
					table.put(0, new JLabel("0"));
					table.put(1, new JLabel("1"));
					table.put(2, new JLabel("2"));
					table.put(3, new JLabel("3"));
					table.put(4, new JLabel("4"));
					table.put(5, new JLabel("5"));
					
					conveyorSlider.setLabelTable(table);
					conveyorSlider.setMajorTickSpacing(1);
					conveyorSlider.setPaintLabels(true);
					conveyorSlider.setSnapToTicks(true);
					conveyorSlider.setPaintTicks(true);
					
					gbc.anchor = GridBagConstraints.NORTHWEST;
					gbc.weightx = 5;
					gbc.weighty = 5;
					gbc.gridx = 0;
					gbc.gridy = 0;
					bottomPanel.add(new JLabel("Valid Offline Workstation Index Index:"));
					gbc.gridy++;
					bottomPanel.add(conveyorSlider, gbc);
					gbc.ipadx = 5;
					gbc.gridx++;
					bottomPanel.add(accept, gbc);
					
					repaint();
					revalidate();
				}
				
			}
		} );
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		JComboBox option = (JComboBox) ae.getSource();
		String selected = (String) option.getSelectedItem();
		
		if(selected.equals("Stop/Start Conveyor (0-14)")) {
			
		}
		else if(selected.equals("Inline Workstation Break/Restart")) {
			
		}
		//TODO- FINISH
	}

}
