// 15819013 Yuta Irisawa
package p4;

abstract class Shape{	// an abstract class to store base and height of a figure
	double base;
	double height;
	Shape(double b, double h){
		base = b; height = h;
	}
	abstract double getArea(); // an abstract method to be implemented in a subclass
}

class Rectangle extends Shape{	// a subclass of Shape for rectangle
	Rectangle(double b, double h){
		super(b, h);	//Calling the constructor of the superclass Shape
	}
	double getArea() {	// actual implementation of the method getArea()
		return base * height;
	}
}

class Triangle extends Shape{ // a subclass of Shape for triangle
	Triangle(double b, double h){
		super(b, h); // Calling the constructor of the superclass Shape
	}
	double getArea() { // actual implementation of the method getArea()
		return base * height * 0.5;
	}
}
public class AreaTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rectangle rect = new Rectangle(2.0, 5.0);
		Triangle tri = new Triangle(2.0, 5.0);
		System.out.printf("Area of the base %.1f, height %.1f triangle:%.1f\n", rect.base, rect.height, rect.getArea());
		System.out.printf("Area of the base %.1f, height %.1f triangle:%.1f\n", tri.base, tri.height, tri.getArea());
		
		System.out.println("Class: " + rect.getClass() + "Superclass: " + rect.getClass().getSuperclass());
		System.out.println("Class: " + tri.getClass() + "Superclass: " + tri.getClass().getSuperclass());
	}

}
