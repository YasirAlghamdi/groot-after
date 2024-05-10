/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * This class provides a data vector which can be filled with data, and
 * various methods are imeplemented for calculating minimum maximum , mean 
 * and rms for the data. This is essential class for data sets graphs and 
 * histograms.
 */

package org.jlab.groot.data;

import java.util.ArrayList;
import java.util.List;
import org.jlab.jnp.readers.TextFileReader;

/**
 *
 * @author gavalian
 */
public class DataVector {
    
    private DataVectorProduct2 dataVectorProduct2 = new DataVectorProduct2();
	private final ArrayList<Double>  datavec = new ArrayList<Double>();
    public DataVector(int size){
        dataVectorProduct2.setIsFixedLength(true);
        for(int i = 0; i < size; i++){
            datavec.add(0.0);
        }
    }
    
    public DataVector(){
        
    }
    
    public DataVector(double[] data){
        dataVectorProduct2.set(data, this.datavec);
    }
    
    public DataVector(List<Double> data){
        set(data);
    }
    /**
     * Initialize the array with given values.
     * @param data initial data
     */
    public final void set(double[] data){
        dataVectorProduct2.set(data, this.datavec);
    }
    
    public final void set(List<Double> data){
        this.datavec.clear();
        for(Double item : data){
            dataVectorProduct2.add(item, this.datavec);
        }
    }
    
    public int  size(){ return datavec.size();}
    public void clear() { datavec.clear();}
    
    public void addDataVector(DataVector vec){
        dataVectorProduct2.getDataVectorProduct().addDataVector(vec, this.datavec);
    }
    /**
     * Add value to the vector and ensure that the vector is ordered. If the value
     * does not appear to be in ordered mode, then set the flag ordered to FALSE.
     * @param value next value to the data vector.
     */
    public void add(double value) {
        dataVectorProduct2.add(value, this.datavec);
    }
    /**
     * calculate the minimum value in the data.
     * @return minimum of all data points
     */
    public double getMin(){
        if(datavec.size()<1) return 0.0;
        double min = datavec.get(0);
        for(Double value : datavec){
            if(value<min) min = value;
        }
        return min;
    }
    /**
     * calculate maximum value in the data
     * @return maximum value of the data.
     */
    public double getMax(){
        if(datavec.size()<1) return 0.0;
        double max = datavec.get(0);
        for(Double value : datavec){
            if(value>max) max = value;
        }
        return max;
    }
    
    public int  getBinSuggestion(){
        return 100;
    }
    
    public void copy(DataVector vec){
        dataVectorProduct2.getDataVectorProduct().copy(vec, this.datavec, this);
    }
    
    public boolean isOrdered(){ return dataVectorProduct2.getDataVectorProduct().getIsVectorOrdered();}
    
    public int findBin(double value){
        return dataVectorProduct2.getDataVectorProduct().findBin(value,0, this.datavec);
    }
    
    public int findBin(double value, int start){
        return dataVectorProduct2.getDataVectorProduct().findBin(value, start, this.datavec);
    }
    
    /**
     * Multiplies the whole data with the provided number
     * @param norm multiplication factor
     */
    public void mult(double norm){
        dataVectorProduct2.getDataVectorProduct().mult(norm, this.datavec);
    }
    /**
     * Divides the content of the vector by given number.
     * @param norm 
     */
    public void divide(double norm){
        dataVectorProduct2.getDataVectorProduct().divide(norm, this.datavec);
    }
    /**
     * Returns cumulative integral of the vector.
     * @return 
     */
    public DataVector getCumulative(){
        DataVector data = new DataVector();
        double integral = 0.0;
        for(Double value : datavec){
            data.add(integral);
            integral += value;
        }
        return data;
    }
    /**
     * Calculates the normalized mean with the given axis.
     * @param xvec
     * @return 
     */
    public double getMean(DataVector xvec){
        return dataVectorProduct2.getDataVectorProduct().getMean(xvec, this.datavec);
    }
    /**
     * Calculates the mean of the vector. 
     * @return mean value
     */
    public double getMean(){
        if(datavec.size()<1) return 0.0;
        double runsumm = 0.0;
        for(Double value : datavec){
            runsumm += value;
        }
        return (runsumm/datavec.size());
    }
    
    
    public double getRMS(){
        double rms  = 0.0;
        double mean = this.getMean();
        for(Double value : datavec){
            rms += (value-mean)*(value-mean);
        }
        double rms2 = rms/datavec.size();
        return Math.sqrt(rms2);
    }
    
    public double sum(){
        return dataVectorProduct2.getDataVectorProduct().sum(this.datavec);
    }
    
    public String getVectorString(){
        return dataVectorProduct2.getDataVectorProduct().getVectorString(this.datavec);
    }
    /**
     * Returns the number of entries in the vector
     * @return size
     */
    public int  getSize() { return dataVectorProduct2.getDataVectorProduct().getSize(this.datavec);}
    
    public double getValue(int index) {
        return dataVectorProduct2.getDataVectorProduct().getValue(index, this.datavec);
    }
    /**
     * Returns the low edge for the bin which is determined by bin width.
     * @param bin bin number
     * @return the low edge for the bin.
     */
    public double getLowEdge(int bin){
        return dataVectorProduct2.getDataVectorProduct().getLowEdge(bin, this.datavec);
    }
    
    public double getHighEdge(int bin){
        return dataVectorProduct2.getDataVectorProduct().getHighEdge(bin, this.datavec);
    }
    
    public double getBinWidth(int index){
        return dataVectorProduct2.getDataVectorProduct().getBinWidth(index, this.datavec);
    }
    
    
    public double[]  getArray(){
        return dataVectorProduct2.getDataVectorProduct().getArray(this.datavec);
    }
    
    public void set(int index, double value){
        dataVectorProduct2.getDataVectorProduct().set(index, value, this.datavec);
    }
    
    public void setValue(int index, double value){
        dataVectorProduct2.setValue(index, value, this.datavec);
    }
    
    public static List<DataVector> readFile(String format, String filename, int startPosition){
        TextFileReader reader = new TextFileReader();
        reader.open(filename);
        String[] tokens = format.split(":");
        List<DataVector> vectors = new ArrayList<DataVector>();
        
        for(int i = 0; i < tokens.length; i++){
            vectors.add(new DataVector());
        }
        
        while(reader.readNext()==true){
            double[] values = reader.getAsDoubleArray();
            if(values.length>=tokens.length+startPosition){
                for(int i = 0; i < tokens.length; i++){
                    vectors.get(i).add(values[i+startPosition]);
                }
            }
        }
        return vectors;
    }
    
    public static List<DataVector> readFile(String format, String filename){
        return DataVector.readFile(format, filename, 0);
    }
}
