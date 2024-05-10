package org.jlab.groot.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jlab.groot.base.AxisAttributes.AxisAttributesPane;
import org.jlab.groot.base.DatasetAttributes.DatasetAttributesPane;
import org.jlab.groot.base.FontProperties;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.graphics.Histogram2DPlotter;
import org.jlab.groot.graphics.IDataSetPlotter;

public class OptionsPanel extends JPanel {
	int pad;
	EmbeddedCanvas can = null;
	JTabbedPane tabbedPane = null;
	JTabbedPane datasetPanes = null;
	public OptionsPanel(EmbeddedCanvas can, int pad){
		this.can = can;
		this.pad = pad;
		tabbedPane = new JTabbedPane();
		this.setLayout(new BorderLayout());
		this.add(tabbedPane,BorderLayout.CENTER);
		initMain();
		initAxes();
		initDatasets();
	}
	
	private void initMain() {
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		main.setMinimumSize(new Dimension(200,300));
		GridBagConstraints c = new GridBagConstraints();
		//main.add(new JLabel("Main Panel"));
		int yGrid = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridy = yGrid++;
		c.weightx = 1.0;
		c.weighty = 1.0;
		JPanel frameOptions = new JPanel();
		initFrameOptions(frameOptions);
		JPanel generalOptions = new JPanel();
		can.initAxisOptions(generalOptions, pad);
		main.add(generalOptions,c);
		c.gridy = yGrid++;
		c.weightx = 1.0;
		c.weighty = 1.0;
		main.add(frameOptions,c);
		tabbedPane.add("Main", main);
	}
	protected void applyToAll() {
		// TODO Auto-generated method stub
		
	}

