import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class Bird {

	// BEHAVIOUR CONFIGURATION
	double separationWeight = 1; // separation: higher = less important / less agressive effect 0.3 -- 1
	double alignmentWeight = 2;  // alignment: higher = less important / less agressive effect	1 -- 10
	double cohesionWeight = 4;   // cohesion: higher = less important / less agressive effect	1 -- 10
	double tolerance = 10; // how close they like to get to each other things (in pixels)
	double variation = 0.1; // speed variation: % factor to vary max speeds	0 -- 0.5

	// STATE VARS
	double x, y, xSpeed, ySpeed;
	int maxX, maxY;
	double maxSpeed;
	Color color;

	Bird(int maxX, int maxY, double maxSpeed, Color color, double separationWeight, double alignmentWeight, double cohesionWeight, double tolerance, double variation)
	{
		this.variation = variation;	
		this.separationWeight = (Math.random()*variation*2-variation)*separationWeight+separationWeight;
		this.alignmentWeight = (Math.random()*variation*2-variation)*alignmentWeight+alignmentWeight;
		this.cohesionWeight = (Math.random()*variation*2-variation)*cohesionWeight+cohesionWeight;
		this.tolerance = (Math.random()*variation*2-variation)*tolerance+tolerance;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxSpeed = (Math.random()*variation*2-variation)*maxSpeed+maxSpeed; // a little bit of random variation for each bird
		this.color = color;
		x = Math.random()*maxX;
		y = Math.random()*maxY;
		xSpeed = Math.random()*100-50;
		ySpeed = Math.random()*100-50;
		setSpeed(Math.random()*maxSpeed);
		setSpeed(maxSpeed);
		//System.out.println(this.maxSpeed);
	}
	void setSpeed(double speed)
	{
		double currentSpeed = Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed);
		ySpeed *= speed / currentSpeed;
		xSpeed *= speed / currentSpeed;
	}

	void setMaxSpeed(double maxSpeed)
	{
		this.maxSpeed = (Math.random()*variation*2-variation)*maxSpeed+maxSpeed; // a little bit of random variation for each bird
	}

	double getSpeed()
	{
		return Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed);
	}

	void move()
	{
		x += xSpeed;
		y += ySpeed;
		if (x<0)
		{
			x-=xSpeed*2;
			//xSpeed*=-1;
		}
		else if (x>=maxX)
		{
			x-=xSpeed*2;
			//xSpeed*=-1;
		}
		if (y<0)
		{
			y-=ySpeed*2;
			//ySpeed *= -1;
		}
		else if (y>=maxY)
		{
			y-=ySpeed*2;
			//ySpeed*=-1;
		}
	}

	void steerTowards(double targetx, double targety)
	{
		if (Math.sqrt((x-targetx)*(x-targetx)+(y-targety)*(y-targety)) > 2) // only go if distance too big
		{
			double newXSpeed = targetx - x;
			double newYSpeed = targety - y;
			double n = Math.sqrt(newXSpeed*newXSpeed+newYSpeed*newYSpeed);
			newXSpeed *= 1/n/cohesionWeight;
			newYSpeed *= 1/n/cohesionWeight;

			//setSpeed(Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed) / (maxX / 10));
			xSpeed += newXSpeed;
			ySpeed += newYSpeed;

			if (getSpeed() > maxSpeed)
				setSpeed(maxSpeed);
		}
	}

	void steerWith(double newXSpeed, double newYSpeed)
	{
		double n = Math.sqrt(newXSpeed*newXSpeed+newYSpeed*newYSpeed);
		newXSpeed *= 1/n/alignmentWeight;
		newYSpeed *= 1/n/alignmentWeight;
		xSpeed += newXSpeed;
		ySpeed += newYSpeed;

		if (getSpeed() > maxSpeed)
			setSpeed(maxSpeed);
	}
	void steerAway(double targetx, double targety, double extraFactor)
	{
		double newXSpeed = x - targetx;
		double newYSpeed = y - targety;
		double n = Math.sqrt(newXSpeed*newXSpeed+newYSpeed*newYSpeed);
		newXSpeed *= 1/n/separationWeight/extraFactor;
		newYSpeed *= 1/n/separationWeight/extraFactor;

		//setSpeed(Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed) / (maxX / 10));
		xSpeed += newXSpeed;
		ySpeed += newYSpeed;

		if (getSpeed() > maxSpeed)
			setSpeed(maxSpeed);
	}

	double distance(Bird b)
	{
		return Math.sqrt((x-b.x)*(x-b.x)+(y-b.y)*(y-b.y));
	}

	boolean keepAway(Iterator<Bird> it)
	{
		boolean ret = false;
		while (it.hasNext())
		{
			Bird b = it.next();
			if (b != this && distance(b) < tolerance)
			{
				steerAway(b.x, b.y, 1);
				ret = true;
			}
		}
		// steer away from the walls
		// -- tolerance is increased by maxSpeed and scaled by separationWeight to give a better sized buffer
		// -- the extrafactor of 1.5 was found to be a good number to "soften" the bird's behaviour
		if (x < tolerance*maxSpeed*separationWeight)
		{
			steerAway(0,y,1.5);
			ret = true;
		}
		if (y < tolerance*maxSpeed*separationWeight)
		{
			steerAway(x,0,1.5);
			ret = true;
		}
		if (x > maxX - tolerance*maxSpeed*separationWeight)
		{
			steerAway(maxX,y,1.5);
			ret = true;
		}
		if (y > maxY - tolerance*maxSpeed*separationWeight)
		{
			steerAway(x,maxY,1.5);
			ret = true;
		}
		return ret;
	}

	public void draw(Graphics g)
	{
		double tailX = (x-xSpeed);
		double tailY = (y-ySpeed);
		g.setColor(color);
		g.drawLine((int)x,(int)y,(int)tailX,(int)tailY);
		g.fillOval((int)x-1, (int)y-1, 3, 3);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param separationWeight the separationWeight to set
	 */
	public void setSeparationWeight(double separationWeight) {
		this.separationWeight = (Math.random()*variation*2-variation)*separationWeight+separationWeight;
	}

	/**
	 * @param alignmentWeight the alignmentWeight to set
	 */
	public void setAlignmentWeight(double alignmentWeight) {
		this.alignmentWeight = (Math.random()*variation*2-variation)*alignmentWeight+alignmentWeight;
	}

	/**
	 * @param cohesionWeight the cohesionWeight to set
	 */
	public void setCohesionWeight(double cohesionWeight) {
		this.cohesionWeight = (Math.random()*variation*2-variation)*cohesionWeight+cohesionWeight;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = (Math.random()*variation*2-variation)*tolerance+tolerance;
	}
	
	public void setVariation(double variation, double maxSpeed, double separationWeight, double alignmentWeight, double cohesionWeight, double tolerance) {
		this.variation = variation;
		this.separationWeight = (Math.random()*variation*2-variation)*separationWeight+separationWeight;
		this.alignmentWeight = (Math.random()*variation*2-variation)*alignmentWeight+alignmentWeight;
		this.cohesionWeight = (Math.random()*variation*2-variation)*cohesionWeight+cohesionWeight;
		this.tolerance = (Math.random()*variation*2-variation)*tolerance+tolerance;
		this.maxSpeed = (Math.random()*variation*2-variation)*maxSpeed+maxSpeed; // a little bit of random variation for each bird
	}
}
