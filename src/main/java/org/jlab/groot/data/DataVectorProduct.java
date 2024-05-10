package org.jlab.groot.data;


import java.util.ArrayList;

public class DataVectorProduct {
	private Boolean isVectorOrdered = true;

	public Boolean getIsVectorOrdered() {
		return isVectorOrdered;
	}

	public void setIsVectorOrdered(Boolean isVectorOrdered) {
		this.isVectorOrdered = isVectorOrdered;
	}

	public double getBinWidth(int index, ArrayList<Double> thisDatavec) {
		if (isVectorOrdered == false) {
			System.err.println(
					"DataVector:: ** ERROR ** : this vector is not ordered." + " Bin widths do not make sense.");
			return 0.0;
		}
		return this.getHighEdge(index, thisDatavec) - this.getLowEdge(index, thisDatavec);
	}

	/**
	* Returns the low edge for the bin which is determined by bin width.
	* @param bin  bin number
	* @return  the low edge for the bin.
	*/
	public double getLowEdge(int bin, ArrayList<Double> thisDatavec) {
		if (isVectorOrdered == false) {
			System.err.println(
					"DataVector:: ** ERROR ** : this vector is not ordered." + " Can not define low egde for the bin");
			return 0.0;
		}
		double value = this.getValue(bin, thisDatavec);
		double dist = 0.0;
		if (bin == 0) {
			dist = Math.abs(this.getValue(bin + 1, thisDatavec) - value);
		} else {
			dist = Math.abs(this.getValue(bin - 1, thisDatavec) - value);
		}
		value = value - 0.5 * dist;
		return value;
	}

	public void addDataVector(DataVector vec, ArrayList<Double> thisDatavec) {
		if (vec.getSize() != this.getSize(thisDatavec)) {
			System.out.println("[addDataVector] error adding vectors. sizes are different");
			return;
		}
		for (int i = 0; i < this.getSize(thisDatavec); i++) {
			thisDatavec.set(i, this.getValue(i, thisDatavec) + vec.getValue(i));
		}
	}

	public void copy(DataVector vec, ArrayList<Double> thisDatavec, DataVector dataVector) {
		thisDatavec.clear();
		for (int loop = 0; loop < vec.getSize(); loop++) {
			dataVector.add(vec.getValue(loop));
		}
	}

	/**
	* Multiplies the whole data with the provided number
	* @param norm  multiplication factor
	*/
	public void mult(double norm, ArrayList<Double> thisDatavec) {
		for (int loop = 0; loop < thisDatavec.size(); loop++) {
			double newValue = this.getValue(loop, thisDatavec) * norm;
			thisDatavec.set(loop, newValue);
		}
	}

	public double getValue(int index, ArrayList<Double> thisDatavec) {
		if (index < 0 || index >= thisDatavec.size()) {
			System.err.println("DataVector:: ** ERROR ** : requested element " + index + " in the vector of size = "
					+ thisDatavec.size());
		}
		return thisDatavec.get(index);
	}

	/**
	* Divides the content of the vector by given number.
	* @param norm  
	*/
	public void divide(double norm, ArrayList<Double> thisDatavec) {
		for (int loop = 0; loop < thisDatavec.size(); loop++) {
			double newValue = this.getValue(loop, thisDatavec) / norm;
			thisDatavec.set(loop, newValue);
		}
	}

	/**
	* Calculates the normalized mean with the given axis.
	* @param xvec
	* @return  
	*/
	public double getMean(DataVector xvec, ArrayList<Double> thisDatavec) {
		if (thisDatavec.size() < 1)
			return 0.0;
		if (xvec.getSize() != this.getSize(thisDatavec)) {
			System.err.println("DataVector::getMean: ** ERROR ** : " + " data vectors doe not have the same size.");
			return 0.0;
		}
		if (xvec.isOrdered() == false) {
			System.err.println(
					"DataVector::getMean: ** ERROR ** : " + " the vector passed to the routine is not ordered.");
			return 0.0;
		}
		double runsumm = 0.0;
		int count = 0;
		for (int loop = 0; loop < this.getSize(thisDatavec); loop++) {
			runsumm += this.getValue(loop, thisDatavec) * xvec.getValue(loop);
			count++;
		}
		return runsumm / count;
	}

	/**
	* Returns the number of entries in the vector
	* @return  size
	*/
	public int getSize(ArrayList<Double> thisDatavec) {
		return thisDatavec.size();
	}

	public void set(int index, double value, ArrayList<Double> thisDatavec) {
		if (index >= 0 && index < getSize(thisDatavec)) {
			thisDatavec.set(index, value);
		} else {
			System.out.println("[DataVector] --> warning : vector has size " + getSize(thisDatavec) + ". index=" + index
					+ " is out of bounds.");
		}
	}

	public double getHighEdge(int bin, ArrayList<Double> thisDatavec) {
		if (isVectorOrdered == false) {
			System.err.println(
					"DataVector:: ** ERROR ** : this vector is not ordered." + " Can not define low egde for the bin");
			return 0.0;
		}
		double value = this.getValue(bin, thisDatavec);
		double dist = 0.0;
		if (bin == this.getSize(thisDatavec) - 1) {
			dist = Math.abs(this.getValue(bin - 1, thisDatavec) - value);
		} else {
			dist = Math.abs(this.getValue(bin + 1, thisDatavec) - value);
		}
		value = value + 0.5 * dist;
		return value;
	}

	public int findBin(double value, int start, ArrayList<Double> thisDatavec) {
		if (start >= this.getSize(thisDatavec))
			return -1;
		for (int loop = start; loop < this.getSize(thisDatavec); loop++) {
			if (this.getValue(loop, thisDatavec) > value)
				return loop;
		}
		return -1;
	}

	public double sum(ArrayList<Double> thisDatavec) {
		double s = 0.0;
		for (int i = 0; i < getSize(thisDatavec); i++)
			s += getValue(i, thisDatavec);
		return s;
	}

	public String getVectorString(ArrayList<Double> thisDatavec) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < getSize(thisDatavec); i++) {
			str.append(String.format("%12.6f", getValue(i, thisDatavec)));
		}
		return str.toString();
	}

	public double[] getArray(ArrayList<Double> thisDatavec) {
		double[] array = new double[this.getSize(thisDatavec)];
		for (int loop = 0; loop < this.getSize(thisDatavec); loop++) {
			array[loop] = this.getValue(loop, thisDatavec);
		}
		return array;
	}
}