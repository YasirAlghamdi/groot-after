/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.groot.graphics;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import org.jlab.groot.base.TStyle;
import org.jlab.groot.data.DataLine;

/**
 *
 * @author gavalian
 */
public class DataLinePlotter {
	
    private static final int MIN_ARROW_SIZE = 2;

    public DataLinePlotter() {}
    
    public void draw(Graphics2D g2d, DataLine line, GraphicsAxisFrame frame) {
        int startX = frame.getAxisPointX(line.getOriginX());
        int startY = frame.getAxisPointY(line.getOriginY());
        int endX = frame.getAxisPointX(line.getEndX());
        int endY = frame.getAxisPointY(line.getEndY());
        int lineColor = line.getLineColor();
        
        g2d.setColor(TStyle.getColor(lineColor));
        g2d.setStroke(new BasicStroke(line.getLineWidth()));

        g2d.drawLine(startX, startY, endX, endY);
        drawArrow(g2d, line, startX, startY, endX, endY);
    }

    private void drawArrow(Graphics2D g2d, DataLine line, int startX, int startY, int endX, int endY) {
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowAngleUp = angle + Math.toRadians(line.getArrowAngle());
        double arrowAngleDown = angle - Math.toRadians(line.getArrowAngle());

        if (line.getArrowSizeOrigin() > MIN_ARROW_SIZE) {
            drawArrowHead(g2d, startX, startY, arrowAngleUp, line.getArrowSizeOrigin());
            drawArrowHead(g2d, startX, startY, arrowAngleDown, line.getArrowSizeOrigin());
        }
        drawArrowHead(g2d, endX, endY, -arrowAngleUp, line.getArrowSizeEnd());
        drawArrowHead(g2d, endX, endY, -arrowAngleDown, line.getArrowSizeEnd());
    }

    private void drawArrowHead(Graphics2D g2d, int x, int y, double angle, double size) {
        double offsetX = Math.cos(angle) * size;
        double offsetY = Math.sin(angle) * size;
        g2d.drawLine(x, y, (int) (x + offsetX), (int) (y + offsetY));
    }
}
