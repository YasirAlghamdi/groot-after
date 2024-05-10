package org.jlab.groot.base;


import org.jlab.groot.graphics.GraphicsAxis;
import java.awt.Graphics2D;
import java.awt.Color;
import org.jlab.groot.graphics.GraphicsAxisFrame;
import java.io.Serializable;

public class GraphicsTestsProduct implements Serializable {
	private GraphicsAxis xaxis = new GraphicsAxis(GraphicsAxis.AXISTYPE_HORIZONTAL);
	private GraphicsAxis yaxis = new GraphicsAxis(GraphicsAxis.AXISTYPE_VERTICAL);

	public void drawAxis(Graphics2D g2d, GraphicsTests graphicsTests) {
		int w = graphicsTests.getSize().width;
		int h = graphicsTests.getSize().height;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(Color.BLACK);
		int offsetX = xaxis.getAxisBounds(g2d);
		int offsetY = yaxis.getAxisBounds(g2d);
		System.out.println(" AXIS BOUNDAS = " + offsetX + "  " + offsetY);
		xaxis.setTitle("X-axis");
		xaxis.setDimension(offsetY, w - 40);
		xaxis.setRange(0.0, 1.0);
		yaxis.setVertical(true);
		xaxis.setRange(0.0, 1.0);
		yaxis.setDimension(h - offsetX, 40);
		xaxis.drawAxis(g2d, offsetY, h - offsetX);
		yaxis.drawAxis(g2d, offsetY, h - offsetX);
	}

	public void drawGraphicsFrame(Graphics2D g2d, GraphicsTests graphicsTests) {
		int w = graphicsTests.getSize().width;
		int h = graphicsTests.getSize().height;
		PadMargins customMargins = new PadMargins();
		customMargins.setBottomMargin(60);
		customMargins.setTopMargin(40);
		customMargins.setLeftMargin(70);
		customMargins.setRightMargin(70);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, w, h);
		g2d.setColor(Color.BLACK);
		GraphicsAxisFrame frame = new GraphicsAxisFrame();
		frame.setFrameDimensions(0, w, 0, h);
		frame.getAxisX().setRange(0.0, 1.0);
		frame.getAxisY().setRange(200, 400);
		frame.updateMargins(g2d);
		frame.setAxisMargins(customMargins);
		frame.drawAxis(g2d, customMargins);
	}
}