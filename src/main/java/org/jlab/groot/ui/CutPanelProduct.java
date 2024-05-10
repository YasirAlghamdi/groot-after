package org.jlab.groot.ui;


import org.jlab.groot.tree.TreeCut;
import org.jlab.groot.tree.Tree;
import org.jlab.groot.tree.TreeSelector;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridBagConstraints;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.io.Serializable;

public class CutPanelProduct implements Serializable {
	private TreeCut cut = null;
	private Tree tree;
	private TreeSelector selector;
	private String name = "";
	private String cutString = "";
	private ArrayList<String> branches;
	private int iconSizeX = 15;
	private int iconSizeY = 15;
	private ImageIcon checkIcon = new ImageIcon();
	private ImageIcon xIcon = new ImageIcon();
	private JLabel validationPlaceHolder = null;
	private JComboBox branchComboBox = new JComboBox();
	private JTextField cutNameTextField = new JTextField();
	private JTextField cutTextArea = new JTextField();
	private JTextField cutValueTextField = new JTextField();
	private JComboBox cutOperator = new JComboBox();
	private String[] operators = { "<", ">", ">=", "<=", "==" };
	private JComboBox branchVariableSelector = new JComboBox();
	private ArrayList<String> cutStrings;
	private ArrayList<JCheckBox> cutBoxes = new ArrayList<JCheckBox>();
	private Map<String, TreeCut> cutMap;
	private JPanel cutOptions = new JPanel();
	private JPanel leftPanel = new JPanel();

