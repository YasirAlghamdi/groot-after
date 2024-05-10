package org.jlab.groot.tree;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
 
public class DynamicTree extends JPanel {
    private DynamicTreeProduct dynamicTreeProduct = new DynamicTreeProduct();
	protected JTree tree;
    public JTree getTree() {
		return tree;
	}
    public static void main(String[] args){
    	DynamicTreeProduct.main(args);
    }

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
 
    public DynamicTree(String name) {
        super(new GridLayout(1,0));
         
        dynamicTreeProduct.setRootNode(new DefaultMutableTreeNode(name));
        dynamicTreeProduct.setTreeModel(new DefaultTreeModel(dynamicTreeProduct.getRootNode(), false));
    dynamicTreeProduct.getTreeModel().addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(dynamicTreeProduct.getTreeModel());
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
 
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
 
    /** Remove all nodes except the root node. */
    public void clear() {
        dynamicTreeProduct.clear();
    }
 
    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                dynamicTreeProduct.getTreeModel().removeNodeFromParent(currentNode);
                return;
            }
        } 
 
        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }
 
    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        return dynamicTreeProduct.addObject(child, this.tree);
    }
 
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return dynamicTreeProduct.addObject(parent, child, false, this.tree);
    }
 
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible) {
        return dynamicTreeProduct.addObject(parent, child, shouldBeVisible, this.tree);
    }
 
    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
 
            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */
 
                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode)(node.getChildAt(index));
 
            System.out.println("The user has finished editing the node.");
            System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }

    }
}