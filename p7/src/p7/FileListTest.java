//15819013 Yuta Irisawa
package p7;
import java.io.*;

public class FileListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File(args[0]);
		File [] files = dir.listFiles();
		for(File file : files)
			System.out.println(file.toString());
	}

}