	public void setCut(TreeCut cut) {
		this.cut = cut;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void setSelector(TreeSelector selector) {
		this.selector = selector;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCutString(String cutString) {
		this.cutString = cutString;
	}

	public void setBranches(ArrayList<String> branches) {
		this.branches = branches;
	}

	public void initCutOptions() {
		if (cutStrings != null && cutStrings.size() > 0) {
			cutOptions.setLayout(new GridBagLayout());
			cutOptions.setBorder(new TitledBorder("Cuts"));
			GridBagConstraints c = new GridBagConstraints();
			int gridy = 0;
			for (int i = 0; i < cutStrings.size(); i++) {
				System.out.println("Cut " + i + " " + cutStrings.get(i));
				cutBoxes.add(new JCheckBox(cutStrings.get(i)));
				cutBoxes.get(i).addActionListener((e) -> {
					this.validateExpression();
				});
				c.gridy = i;
				cutOptions.add(cutBoxes.get(i), c);
			}
		}
	}

	public void validateExpression() {
		boolean passed = TreeCut.validateExpression(this.cutTextArea.getText(), this.tree.getListOfBranches());
		if (!passed) {
			validationPlaceHolder.setIcon(xIcon);
			validationPlaceHolder.repaint();
		} else {
			validationPlaceHolder.setIcon(checkIcon);
			validationPlaceHolder.repaint();
		}
	}

	@SuppressWarnings("unchecked")
	public void init(CutPanel cutPanel) {
		try {
			Image checkImage = ImageIO.read(Tree.class.getClassLoader().getResource("icons/general/checkmark.png"));
			Image xImage = ImageIO.read(Tree.class.getClassLoader().getResource("icons/general/xmark.png"));
			checkIcon = new ImageIcon(checkImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
			xIcon = new ImageIcon(xImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
		} catch (Exception e) {
		}
		cutMap = tree.getSelector().getSelectorCuts();
		cutStrings = new ArrayList<String>();
		Object[] keys = cutMap.keySet().toArray();
		Object[] treeCuts = cutMap.values().toArray();
		for (int i = 0; i < cutMap.keySet().size(); i++) {
			cutStrings.add((String) keys[i]);
		}
		validationPlaceHolder = new JLabel(xIcon);
		cutNameTextField.setText(name);
		for (int i = 0; i < branches.size(); i++) {
			branchVariableSelector.addItem(branches.get(i));
		}
		for (int i = 0; i < operators.length; i++) {
			cutOperator.addItem(operators[i]);
		}
		JButton addCutButton = new JButton("Add to Cut");
		addCutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		cutTextArea.setColumns(20);
		cutTextArea.setText(cutString);
		JButton saveButton = new JButton("Apply");
		leftPanel.setLayout(new GridBagLayout());
		JPanel nameExpressionPanel = new JPanel(new GridBagLayout());
		nameExpressionPanel.setBorder(new TitledBorder("Cut Definition"));
		GridBagConstraints c4 = new GridBagConstraints();
		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.weightx = 1.0;
		c4.weighty = 1.0;
		cutValueTextField.setColumns(6);
		int gridInt = 0;
		c4.gridwidth = 2;
		c4.gridy = gridInt++;
		nameExpressionPanel.add(new JLabel("Cut Name:"), c4);
		c4.gridwidth = 4;
		nameExpressionPanel.add(cutNameTextField, c4);
		branchComboBox = new JComboBox(tree.getListOfBranches().toArray());
		branchComboBox.setMaximumSize(new Dimension(52, branchComboBox.getPreferredSize().height));
		branchComboBox.setPreferredSize(new Dimension(52, branchComboBox.getPreferredSize().height));
		branchComboBox.setMinimumSize(new Dimension(52, branchComboBox.getPreferredSize().height));
		branchComboBox.addActionListener((e) -> {
			cutTextArea.setText(cutTextArea.getText() + branchComboBox.getSelectedItem());
			this.validateExpression();
		});
		c4.gridy = gridInt++;
		c4.fill = GridBagConstraints.HORIZONTAL;
		c4.gridwidth = 2;
		nameExpressionPanel.add(new JLabel("Cut Expression:"), c4);
		c4.gridwidth = 3;
		nameExpressionPanel.add(cutTextArea, c4);
		c4.gridwidth = 1;
		nameExpressionPanel.add(branchComboBox, c4);
		nameExpressionPanel.add(validationPlaceHolder, c4);
		cutTextArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				validateExpression();
			}
		});
		JPanel previewOptions = new JPanel(new GridBagLayout());
		GridBagConstraints cPreview = new GridBagConstraints();
		cPreview.fill = GridBagConstraints.BOTH;
		cPreview.weightx = 1.0;
		cPreview.weighty = 1.0;
		previewOptions.setBorder(new TitledBorder("Preview Options"));
		JCheckBox previewCheckBox = new JCheckBox("Show Preview");
		String[] previewOptionsList = { "Cut Preview", "Lines", "Blue/Red" };
		JComboBox previewOptionBox = new JComboBox(previewOptionsList);
		previewOptions.add(previewCheckBox, cPreview);
		previewOptions.add(previewOptionBox, cPreview);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 0;
		c.gridx = 0;
		leftPanel.add(nameExpressionPanel, c);
		initCutOptions();
		if (cutStrings != null && cutStrings.size() > 0) {
			c.gridy++;
			leftPanel.add(this.cutOptions, c);
		}
		c.gridy++;
		cutPanel.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1.0;
		c2.weighty = 1.0;
		c2.gridwidth = 2;
		c2.gridy = 0;
		cutPanel.add(leftPanel, c2);
		c2.gridx = 0;
		c2.gridwidth = 1;
		c2.gridy++;
		JButton cancelButton = new JButton("Cancel");
		cutPanel.add(cancelButton, c2);
		c2.gridx++;
		cutPanel.add(saveButton, c2);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cut != null) {
					cut.setName(cutNameTextField.getText());
					cut.setExpression(cutTextArea.getText());
				} else {
					selector.addCut(new TreeCut(cutNameTextField.getText(), cutTextArea.getText(), branches));
				}
				SwingUtilities.getWindowAncestor(cutNameTextField).dispose();
			}
		});
	}
}