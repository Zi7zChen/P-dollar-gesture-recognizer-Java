package $P;

import java.util.ArrayList;

//Point Cloud Class, Which represents the collections of the points and what should be the name of the gesture.
public class PCTemplate {
	private String name;
	private ArrayList<Point> points;
	//When the Template is Initialized, perform the resample, scale, and translate function.
	PCTemplate(String name, ArrayList<Point> points, int numP) {
		this.name=name;
		this.points=Function.Resample(points, numP);
		this.points=Function.Scale(this.points);
		this.points=Function.TranslateTo(this.points, Recognizer.origin);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
}

