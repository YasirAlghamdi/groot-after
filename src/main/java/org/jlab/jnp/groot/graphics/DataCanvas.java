/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.jnp.groot.graphics;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import org.jfree.pdf.PDFDocument;
import org.jfree.pdf.PDFGraphics2D;
import org.jfree.pdf.Page;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.math.F1D;
import org.jlab.jnp.graphics.attr.AttributeCollection;
import org.jlab.jnp.graphics.attr.AttributeDialog;
import org.jlab.jnp.graphics.attr.AttributeType;
import org.jlab.jnp.graphics.base.Background2D;
import org.jlab.jnp.graphics.base.Canvas2D;
import org.jlab.jnp.graphics.base.Node2D;
import org.jlab.jnp.graphics.base.NodeRegion2D;
import org.jlab.jnp.graphics.base.PopupProvider;

/**
 *
 * @author gavalian
 */
public class DataCanvas extends Canvas2D {
    
    
    private DataCanvasProduct dataCanvasProduct = new DataCanvasProduct();
	private AttributeCollection   attributes = null;
    public DataCanvas(){
        super();
        //this.addNode(new GraphicsAxis());
        PopupProvider popup = new PopupProvider();
        this.setPopupProvider(popup);
        attributes = new AttributeCollection(new AttributeType[]{},new String[]{});
        Background2D back = Background2D.createBackground(255, 255, 255);
        setBackground(back);
    }
    
    public void initBackground(int red, int green, int blue){
        Background2D back = Background2D.createBackground(red,green,blue);
        setBackground(back);
    }
    
    public void setPadInsets(int t, int l, int b, int r){
        for(Node2D item : this.getGraphicsComponents()){
            item.getInsets().set(t, l, b, r);
        }
    }

    public void setAxisFont(String fontname, int fontsize, int fontface){
        for(Node2D item : this.getGraphicsComponents()){
            DataRegion region = (DataRegion) item;
            region.setAxisFont(fontname, fontsize, fontface);
        }
        repaint();
    }
    
    public void setAxisLimits(double xmin, double xmax, double ymin, double ymax){
        dataCanvasProduct.setAxisLimits(xmin, xmax, ymin, ymax, this);
    }
    
    public void setAxisLimits(boolean automatic){
        dataCanvasProduct.setAxisLimits(automatic, this);
    }
    
    public void setAxisTitleFont(String fontname, int fontsize, int fontface){
        dataCanvasProduct.setAxisTitleFont(fontname, fontsize, fontface, this);
    }
    public DataCanvas left(int offset){
        return dataCanvasProduct.left(offset, this);
    }
    
    
    public DataCanvas setAxisTicks(int ticks, String axis){
        return dataCanvasProduct.setAxisTicks(ticks, axis, this);
    }
    
    public DataCanvas setAxisTitleOffset(int offset, String axis){
        return dataCanvasProduct.setAxisTitleOffset(offset, axis, this);
    }
    
    public DataCanvas right(int offset){
        return dataCanvasProduct.right(offset, this);
    }
    
    public DataCanvas top(int offset){
        return dataCanvasProduct.top(offset, this);
    }
    
    public DataCanvas bottom(int offset){
        return dataCanvasProduct.bottom(offset, this);
    }
    
    public DataCanvas cd(int region){
        return dataCanvasProduct.cd(region, this);
    }
    
    public DataCanvas draw(IDataSet ds){
        return dataCanvasProduct.draw(ds, this);
    }
    
    public DataCanvas setAxisFontSize(int size){
        return dataCanvasProduct.setAxisFontSize(size, this);
    }
    
    public DataCanvas setAxisTitleFontSize(int size){
        return dataCanvasProduct.setAxisTitleFontSize(size, this);
    }
    
    public DataCanvas setAxisTitleOffsetX(Integer offset){
        return dataCanvasProduct.setAxisTitleOffsetX(offset, this);
    }
    
    
    public DataCanvas addLegend(Legend leg){
        return dataCanvasProduct.addLegend(leg, this);
    }
    
    public DataCanvas setAxisTitleOffsetY(Integer offset){
        return dataCanvasProduct.setAxisTitleOffsetY(offset, this);
    }
    
    public DataCanvas draw(IDataSet ds, String options){
        return dataCanvasProduct.draw(ds, options, this);
    }
    
    
    
    public DataRegion getRegion(int region){
        return dataCanvasProduct.getRegion(region, this);
    }
    
    public void divide(double left, double bottom, int cols, int rows){
        this.getGraphicsComponents().clear();
        for(int i = 0; i < cols*rows; i++){
            DataRegion pad = new DataRegion("canvas_pad_"+i);
            this.addNode(pad);
        }
        this.arrangeWithGap(left, bottom, cols, rows);
    }
    
    @Override
    public void divide(int cols, int rows){
        this.getGraphicsComponents().clear();
        for(int i = 0; i < cols*rows; i++){
            DataRegion pad = new DataRegion("canvas_pad_"+i);
            this.addNode(pad);
        }
        this.arrange(cols, rows);
    }
    
    public void divide(double[][] fractions){
        int ncolumns = fractions.length;
        int size = 0;
        for(int i = 0; i < ncolumns; i++) size += fractions[i].length;
        System.out.println("DIVIDING CANVAS (cols): " + ncolumns + " TOTAL SIZE = " + size);
        this.getGraphicsComponents().clear();
        for(int i = 0; i < size; i++)  addNode(new DataRegion("canvas_pad_"+i));
        arrange(fractions);
    }
    
    public void editAttributes(){
        dataCanvasProduct.editAttributes(this);
    }
    
    public void export(String filename){
        PDFDocument pdfDoc = new PDFDocument();
            Page page = pdfDoc.createPage(new Rectangle(this.getSize().width, this.getSize().height));
            PDFGraphics2D g2 = page.getGraphics2D();
            this.paint(g2);
            pdfDoc.writeToFile(new File(filename));
    }
}
