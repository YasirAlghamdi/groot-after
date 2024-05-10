/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.groot.math;

import org.jlab.groot.data.H2F;

/**
 *
 * @author gavalian
 */
public class Func2D {
    

    private       String          funcName = "f1d";
    private final UserParameters  userPars = new UserParameters();
    private final Dimension1D       rangeX = new Dimension1D();
    private final Dimension1D       rangeY = new Dimension1D();
    
    
    private double      funcChi2       = 0.0;
    private int         funcNDF        = 0;
    
    public Func2D(){
        
    }
    
    public Func2D(String name,int npars, double xmin, double xmax, double ymin, double ymax){
        funcName = name;
        rangeX.setMinMax(xmin, xmax);
        rangeY.setMinMax(ymin, ymax);
        setNParams(npars);
    }
    
    public Func2D(String name, double xmin, double xmax, double ymin, double ymax){
        funcName = name;
        rangeX.setMinMax(xmin, xmax);
        rangeY.setMinMax(ymin, ymax);
    }
    
    public final void setNParams(int nparams){
        userPars.clear();
        for(Integer i = 0; i < nparams; i++){
            String name = "p" + i.toString();
            UserParameter par = new UserParameter();
            par.setName(name);
            userPars.parameters.add(par);
        }
    }
    
    public final UserParameter parameter(int index){
        return this.userPars.getParameter(index);
    }
    
    public boolean inRange(double x, double y){
        if(rangeX.contains(x)==false) return false;
        return rangeY.contains(y);
    }
    
    public double evaluate(double x, double y){
        return 1;
    }
    
    public double getChiSquare(){
        return this.funcChi2;
    }
    
    public int getNDF(){
        return this.funcNDF;
    }
    
    public void setChiSquare(double chi2){
        this.funcChi2 = chi2;
    }
    
    public void setNDF(int ndf){
        this.funcNDF = ndf;
    }
    
    public int getNPars(){
        return this.userPars.getParameters().size();
    }
    
    public void setParameters(double[] pars){
        int npars = userPars.getParameters().size();
        for(int i = 0; i < npars; i++) 
            userPars.getParameters().get(i).setValue(pars[i]);
    }

	public double getChi2(double[] pars, String options, H2F datasetH2F) {
		double chi2 = 0.0;
		int npointsX = datasetH2F.getXAxis().getNBins();
		int npointsY = datasetH2F.getYAxis().getNBins();
		setParameters(pars);
		int ndf = 0;
		for (int npX = 0; npX < npointsX; npX++) {
			for (int npY = 0; npY < npointsY; npY++) {
				double x = datasetH2F.getXAxis().getBinCenter(npX);
				double y = datasetH2F.getYAxis().getBinCenter(npY);
				double z = datasetH2F.getBinContent(npX, npY);
				double zerr = Math.sqrt(Math.abs(z));
				boolean usePoint = true;
				if (inRange(x, y) == true) {
					double zv = evaluate(x, y);
					double normalization = zerr * zerr;
					if (options.contains("N") == true) {
						normalization = Math.abs(z);
					}
					if (options.contains("W") == true) {
						normalization = 1.0;
					}
					if (Math.abs(normalization) > 0.000000000001) {
						chi2 += (zv - z) * (zv - z) / normalization;
						ndf++;
					}
				}
			}
		}
		int npars = getNPars();
		setNDF(ndf - npars);
		return chi2;
	}
}
