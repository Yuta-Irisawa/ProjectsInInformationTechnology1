//15819013 Yuta Irisawa
package p7;

import java.io.*;
import java.util.*;

import java.util.Map.Entry;

//for 7th week
public class WordCount {

	private String sourceName;	//variable to store the name of the information source (input file name)
	private int total;		// variable to store the number of words
	private HashMap<String, Integer> wordFreqMap;	// HashMap to store the frequency of each word
	// for 7th week
	private HashMap<String, Double> tfidfWeight;
	private static HashMap<String, WordCount> sourceWordCountMap = new HashMap<String, WordCount>();
	private static HashMap<String, Integer> docFreqMap = new HashMap<String, Integer>();
	// Ex7-3
	private static Set<String> stopWordsSet = new LinkedHashSet<String>();

	// Initialize sourceName, total, wordFreqMap, and tfidfWeight fields.
	WordCount(String fn) {
		sourceName = fn;
		total = 0;
		wordFreqMap = new HashMap<String, Integer>();
		tfidfWeight = new HashMap<String, Double>();
	}

	// Getter method to return the value of sourceName
	String getSourceName() {
		return sourceName;
	}

	// Setter method to set the name of the information source (file name)
	void setSourceName(String s) {
		sourceName = s;
	}

	// Method to increment the frequency of a word specified by its argument
	private void countFreq(String word) {
		// if HashMap has this argument word, increase the frequency by 1
		if(wordFreqMap.containsKey(word))
			wordFreqMap.put(word, wordFreqMap.get(word) + 1);
		// otherwise, set the frequency to 1
		else
			wordFreqMap.put(word, 1);
	}

	// Method to read text from the information source (input file) and count the frequency of each word
	void readFile() {
		// try block is required because BufferedReader throw exception
		try {
			String FileName = "data\\" + sourceName;
			InputStreamReader isr = new InputStreamReader(new FileInputStream(FileName), "Shift-JIS");
			BufferedReader br = new BufferedReader(isr);
			
			readStopWords();	// Ex7-3
			
			String s, token[];
			while ((s = br.readLine()) != null) {
				if(s.isEmpty())continue;
				// separated by space and saved in "token"
				token = s.split("\\s+");
				for(int i = 0; i < token.length; i++) {
					// Unify all letters with Lowercase (Ex7-3)
					token[i] = token[i].toLowerCase();
					// Do not countFreq if token [i] exists in stopWords (Ex7-3)
					if(stopWordsSet.contains(token[i])) {
						// System.out.println(token[i]);
						continue;
					}
					
					countFreq(token[i]);
					total++;		// "total" stores the number of all words
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	// Method to return the frequency of a word specified by its argument
	int getFreq(String word) {
		return wordFreqMap.get(word);
	}

	// Returns the set of words stored in its instance.
	Set<String> getWordSet() {
		return wordFreqMap.keySet();
	}

	// Writes the top n words in frequency (together with their frequency).
	void writeTopNFrequentWords(int n, String outputFileName) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputFileName), "Shift-JIS");
			BufferedWriter bw = new BufferedWriter(osw);
			PrintWriter pw = new PrintWriter(bw);
			
			readFile();
			
			// Sort by frequency
			List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(wordFreqMap.entrySet());
			Collections.sort(entries, new Comparator<Entry<String, Integer>>(){
				public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2)
				{
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});			
			
			pw.printf("%s\n", sourceName);
			pw.printf("Top %d words in frequency\n", n);
			
			int i = 0;
			for(Entry<String, Integer> entry : entries) {
				pw.printf("%d:%s(%d)\n", i+1, entry.getKey(), entry.getValue()); 
				i++;
				if(i==n) {
					break;
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Set sourceWordCountMap that stores the pairs of a sourceName (key) and the instance of WordCount (value).
	static void setSourceWordCountMap(String inputDirPath) {
		File dir = new File(inputDirPath);
		File [] files = dir.listFiles();
		for(File file : files) {
			WordCount wc = new WordCount(file.getName());
			sourceWordCountMap.put(file.getName(), wc);
		}
	}

	// Calculates the TF-IDF weight of each word stored in its instance. See the slide p.15 for the definition of TF-IDF.
	void calTFIDF() {
		for(String docFreq : wordFreqMap.keySet()) {
			int n_wd = getFreq(docFreq);
			int n_d = total;
			double tf_wd = (double)n_wd/(double)n_d;
			
			int docNum = 5;
			int d_w = docFreqMap.get(docFreq);
			double idf_w = Math.log((double)docNum/(double)d_w);
			
			tfidfWeight.put(docFreq, tf_wd * idf_w);
		}
	}

	// Writes the top n words in TF-IDF weight (together with their TF-IDF weight).
	void writeTopNTfIdfWords(int n, String outputFileName) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputFileName), "Shift-JIS");
			BufferedWriter bw = new BufferedWriter(osw);
			PrintWriter pw = new PrintWriter(bw);
			
			readFile();
			
			// Sort by frequency
			List<Entry<String, Double>> entries = new ArrayList<Entry<String, Double>>(tfidfWeight.entrySet());
			Collections.sort(entries, new Comparator<Entry<String, Double>>(){
				public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2)
				{
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});			
			
			pw.printf("%s\n", sourceName);
			pw.printf("Top %d words in TF-IDF weight\n", n);
			
			int i = 0;
			for(Entry<String, Double> entry : entries) {
				pw.printf("%d:%s(%.3f)\n", i+1, entry.getKey(), entry.getValue()); 
				i++;
				if(i==n) {
					break;
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Set docFreqMap that stores the pairs of a word (key) and the number of documents in which the word appears (value).
	static void setDocFreqMap() {
		for(String sourceName: sourceWordCountMap.keySet()) {
			WordCount wc = sourceWordCountMap.get(sourceName);
			wc.readFile();
			Set<String> wordSet =  wc.getWordSet();
			for(String word: wordSet) {
				// if docFreqMap has this word, increase the frequency by 1
				if(docFreqMap.containsKey(word))
					docFreqMap.put(word, docFreqMap.get(word) + 1);
				// otherwise, set the frequency to 1
				else
					docFreqMap.put(word, 1);
			}
		}
	}
	
	// Set to stopWordsSet (Ex7-3)
	static void setStopWordsSet(String word) {
		stopWordsSet.add(word);
	}
	
	// Read the stop words from stop_words.txt (Ex7-3)
	void readStopWords() {
		// try block is required because BufferedReader throw exception
		try {
			String FileName = "data/stop_words.txt";
			InputStreamReader isr = new InputStreamReader(new FileInputStream(FileName), "Shift-JIS");
			BufferedReader br = new BufferedReader(isr);
			String s;
			while ((s = br.readLine()) != null) {
				setStopWordsSet(s);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("java WordCount [freq|tfidf] [input dir path] [source name]");
			System.exit(-1);
		}
		String type = args[0];
		String inputDirPath = args[1];
		String sourceName = args[2];

		setSourceWordCountMap(inputDirPath);
		setDocFreqMap();
		WordCount wc = sourceWordCountMap.get(sourceName);
//		String outputFileName = type + "_" + sourceName;
		String outputFileName = type+ "_2_" + sourceName;		// Ex7-3

		wc.calTFIDF();
		switch (type) {
		case "freq":
			// for Exercise 7-1
			wc.writeTopNFrequentWords(5, outputFileName);
			break;
		case "tfidf":
			// for Exercise 7-2
			wc.writeTopNTfIdfWords(5, outputFileName);
			break;
		default:
			System.err.println("java WordCount [freq|tfidf] [input dir path] [source name]");
		}
	}

}
