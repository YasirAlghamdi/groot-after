package org.jlab.jnp.groot.graphics;


import java.util.List;
import java.util.ArrayList;

public class NiceScaleProduct {
	private double minPoint;
	private double maxPoint;
	private double maxTicks = 10;
	private double tickSpacing;
	private double niceMin;
	private String orderString = "%.2f";

	public double getMinPoint() {
		return minPoint;
	}

	public void setMinPoint(double minPoint) {
		this.minPoint = minPoint;
	}

	public double getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(double maxPoint) {
		this.maxPoint = maxPoint;
	}

	public double getMaxTicks() {
		return maxTicks;
	}

	public double getTickSpacing() {
		return tickSpacing;
	}

	public void setTickSpacing(double tickSpacing) {
		this.tickSpacing = tickSpacing;
	}

	public double getNiceMin() {
		return niceMin;
	}

	public void setNiceMin(double niceMin) {
		this.niceMin = niceMin;
	}

	public String getOrderString() {
		return orderString;
	}

	/**
	* Sets the minimum and maximum data points for the axis.
	* @param minPoint  the minimum data point on the axis
	* @param maxPoint  the maximum data point on the axis
	*/
	public void setMinMaxPoints(double minPoint, double maxPoint, NiceScale niceScale) {
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
		niceScale.calculate();
	}

	/**
	* Sets maximum number of tick marks we're comfortable with
	* @param maxTicks  the maximum number of tick marks for the axis
	*/
	public void setMaxTicks(double maxTicks, NiceScale niceScale) {
		this.maxTicks = maxTicks;
		niceScale.calculate();
	}

	public static void main(String[] args) {
		NiceScale nice = new NiceScale(0, 10000);
		nice.setMinMaxPoints(0, 100000);
		nice.setOrderString();
		System.out.println("Order String = " + nice.getOrderString());
		System.out.println("spacing " + nice.getSpacing() + " ");
		System.out.println("order   " + nice.getScaleOrder());
		System.out.println("num order " + nice.getNumberOrder(100));
		List<Double> ticks = new ArrayList<Double>();
		nice.getTicks(ticks);
		for (int i = 0; i < ticks.size(); i++) {
			System.out.println(" --- " + i + "  value = " + ticks.get(i));
		}
		for (int i = 0; i < 10; i++) {
			double num = 100 * Math.pow(10, -i);
			System.out.printf("%18.8f   -  %d\n", num, nice.getNumberOrder(num));
		}
		System.out.printf("%.0f\n", 100000.0);
		System.out.println("----------------->>>");
		nice.setMinMaxPoints(0, 11744.739990);
		nice.setOrderString();
		System.out.println(nice.getOrderString());
	}

	public void setOrderString(NiceScale niceScale) {
		int order = niceScale.getNumberOrder(this.tickSpacing);
		if (order == 0) {
			int nminorder = niceScale.getNumberOrder(niceMin);
			if (nminorder == 0) {
				orderString = "%.0f";
				return;
			}
		}
		if (order >= 0) {
			orderString = "%." + order + "f";
		} else {
			orderString = "%e";
		}
	}

	public int getScaleOrder() {
		for (int i = 0; i < NiceScale.powers.length; i++) {
			int data = ((int) (this.tickSpacing * NiceScale.powers[i]));
			int tail = data % 10;
			if (data != 0 && tail == 0)
				return (i - 1);
		}
		return -1;
	}
}