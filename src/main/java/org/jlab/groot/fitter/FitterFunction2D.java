/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.groot.fitter;

import org.freehep.math.minuit.FCNBase;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.math.Func2D;

/**
 *
 * @author gavalian
 */
public class FitterFunction2D  implements FCNBase {
    private Func2D    function = null;
    private IDataSet  dataset  = null;
    private H2F    datasetH2F  = null;
    private String    fitOptions = "";
    private int       numberOfCalls = 0;
    private long      startTime     = 0L;
    private long      endTime       = 0L;

    
    public FitterFunction2D(Func2D func, IDataSet data,String options){
        dataset    = data;
        function   = func;
        fitOptions = options;
        if(dataset instanceof H2F){
            datasetH2F = (H2F) dataset;
        } else {
            System.out.println(" ERROR : data set has to be H2F");
        }
    }

    public Func2D getFunction(){return function;}

    @Override
    public double valueOf(double[] pars) {
        double chi2 = 0.0;
        function.setParameters(pars);        
        chi2 = function.getChi2(pars,fitOptions, datasetH2F);
        numberOfCalls++;
        this.function.setChiSquare(chi2);
        return chi2;
    }
}
