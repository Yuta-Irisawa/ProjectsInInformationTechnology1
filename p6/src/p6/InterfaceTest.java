//15819013 Yuta Irisawa
package p6;

interface In1{				//Definition of interface In1
	int v1 = 10;				//Constant (modifier "public static final" is omitted)
	void showMessage1();	//Abstract method (modifier "public abstract" is omitted)
}

interface In2{
	int v2 = 20;
	void showMessage2();
}

class BaseClass{
	void showMessage() {
		System.out.println("showMessage of BaseClass");
	}
}

class InClass implements In1, In2{	//Implementation of In1
	public void showMessage1() {	//Modifier "public" must be specified
		System.out.println("showMessage of InClass");
	}
	public void showMessage2() {
		System.out.println("showMessage2 of InClass");
	}
}

interface In1ext extends In1, In2{
	int v1ext = 100;
	void showMessage1ext();
}

class InClassExt implements In1ext{
	public void showMessage1() {
		System.out.println("showMessage1 of InClassExt");
	}
	public void showMessage1ext() {
		System.out.println("showMessage1ext of InClassExt");
	}
	public void showMessage2() {
		System.out.println("showMessage2 of InClassExt");
	}
}

public class InterfaceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InClass inObj = new InClass();
		System.out.println("Constant of In1: " + In1.v1);
		inObj.showMessage1();
		System.out.println("Constant of In2: " + In2.v2);
		inObj.showMessage2();
		
		InClassExt inExtObj = new InClassExt();
		System.out.println("Constant v1 of In1ext: " + In1ext.v1);
		System.out.println("Constant v1ext of In1ext: " + In1ext.v1ext);
		inExtObj.showMessage1();
		inExtObj.showMessage1ext();
		
		System.out.println("Constant v2 of In1ext: " + In1ext.v2);
		inExtObj.showMessage2();
		
//		inObj.showMessage();
		
		In1 in1Var = new InClass();		//Reference through a reference type variable of an interface
		in1Var.showMessage1();
		if(in1Var instanceof InClass)
			System.out.println("Instance of InClass");
		else
			System.out.println("Instance of InClassExt");
		in1Var = inExtObj;
		in1Var.showMessage1();
		if(in1Var instanceof InClass)
			System.out.println("Instance of InClass");
		else
			System.out.println("Instance of InClassExt");
	}

}
