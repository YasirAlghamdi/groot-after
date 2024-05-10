package org.jlab.jnp.groot.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * NiceScale class provides nice labels calculations
 * for graphics axis.
 * @author gavalian
 */

public class NiceScale {
    
    private NiceScaleProduct niceScaleProduct = new NiceScaleProduct();

	public static int[] powers = new int[]{1,10,100,1000,10000,100000,1000000,10000000};
    
    private double range;
    private double niceMax;
    /**
     * Instantiates a new instance of the NiceScale class.
     *
     * @param min the minimum data point on the axis
     * @param max the maximum data point on the axis
     */
    public NiceScale(double min, double max) {
        niceScaleProduct.setMinPoint(min);
        niceScaleProduct.setMaxPoint(max);
        calculate();
    }
    /**
     * Calculate and update values for tick spacing and nice
     * minimum and maximum data points on the axis.
     */
    public void calculate() {
        this.range = niceNum(niceScaleProduct.getMaxPoint() - niceScaleProduct.getMinPoint(), false);
        niceScaleProduct.setTickSpacing(niceNum(range / (niceScaleProduct.getMaxTicks() - 1), true));
        niceScaleProduct.setNiceMin(Math.floor(niceScaleProduct.getMinPoint() / niceScaleProduct.getTickSpacing())
				* niceScaleProduct.getTickSpacing());
        this.niceMax =
                Math.ceil(niceScaleProduct.getMaxPoint() / niceScaleProduct.getTickSpacing()) * niceScaleProduct.getTickSpacing();
    }
    
    public double getSpacing(){
        return this.niceScaleProduct.getTickSpacing();
    }
    /**
     * Returns a "nice" number approximately equal to range Rounds
     * the number if round = true Takes the ceiling if round = false.
     *
     * @param range the data range
     * @param round whether to round the result
     * @return a "nice" number to be used for the data range
     */
    private double niceNum(double range, boolean round) {
        double exponent; /** exponent of range */
        double fraction; /** fractional part of range */
        double niceFraction; /** nice, rounded fraction */
        
        exponent = Math.floor(Math.log10(range));
        fraction = range / Math.pow(10, exponent);
        
        if (round) {
            if (fraction < 1.5)
                niceFraction = 1;
            else if (fraction < 3)
                niceFraction = 2;
            else if (fraction < 7)
                niceFraction = 5;
            else
                niceFraction = 10;
        } else {
            if (fraction <= 1)
                niceFraction = 1;
            else if (fraction <= 2)
                niceFraction = 2;
            else if (fraction <= 5)
                niceFraction = 5;
            else
                niceFraction = 10;
        }
        
        return niceFraction * Math.pow(10, exponent);
    }
    /**
     * Sets the minimum and maximum data points for the axis.
     *
     * @param minPoint the minimum data point on the axis
     * @param maxPoint the maximum data point on the axis
     */
    public void setMinMaxPoints(double minPoint, double maxPoint) {
        niceScaleProduct.setMinMaxPoints(minPoint, maxPoint, this);
    }
    
    /**
     * Sets maximum number of tick marks we're comfortable with
     *
     * @param maxTicks the maximum number of tick marks for the axis
     */
    public void setMaxTicks(double maxTicks) {
        niceScaleProduct.setMaxTicks(maxTicks, this);
    }
    
    public void getTicks(List<Double> ticks){
        ticks.clear();
        for(int i = 0 ; i < this.niceScaleProduct.getMaxTicks()+5; i++){
            //double value = this.minPoint + i*this.tickSpacing;
            double value = niceScaleProduct.getNiceMin() + i*this.niceScaleProduct.getTickSpacing();
            if(value>=this.niceScaleProduct.getMinPoint()&&value<=this.niceScaleProduct.getMaxPoint())
                ticks.add(value);
        }
    }
    public int getNumberOrder(double num){
        
        if(num<10e-32) return 0;
        
        for(int i = 0; i < NiceScale.powers.length; i++){
            int data = ( (int) (num*NiceScale.powers[i]));
            int tail = data%10;
            if(data!=0&&tail==0&&i==0) return 0;
            if(data!=0&&tail==0)       return (i-1);
            /*System.out.printf("oreder = %3d, dive = %9d, data = %5d, tail = %5d\n",
                    i,NiceScale.powers[i],data,tail);*/
        }
        return -1;
    }
    public int getScaleOrder(){
        return niceScaleProduct.getScaleOrder();
    }
    
    public String getOrderString(){return this.niceScaleProduct.getOrderString();}
    
    public void   setOrderString(){
        niceScaleProduct.setOrderString(this);
        
    }
    public static void main(String[] args){
        NiceScaleProduct.main(args);
    }
}
