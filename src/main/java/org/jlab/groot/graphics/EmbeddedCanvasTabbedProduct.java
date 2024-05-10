package org.jlab.groot.graphics;


import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.imageio.ImageIO;
import org.jlab.groot.tree.Tree;
import java.io.IOException;
import javax.swing.JButton;
import java.io.Serializable;

public class EmbeddedCanvasTabbedProduct implements Serializable {
	private JPanel actionPanel = null;
	private int iconSizeX = 25;
	private int iconSizeY = 25;

	public void initBottomBar(EmbeddedCanvasTabbed embeddedCanvasTabbed) {
		actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout());
		embeddedCanvasTabbed.add(actionPanel, BorderLayout.PAGE_END);
		ImageIcon newTabIcon = new ImageIcon();
		ImageIcon removeTabIcon = new ImageIcon();
		ImageIcon editTabIcon = new ImageIcon();
		ImageIcon clearTabIcon = new ImageIcon();
		try {
			Image addImage = ImageIO.read(Tree.class.getClassLoader().getResource("icons/tree/canvas_add.png"));
			Image clearImage = ImageIO
					.read(Tree.class.getClassLoader().getResource("icons/tree/1477453861_star_full.png"));
			Image deleteImage = ImageIO.read(Tree.class.getClassLoader().getResource("icons/tree/canvas_delete.png"));
			Image editImage = ImageIO
					.read(Tree.class.getClassLoader().getResource("icons/tree/1477454132_calendar.png"));
			newTabIcon.setImage(addImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
			clearTabIcon.setImage(clearImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
			editTabIcon.setImage(editImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
			removeTabIcon.setImage(deleteImage.getScaledInstance(iconSizeX, iconSizeY, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JButton buttonAdd = new JButton(newTabIcon);
		buttonAdd.setActionCommand("add canvas");
		buttonAdd.addActionListener(embeddedCanvasTabbed);
		JButton buttonRemove = new JButton(removeTabIcon);
		buttonRemove.setActionCommand("remove canvas");
		buttonRemove.addActionListener(embeddedCanvasTabbed);
		JButton buttonDivide = new JButton(editTabIcon);
		buttonDivide.setActionCommand("divide");
		buttonDivide.addActionListener(embeddedCanvasTabbed);
		JButton buttonClear = new JButton(clearTabIcon);
		buttonClear.setActionCommand("clear");
		buttonClear.addActionListener(embeddedCanvasTabbed);
		actionPanel.add(buttonAdd);
		actionPanel.add(buttonRemove);
		actionPanel.add(buttonDivide);
		actionPanel.add(buttonClear);
	}
}