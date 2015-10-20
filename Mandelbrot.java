/*
 * Mandelbrot.java
 * 
 * Version: $Id: Mandelbrot.java, v 11.2.1 2014/17/11 23:20:41
 * 
 * Revisions: 
 * 		
 *   	Initial Revision
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * This program implements the Mandelbrot computation and further uses the zoom
 * functionality as required
 * 
 * 
 * @author Sanika Kulkarni
 * @author Yashashree Kulkarni
 */

public class Mandelbrot extends JFrame implements MouseListener {

	private final int MAX = 5000;
	private final static int LENGTH = 800;
	private final double ZOOM = 1000;
	private BufferedImage I;
	private static double zx, zy, cX, cY, tmp;
	private static double x, y, x1, y1, minimumX = 0, minimumY = 0,
			maximumX = LENGTH, maximumY = LENGTH, newX1 = 0, newX2, newY1 = 0,
			newY2, xIncr = 1, yIncr = 1, prevX = 0, prevY = 0;
	private int[] colors = new int[MAX];

	public Mandelbrot() {
		super("Mandelbrot Set");

		initColors();
		setBounds(100, 100, LENGTH, LENGTH);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addMouseListener(this);
	}

	public void createSet() {
		I = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				zx = zy = 0;
				cX = (newX1 - LENGTH) / ZOOM;
				cY = (newY1 - LENGTH) / ZOOM;
				int iter = 0;
				while ((zx * zx + zy * zy < 4) && (iter < MAX - 1)) {
					tmp = zx * zx - zy * zy + cX;
					zy = 2.0 * zx * zy + cY;
					zx = tmp;
					iter++;
				}
				if (iter > 0)
					I.setRGB(x, y, colors[iter]);
				else
					I.setRGB(x, y, iter | (iter << 8));
				repaint();
				newX1 = newX1 + xIncr;

			}
			newX1 = prevX;
			newY1 = newY1 + yIncr;
		}
		newY1 = prevY;
	}

	public void initColors() {
		for (int index = 0; index < MAX; index++) {
			colors[index] = Color.HSBtoRGB(index / 256f, 1, index
					/ (index + 8f));
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(I, 0, 0, this);
	}

	/**
	 * The main program starts here
	 *
	 * @param args
	 *            command line arguments (ignored)
	 */
	public static void main(String[] args) {
		Mandelbrot aMandelbrot = new Mandelbrot();
		aMandelbrot.setVisible(true);
		aMandelbrot.createSet();
	}

	/**
	 * mousePressed() This method gives the values of new x and y.
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			x = e.getX();
			y = e.getY();
		}

	}

	@Override
	/** mouseReleased()   This method gives the values of new
	 *                   maximum and minimum x and y and prints them
	 *                   to give new positions of x and y
	 * 
	 */
	public void mouseReleased(MouseEvent e) {

		x1 = e.getX();
		y1 = e.getY();
		SetMinMax(x, x1, true);
		SetMinMax(y, y1, false);
		System.out.println("Minimum : " + " " + minimumX + " " + minimumY);
		System.out.println("Maximum : " + " " + maximumX + " " + maximumY);
		SetNewXPositions();
		SetNewYPositions();
		createSet();
	}

	/**
	 * SetNewYPositions() This method gives the values of new co-ordinate of y
	 * 
	 * 
	 */
	private void SetNewYPositions() {
		newY1 = (minimumY * yIncr) + prevY;
		prevY = newY1;
		newY2 = maximumY;
		yIncr = (newY2 - newY1) / LENGTH;
	}

	/**
	 * SetNewYPositions() This method gives the values of new co-ordinate of x
	 * 
	 * 
	 */
	public void SetNewXPositions() {
		newX1 = (minimumX * xIncr) + prevX;
		prevX = newX1;
		newX2 = maximumX;
		xIncr = (newX2 - newX1) / LENGTH;

	}

	/**
	 * SetMinMax() This method compares the new values of x and y
	 * 
	 * 
	 * @param double t1 temporary value 1
	 * @param double t2 temporary value 2
	 * @param boolean temp boolean temporary
	 */
	public void SetMinMax(double t1, double t2, boolean temp) {
		if (temp) {
			if (t1 > t2) {
				maximumX = t1;
				minimumX = t2;
			} else {
				maximumX = t2;
				minimumX = t1;
			}
		} else {
			if (t1 > t2) {
				maximumY = t1;
				minimumY = t2;
			} else {
				maximumY = t2;
				minimumY = t1;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}