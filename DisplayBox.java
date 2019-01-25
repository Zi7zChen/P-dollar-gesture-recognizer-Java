package $P;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


//taken from the $1 Display function, modified for $P
//java canvas, main is here
public class DisplayBox {	
	public static void main(String[] args){
		new DisplayBox().show();
	}
	public void show(){
		//main frame is created
		JFrame frame = new JFrame("$P Display Box");
		Container content = frame.getContentPane();
		//set layout on content pane
		content.setLayout(new BorderLayout());
		//create draw area
		Draw drawArea = new Draw();
		content.add(drawArea, BorderLayout.CENTER);
		//add the jpanel with buttons that recognizes and clears the current gesture
		JPanel panel = new JPanel();
		JButton rButton = new JButton("Recognize");
		JButton cButton = new JButton("clear");
		//perform the respective function of the buttons.
		ActionListener actionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == rButton) {
					drawArea.showResult();
				} else if (e.getSource() == cButton) {
					drawArea.clear();
				}
			}
		};	
		rButton.addActionListener(actionListener);
		cButton.addActionListener(actionListener);
		panel.add(rButton);
		panel.add(cButton);
		//add to content panel
		content.add(panel, BorderLayout.SOUTH);
		frame.setSize(800, 600);
		//allow to close frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//allow the swing paint result
		frame.setVisible(true);
	}
}

class Draw extends JComponent{
	//initialize the variables
	int currentX, currentY, previousX, previousY;
	private ArrayList<Point> p = new ArrayList<Point>();
	Image display;
	Graphics2D graphics2D;
	Recognizer pDollar = new Recognizer();
	public int StrokeCount=0;
	public Draw(){
		setDoubleBuffered(true);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event){
				//When mouse is pressed, starting getting the values of x, y
				previousX = event.getX();
				previousY = event.getY();
				//Indicate the stroke ID
				StrokeCount++;
				p.add(new Point(previousX,previousY,StrokeCount));
			}
		});

		//when the mouse is dragged
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent event){
				//update x,y when the mouse is dragged
				currentX = event.getX();
				currentY = event.getY();
				//store the x,y, and the current stroke ID as a Point
				p.add(new Point(currentX,currentY,StrokeCount));

				if(graphics2D != null){
					//draw line
					graphics2D.drawLine(previousX, previousY, currentX, currentY);
				}
				//update the drawing area
				repaint();
				//store the current x,y as previous, and get ready for next set of x,y
				previousX = currentX;
				previousY = currentY;
			}
		});
	}

	//Show the result of the  on the screen
	public void showResult(){
		String result =pDollar.Recognize(p).getName();
		JOptionPane.showMessageDialog(null, "input: "+result, "Result: ",JOptionPane.INFORMATION_MESSAGE);
		clear();
	}
	protected void paintComponent(Graphics g){
		if (display == null){
			//create the first image
			display = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) display.getGraphics();
			//making the lines smooth
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//clear draw area
			clear();
		}
		g.drawImage(display, 0, 0, null);

	}
	//clear the screen after the result is shown
	public void clear(){
		p.clear();
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.red);
		repaint();
	}
}

