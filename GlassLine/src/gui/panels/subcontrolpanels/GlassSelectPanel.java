
package gui.panels.subcontrolpanels;

import java.awt.*;

import gui.panels.ControlPanel;

import javax.swing.*;

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
	JPanel checkBoxPanel = new JPanel();
	JPanel quantityPanel = new JPanel();
	JLabel quantityLabel = new JLabel("Number of pieces:");
	JTextField quantityField = new JTextField(10);
	JButton acceptButton = new JButton("Create");
	JButton newPartButton = new JButton("New Part");
	
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
		}
		
		checkBoxPanel.setLayout(new GridLayout(1,2));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		for(int i = 0; i < 5; i++) {
			leftPanel.add(partPanel[i]);
			rightPanel.add(partPanel[i+5]);
		}
		checkBoxPanel.add(leftPanel);
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
		gbc.ipadx = 400;
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