	private void initFrameOptions(JPanel frameOptions) {
		frameOptions.setBorder(new TitledBorder("Frame Options"));
		frameOptions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
      
        JTextField xPixels = new JTextField(4);
        JTextField yPixels = new JTextField(4);
        JTextField ratio = new JTextField(4);
        JCheckBox lockRatio = new JCheckBox("Lock Ratio");
        double width = can.getSize().getWidth();
        double height = can.getSize().getHeight();
        xPixels.setText(String.format("%d",(int) width));
        yPixels.setText(String.format("%d",(int) height));
        ratio.setText(String.format("%.02f",width/height));
        c.gridy = 0;
        c.weightx = 1.0;
        frameOptions.add(new JLabel("Width:"), c);
        frameOptions.add(xPixels, c);        
        frameOptions.add(new JLabel("Height:"), c);
        frameOptions.add(yPixels, c);
        xPixels.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		can.setPreferredSize(new Dimension(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText())));
        		can.setSize(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText()));
        		  double width = can.getSize().getWidth();
        	        double height = can.getSize().getHeight();
        	        xPixels.setText(String.format("%d",(int)width));
        	        yPixels.setText(String.format("%d",(int)height));
        	        ratio.setText(String.format("%.02f",width/height));
        	        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(can);
        	        topFrame.pack(); 
        	        can.update();
        	}
        });
        
        yPixels.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		can.setPreferredSize(new Dimension(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText())));
        		can.setSize(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText()));
        		  double width = can.getSize().getWidth();
        	        double height = can.getSize().getHeight();
        	        xPixels.setText(String.format("%d",(int)width));
        	        yPixels.setText(String.format("%d",(int)height));
        	        ratio.setText(String.format("%.02f",width/height));
        	        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(can);
        	        topFrame.pack();  
        	        can.update();
        	}
        });
        c.gridy = 1;
        frameOptions.add(new JLabel("Aspect Ratio:"), c);
        frameOptions.add(ratio, c);
        frameOptions.add(lockRatio, c);
        can.addComponentListener(new ComponentListener(){
			@Override
			public void componentResized(ComponentEvent e){
        		  	double width = can.getSize().getWidth();
        	        double height = can.getSize().getHeight();
        	        xPixels.setText(String.format("%d",(int)width));
        	        yPixels.setText(String.format("%d",(int)height));
        	        ratio.setText(String.format("%.02f",width/height));
        	       // canvas.setPreferredSize(new Dimension(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText())));
            		//canvas.setSize(Integer.parseInt(xPixels.getText()),Integer.parseInt(yPixels.getText()));
        	        //JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(canvas);
        	        //topFrame.pack(); 
        	        can.update();
        	    
        	}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
        });
        
        //axisOptions.add(frameOptions,BorderLayout.CENTER);
	}

	
	private void initAxes() {
		JTabbedPane axes = new JTabbedPane();
		AxisAttributesPane panex = new AxisAttributesPane(can.getPad(pad).getAxisFrame().getAxisX().getAttributes());
		AxisAttributesPane paney = new AxisAttributesPane(can.getPad(pad).getAxisFrame().getAxisY().getAttributes());
	
		panex.addAttributeListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				can.repaint();
			}
		});
		paney.addAttributeListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				can.repaint();
			}
		});
		axes.add("X Axis", panex);
		axes.add("Y Axis", paney);
		if(can.getPad(pad).getDatasetPlotters().size()>0){
			if(can.getPad(pad).getDatasetPlotters().get(0) instanceof Histogram2DPlotter){
				AxisAttributesPane panez = new AxisAttributesPane(can.getPad(pad).getAxisFrame().getAxisZ().getAttributes());
				panez.addAttributeListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						can.repaint();
					}
				});
				axes.add("Z Axis", panez);
			}
		}
		tabbedPane.add("Axes",axes);
	}
	
	private void initDatasets() {
		List<IDataSetPlotter> datasets = can.getPad(pad).getDatasetPlotters();
		ArrayList<DatasetAttributesPane> datasetPaneList = new ArrayList<DatasetAttributesPane>();
		datasetPanes = new JTabbedPane();
		for(IDataSetPlotter dataset : datasets){
			DatasetAttributesPane tempPane = new DatasetAttributesPane(dataset.getDataSet().getAttributes());
			tempPane.addAttributeListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					can.repaint();
				}
			});
			datasetPaneList.add(tempPane);
			datasetPanes.add(dataset.getName(),tempPane);
			ActionListener removeButtonListener = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=0; i<datasetPaneList.size(); i++){
						if(e.getSource().equals(datasetPaneList.get(i).buttonRemove)){
							int tabSelectionIndex = datasetPanes.getSelectedIndex();
							datasetPanes.setSelectedIndex((tabSelectionIndex>=1)?tabSelectionIndex-1:0);
							datasetPanes.remove(i);
							datasetPaneList.remove(i);
							datasetPanes.repaint();
							can.getPad(pad).remove(datasets.get(i).getDataSet());
							break;
						}
					}
					can.update();
				}
				
			};
			
			ActionListener defaultButtonListener  = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=0; i<datasetPaneList.size(); i++){
						if(e.getSource().equals(datasetPaneList.get(i).buttonDefault)){
							int tabSelectionIndex = datasetPanes.getSelectedIndex();
							datasets.get(i).getDataSet().getAttributes().setDefault();
							DatasetAttributesPane pane = new DatasetAttributesPane(datasets.get(i).getDataSet().getAttributes());
							pane.buttonRemove.addActionListener(removeButtonListener);
							pane.buttonDefault.addActionListener(this);
							pane.addAttributeListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent e) {
									can.repaint();
								}
							});
							//datasetPanes.setSelectedIndex((tabSelectionIndex>=1)?tabSelectionIndex-1:0);
							datasetPaneList.remove(i);
							datasetPanes.setComponentAt(i, pane);
							datasetPaneList.add(i,pane);
							datasetPanes.repaint();
							break;
						}
					}
					can.update();
				}
				
			};
			tempPane.buttonRemove.addActionListener(removeButtonListener);
			tempPane.buttonDefault.addActionListener(defaultButtonListener);
			
		}
		tabbedPane.add("Datasets",datasetPanes);
	}
	
}
