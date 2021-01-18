package p8;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

class TreeNode {
	private String label;
	private String elabel;
	private ArrayList<TreeNode> children;	//再帰的
	
	// コンストラクタ
	TreeNode(String s) {
		label = s;
		children = new ArrayList<TreeNode>();
	}
	
	// セッターとゲッター
	void setLabel(String s) {
		label = s;
	}

	void setElabel(String s) {
		elabel = s;
	}

	String getLabel() {
		return label;
	}

	String getElabel() {
		return elabel;
	}

	int getChildrenNum() {
		return children.size();
	}

	TreeNode getChild(int i) {
		if (0 <= i && i < children.size())
			return children.get(i);
		else
			return null;
	}
	
	// children(TreeNode)にn(TreeNode)を下に加えるメソッド
	void addChild(TreeNode n) {
		children.add(n);
	}
}

// データの属性値を格納するクラス
class DataList {
	private LinkedList<ArrayList<String>> dataList;
	
	// コンストラクタ
	DataList() {
		dataList = new LinkedList<ArrayList<String>>();
	}
	
	// 1行の文字列を空白で区切ってその文字列をdataListに格納する
	void add(String line) {
		String[] token = line.split("\\s+");
		ArrayList<String> lineData = new ArrayList<String>(Arrays.asList(token));
		add(lineData);
	}
	
	// ArrayList<String> dをdataListに格納する
	void add(ArrayList<String> d) {
		dataList.add(d);
	}
	
	// dataListのi番目の要素を取り出すゲッター
	ArrayList<String> get(int i) {
		return dataList.get(i);
	}
	
	// dataListのサイズを返す
	int size() {
		return dataList.size();
	}
}

// 属性を格納するクラス
class AttrList {
	private ArrayList<String> attrList;
	
	// コンストラクタ
	AttrList() {
		attrList = new ArrayList<String>();
	}
	
	// sという1行を空白で分け、attrListに格納していく
	void setAttributes(String s) {
		String[] token = s.split("\\s");
		for (int i = 0; i < token.length; i++)
			add(token[i]);
	}
	
	// sという文字をattrListに格納する
	void add(String s) {
		attrList.add(s);
	}
	
	// attrListのi番目の要素を取り出す
	String get(int i) {
		return attrList.get(i);
	}
	
	// attrListのサイズを返す
	int size() {
		return attrList.size();
	}
	
	// sという文字がattrListの何番目の要素にあるかを返す
	int indexOf(String s) {
		return attrList.indexOf(s);
	}
}

public class SimpleDT {
	private String fileName;
	private AttrList attrList;
	private DataList dataList;
	private TreeNode rootNode;
	
	// コンストラクタ
	SimpleDT(String s) {
		fileName = s;
		attrList = new AttrList();
		dataList = new DataList();
	}
	
	// fileNameという名のファイルから1行ずつ読み込む
	void readFile() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = in.readLine();
			attrList.setAttributes(line);
			while ((line = in.readLine()) != null) {
				dataList.add(line);
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		System.out.printf("read %d records\n", dataList.size());
	}
	
	// 情報利得が1番高い属性でその属性値毎にDataListを作り、その配列を作成する
	DataList[] divideData(DataList dlist, int col, int numOfDivisions) {
		// numOfDivisions個のDataListに分けるためのLinkedListを確保
		DataList[] dividedDataLists = new DataList[numOfDivisions];
		// numOfDivisions個に分ける
		for (int i = 0; i < numOfDivisions; i++)
			dividedDataLists[i] = new DataList();
		// col列でフィルタをかける時のための仮に保存するためのHashMap
		HashMap<String, Integer> attrValueIndexMap = new HashMap<String, Integer>();
		int index;

		for (int i = 0; i < dlist.size(); i++) {
			// 情報利得が1番高い属性の属性値をvalueに保存
			ArrayList<String> data = dlist.get(i);
			String value = data.get(col);
			
			// 情報利得が1番高い属性でフィルタをかけ、その結果をdividedDataListsに属性値毎に格納する
			if (attrValueIndexMap.containsKey(value))
				index = attrValueIndexMap.get(value); 
			else {
				index = attrValueIndexMap.size();
				attrValueIndexMap.put(value, index);
			}
			dividedDataLists[index].add(data);
		}
		
		return dividedDataLists;
	}
	
	// orgAttrListからcol行目の属性を抜き取る
	AttrList deleteAttribute(AttrList orgAttrList, int col) {
		AttrList newAttrList = new AttrList();
		for (int i = 0; i < orgAttrList.size(); i++) {
			if (i == col)
				continue;
			newAttrList.add(orgAttrList.get(i));
		}		
		return newAttrList;
	}

	double log2(double d) {
		return Math.log(d)/Math.log(2);
	}

