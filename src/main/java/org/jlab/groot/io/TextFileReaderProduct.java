package org.jlab.groot.io;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;

public class TextFileReaderProduct {
	private BufferedReader bufferedReader = null;
	private String tokenizer = "\\s+";

	public void setTokenizer(String tokenizer) {
		this.tokenizer = tokenizer;
	}

	/**
	* Open a text file for reading.
	* @param name  name of the file
	* @return  
	*/
	public boolean openFile(String name) {
		try {
			FileReader fileReader = new FileReader(name);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException ex) {
			System.out.println("[TextFileReader] ---> error openning file : " + name);
			this.bufferedReader = null;
			return false;
		}
		return true;
	}

	/**
	* reads the next line in the file and populates the internal list of tokens
	* @return  true if read was successful, false otherwise.
	*/
	public boolean readNext(List<String> thisReadLineTokens) {
		if (this.bufferedReader == null) {
			thisReadLineTokens.clear();
			return false;
		}
		try {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("#") == false)
					break;
			}
			thisReadLineTokens.clear();
			if (line == null) {
				return false;
			}
			String[] tokens = line.trim().split(this.tokenizer);
			for (String token : tokens) {
				thisReadLineTokens.add(token);
			}
		} catch (IOException ex) {
			Logger.getLogger(TextFileReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	public static void main(String[] args) {
		TextFileReader reader = new TextFileReader();
		reader.openFile("/Users/gavalian/Work/Software/Release-9.0/COATJAVA/coatjava/readertest.txt");
		while (reader.readNext() == true) {
			reader.show();
			double[] array = reader.getAsDouble(10, 13);
			for (int i = 0; i < array.length; i++) {
				System.out.print(" " + array[i]);
			}
			System.out.println();
		}
	}
}