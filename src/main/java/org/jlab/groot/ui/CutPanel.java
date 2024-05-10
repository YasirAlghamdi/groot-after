package org.jlab.groot.ui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.jlab.groot.data.DataVector;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.tree.Tree;
import org.jlab.groot.tree.TreeCut;
import org.jlab.groot.tree.TreeExpression;
import org.jlab.groot.tree.TreeSelector;

public class CutPanel extends JPanel {
	private CutPanelProduct cutPanelProduct = new CutPanelProduct();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EmbeddedCanvas previewCanvas = new EmbeddedCanvas();
	ScrollPane globalCuts = new ScrollPane();

	JTextArea textArea = new JTextArea();
	boolean editMode = false;
	
	private static enum Mode {
		INSERT, COMPLETION
	};
	private Mode mode = Mode.INSERT;
	private static final String COMMIT_ACTION = "commit";

	public CutPanel(Tree tree) {
		cutPanelProduct.setTree(tree);
		cutPanelProduct.setSelector(tree.getSelector());
		cutPanelProduct.setName("New Cut");
		cutPanelProduct.setCutString("");
		cutPanelProduct.setBranches((ArrayList<String>) tree.getListOfBranches());
		cutPanelProduct.init(this);
	}

	public CutPanel(Tree tree, TreeCut cut) {
		cutPanelProduct.setTree(tree);
		cutPanelProduct.setSelector(tree.getSelector());
		cutPanelProduct.setName(cut.getName());
		cutPanelProduct.setCutString(cut.getExpression());
		cutPanelProduct.setBranches((ArrayList<String>) tree.getListOfBranches());
		cutPanelProduct.setCut(cut);
		editMode = true;
		cutPanelProduct.init(this);
		cutPanelProduct.validateExpression();
	}

	public void showPreview(){
		
	}

}
