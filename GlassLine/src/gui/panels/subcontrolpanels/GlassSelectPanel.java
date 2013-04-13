
package gui.panels.subcontrolpanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.panels.ControlPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The GlassSelectPanel class contains buttons allowing the user to select what
 * type of glass to produce.
 */
@SuppressWarnings("serial")
public class GlassSelectPanel extends JPanel implements ActionListener
{
	/** The ControlPanel this is linked to */
	private ControlPanel parent;

	GridBagConstraints gbc = new GridBagConstraints();
	JComboBox settingSelect = new JComboBox();
	JPanel settingPanel = new JPanel();
	JPanel checkBoxPanel = new JPanel();
	JPanel quantityPanel = new JPanel();
	JLabel quantityLabel = new JLabel("Number of pieces:");
	JLabel settingLabel = new JLabel("Setting Name:");
	JTextField quantityField = new JTextField(10);
	JTextField settingNameField = new JTextField(10);
	JButton acceptButton = new JButton("Set Current");
	JButton saveButton = new JButton("Save Setting");
	
	JPanel[] partPanel = new JPanel[15];
	JLabel[] partLabels = new JLabel[15];
	JCheckBox[] partCheckBoxes = new JCheckBox[15];
	
	boolean [] setting = new boolean[15];
	
	
	
	/**
	 * Creates a new GlassSelect and links it to the control panel
	 * @param cp
	 *        the ControlPanel linked to it
	 */
	public GlassSelectPanel(ControlPanel cp)
	{
		parent = cp;
		
		//initialize setting select combobox
		settingSelect.addItem("Default");
		
		//initialize labels
		partLabels[0] = new JLabel("Cutter");
		partLabels[1] = new JLabel("Conv 1");
		partLabels[2] = new JLabel("Breakout");
		partLabels[3] = new JLabel("Manual Breakout");
		partLabels[3].setFont(new Font("Dialog",Font.BOLD, 11));
		partLabels[4] = new JLabel("Conv 4");
		partLabels[5] = new JLabel("Drill");
		partLabels[6] = new JLabel("Cross Seamer");
		partLabels[7] = new JLabel("Grinder");
		partLabels[8] = new JLabel("Washer");
		partLabels[9] = new JLabel("Conv 9");
		partLabels[10] = new JLabel("Painter");
		partLabels[11] = new JLabel("UV Light");
		partLabels[12] = new JLabel("Conv 12");
		partLabels[13] = new JLabel("Oven");
		partLabels[14] = new JLabel("Truck");
		
		//initialize checkboxes and panel creation
		for(int i = 0; i < 15; i++) {
			partCheckBoxes[i] = new JCheckBox();
			setting[i] = false;
			partCheckBoxes[i].addActionListener(this);
			partPanel[i] = new JPanel();
			partPanel[i].add(partLabels[i]);
			partPanel[i].add(partCheckBoxes[i]);
			partPanel[i].setBackground(new Color(238,238,238));
		}
		
		partCheckBoxes[1].setSelected(true);
		partCheckBoxes[1].setEnabled(false);
		setting[1] = true;
		partCheckBoxes[4].setSelected(true);
		partCheckBoxes[4].setEnabled(false);
		setting[4] = true;
		partCheckBoxes[9].setSelected(true);
		partCheckBoxes[9].setEnabled(false);
		setting[9] = true;
		partCheckBoxes[12].setSelected(true);
		partCheckBoxes[12].setEnabled(false);
		setting[12] = true;
		partCheckBoxes[14].setSelected(true);
		partCheckBoxes[14].setEnabled(false);
		//Layout for panel that contains the checkboxes as well as the 'Create new setting' panel.
		checkBoxPanel.setLayout(new GridLayout(1,3));
		
		//the Left-most panel contains the first 5 checkboxes
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		//the middle panel contains the last 5 checkboxes
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < 7; i++) {
			leftPanel.add(partPanel[i]);
			midPanel.add(partPanel[i+7]);
		}
		
		//the right panel contains the setting name panel.
		JPanel rightPanel = new JPanel();
		GridBagConstraints g1 = new GridBagConstraints();
		rightPanel.setLayout(new GridBagLayout());
		g1.gridx = 0;
		g1.gridy = 0;
		rightPanel.add(settingLabel, g1);
		settingNameField.setMaximumSize(new Dimension(100,30));
		settingNameField.setPreferredSize(new Dimension(100,30));
		settingNameField.setMinimumSize(new Dimension(100,30));
		g1.gridy++;
		rightPanel.add(settingNameField, g1);
		g1.gridy++;
		rightPanel.add(saveButton,g1);
		checkBoxPanel.add(leftPanel);
		checkBoxPanel.add(midPanel);
		checkBoxPanel.add(rightPanel);
		acceptButton.addActionListener(this);
		
		quantityPanel.add(quantityLabel);
		quantityPanel.add(quantityField);
		quantityPanel.add(acceptButton);
		
		//adding to main panel
		setLayout(new GridBagLayout());
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.ipadx = 350;
		add(settingSelect, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		add(checkBoxPanel, gbc);
		
		gbc.gridwidth = 1;	
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		add(quantityPanel,gbc);
	}

	/**
	 * Returns the parent panel
	 * @return the parent panel
	 */
	public ControlPanel getGuiParent()
	{
		return parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 15; i++) {
			if(e.getSource() == partCheckBoxes[i]) {
				if(partCheckBoxes[i].isSelected()) {
					setting[i] = true;
				}
				else setting[i] = false;
			}
		}
		if(e.getSource() == acceptButton) {
			getGuiParent().getStatePanel().startButton.setEnabled(true);
			getGuiParent().setSetting(Integer.parseInt(quantityField.getText()),setting);
		}
	}
}
