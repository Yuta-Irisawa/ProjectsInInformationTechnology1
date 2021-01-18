//15819013 Yuta Irisawa
// 独自メソッドあり(I implemented my own methods)
package p6;

import java.io.*;
import java.util.*;

import java.io.BufferedReader;

public class TextInfo {
	private String source_name;	//variable to store the name of the information source (input file name)
	private int total;		// variable to store the number of words
	private HashMap<String, Integer> word_freq;	// HashMap to store the frequency of each word
	
	// Constructor that takes the name of the information source as its argument
	TextInfo(String fn) {
		// initialize member variable
		source_name = fn;
		total = 0;
		word_freq = new HashMap<String, Integer>();
	}
	
	// Getter method to return the value of source_name
	String getSourceName() {
		return source_name;
	}
	
	// Setter method to set the name of the information source (file name)
	void setSourceName(String s) {
		source_name = s;
	}
	
	// Method to increment the frequency of a word specified by its argument
	private void countFreq(String word) {
		// if HashMap has this argument word, increase the frequency by 1
		if(word_freq.containsKey(word))
			word_freq.put(word, word_freq.get(word) + 1);
		// otherwise, set the frequency to 1
		else
			word_freq.put(word, 1);
	}
	
	// Method to read text from the information source (input file) and count the frequency of each word
	void readFile() {
		// try block is required because BufferedReader throw exception
		try {
			BufferedReader br = new BufferedReader(new FileReader(getSourceName()));
			String line, token[];
			// read line by one line
			while((line = br.readLine()) != null) {
				// separated by space and saved in "token"
				token = line.split("\\s+");
				for(int i = 0; i < token.length; i++) {
					countFreq(token[i]);
					total++;		// "total" stores the number of all words
				}
			}
			br.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	// Method to return the frequency of a word specified by its argument
	int getFreq(String word) {	
		return word_freq.get(word);
	}
	
	// Method to output of the frequency (頻度) of every word
	void writeAllFreq(String outputFilePath) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
			PrintWriter pw = new PrintWriter(bw);
			
			pw.printf("Information source: %s\n",getSourceName());
			for(String key: word_freq.keySet()) {
				pw.printf("Frequency of \"%s\": %d\n", key, getFreq(key));
			}
			
			pw.close();
			bw.close();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	// Return the self-information of the specified word
	double calSelfInfo(String word) {
		// "p" is the probability of appearance
		double p = word_freq.get(word) / (double)total;
		return -(Math.log(p)/Math.log(2));
	}
		
	// Method to output the self-information of every word
	void writeAllSelfInfo(String outputFilePath) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));
			PrintWriter pw = new PrintWriter(bw);
		
			pw.printf("Information source: %s\n",getSourceName());
			for(String key: word_freq.keySet()) {
				pw.printf("Self-Information of \"%s\": %.3f\n", key, calSelfInfo(key));
			}
			pw.printf("Information of \"%s\": %.3f", getSourceName(), calInfo());
			
			pw.close();
			bw.close();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	// Method to return the entropy of the information source
	double calInfo() {
		// "entropy" means 平均情報量
		double entropy = 0;
		for(String key: word_freq.keySet()) {
			// "p" is the probability of appearance
			double p = word_freq.get(key) / (double)total;
			entropy += p * calSelfInfo(key);
		}
		return entropy;
	}
	
	// Method that returns Boolean value that express whether or not a specified word exists as an information source symbol.
	boolean existWord(String word) {
		for(String key: word_freq.keySet()) {
			if(key.equals(word)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("java TextInfo [freq|self-info] [input file path] [output file path]");
			System.exit(-1);
		}
		String type = args[0];
		String inputFilePath = args[1];
		String outputFilePath = args[2];
		TextInfo source = new TextInfo(inputFilePath);
		source.readFile();		
		switch (type) {
		case "freq":
			source.writeAllFreq(outputFilePath);
			break;
		case "self-info":
			source.writeAllSelfInfo(outputFilePath);
			break;
		default:
			System.err.println("java TextInfo [freq|self-info] [input file path] [output file path]");
		}

		/*
		 * If you implement your own functions, uncomment the following two lines, and
		 * make a code to test your own functions under those lines.
		 */
//		 System.out.println("\n Hereafter, the results of my own functions");
//		 System.out.println("=========================================");
//		 
//		 String  confirmExistence = "sunny";
//		 if(source.existWord(confirmExistence) == true) {
//			 System.out.printf("\"%s\" exists in the information source symbol !\n", confirmExistence);
//		 }else {
//			 System.out.printf("\"%s\" doesn't exist in the information source symbol !\n", confirmExistence);
//		 }
	}

}
