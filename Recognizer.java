package $P;

import java.util.ArrayList;

public class Recognizer {
	
	static int numP = 32;
	static Point origin= new Point(0,0,0);
	static ArrayList<PCTemplate> PCTemplates = new ArrayList<PCTemplate>();
	//Constructor that adds the Template data to a ArrayList
	public Recognizer(){
		PCTemplates.add(new PCTemplate("T",PCData.T,numP));
		PCTemplates.add(new PCTemplate("N",PCData.N,numP));
		PCTemplates.add(new PCTemplate("D",PCData.D,numP));
		PCTemplates.add(new PCTemplate("P",PCData.P,numP));
		PCTemplates.add(new PCTemplate("X",PCData.X,numP));
		PCTemplates.add(new PCTemplate("H",PCData.H,numP));
		PCTemplates.add(new PCTemplate("I",PCData.I,numP));
		PCTemplates.add(new PCTemplate("Exclamation",PCData.Exclamation,numP));
		PCTemplates.add(new PCTemplate("Line",PCData.Line,numP));
		PCTemplates.add(new PCTemplate("Five Point Star",PCData.FivePointstar,numP));
		PCTemplates.add(new PCTemplate("Null",PCData.Null,numP));
		PCTemplates.add(new PCTemplate("Arrow Head",PCData.Arrowhead,numP));
		PCTemplates.add(new PCTemplate("Pitch Fork",PCData.Pitchfork,numP));
		PCTemplates.add(new PCTemplate("Six Point Star",PCData.SixPointStar,numP));
		PCTemplates.add(new PCTemplate("Asterisk",PCData.Asterisk,numP));
		PCTemplates.add(new PCTemplate("Half Note",PCData.HalfNote,numP));
	}
	//Recognize method, that resample, scale, translate the user gesture and map it with the Template data
	public PCTemplate Recognize(ArrayList<Point> p){
		p=Function.Resample(p, numP);
		p=Function.Scale(p);
		p=Function.TranslateTo(p,origin);
		double b=Double.POSITIVE_INFINITY;
		PCTemplate result=null;
		//Perfrom the GreedyCloudMath to find the closest matching templates
		for(int i=0; i<PCTemplates.size();i++){
			double d = Function.GreedyCloudMath(p, PCTemplates.get(i));
			if (b > d) {
				b = d;
				result = PCTemplates.get(i);
			}
		}
		//returns the matching template
		return result;
	}
}
