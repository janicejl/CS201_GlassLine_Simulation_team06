
package gui.panels.subcontrolpanels;

import java.awt.*;

import gui.panels.ControlPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The GlassSelectPanel class contains buttons allowing the user to select what
 * type of glass to produce.
 */
@SuppressWarnings("serial")
public class GlassSelectPanel extends JPanel
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
	JButton acceptButton = new JButton("Create");
	JButton saveButton = new JButton("Save Setting");
	
	JPanel[] partPanel = new JPanel[10];
	JLabel[] partLabels = new JLabel[10];
	JCheckBox[] partCheckBoxes = new JCheckBox[10];
	
	
	
	/**
	 * Creates a new GlassSelect and links it to the control panel
	 * @param cp
	 *        the ControlPanel linked to it
	 */
	public GlassSelectPanel(ControlPanel cp)
	{
		parent = cp;
		settingSelect.addItem("Default");
		
		partLabels[0] = new JLabel("Cutter");
		partLabels[1] = new JLabel("Breakout");
		partLabels[2] = new JLabel("Manual Breakout");
		partLabels[3] = new JLabel("Drill");
		partLabels[4] = new JLabel("Cross Seamer");
		partLabels[5] = new JLabel("Grinder");
		partLabels[6] = new JLabel("Washer");
		partLabels[7] = new JLabel("Painter");
		partLabels[8] = new JLabel("UV Light");
		partLabels[9] = new JLabel("Oven");
		
		for(int i = 0; i < 10; i++) {
			partCheckBoxes[i] = new JCheckBox();
			partPanel[i] = new JPanel();
			partPanel[i].add(partLabels[i]);
			partPanel[i].add(partCheckBoxes[i]);
			partPanel[i].setBackground(new Color(238,238,238));
		}
		
		checkBoxPanel.setLayout(new GridLayout(1,3));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < 5; i++) {
			leftPanel.add(partPanel[i]);
			midPanel.add(partPanel[i+5]);
		}
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
		
		quantityPanel.add(quantityLabel);
		quantityPanel.add(quantityField);
		quantityPanel.add(acceptButton);
		
		//adding
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
}
