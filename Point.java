package $P;

//Point class that stores x,y Stroke ID 
public class Point {
	private double x, y;
	private int id;
	public Point(double x, double y, int id){
		this.x=x;
		this.y=y;
		this.id=id;
	}
	public String printing() {
		return "(" + x + "," + y + ") ID: " +id+" ";
	}
	public double getX(){
		return x;
	}
	public void setX(double x){
		this.x=x;
	}
	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y=y;
	}
	public int getID() {
		return id;
	}
}
