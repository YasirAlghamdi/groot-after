/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jlab.groot.base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jlab.groot.graphics.GraphicsAxis;
import org.jlab.groot.graphics.GraphicsAxisFrame;
import org.jlab.groot.ui.LatexText;
import org.jlab.groot.ui.PaveText;


/**
 *
 * @author gavalian
 */
public class GraphicsTests extends JPanel {
    
    private GraphicsTestsProduct graphicsTestsProduct = new GraphicsTestsProduct();
	/**
	 * 
	 */
	private static final long serialVersionUID = 7128170889587576863L;
	ColorPalette palette = new ColorPalette();
    public GraphicsTests(){
        super();
        this.setPreferredSize(new Dimension(500,500));
        
    }
    
    @Override
    public void paint(Graphics g){ 

        Long st = System.currentTimeMillis();
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint( RenderingHints.  KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint( RenderingHints.  KEY_STROKE_CONTROL,
               RenderingHints.VALUE_STROKE_PURE);
        
        //g2d.setRenderingHint( RenderingHints.  KEY_RENDERING, 
        //        RenderingHints.VALUE_RENDER_QUALITY);
        
        int xT = 100;
        int yT = 200;
        g2d.setColor(Color.red);
        g2d.drawOval(xT-4, yT-4, 8,8);
        g2d.setColor(Color.blue);
        LatexText  text = new LatexText("Rotation Test");
        
        
        text.setFontSize(18);
        
        text.drawString(g2d, xT,yT,LatexText.ALIGN_CENTER,LatexText.ALIGN_TOP,LatexText.ROTATE_LEFT);
        text.drawString(g2d, xT,yT,1,1);
        System.out.println("Pave text definition");
        PaveText pave = new PaveText(2);
        pave.setPosition(300, 300);
        pave.addText("Entries","124500");

        pave.addText("RMS","0.047+/-1.2345");
        for(int i = 0; i < 1; i++){
            pave.addText("Mean","0.567");
            pave.addText("#chi^2/NDF","126.34/57");
        }
        pave.addText("Underflow","3456");
        pave.addText("Overflow","23");
        pave.drawPave(g2d, 200,200);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(200,200, 40, 40);        
        g2d.fillOval(203,203, 34, 34);
        
        WobbleStroke  stroke = new WobbleStroke(1f,6f);
        g2d.setStroke(stroke);
        g2d.drawLine(0, 0, 200, 200);
//this.drawGraphicsFrame(g2d);
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsTests canvas = new GraphicsTests();
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
    
}
