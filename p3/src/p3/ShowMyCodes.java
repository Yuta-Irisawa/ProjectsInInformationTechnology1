package p3;

//15819013 Yuta Irisawa
public class ShowMyCodes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String name = "Yuta Irisawa";
		/*for Practice 3-6*/
		//StringBuffer name = new StringBuffer("John Smith");
		
		for(int i = 0; i < name.length(); i++)
			System.out.printf("[%c] = %d\n", name.charAt(i), (int)name.charAt(i)); 
		//name.charAt(i):nameオブジェクトのi番目の文字
		//(int)name.charAt(i):nameオブジェクトのi番目の文字をASCIIコードで直したもの
	}

}
