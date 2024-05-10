package org.jlab.jnp.groot.graphics;


import org.jlab.groot.data.IDataSet;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.math.F1D;
import org.jlab.jnp.graphics.base.Node2D;
import org.jlab.jnp.graphics.attr.AttributeType;
import org.jlab.jnp.graphics.attr.AttributeDialog;
import java.util.Arrays;
import java.awt.Font;
import java.io.Serializable;

public class DataCanvasProduct implements Serializable {
	private int activeRegion = 0;

	public DataCanvas cd(int region, DataCanvas dataCanvas) {
		if (region >= 0 && region < dataCanvas.getGraphicsComponents().size()) {
			activeRegion = region;
		}
		return dataCanvas;
	}

	public void setAxisLimits(double xmin, double xmax, double ymin, double ymax, DataCanvas dataCanvas) {
		getRegion(this.activeRegion, dataCanvas).getGraphicsAxis().setAxisLimits(xmin, xmax, ymin, ymax);
	}

	public void setAxisLimits(boolean automatic, DataCanvas dataCanvas) {
		if (automatic == true) {
			getRegion(this.activeRegion, dataCanvas).getGraphicsAxis().setAxisAutomatic();
		}
	}

	public DataCanvas draw(IDataSet ds, String options, DataCanvas dataCanvas) {
		if (ds instanceof GraphErrors) {
			if (options.contains("same") == false)
				getRegion(activeRegion, dataCanvas).getGraphicsAxis().reset();
			getRegion(activeRegion, dataCanvas).getGraphicsAxis()
					.addDataNode(new GraphNode2D((GraphErrors) ds, options));
		}
		if (ds instanceof H1F) {
			if (options.contains("same") == false)
				getRegion(activeRegion, dataCanvas).getGraphicsAxis().reset();
			getRegion(activeRegion, dataCanvas).getGraphicsAxis().addDataNode(new HistogramNode1D((H1F) ds, options));
		}
		if (ds instanceof H2F) {
			if (options.contains("same") == false)
				getRegion(activeRegion, dataCanvas).getGraphicsAxis().reset();
			getRegion(activeRegion, dataCanvas).getGraphicsAxis().addDataNode(new HistogramNode2D((H2F) ds));
		}
		if (ds instanceof F1D) {
			if (options.contains("same") == false)
				getRegion(activeRegion, dataCanvas).getGraphicsAxis().reset();
			getRegion(activeRegion, dataCanvas).getGraphicsAxis().addDataNode(new FunctionNode1D((F1D) ds));
		}
		return dataCanvas;
	}

	public DataCanvas addLegend(Legend leg, DataCanvas dataCanvas) {
		this.getRegion(this.activeRegion, dataCanvas).addNode(leg);
		return dataCanvas;
	}

	public DataRegion getRegion(int region, DataCanvas dataCanvas) {
		return (DataRegion) dataCanvas.getGraphicsComponents().get(region);
	}

	public void setAxisTitleFont(String fontname, int fontsize, int fontface, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getAxisX().setAxisTitleFont(fontname, fontsize, fontface);
			region.getGraphicsAxis().getAxisY().setAxisTitleFont(fontname, fontsize, fontface);
		}
		dataCanvas.repaint();
	}

	public DataCanvas setAxisTitleOffsetX(Integer offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getAxisX().getAttributes().changeValue(AttributeType.AXISTITLEOFFSET,
					offset.toString());
		}
		return dataCanvas;
	}

	public void editAttributes(DataCanvas dataCanvas) {
		DataRegion pad = (DataRegion) dataCanvas.getGraphicsComponents().get(1);
		AttributeDialog dialog = new AttributeDialog(Arrays.asList(pad.getGraphicsAxis().getAxisX().getAttributes(),
				pad.getGraphicsAxis().getAxisY().getAttributes()));
		dialog.pack();
		dialog.setVisible(true);
	}

	public DataCanvas setAxisTitleOffsetY(Integer offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getAxisY().getAttributes().changeValue(AttributeType.AXISTITLEOFFSET,
					offset.toString());
		}
		return dataCanvas;
	}

	public DataCanvas setAxisFontSize(int size, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			Font fontX = region.getGraphicsAxis().getAxisX().getAxisFont();
			region.getGraphicsAxis().getAxisX().setAxisFont(new Font(fontX.getFontName(), size, fontX.getStyle()));
			Font fontY = region.getGraphicsAxis().getAxisY().getAxisFont();
			region.getGraphicsAxis().getAxisY().setAxisFont(new Font(fontY.getFontName(), size, fontY.getStyle()));
		}
		return dataCanvas;
	}

	public DataCanvas setAxisTitleFontSize(int size, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			Font fontX = region.getGraphicsAxis().getAxisX().getAxisTitleFont();
			region.getGraphicsAxis().getAxisX().setAxisTitleFont(new Font(fontX.getFontName(), size, fontX.getStyle()));
			Font fontY = region.getGraphicsAxis().getAxisY().getAxisTitleFont();
			region.getGraphicsAxis().getAxisY().setAxisTitleFont(new Font(fontY.getFontName(), size, fontY.getStyle()));
		}
		return dataCanvas;
	}

	public DataCanvas left(int offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getInsets().left(offset);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas right(int offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getInsets().right(offset);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas top(int offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getInsets().top(offset);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas bottom(int offset, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().getInsets().bottom(offset);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas setAxisTicks(int ticks, String axis, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().setAxisTicks(ticks, axis);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas setAxisTitleOffset(int offset, String axis, DataCanvas dataCanvas) {
		for (Node2D item : dataCanvas.getGraphicsComponents()) {
			DataRegion region = (DataRegion) item;
			region.getGraphicsAxis().setAxisTitleOffset(offset, axis);
		}
		dataCanvas.repaint();
		return dataCanvas;
	}

	public DataCanvas draw(IDataSet ds, DataCanvas dataCanvas) {
		draw(ds, "", dataCanvas);
		return dataCanvas;
	}
}