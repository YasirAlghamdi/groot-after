package org.jlab.groot.tree;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JFrame;
import javax.swing.tree.TreePath;
import java.io.Serializable;
import javax.swing.JTree;

public class DynamicTreeProduct implements Serializable {
	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel treeModel;

	public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(DefaultMutableTreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
		this.treeModel = treeModel;
	}

	/**
	* Add child to the currently selected node. 
	*/
	public DefaultMutableTreeNode addObject(Object child, JTree thisTree) {
		DefaultMutableTreeNode parentNode = rootNode;
		return addObject(parentNode, child, true, thisTree);
	}

	public static void main(String[] args) {
		JFrame testFrame = new JFrame();
		DynamicTree blah = new DynamicTree("Blah");
		DefaultMutableTreeNode blah1 = blah.addObject("blah1");
		blah.addObject(blah1, "blah2");
		testFrame.add(blah);
		testFrame.pack();
		testFrame.setVisible(true);
	}

	/**
	* Remove all nodes except the root node. 
	*/
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible,
			JTree thisTree) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		if (parent == null) {
			parent = rootNode;
		}
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		if (shouldBeVisible) {
		}
		thisTree.setSelectionPath(new TreePath(childNode.getPath()));
		return childNode;
	}
}