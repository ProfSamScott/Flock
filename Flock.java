import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;


public class Flock {
	// CONFIGURATION OF THE FLOCK
	int gridSize = 2; // set to 1 for a unified flock, higher for an NxN grid of separate flocks
	double maxSpeed = 3.5; // maximum speed of a single bird
	final Color redBirdColor = new Color(255,50,50);
	final Color birdColor = new Color(50,50,50);
	
	double separationWeight = 1; // separation: higher = less important / less agressive effect 0.3 -- 1
	double alignmentWeight = 2;  // alignment: higher = less important / less agressive effect	1 -- 10
	double cohesionWeight = 4;   // cohesion: higher = less important / less agressive effect	1 -- 10
	double tolerance = 10; // how close they like to get to each other things (in pixels)
	double variation = 0.1; // speed variation: % factor to vary max speeds	0 -- 0.5
	
	int numBirds;
	int maxX, maxY;
	Bird redBird;

	LinkedList<Bird>[][] hashedFlock = new LinkedList[gridSize][gridSize];

	Flock(int numBirds, int maxX, int maxY)
	{
		this.numBirds = numBirds;
		this.maxX = maxX;
		this.maxY = maxY;

		initHash();

		synchronized (hashedFlock)
		{
			redBird = new Bird(maxX, maxY, maxSpeed, redBirdColor, separationWeight, alignmentWeight, cohesionWeight, tolerance, variation);
			addToHash(redBird);
			for (int i=0; i<numBirds-1; i++)
				addToHash(new Bird(maxX, maxY, maxSpeed, birdColor, separationWeight, alignmentWeight, cohesionWeight, tolerance, variation));
		}
	}

	public void setNumBirds(int numBirds)
	{
		this.numBirds = numBirds;
		//System.out.println(numBirds);
	}

	public void setGridSize(int gridSize)
	{
		this.gridSize = gridSize;
	}

	public void redBird()
	{
		redBird.setColor(redBirdColor);
	}
	public void noRedBird()
	{
		redBird.setColor(birdColor);
	}
	public void setSeparation(double separation)
	{
		separationWeight = separation;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setSeparationWeight(separation);
					}
				}
		}
	}
	public void setAlignment(double alignment)
	{
		alignmentWeight = alignment;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setAlignmentWeight(alignment);
					}
				}
		}
	}
	public void setCohesion(double cohesion)
	{
		cohesionWeight = cohesion;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setCohesionWeight(cohesion);
					}
				}
		}
	}
	public void setTolerance(double tolerance)
	{
		this.tolerance = tolerance;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setTolerance(tolerance);
					}
				}
		}
	}

	public void setMaxSpeed(double maxSpeed)
	{
		this.maxSpeed = maxSpeed;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setMaxSpeed(maxSpeed);
					}
				}
		}
	}

	public void setVariation(double variation)
	{
		this.variation = variation;
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<hashedFlock.length; j++)
				{
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						it.next().setVariation(variation, maxSpeed, separationWeight, alignmentWeight, cohesionWeight, tolerance);
					}
				}			
		}
	}
	
	private void initHash()
	{
		synchronized(hashedFlock)
		{
			hashedFlock = new LinkedList[gridSize][gridSize];
			for (int i=0; i<gridSize; i++)
				for (int j=0; j<gridSize; j++)
					hashedFlock[i][j] = new LinkedList<Bird>();
		}
	}

	private void addToHash(Bird b)
	{
		synchronized (hashedFlock)
		{
			hashedFlock[(int)(b.x/maxX*hashedFlock.length)][(int)(b.y/maxY*hashedFlock.length)].add(b);
		}
	}

	void move()
	{
		synchronized(hashedFlock)
		{
			LinkedList<Bird>[][] oldHashedFlock = hashedFlock; // base all calculations on old hash table

			initHash(); // clear out the hash table
			int birdCount = 0, redBirds = 0;

			for (int i=0; i<oldHashedFlock.length; i++)  // process each grid square separately
				for (int j=0; j<oldHashedFlock.length; j++)
				{
					// Get average position and velocity of this grid square
					double totalX = 0, totalY = 0, totalXS = 0, totalYS = 0;
					int numBirdsHere = oldHashedFlock[i][j].size(); // number of birds in this grid square
					Iterator<Bird> it = oldHashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						Bird b = it.next();
						totalX += b.x;
						totalY += b.y;
						totalXS += b.xSpeed;
						totalYS += b.ySpeed;
					}
					double avgX = totalX/numBirdsHere;
					double avgY = totalY/numBirdsHere;
					double avgXS = totalXS/numBirdsHere;
					double avgYS = totalYS/numBirdsHere;
					//System.out.println(avgX);

					// update the other birds' velocities
					it = oldHashedFlock[i][j].iterator();
					while (it.hasNext())
					{
						Bird b = it.next();
						birdCount++;
						if (birdCount < numBirds || b == redBird && redBirds < 1) // if too many birds, stop
						{
							if (b == redBird)
							{
								redBirds++;
								birdCount--;
							}
							if (!b.keepAway(oldHashedFlock[i][j].iterator())) // 1. evade. If evasive action, don't do anythign else
							{
								b.steerWith(avgXS, avgYS);				// 2. steer towards avg flock direction
								b.steerTowards(avgX, avgY);				// 3. steer towards avg flock location
							}
							b.move();		// move the bird
							addToHash(b);	// put it back in the new hash
						}
					}
				}
			// if not enough birds, add extras
			for (int i=birdCount+redBirds; i<numBirds; i++)
				addToHash(new Bird(maxX, maxY, maxSpeed, new Color(50,50,50), separationWeight, alignmentWeight, cohesionWeight, tolerance, variation));

		}
	}

	public void draw(Graphics g)
	{
		//g.setColor(new Color(50,50,50));
		synchronized(hashedFlock)
		{
			for (int i=0; i<hashedFlock.length; i++)
				for (int j=0; j<hashedFlock.length; j++)
				{
					//g.setColor(new Color(i*255/gridSize, j*255/gridSize, 0));
					Iterator<Bird> it = hashedFlock[i][j].iterator();
					while (it.hasNext())
						it.next().draw(g);
				}
		}
	}
}
