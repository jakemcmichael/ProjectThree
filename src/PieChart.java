import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PieChart extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 500;
	int a;
	int b;
	int c;
	int d;
	int e;
	int f;

	public PieChart(int a, int b, int c, int d, int e, int f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int slice1 = a; // for acting series credit
		int slice2 = b; // for acting movie credit
		int slice3 = c; // for directing series credit
		int slice4 = d; // for directing movie credit
		int slice5 = e; // for producing series credit
		int slice6 = f; // for producing movie credit
		double count = (slice1 + slice2 + slice3 + slice4 + slice5 + slice6);
		double percentage1 = slice1 / count;
		double percentage2 = slice2 / count;
		double percentage3 = slice3 / count;
		double percentage4 = slice4 / count;
		double percentage5 = slice5 / count;
		double percentage6 = slice6 / count;
		double remainderPercent = (100
				- (percentage1 + percentage2 + percentage3 + percentage4 + percentage5 + percentage6));
		double remainder = 360 / remainderPercent;
		Double Slice1 = new Double(360 * percentage1);
		Double Slice2 = new Double(360 * percentage2);
		Double Slice3 = new Double(360 * percentage3);
		Double Slice4 = new Double(360 * percentage4);
		Double Slice5 = new Double(360 * percentage5);
		Double Slice6 = new Double(360 * percentage6);
		int degreeSlice1 = Slice1.intValue();
		int degreeSlice2 = Slice2.intValue();
		int degreeSlice3 = Slice3.intValue();
		int degreeSlice4 = Slice4.intValue();
		int degreeSlice5 = Slice5.intValue();
		int degreeSlice6 = Slice6.intValue();
		if (slice6 != 0) {
			degreeSlice6 += remainder;
		} else if (slice5 != 0) {
			degreeSlice5 += remainder;
		} else if (slice4 != 0) {
			degreeSlice4 += remainder;
		} else if (slice3 != 0) {
			degreeSlice3 += remainder;
		} else if (slice2 != 0) {
			degreeSlice2 += remainder;
		} else if (slice1 != 0) {
			degreeSlice1 += remainder;
		}
		g2d.setColor(Color.blue);
		g2d.fillArc(0, 30, SIZE, SIZE, 0, degreeSlice1);
		g2d.setColor(Color.gray);
		g2d.fillArc(0, 30, SIZE, SIZE, (degreeSlice1), degreeSlice2);
		g2d.setColor(Color.green);
		g2d.fillArc(0, 30, SIZE, SIZE, (degreeSlice1 + degreeSlice2), degreeSlice3);
		g2d.setColor(Color.red);
		g2d.fillArc(0, 30, SIZE, SIZE, (degreeSlice1 + degreeSlice2 + degreeSlice3), degreeSlice4);
		g2d.setColor(Color.magenta);
		g2d.fillArc(0, 30, SIZE, SIZE, (degreeSlice1 + degreeSlice2 + degreeSlice3 + degreeSlice4), degreeSlice5);
		g2d.setColor(Color.CYAN);
		g2d.fillArc(0, 30, SIZE, SIZE, (degreeSlice1 + degreeSlice2 + degreeSlice3 + degreeSlice4 + degreeSlice5),
				degreeSlice6);
		if (slice6 != 0) {
			g2d.drawString("The cyan slice represents the " + f + " series producer credits.", 30, 625);
		}
		if (slice1 != 0) {
			g2d.setColor(Color.blue);
			g2d.drawString("The blue slice represents the " + a + " movie actor credits ", 30, 550);
		}
		if (slice2 != 0) {
			g2d.setColor(Color.gray);
			g2d.drawString("The gray slice represents the " + b + " series actor credits ", 30, 565);
		}
		if (slice3 != 0) {
			g2d.setColor(Color.GREEN);
			g2d.drawString("The green slice represents the " + c + " movie director credits", 30, 580);
		}
		if (slice4 != 0) {
			g2d.setColor(Color.red);
			g2d.drawString("The red slice represents the " + d + " series director credits", 30, 595);
		}
		if (slice5 != 0) {
			g2d.setColor(Color.magenta);
			g2d.drawString("The magenta slice represents the " + e + " movie producer credits", 30,
					610);
		}
		Font hh = new Font(TOOL_TIP_TEXT_KEY, ALLBITS, 24);
		g2d.setColor(Color.black);
		g2d.setFont(hh);
		g2d.drawString("Actor, Producer, and Director pie chart.", 50, 20);
	}
}