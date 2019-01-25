package $P;

import java.util.ArrayList;

//Code was converted and somehwat optimized from the JAVAscript provided on the website

public class Function {
	//Function that returns the shortest ClodDisance between the user input and Template gesture
	public static double GreedyCloudMath(ArrayList<Point> p, PCTemplate pct){
		double e=0.5;
		double step= Math.floor(Math.pow(p.size(), 1-e));
		double min = Double.MAX_VALUE;
		for(int i=0; i<p.size(); i+=step){
			double d1=CloudDistance(p,pct.getPoints(),i);
			double d2=CloudDistance(pct.getPoints(),p,i);
			min = Math.min(min, Math.min(d1, d2));
		}
		return min;
	}
	//Function that returns the distance between the user input points and the Template data points
	public static double CloudDistance(ArrayList<Point> p1, ArrayList<Point>p2, int start){
		boolean[] matched = new boolean[p1.size()];
		double sum = 0;
		int i=start;
		do{
			int index=-1;
			double min=Double.POSITIVE_INFINITY;
			for(int j=0; j<matched.length;j++){
				if(!matched[j]){
					double d=Distance(p1.get(i),p2.get(j));
					if(d < min){
						min =d;
						index =j;
					}
				}
			}
			matched[index] = true;
			double weight = 1-((i-start+p1.size())% p1.size())/p1.size();
			sum += weight*min;
			i=(i+1)%p1.size();
		}while(i !=start);
		return sum;
	}
	// The physically distance between two points
	public static double Distance(Point p1, Point p2){
		double dx = p2.getX() - p1.getX();
		double dy = p2.getY() - p1.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	// The length of the entire given ArrayList of the points
	public static double Length(ArrayList<Point> p){
		double length =0;
		for(int i=1; i<p.size();i++){
			if(p.get(i).getID() == p.get(i-1).getID())
				length+=Distance(p.get(i-1),p.get(i));
		}
		return length;
	}
	//Resample the given arraylist of points into desire length, evenly spaced out
	public static ArrayList<Point> Resample(ArrayList<Point> p, int n){
		double I = Length(p)/(n-1);
		double D = 0.0;
		ArrayList<Point> newPoint = new ArrayList<Point>();
		newPoint.add(p.get(0));

		for (int i=1; i<p.size();i++){
			if(p.get(i).getID()==p.get(i-1).getID()){
				Point p1=p.get(i-1);
				Point p2=p.get(i);
				double d = Distance(p1,p2);
				if((D+d)>=I){
					double qx =p1.getX() +((I-D)/d) * (p2.getX() - p1.getX());
					double qy =p1.getY() +((I-D)/d) * (p2.getY() - p1.getY());
					Point q = new Point(qx,qy,p.get(i).getID());
					newPoint.add(q);
					p.add(i, q);
					D=0.0;
				}
				else D+=d;
			}
		}
		if (newPoint.size() == n - 1)
			newPoint.add(p.get(p.size()-1));
		return newPoint;
	}

	//rescale the points while conserving the shape, so it fits within the bouding box
	public static ArrayList<Point> Scale(ArrayList<Point> points){
		ArrayList<Point> newPoint= new ArrayList<Point>();
		double minX=Double.POSITIVE_INFINITY;
		double minY=Double.POSITIVE_INFINITY;
		double maxX=Double.NEGATIVE_INFINITY;
		double maxY=Double.NEGATIVE_INFINITY;
		for (Point p : points) {
			minX = Math.min(minX, p.getX());
			minY = Math.min(minY, p.getY());
			maxX = Math.max(maxX, p.getX());
			maxY = Math.max(maxY, p.getY());
		}
		double size = Math.max(maxX - minX, maxY - minY);
		for (Point p : points) {
			double qx = (p.getX()-minX)/size;
			double qy = (p.getY()-minY)/size;
			newPoint.add(new Point(qx,qy,p.getID()));
		}
		return newPoint;
	}
	//Translate the points to the Center, or origin
	public static ArrayList<Point> TranslateTo(ArrayList<Point> p, Point pt){
		Point c=Centroid(p);
		ArrayList<Point> newPoint= new ArrayList<Point>();
		for(int i=0; i<p.size();i++){
			double qx = p.get(i).getX()+pt.getX()-c.getX();
			double qy = p.get(i).getY()+pt.getY()-c.getY();
			newPoint.add(new Point(qx,qy,p.get(i).getID()));
		}
		return newPoint;
	}
	public static Point Centroid(ArrayList<Point> p){
		double xsum=0;
		double ysum=0;
		for(int i=0; i<p.size();i++){
			xsum+=p.get(i).getX();
			ysum+=p.get(i).getY();
		}
		xsum/=p.size();
		ysum/=p.size();
		return new Point(xsum,ysum,0);
	}
}
