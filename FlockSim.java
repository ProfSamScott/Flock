import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class FlockSim extends JPanel implements Runnable {

	// SIZE OF FLOCK
	int numBirds = 50;


	final int pauseDuration = 1000/30;
	Flock flock;

	public FlockSim(int numBirds, int width, int height)
	{
		this.numBirds = numBirds;
		flock = new Flock(numBirds, width, height);
		new Thread(this).start();
	}

	public void run()
	{
		while (true)
		{
			sleep(pauseDuration);
			flock.move();
			repaint();
		}
	}

	public void setNumBirds(int numBirds)
	{
		this.numBirds = numBirds;
		flock.setNumBirds(numBirds);
		firstRun = true;
	}

	public void setGridSize(int gridSize)
	{
		flock.setGridSize(gridSize);
		firstRun = true;

	}

	public void setMaxSpeed(double maxSpeed)
	{
		flock.setMaxSpeed(maxSpeed);
		firstRun = true;

	}

	public void redBird()
	{
		flock.redBird();
		firstRun = true;

	}
	public void noRedBird()
	{
		flock.noRedBird();
		firstRun = true;

	}
	public void setSeparation(double separation)
	{
		flock.setSeparation(separation);
		firstRun = true;

	}
	public void setAlignment(double alignment)
	{
		flock.setAlignment(alignment);
		firstRun = true;

	}
	public void setCohesion(double cohesion)
	{
		flock.setCohesion(cohesion);
		firstRun = true;

	}
	public void setTolerance(double tolerance)
	{
		flock.setTolerance(tolerance);
		firstRun = true;
	}
	public void setVariation(double variation)
	{
		flock.setVariation(variation);
		firstRun = true;
	}	
	private void sleep(int t)
	{
		try
		{
			Thread.sleep(t);
		}
		catch (InterruptedException e)
		{
		}
	}
	boolean firstRun = true;
	long time;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(new Color(180,255,180));
		g.fillRect(1,1,getWidth()-2, getHeight()-2);

		if (firstRun)
		{
			time = System.nanoTime();
			firstRun = false;
		}
		if (System.nanoTime() - time < 2000000000L)
		{
			g.setColor(new Color(50,50,50));
			g.setFont(new Font("SansSerif",Font.BOLD,30));
			g.drawString("Flocking Simulation",5,30);
			g.setFont(new Font("SansSerif",Font.PLAIN,12));
			g.drawString("by sam.scott@sheridancollege.ca",5,64);
			g.setFont(new Font("SansSerif",Font.PLAIN,12));
			g.drawString("http://wikipedia.org/wiki/Flocking_(behavior)",5,47);

		}

		if (flock != null)
			flock.draw(g);
	}

}
