package org.jlab.groot.ui;


import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;
import java.awt.GridBagConstraints;
import java.io.Serializable;

public class CutPanelProductProduct implements Serializable {
	private ArrayList<String> cutStrings;
	private ArrayList<JCheckBox> cutBoxes = new ArrayList<JCheckBox>();
	private JPanel cutOptions = new JPanel();

	public ArrayList<String> getCutStrings() {
		return cutStrings;
	}

	public void setCutStrings(ArrayList<String> cutStrings) {
		this.cutStrings = cutStrings;
	}

	public JPanel getCutOptions() {
		return cutOptions;
	}

	public void initCutOptions(CutPanelProduct cutPanelProduct) {
		if (cutStrings != null && cutStrings.size() > 0) {
			cutOptions.setLayout(new GridBagLayout());
			cutOptions.setBorder(new TitledBorder("Cuts"));
			GridBagConstraints c = new GridBagConstraints();
			int gridy = 0;
			for (int i = 0; i < cutStrings.size(); i++) {
				System.out.println("Cut " + i + " " + cutStrings.get(i));
				cutBoxes.add(new JCheckBox(cutStrings.get(i)));
				cutBoxes.get(i).addActionListener((e) -> {
					cutPanelProduct.validateExpression();
				});
				c.gridy = i;
				cutOptions.add(cutBoxes.get(i), c);
			}
		}
	}
}