	double subInfo(DataList dlist, int col, String attrValue) {
		double info = 0.0;
		int attrValuetotal = 0;
		HashMap<String, Integer> classLabelList = new HashMap<String, Integer>();
		// classLabelListにattrValueにおける頻度を入れる
		for(int i=0; i<dlist.size(); i++) {
			if(dlist.get(i).get(col).equals(attrValue)) {
				attrValuetotal ++;
				String classLabel = dlist.get(i).get(dlist.get(i).size() - 1);
				
				if(classLabelList.containsKey(classLabel)) {
					classLabelList.put(classLabel, classLabelList.get(classLabel) + 1); 
				}else {
					classLabelList.put(classLabel, 1);
				}
			}
		}
		// エントロピーの計算
		info = preInfo(attrValuetotal, classLabelList);

		return info;
	}
	
	/// 属性選択後のエントロピーの計算
	double postInfo(DataList dlist, int col, HashMap<String, Integer> attrValueFreqMap) {
		double info = 0.0;
		int size = dlist.size();
		for(String key : attrValueFreqMap.keySet()) {
			double p = (double)attrValueFreqMap.get(key)/size;
			info += p * subInfo(dlist, col, key);
		}
		return info;
	}
	
	// 属性選択前のエントロピーの計算
	double preInfo(int numOfData, HashMap<String, Integer> cDist) {
		double info = 0.0;
		for(String key: cDist.keySet()) {
			double p = cDist.get(key) / (double)numOfData;
			info += - p * log2(p);
		}
		return info;
	}

	String findMajority(HashMap<String, Integer> attrValueFreqMap) {
		int maxFreq = 0;
		String maxKey = null;
		for (String key : attrValueFreqMap.keySet()) {
			if (maxFreq < attrValueFreqMap.get(key)) {
				maxFreq = attrValueFreqMap.get(key);
				maxKey = key;
			}
		}
		return maxKey;
	}
	
	// 属性ごとに属性値の頻度を数えるメソッド
	ArrayList<HashMap<String, Integer>> getAttrValueFreqMapList(DataList dlist, AttrList alist) {
		ArrayList<HashMap<String, Integer>> attrValueFreqMap = new ArrayList<HashMap<String, Integer>>();

		int colLength = dlist.get(0).size();
		int rowLength = dlist.size();
		
		for(int i=0; i<colLength; i++) {
			HashMap<String, Integer> valueFreqMap = new HashMap<String, Integer>();
			for(int j=0; j<rowLength; j++) {
				String attrvalue = dlist.get(j).get(i);
				if(valueFreqMap.containsKey(attrvalue)) {
					valueFreqMap.put(attrvalue, valueFreqMap.get(attrvalue) + 1);
				}else {
					valueFreqMap.put(attrvalue, 1);
				}
			}
			
			// 最後の列はplay or not_playだからそれ以外で行う
			if(i<colLength-1) {
				// 属性値が1のみのものはaddしない
				if(valueFreqMap.size()==1) {
					continue;
				}
			}
			attrValueFreqMap.add(valueFreqMap);
		}
		
		
		return attrValueFreqMap;
	}

	TreeNode makeTree(DataList dlist, AttrList alist) {
		ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dlist, alist); // ex)outlook ->{rain=5, overcast=4, sunny=5}
		DataList[] dividedDataLists;
		AttrList newAttrList;
		String clsName = null;
		double maxGain = 0.0;
		int maxId = -1;
		int col;
		
		if (alist.size() == 1) {
			clsName = findMajority(attrValueFreqMapList.get(0));
			return new TreeNode(clsName);
		}

		// classがplayのみまたはnot_playのみになったら、そのclassNameをTreeNodeの子ノードにする
		if (attrValueFreqMapList.get(alist.size() - 1).size() == 1) {
			for (String key : attrValueFreqMapList.get(alist.size() - 1).keySet())
				clsName = key;
			return new TreeNode(clsName);
		}
		
		double preInfo = preInfo(dlist.size(), attrValueFreqMapList.get(alist.size() - 1));
		System.out.printf("  preInfo: %.3f\n", preInfo);

		for (int i = 0; i < alist.size() - 1; i++) {
			double postInfo = postInfo(dlist, attrList.indexOf(alist.get(i)), attrValueFreqMapList.get(i));
			double gain = preInfo - postInfo;
			System.out.printf("    gain for %s: %.3f\n", alist.get(i), gain);
			// 1番高い情報利得をmaxGainに保持し、その要素をmaxIdに保存
			if (maxGain < gain) {
				maxGain = gain;
				maxId = i;
			}
		}

		if (maxGain == 0.0) {
			clsName = findMajority(attrValueFreqMapList.get(alist.size() - 1));
			return new TreeNode(clsName);
		}
		
		// maxGainの属性のattrListにおけるindexをcolとする
		col = attrList.indexOf(alist.get(maxId));
		
		dividedDataLists = divideData(dlist, col, attrValueFreqMapList.get(maxId).size());
		newAttrList = deleteAttribute(alist, maxId);
		TreeNode newNode = new TreeNode(alist.get(maxId)); //maxGainを持つ属性に子ノードを作る

