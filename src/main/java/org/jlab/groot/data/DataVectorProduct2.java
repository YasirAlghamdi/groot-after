package org.jlab.groot.data;


import java.util.ArrayList;

public class DataVectorProduct2 {
	private DataVectorProduct dataVectorProduct = new DataVectorProduct();
	private Boolean isFixedLength = false;

	public DataVectorProduct getDataVectorProduct() {
		return dataVectorProduct;
	}

	public void setIsFixedLength(Boolean isFixedLength) {
		this.isFixedLength = isFixedLength;
	}

	/**
	* Initialize the array with given values.
	* @param data  initial data
	*/
	public final void set(double[] data, ArrayList<Double> thisDatavec) {
		thisDatavec.clear();
		for (int loop = 0; loop < data.length; loop++) {
			if (loop != 0)
				if (data[loop] < data[loop - 1])
					dataVectorProduct.setIsVectorOrdered(false);
			thisDatavec.add(data[loop]);
		}
	}

	/**
	* Add value to the vector and ensure that the vector is ordered. If the value does not appear to be in ordered mode, then set the flag ordered to FALSE.
	* @param value  next value to the data vector.
	*/
	public void add(double value, ArrayList<Double> thisDatavec) {
		if (this.isFixedLength == false) {
			if (dataVectorProduct.getIsVectorOrdered() == true) {
				if (!thisDatavec.isEmpty())
					if (value < thisDatavec.get(thisDatavec.size() - 1))
						dataVectorProduct.setIsVectorOrdered(false);
			}
			thisDatavec.add(value);
		} else {
			System.out.println("[DataVector] error : add function does not work with fixed length vectors");
		}
	}

	public void setValue(int index, double value, ArrayList<Double> thisDatavec) {
		if (this.isFixedLength == true) {
			if (index >= 0 && index < thisDatavec.size()) {
				thisDatavec.set(index, value);
			}
		} else {
			System.out.println("[DataVector] error : setValue works only for fixed length vectors.");
		}
	}
}