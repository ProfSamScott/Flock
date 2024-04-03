/**
 * This is a demonstration of flocking/swarming in Java.
 * <applet code="FlockApplet.class" width="600" height="480"></applet>
 * February 4, 2007
 *
 * @author Sam Scott
 **/
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.MalformedURLException;
import java.text.DecimalFormat;


public class FlockApplet extends JPanel
{
	FlockSim flockPanel;
	JSlider numBirds, gridSize, maxSpeed, separation, cohesion, alignment, tolerance, variance;
	JLabel numBirdsLabel, gridSizeLabel, maxSpeedLabel, separationLabel, cohesionLabel, alignmentLabel, toleranceLabel, varianceLabel;
	JCheckBox redBirdBox;
	DecimalFormat df = new DecimalFormat("0.0");
	DecimalFormat df2 = new DecimalFormat("0.00");

	/**
	 * Calls makeGUI() to create a new JPanel, then calls
	 * setContentPane to put the JPanel in place on the JApplet
	 *
	 **/
	public void init ()
	{
		JPanel mainPanel = makeGUI ();
		this.setPreferredSize(new Dimension(600,500));
		//setContentPane (mainPanel);
		setBackground(new Color(150,230,150));
	} // init method

	public static void main(String[] args) {
		JFrame frame = new JFrame("Flocking Simulation");
		FlockApplet p = new FlockApplet();
		p.init();
		frame.setContentPane(p);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public class NumBirdsListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			numBirdsLabel.setText (""+numBirds.getValue());
			flockPanel.setNumBirds(numBirds.getValue());
			//showStatus("numBirds = "+numBirds.getValue());
		}
	}

	public class GridSizeListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			gridSizeLabel.setText (""+gridSize.getValue());
			flockPanel.setGridSize(6-gridSize.getValue());
			//showStatus("gridSize = "+(6-gridSize.getValue()));

		}
	}

	public class MaxSpeedListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			maxSpeedLabel.setText (""+maxSpeed.getValue()/10.0);
			flockPanel.setMaxSpeed(maxSpeed.getValue()/10.0);
			//showStatus("maxSpeed = "+maxSpeed.getValue()/10.0);
		}
	}

	public class RedBirdBoxListener implements ActionListener
	{

		public void actionPerformed (ActionEvent e)
		{
			if (redBirdBox.isSelected())
				flockPanel.redBird();
			else
				flockPanel.noRedBird();
		}
	}

	public class SeparationListener implements ChangeListener
	{


		public void stateChanged (ChangeEvent e)
		{
			separationLabel.setText(df.format((separation.getValue()*10/7.8+10)/10.0));
			flockPanel.setSeparation((100-separation.getValue())/100.0);
			//showStatus("separationWeight = "+(100-separation.getValue())/100.0);
		}
	}

	public class AlignmentListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			alignmentLabel.setText (df.format(11-(100-alignment.getValue())/10.0));
			flockPanel.setAlignment((100-alignment.getValue())/10.0);
			//showStatus("alignmentWeight = "+df.format((100-alignment.getValue())/10.0));
		}
	}

	public class CohesionListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			cohesionLabel.setText (df.format(11-(100-cohesion.getValue())/10.0));
			flockPanel.setCohesion((100-cohesion.getValue())/10.0);
			//showStatus("cohesionWeight = "+df.format((100-cohesion.getValue())/10.0));
		}
	}

	public class ToleranceListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			toleranceLabel.setText (df.format(11-(100-tolerance.getValue())/10.0));
			flockPanel.setTolerance(5+(90-tolerance.getValue())/90.0*10.0);
			//showStatus("tolerance = "+df.format(5+(90-tolerance.getValue())/90.0*10.0));
		}
	}

	public class VarianceListener implements ChangeListener
	{

		public void stateChanged (ChangeEvent e)
		{
			varianceLabel.setText (df.format(11-(100-variance.getValue())/10.0));
			flockPanel.setVariation(variance.getValue()/90.0*0.5);
			//showStatus("variation = "+df2.format(variance.getValue()/90.0*0.5));
		}
	}
	/**
	 * Creates the main GUI layout in a JPanel.
	 *
	 * @return A new JPanel with the layout for the JApplet's GUI.
	 **/
	public JPanel makeGUI ()
	{
		JPanel newPanel = this;
		newPanel.setBackground(new Color(150,230,150));

		// flockPanel components
		flockPanel = new FlockSim (50, 600, 400);
		flockPanel.setPreferredSize(new Dimension(600, 400));
		flockPanel.setMinimumSize(new Dimension(600, 400));
		flockPanel.setMaximumSize(new Dimension(600, 400));
		flockPanel.setBorder (BorderFactory.createLineBorder (Color.BLACK));

		// numBirdsPanel components
		JPanel numBirdsPanel = new JPanel();
		numBirdsPanel.setBackground(new Color(150,230,150));
		numBirdsPanel.setLayout (new BoxLayout (numBirdsPanel, BoxLayout.X_AXIS));
		numBirds = new JSlider(JSlider.HORIZONTAL, 1, 500, 50);
		numBirds.setToolTipText("For a bigger flock.");
		numBirds.addChangeListener(new NumBirdsListener());
		numBirds.setBackground(new Color(150,230,150));
		JLabel title = new JLabel("Size: ");
		//title.setToolTipText("For a bigger swarm.");
		title.setMinimumSize(new Dimension(40,0));
		title.setMaximumSize(new Dimension(40,14));
		title.setPreferredSize(new Dimension(40,0));
		numBirdsLabel = new JLabel("50");
		//numBirdsLabel.setToolTipText("For a bigger swarm.");
		numBirdsLabel.setMinimumSize(new Dimension(25,0));
		numBirdsLabel.setMaximumSize(new Dimension(25,14));
		numBirdsLabel.setPreferredSize(new Dimension(25,0));
		redBirdBox = new JCheckBox("One Red");
		redBirdBox.setToolTipText("For a bird you can follow.");
		redBirdBox.addActionListener(new RedBirdBoxListener());
		redBirdBox.setBackground(new Color(150,230,150));
		redBirdBox.setSelected(true);
		numBirdsPanel.add(Box.createHorizontalStrut(5));
		numBirdsPanel.add(title);
		numBirdsPanel.add(numBirdsLabel);
		numBirdsPanel.add(numBirds);
		numBirdsPanel.add(redBirdBox);
		numBirdsPanel.add(Box.createHorizontalStrut(5));


		// gridSizePanel/maxSpeed components
		JPanel gridSizePanel = new JPanel();
		gridSizePanel.setLayout (new BoxLayout (gridSizePanel, BoxLayout.X_AXIS));
		gridSizePanel.setBackground(new Color(150,230,150));
		gridSize = new JSlider(JSlider.HORIZONTAL, 1, 5, 4);
		gridSize.setToolTipText("For a more unified flock.");
		gridSize.setBackground(new Color(150,230,150));
		gridSize.setMinimumSize(new Dimension(60,00));
		gridSize.setMaximumSize(new Dimension(60,20));
		gridSize.setPreferredSize(new Dimension(60,00));
		gridSize.addChangeListener(new GridSizeListener());
		title = new JLabel("Unity: ");
		//title.setToolTipText("For a more unified swarm.");
		title.setMinimumSize(new Dimension(40,00));
		title.setMaximumSize(new Dimension(40,14));
		title.setPreferredSize(new Dimension(40,00));
		gridSizeLabel = new JLabel("4");
		//gridSizeLabel.setToolTipText("For a more unified swarm.");
		gridSizeLabel.setMinimumSize(new Dimension(10,0));
		gridSizeLabel.setMaximumSize(new Dimension(10,14));
		gridSizeLabel.setPreferredSize(new Dimension(10,0));
		gridSizePanel.add(Box.createHorizontalStrut(5));
		gridSizePanel.add(title);
		gridSizePanel.add(gridSizeLabel);
		gridSizePanel.add(gridSize);
		gridSizePanel.add(Box.createHorizontalStrut(10));
		maxSpeed = new JSlider(JSlider.HORIZONTAL, 20, 100, 35);
		maxSpeed.setToolTipText("For a faster flock.");
		maxSpeed.addChangeListener(new MaxSpeedListener());
		maxSpeed.setBackground(new Color(150,230,150));
		title = new JLabel("Speed: ");
		//title.setToolTipText("For a faster swarm.");
		title.setMinimumSize(new Dimension(50,0));
		title.setMaximumSize(new Dimension(50,14));
		title.setPreferredSize(new Dimension(50,0));
		maxSpeedLabel = new JLabel("3.5");
		//maxSpeedLabel.setToolTipText("For a faster swarm.");
		maxSpeedLabel.setMinimumSize(new Dimension(25,0));
		maxSpeedLabel.setMaximumSize(new Dimension(25,14));
		maxSpeedLabel.setPreferredSize(new Dimension(25,0));
		gridSizePanel.add(title);
		gridSizePanel.add(maxSpeedLabel);
		gridSizePanel.add(maxSpeed);
		gridSizePanel.add(Box.createHorizontalStrut(5));

		// birdParamsPanel components
		JPanel birdParamsPanel = new JPanel();
		birdParamsPanel.setLayout (new BoxLayout (birdParamsPanel, BoxLayout.X_AXIS));
		birdParamsPanel.setBackground(new Color(150,230,150));
		separation = new JSlider(JSlider.HORIZONTAL, 0, 70, 0);
		separation.setToolTipText("For avoiding collisions.");
		separation.setBackground(new Color(150,230,150));
		separation.addChangeListener(new SeparationListener());
		title = new JLabel("Separation: ");
		//title.setToolTipText("For keeping away from the neighbours.");
		title.setMinimumSize(new Dimension(70,00));
		title.setMaximumSize(new Dimension(70,14));
		title.setPreferredSize(new Dimension(70,00));
		separationLabel = new JLabel("1.0");
		//separationLabel.setToolTipText("For keeping away from the neighbours.");
		separationLabel.setMinimumSize(new Dimension(25,0));
		separationLabel.setMaximumSize(new Dimension(25,14));
		separationLabel.setPreferredSize(new Dimension(25,0));
		birdParamsPanel.add(Box.createHorizontalStrut(5));
		birdParamsPanel.add(title);
		birdParamsPanel.add(separationLabel);
		birdParamsPanel.add(separation);
		birdParamsPanel.add(Box.createHorizontalStrut(10));

		alignment = new JSlider(JSlider.HORIZONTAL, 0, 90, 70);
		alignment.setToolTipText("For swimming with the stream.");
		alignment.setBackground(new Color(150,230,150));
		alignment.addChangeListener(new AlignmentListener());
		title = new JLabel("Alignment: ");
		//title.setToolTipText("For heading the same way as everyone else.");
		title.setMinimumSize(new Dimension(70,00));
		title.setMaximumSize(new Dimension(70,14));
		title.setPreferredSize(new Dimension(70,00));
		alignmentLabel = new JLabel("8.0");
		//alignmentLabel.setToolTipText("For heading the same way as everyone else.");
		alignmentLabel.setMinimumSize(new Dimension(25,0));
		alignmentLabel.setMaximumSize(new Dimension(25,14));
		alignmentLabel.setPreferredSize(new Dimension(25,0));
		birdParamsPanel.add(Box.createHorizontalStrut(5));
		birdParamsPanel.add(title);
		birdParamsPanel.add(alignmentLabel);
		birdParamsPanel.add(alignment);
		birdParamsPanel.add(Box.createHorizontalStrut(10));

		cohesion = new JSlider(JSlider.HORIZONTAL, 0, 90, 50);
		cohesion.setToolTipText("For heading into the flock.");
		cohesion.setBackground(new Color(150,230,150));
		cohesion.addChangeListener(new CohesionListener());
		title = new JLabel("Cohesion: ");
		//title.setToolTipText("For heading towards one another.");
		title.setMinimumSize(new Dimension(70,00));
		title.setMaximumSize(new Dimension(70,14));
		title.setPreferredSize(new Dimension(70,00));
		cohesionLabel = new JLabel("6.0");
		//cohesionLabel.setToolTipText("For heading towards one another.");
		cohesionLabel.setMinimumSize(new Dimension(25,0));
		cohesionLabel.setMaximumSize(new Dimension(25,14));
		cohesionLabel.setPreferredSize(new Dimension(25,0));
		birdParamsPanel.add(Box.createHorizontalStrut(5));
		birdParamsPanel.add(title);
		birdParamsPanel.add(cohesionLabel);
		birdParamsPanel.add(cohesion);
		birdParamsPanel.add(Box.createHorizontalStrut(10));
		gridSizePanel.add(Box.createHorizontalStrut(5));

		// misc components
		JPanel miscComponents = new JPanel();
		miscComponents.setLayout (new BoxLayout (miscComponents, BoxLayout.X_AXIS));
		miscComponents.setBackground(new Color(150,230,150));
		tolerance = new JSlider(JSlider.HORIZONTAL, 0, 90, 45);
		tolerance.setToolTipText("For cozier birds.");
		tolerance.setBackground(new Color(150,230,150));
		tolerance.addChangeListener(new ToleranceListener());
		title = new JLabel("Tolerance: ");
		//title.setToolTipText("For a more unified swarm.");
		title.setMinimumSize(new Dimension(70,00));
		title.setMaximumSize(new Dimension(70,14));
		title.setPreferredSize(new Dimension(70,00));
		toleranceLabel = new JLabel("5.5");
		//gridSizeLabel.setToolTipText("For a more unified swarm.");
		toleranceLabel.setMinimumSize(new Dimension(25,0));
		toleranceLabel.setMaximumSize(new Dimension(25,14));
		toleranceLabel.setPreferredSize(new Dimension(25,0));
		miscComponents.add(Box.createHorizontalStrut(5));
		miscComponents.add(title);
		miscComponents.add(toleranceLabel);
		miscComponents.add(tolerance);
		miscComponents.add(Box.createHorizontalStrut(10));
		variance = new JSlider(JSlider.HORIZONTAL, 0, 90, 18);
		variance.setToolTipText("For less similar birds.");
		variance.addChangeListener(new VarianceListener());
		variance.setBackground(new Color(150,230,150));
		title = new JLabel("Variation: ");
		//title.setToolTipText("For a faster swarm.");
		title.setMinimumSize(new Dimension(60,0));
		title.setMaximumSize(new Dimension(60,14));
		title.setPreferredSize(new Dimension(60,0));
		varianceLabel = new JLabel("2.8");
		//maxSpeedLabel.setToolTipText("For a faster swarm.");
		varianceLabel.setMinimumSize(new Dimension(25,0));
		varianceLabel.setMaximumSize(new Dimension(25,14));
		varianceLabel.setPreferredSize(new Dimension(25,0));
		miscComponents.add(title);
		miscComponents.add(varianceLabel);
		miscComponents.add(variance);
		miscComponents.add(Box.createHorizontalStrut(5));

		// newPanel layout
		newPanel.setLayout (new BoxLayout (newPanel, BoxLayout.Y_AXIS));
		newPanel.setBorder (BorderFactory.createLineBorder (Color.BLACK));
		newPanel.add (flockPanel);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout (new BoxLayout (controlPanel, BoxLayout.Y_AXIS));
		controlPanel.setBackground(new Color(150,230,150));
		controlPanel.setBorder (BorderFactory.createLineBorder (Color.BLACK));
		controlPanel.add (numBirdsPanel);
		controlPanel.add (gridSizePanel);
		controlPanel.add (birdParamsPanel);
		controlPanel.add (miscComponents);
		controlPanel.add (Box.createVerticalGlue ());
		newPanel.add (controlPanel);

		// return the panel
		return newPanel;
	}
} // GUIClassTemplate class