		for (int i = 0; i < dividedDataLists.length; i++) {
			// 子ノードが0でなければ再帰的にmakeTreeを繰り返す
			if (dividedDataLists[i].size() != 0) {
				TreeNode tmp = makeTree(dividedDataLists[i], newAttrList);
				tmp.setElabel(dividedDataLists[i].get(0).get(col));
				newNode.addChild(tmp);
			}
		}
		return newNode;
	}

	void buildTree() {
		rootNode = makeTree(dataList, attrList);
	}
	
	// 独自で作ったメソッド(指定されたnodeのLabelの属性値を集合Set<String>で返す)
	Set<String> makeWordSet(TreeNode node){
		int index = 0;
		Set<String> wordSet = new LinkedHashSet<String>();

		for(int i=0; i<attrList.size(); i++) {
			String s = attrList.get(i);
			
			if(s==node.getLabel()) {
				index = attrList.indexOf(s);
			}
		}
		
		for(int i=0; i<dataList.size(); i++) {
			if(wordSet.contains(dataList.get(i).get(index))) {
				continue;
			}else {
				wordSet.add(dataList.get(i).get(index));
			}
		}
		
		return wordSet;
	}
	
	void printNode(TreeNode node, int level) {
		Set<String> wordSet = makeWordSet(node);
		Iterator iterator = wordSet.iterator();
		
		// 再帰的処理を試みた
		int N = node.getChildrenNum();
		for(int i=0; i<N; i++) {
			if(level>0) {
				for(int k=0; k<level-1; k++) {
					System.out.print("   ");
				}
				System.out.print("|   ");
			}
			System.out.printf("%s = %s", node.getLabel(), iterator.next());
			if(node.getChild(i).getChildrenNum() == 0) {
				System.out.println(": " + node.getChild(i).getLabel());
			}else {
				System.out.println();
				level++;
				printNode(node.getChild(i), level);
				level--;
			}
		}
		
		// 再帰処理を使わず書いた
//		for(int i=0; i<node.getChildrenNum(); i++) {
//			System.out.printf("%s = %s",node.getLabel(), iterator.next());
//			
//			if(node.getChild(i).getChildrenNum()==0) {
//				System.out.println(": " + node.getChild(i).getLabel());
//			}else {
//				Set<String> wordSet2 = makeWordSet(node.getChild(i));
//				Iterator iterator2 = wordSet2.iterator();
//				
//				for(int j=0; j<node.getChild(i).getChildrenNum(); j++) {
//					System.out.printf("\n|   %s = %s: %s", node.getChild(i).getLabel(), iterator2.next(), node.getChild(i).getChild(j).getLabel());
//				}
//				System.out.println();
//			}
//		}
	}

	void printTree() {
		printNode(rootNode, 0);
	}

	public void testGetAttrValueFreqMapList() {
		ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dataList, attrList);
		for (int i = 0; i < attrValueFreqMapList.size(); i++) {
			String attrName = attrList.get(i);
			HashMap<String, Integer> attrValueFreqMap = attrValueFreqMapList.get(i);
			for (Entry<String, Integer> attrValueFreqEntry : attrValueFreqMap.entrySet()) {
				String attrValue = attrValueFreqEntry.getKey();
				int freq = attrValueFreqEntry.getValue();
				System.out.printf("%s %s %d\n", attrName, attrValue, freq);
			}
		}
	}

	public void testPreInfo() {
		ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dataList, attrList);
		double preInfo = preInfo(dataList.size(), attrValueFreqMapList.get(attrList.size() - 1));
		System.out.printf("Root preInfo %.3f\n", preInfo);
	}

	public void testSubInfo() {
		ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dataList, attrList);
		HashMap<String, Integer> attrValueFreqMap = attrValueFreqMapList.get(0);
		for (String attrValue : attrValueFreqMap.keySet()) {
			double subInfo = subInfo(dataList, 0, attrValue);
			System.out.printf("%s %s subInfo %.3f\n", attrList.get(0), attrValue, subInfo);
		}
	}

	public void testPostInfo() {
		ArrayList<HashMap<String, Integer>> attrValueFreqMapList = getAttrValueFreqMapList(dataList, attrList);
		for (int i = 0; i < attrValueFreqMapList.size() - 1; i++) {
			double postInfo = postInfo(dataList, i, attrValueFreqMapList.get(i));
			System.out.printf("%s postInfo %.3f\n", attrList.get(i), postInfo);
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("java p8.SimpleDT [testGetAttrValueFreqMapList|testPreInfo|testSubInfo|testPostInfo|testPrintTree] [input file path]");
			System.exit(-1);
		}
		String testType = args[0];
		String inputFilePath = args[1];
		SimpleDT dt = new SimpleDT(inputFilePath);
		dt.readFile();

		switch (testType) {
		case "testGetAttrValueFreqMapList":
			dt.testGetAttrValueFreqMapList();
			break;
		case "testPreInfo":
			dt.testPreInfo();
			break;
		case "testSubInfo":
			dt.testSubInfo();
			break;
		case "testPostInfo":
			dt.testPostInfo();
			break;
		case "testPrintTree":
			dt.buildTree();
			dt.printTree();
			break;
		}
	}
}
