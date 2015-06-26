import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JComboBox;











//import com.swtdesigner.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;
import javax.swing.JCheckBoxMenuItem;

import java.awt.TextArea;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.moeaframework.core.Solution;

import java.awt.event.FocusAdapter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GeneticProg {
	/*
	  CONSTANTS DECLARATION
	*/
	String _FUNCSET = "+ - * /";
	String _TERMSET = "0, 1, 2, 3, 5, 6, 7, 8, 9, x";
	String _FRMTITLE = "Genetic Programming";
	String _TARGETPRG = "Target function: ((X * X) - 1) / 2";
	String _FILENAME = "trainingdata.txt"; 
	
	private JFrame frame;
	private JTextField textFieldX;
	private double[] xValues;
	private double[] yValues;
	private JTextField textFieldY;
	private JTextField textFieldXOver;
	private JTextField textFieldMut;
	private JTextField textFieldTSize;
	private JTextField textFieldPopSize;
	private JTextField textFieldMaxGen;
	private JTextField textFieldMaxTreeHeight;
	private JTextField textFieldMaxRunTime;
	private JComboBox cmbInitTreeMethod;
	private JComboBox cmbFitnessType;
	private JComboBox cmbFitErrorType;
	private JLabel lblFunctionSet = new JLabel("Function Set: " + _FUNCSET);
	private JLabel lblTerminalSet = new JLabel("Terminal Set: " + _TERMSET);
	private JLabel lblXValues = new JLabel("X Values:");
	private JLabel lblInputs = new JLabel("Inputs:");
	private JLabel lblOutputs = new JLabel("Outputs:");
	private JButton btnCalcY, btnReset, btnRun;
	private JLabel lblYValues, lblTournamentSize, lblMaximumRunTime, lblMaximumTreeHeight,lblMaximumGeneration,
				lblPopulationSize,lblMutationProbability,lblCrossoverProbability,lblReproduction  ;
	private JLabel lblFitnessValue,lblRunTime, lblTargetFuncConfirm;
	private static long startTime;
	private static long endTime;
	private static long currentTime;
	private static boolean bIsAnyEmpty=false, bIsRunForever=false;
	private int trainingSetSize;
	private JTextField textFieldRepro;
	
	private GPUtils utils = new GPUtils();
	private TrainingData tnData;
	private Settings setting = new Settings();
	
	private TextArea textAreaSol;
	private JTextField textFieldOutFitness;
	private JTextField textFieldRunTime;
	int generationCount=0;
	private Double doubleXs[];
	private Double doubleYs[];
	private String strArrayXs[]; 
	String  newXstr;
	String  newYstr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					startTime = 0;
					endTime = 0;
					GeneticProg window = new GeneticProg();
					window.frame.setVisible(true);			        
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public GeneticProg() throws NumberFormatException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void initialize() throws NumberFormatException, IOException {
				
		/* ***********************
		 * Initialize the frame
		 ************************* */
		StringBuilder strTrainingDataX = new StringBuilder();
		StringBuilder strTrainingDataY = new StringBuilder();
		FileInputStream inputStream = new FileInputStream(_FILENAME);
		        
		        DataInputStream in = new DataInputStream(inputStream);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        
		        String strLine;
		        while ((strLine = br.readLine()) != null) {
		            String[] strArr = strLine.split(",");
//		            double inputData = Double.parseDouble(strArr[0]);
//		            double outputData = Double.parseDouble(strArr[1]);
		            strTrainingDataX.append(strArr[0]).append(",");
		            strTrainingDataY.append(strArr[1]).append(",");
		        
		        }
		        
		     String Xstr =  strTrainingDataX.toString();
		     String Ystr = strTrainingDataY.toString();
		
		          newXstr = Xstr.substring(0, Xstr.length()-1);
		          newYstr = Ystr.substring(0, Ystr.length()-1);
		      
		
		 //String newXstr =  Xstr.substring(0,Xstr.length()-1);
		   
		        System.out.println("input is,  " + newXstr);
		        System.out.println("output is " + newYstr);
		frame = new JFrame();
		frame.setBounds(100, 100, 657, 636);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setTitle(_FRMTITLE);
		
		textFieldX = new JTextField();
		textFieldX.setEditable(false);
		textFieldX.setBounds(106, 114, 230, 20);
		frame.getContentPane().add(textFieldX);
		textFieldX.setColumns(10);
		//textFieldX.setText("-8,-3,-1,0,2,4,5,7,11");
		textFieldX.setText(newXstr);
		
		textFieldX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textFieldX.selectAll();         
			}
		});
		textFieldX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				String sValue = textFieldX.getText();   
				int iStrLength = sValue.length();
				//System.out.println("X text: " + sValue);
				//System.out.println(arg0.getKeyCode());
				
				boolean isXInputValid = utils.isXInputValid(arg0);
				if ((arg0.getKeyChar() == ',' ) && (sValue.charAt(iStrLength-1) == ','))
				{
					//System.out.println("Last char: " + sValue.substring(iStrLength-1));
					JOptionPane.showMessageDialog(frame,
							"Enter X values again in [number], [number] format. \n Example: 1,2,3,4");
					textFieldX.setText(sValue) ;
					return;
				}
				if (!isXInputValid)
				{
					JOptionPane.showMessageDialog(frame, 
							"Enter X values again in [number], [number] format. \n Example: 1,2,3,4");
					textFieldX.setText(sValue);
					return;
				}	
				return;
			}
		});
	
		/*
		 * Y values Calculation
		 */
		lblYValues = new JLabel("Y Values:");
		lblYValues.setBounds(44, 155, 59, 14);
		frame.getContentPane().add(lblYValues);
		
		textFieldY = new JTextField();
		textFieldY.setEditable(false);
		textFieldY.setForeground(Color.BLACK);
		textFieldY.setBounds(106, 155, 230, 20);
		frame.getContentPane().add(textFieldY);
		textFieldY.setColumns(10);
		textFieldY.setText(newYstr);
//		
//		btnCalcY = new JButton("Calculate Y");
//		btnCalcY.setFont(new Font("Tahoma", Font.PLAIN, 9));
//		btnCalcY.setBounds(106, 180, 81, 20);
//		frame.getContentPane().add(btnCalcY);
		
//		btnCalcY.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				
//				//parse X values
//				if (textFieldX.getText().isEmpty())
//				{
//					JOptionPane.showMessageDialog(frame,"Please input X value(s).");
//					return;
//				}
//				String sYValues ="";
//				//Assign x, y to TrainingData properties and get also get a return Y values 
//				sYValues = calculateY(textFieldX.getText());
//				//Display Y values to Y text field
//				textFieldY.setText(sYValues);
//				return;
//			}
//		});
		/*
		 * Population size
		 */
		lblPopulationSize = new JLabel("Population Size (1-9999):");
		lblPopulationSize.setBounds(44, 217, 143, 14);
		frame.getContentPane().add(lblPopulationSize);
		
		textFieldPopSize = new JTextField();
		textFieldPopSize.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textFieldPopSize.selectAll();
			}
		});
		textFieldPopSize.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldPopSize.setText("1000");
		textFieldPopSize.setBounds(266, 215, 70, 20);
		frame.getContentPane().add(textFieldPopSize);
		textFieldPopSize.setColumns(10);
		
		//Typed in Pop Size
		textFieldPopSize.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldPopSize.getText(); //keep current string  
				//System.out.println("Pop Text: " + sValue);
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Please input the Population Size as a positive integer.");
					textFieldPopSize.setText(sValue); //reassign string (remove invalid char)
					return;
				}
				return;
			}
		});
		/*
		 * Max Generations
		 */
//		lblMaximumGeneration = new JLabel("Maximum Generation (0-99999):");
//		lblMaximumGeneration.setBounds(44, 240, 185, 14);
//		frame.getContentPane().add(lblMaximumGeneration);
		
		textFieldMaxGen = new JTextField();
		textFieldMaxGen.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textFieldMaxGen.selectAll();
			}
		});
		textFieldMaxGen.setText("0");
//		textFieldMaxGen.setHorizontalAlignment(SwingConstants.RIGHT);
//		textFieldMaxGen.setBounds(266, 240, 70, 20);
//		frame.getContentPane().add(textFieldMaxGen);
//		textFieldMaxGen.setColumns(10);
	
		/*************************
		* Typed in Max Generation
		**************************/
//		textFieldMaxGen.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent arg0) {
//				String sValue = textFieldMaxGen.getText(); //keep current string
//				//System.out.println("Max Gen: " + sValue + "Keychar: " + arg0.getKeyCode());
//				
//				boolean isInputValid = utils.isNumInputValid(arg0);				
//				if (!isInputValid)
//				{
//					JOptionPane.showMessageDialog(frame,"Please input the Maximum Generation as a positive integer.");
//					textFieldMaxGen.setText(sValue); //reassign string (remove invalid char)
//					return;
//				}
//				return;
//			}
//		});
		/* 
		 * Max Tree height inputs
		 */
//		lblMaximumTreeHeight = new JLabel("Maximum Tree Height (1-19):");
//		lblMaximumTreeHeight.setBounds(44, 265, 166, 14);
//		frame.getContentPane().add(lblMaximumTreeHeight);
		
		textFieldMaxTreeHeight = new JTextField();
//		textFieldMaxTreeHeight.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent arg0) {
//				textFieldMaxTreeHeight.selectAll();
//			}
//		});
		textFieldMaxTreeHeight.setText("3");
//		textFieldMaxTreeHeight.setHorizontalAlignment(SwingConstants.RIGHT);
//		textFieldMaxTreeHeight.setBounds(266, 265, 70, 20);
//		frame.getContentPane().add(textFieldMaxTreeHeight);
//		textFieldMaxTreeHeight.setColumns(10);
		/*************************
		* Typed in Max Tree Height
		**************************/		
		textFieldMaxTreeHeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldMaxTreeHeight.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Please input the Maximum Tree Height as a positive integer.");
					textFieldMaxTreeHeight.setText(sValue); //reassign string (remove invalid char)
					return;
				}
				return;
			}
		});
		/*
		 * Crossover inputs
		 */
		lblCrossoverProbability = new JLabel("Crossover Probability:");
		lblCrossoverProbability.setBounds(402, 117, 147, 14);
		frame.getContentPane().add(lblCrossoverProbability);
		
		textFieldXOver = new JTextField();
		textFieldXOver.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldXOver.selectAll();
			}
		});
		textFieldXOver.setText("90");
		textFieldXOver.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldXOver.setBounds(559, 114, 48, 20);
		frame.getContentPane().add(textFieldXOver);
		textFieldXOver.setColumns(10);
		
		/*************************
		* Typed in CrossOver
		**************************/
		
		textFieldXOver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldXOver.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Crossover probability is a positive integer and less than 100.");
					textFieldXOver.setText(sValue); //reassign string (remove invalid char)
					return;
				}	
				return;
			}
		});
		/* 
		 **********************
		 * Typed in Mutation
		 **********************
		 */
		lblMutationProbability = new JLabel("Mutation Probability:");
		lblMutationProbability.setBounds(402, 149, 147, 14);
		frame.getContentPane().add(lblMutationProbability);
		
		textFieldMut = new JTextField();
		textFieldMut.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textFieldMut.selectAll();
			}
		});
		textFieldMut.setText("10");
		textFieldMut.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldMut.setBounds(559, 145, 48, 20);
		frame.getContentPane().add(textFieldMut);
		textFieldMut.setColumns(10);
		textFieldMut.setEditable(true);

		textFieldMut.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldMut.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Mutation probability is a positive integer and less than 100.");
					textFieldMut.setText(sValue); //reassign string (remove invalid char)
					return;
				}	
				return;
			}
		});
		
		/*
		 * Type Reproduction %
		 */
		lblReproduction = new JLabel("Reproduction Probability:");
		lblReproduction.setBounds(402, 180, 147, 14);
		frame.getContentPane().add(lblReproduction);
		textFieldRepro = new JTextField();
		textFieldRepro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textFieldRepro.selectAll();
			}
		});
		textFieldRepro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldRepro.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Reproduction probability is a positive integer and less than 100.");
					textFieldRepro.setText(sValue); //reassign string (remove invalid char)
					return;
				}
				return;
			}
		});
		textFieldRepro.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldRepro.setText("0");
		textFieldRepro.setBounds(559, 176, 48, 20);
		frame.getContentPane().add(textFieldRepro);
		textFieldRepro.setColumns(10);
		
		/* 
		 * *************************
		 * Typed in Tournament Size
		 ***************************
		 */
		lblTournamentSize = new JLabel("Tournament Size:");
		lblTournamentSize.setBounds(402, 209, 109, 14);
		frame.getContentPane().add(lblTournamentSize);
		
		textFieldTSize = new JTextField();
		textFieldTSize.setText("0");
		textFieldTSize.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTSize.setBounds(559, 205, 48, 20);
		frame.getContentPane().add(textFieldTSize);
		textFieldTSize.setColumns(10);
		textFieldTSize.setEditable(false);
		
		textFieldTSize.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldTSize.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Please input the Tournament size as a positive integer.");
					textFieldTSize.setText(sValue); //reassign string (remove invalid char)
					return;
				}	
			}
		});
		
		btnRun = new JButton("Run");
		btnRun.setBounds(308, 335, 89, 23);
		frame.getContentPane().add(btnRun);
		
		JLabel lblTargetProgram = new JLabel(_TARGETPRG);
		lblTargetProgram.setForeground(Color.BLUE);
		lblTargetProgram.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTargetProgram.setBounds(20, 11, 209, 14);
		frame.getContentPane().add(lblTargetProgram);
		
		lblXValues.setBounds(44, 117, 59, 14);
		frame.getContentPane().add(lblXValues);
		
		lblTerminalSet.setBounds(44, 61, 204, 14);
		frame.getContentPane().add(lblTerminalSet);
		
		lblFunctionSet.setBounds(44, 80, 132, 14);
		frame.getContentPane().add(lblFunctionSet);
		
		lblInputs.setBounds(20, 36, 111, 14);
		frame.getContentPane().add(lblInputs);
		
		lblOutputs.setBounds(20, 376, 59, 14);
		frame.getContentPane().add(lblOutputs);
				
//		lblMaximumRunTime = new JLabel("Maximum Run Time (minutes):");
//		lblMaximumRunTime.setBounds(44, 290, 172, 14);
//		frame.getContentPane().add(lblMaximumRunTime);
		textFieldMaxRunTime = new JTextField();
//		textFieldMaxRunTime.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent arg0) {
//				textFieldMaxRunTime.selectAll();
//			}
//		});
//		textFieldMaxRunTime.setEditable(true);
		textFieldMaxRunTime.setText("15");
//		textFieldMaxRunTime.setHorizontalAlignment(SwingConstants.RIGHT);
//		textFieldMaxRunTime.setBounds(266, 290, 70, 20);
//		frame.getContentPane().add(textFieldMaxRunTime);
//		textFieldMaxRunTime.setColumns(10);
		
		/****************************
		* Typed in Max Running Time
		*****************************/		
		textFieldMaxRunTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				String sValue = textFieldMaxRunTime.getText(); //keep current string
				
				boolean isInputValid = utils.isNumInputValid(arg0);				
				if (!isInputValid)
				{
					JOptionPane.showMessageDialog(frame,"Please input the Maximum Running Timeas a positive integer.");
					textFieldMaxTreeHeight.setText(sValue); //reassign string (remove invalid char)
					return;
				}
				return;
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 33, 620, 2);
		frame.getContentPane().add(separator);
						
		JLabel lblInitTreeMethod = new JLabel("Init Tree Method:");
		lblInitTreeMethod.setBounds(402, 237, 94, 14);
		frame.getContentPane().add(lblInitTreeMethod);
		
		cmbInitTreeMethod = new JComboBox();
		cmbInitTreeMethod.setMaximumRowCount(3);
		cmbInitTreeMethod.addItem("Full");
		cmbInitTreeMethod.addItem("Grow");
		cmbInitTreeMethod.addItem("Mixed");
		cmbInitTreeMethod.setBounds(509, 232, 101, 20);
		cmbInitTreeMethod.setEditable(false);
		frame.getContentPane().add(cmbInitTreeMethod);
		
		JLabel lblFitnessType = new JLabel("Fitness Type:");
		lblFitnessType.setBounds(402, 263, 78, 14);
		frame.getContentPane().add(lblFitnessType);
		
		cmbFitnessType = new JComboBox();
		cmbFitnessType.setMaximumRowCount(2);
		cmbFitnessType.addItem("Standard");
		cmbFitnessType.addItem("Normalized");
		cmbFitnessType.setBounds(509, 260, 101, 20);
		cmbFitnessType.setEditable(false);
		frame.getContentPane().add(cmbFitnessType);
		
		JLabel lblFitnessTypeError = new JLabel("Fitness Error Type:");
		lblFitnessTypeError.setBounds(402, 290, 109, 14);
		frame.getContentPane().add(lblFitnessTypeError);
		
		cmbFitErrorType = new JComboBox();
		cmbFitErrorType.setMaximumRowCount(3);
		cmbFitErrorType.addItem("Squared");
		cmbFitErrorType.addItem("Scaled");
		cmbFitErrorType.addItem("Error");
		cmbFitErrorType.setEditable(false);
		cmbFitErrorType.setBounds(509, 287, 101, 20);
		frame.getContentPane().add(cmbFitErrorType);
				
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 369, 620, 2);
		frame.getContentPane().add(separator_1);
			
		JLabel lblEg = new JLabel("(e.g: 3, 4, 5, 6, 7, 10, -4, -6)");
		lblEg.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEg.setBounds(106, 135, 166, 14);
		frame.getContentPane().add(lblEg);
		
		JLabel lblBestSolution = new JLabel("Best solution:");
		lblBestSolution.setBounds(45, 468, 86, 14);
		frame.getContentPane().add(lblBestSolution);
		
		lblFitnessValue = new JLabel("Fitness value:");
		lblFitnessValue.setBounds(44, 402, 87, 14);
		frame.getContentPane().add(lblFitnessValue);
		
		lblRunTime = new JLabel("Running time:");
		lblRunTime.setBounds(44, 427, 81, 14);
		frame.getContentPane().add(lblRunTime);
		
		JCheckBoxMenuItem chckbxmntmAbout = new JCheckBoxMenuItem("About");
		chckbxmntmAbout.setSelected(true);
		chckbxmntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
					JOptionPane.showMessageDialog(frame,"SEIS610-Genetic Programming project team members: \n" +
							"       * Krista Sheely \n" + 
							"       * Sandhya Sivaramakrishnan \n"  +
							"       * Phan Vu \n" + 
							"       * Dave York");
				}
			});
		chckbxmntmAbout.setBounds(570, 0, 78, 22);
		frame.getContentPane().add(chckbxmntmAbout);
		
//		btnReset = new JButton("Reset");
//		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 9));
//		btnReset.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				textFieldX.setText("");
//				textFieldY.setText("");
//				return;
//			}
//		});
//		btnReset.setBounds(195, 179, 59, 21);
//		frame.getContentPane().add(btnReset);
		
		textAreaSol = new TextArea();
		textAreaSol.setForeground(Color.BLUE);
		textAreaSol.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaSol.setBackground(Color.WHITE);
		textAreaSol.setEditable(false);
		textAreaSol.setBounds(140, 452, 478, 136);
		frame.getContentPane().add(textAreaSol);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 128, 128)));
		panel.setBounds(37, 102, 313, 222);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.GRAY));
		panel_1.setBounds(382, 102, 240, 222);
		frame.getContentPane().add(panel_1);
		
		textFieldOutFitness = new JTextField();
		textFieldOutFitness.setText("0.0");
		textFieldOutFitness.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldOutFitness.setForeground(Color.BLUE);
		textFieldOutFitness.setEditable(false);
		textFieldOutFitness.setBounds(140, 399, 139, 20);
		frame.getContentPane().add(textFieldOutFitness);
		textFieldOutFitness.setColumns(10);
		
		textFieldRunTime= new JTextField();
		textFieldRunTime.setText("0");
		textFieldRunTime.setEditable(false);
		textFieldRunTime.setForeground(Color.BLUE);
		textFieldRunTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		textFieldRunTime.setBounds(140, 424, 347, 20);
		frame.getContentPane().add(textFieldRunTime);
		textFieldRunTime.setColumns(10);
		
		lblTargetFuncConfirm = new JLabel("");
		lblTargetFuncConfirm.setForeground(Color.BLUE);
		lblTargetFuncConfirm.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTargetFuncConfirm.setBounds(325, 401, 285, 14);
		frame.getContentPane().add(lblTargetFuncConfirm);
		
		frame.getContentPane().setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[]{textFieldX, btnCalcY, btnReset,
							textFieldPopSize, textFieldMaxGen, textFieldMaxTreeHeight, textFieldMaxRunTime, 
							textFieldXOver, textFieldMut, textFieldRepro, textFieldTSize, 
							cmbInitTreeMethod, cmbFitnessType, cmbFitErrorType, 
							btnRun }));
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{textFieldX, btnCalcY, btnReset,
				textFieldPopSize, textFieldMaxGen, textFieldMaxTreeHeight, textFieldMaxRunTime,
				textFieldXOver, textFieldMut, textFieldRepro, textFieldTSize, 
				cmbInitTreeMethod, cmbFitnessType, cmbFitErrorType,  btnRun}));
		
		btnRun.addMouseListener(new MouseAdapter() {
			@Override		
			public void mouseReleased(MouseEvent arg0) 
			{
				//clear the output text
				textAreaSol.setText("");
				textFieldOutFitness.setText("");
				textFieldRunTime.setText("");
								
				if (!bIsAnyEmpty)
				{
					long duration = 0;
					long lMin =0, lSec =0, lMillsec =0;
					String sDurationTemp="";
					String sOut = ""; //to hold output string to be put to screen
					
					BinaryTree oTreeOut = new BinaryTree(); //tree to be put to screen
					TwoChildNode sNodeOut = new TwoChildNode(); //root node of tree to be put to screen
					
					try 
					{
						/*******************************************************/
						/*                       SETUP                         */
						/*******************************************************/
						startTime = System.currentTimeMillis();
						endTime = startTime + (setting.getMaxRunTime() * 60000);
						int populationSize = (int)setting.getPopSize();
						int maxGenerations = (int)setting.getMaxGen();
						int maxHeight = (int)setting.getMaxHeight();
						double toMutate = (double)setting.getMutationProb();
						double toCrossOver = (double)setting.getCrssOverProb();
						double toReproduce = (double)setting.getReproductionProb();
						int perfectFitnessPopulationNum = 0, populationNumToCrossOver, whichEvolutionMethod;
						int goodTrees=0, treesToMutate, treesToCrossOver, treesToReproduce;
						int g=0;//hold f value for calculation
						int a=0, f=0;
						
						BinaryTree thisTree[] = new BinaryTree[populationSize];
						Fitness thisFitness = new Fitness(populationSize,trainingSetSize);
						TwoChildNode thisTreeRootNode[] = new TwoChildNode[populationSize];
						
						/*******************************************************/
						/*                INITIALIZE THE TREE                  */
						/*******************************************************/
						for(a=0;a<populationSize;a++)
						{
							thisTree[a] = new BinaryTree(a,1);
							thisTreeRootNode[a] = new TwoChildNode();
							thisTreeRootNode[a] = thisTree[a].initializeBinaryTree(maxHeight);
						}
						
						if(bIsRunForever)
							maxGenerations = 999999999;
						
						for(f=0;f<maxGenerations;f++)
						{
							g++;
							generationCount = g;
							/*******************************************************/
							/*               CALCULATE THE FITNESS                 */
							/*******************************************************/
							double trainingData[][] = new double[tnData.getTrainingSetSize()][2];
							double evaluatedValue = 0;
							double evaluatedFitness = 0;
							double totalFitness = 0;
							boolean perfectFitness;
							trainingData = tnData.getTrainingDataSet();
							for(a=0;a<populationSize;a++)
							{
								perfectFitness = true;
								totalFitness = 0;
								thisTree[a].setBadTree(false);
								thisTree[a].setHasAnXTerminal(false);
								for(int b=0;b<trainingSetSize;b++)
								{
									thisFitness.setFitnessXValue(a,b,trainingData[b][0]);
									thisFitness.setFitnessYValue(a,b,trainingData[b][1]);
									evaluatedValue = thisTree[a].evaluateTree(thisTreeRootNode[a], trainingData[b][0], true);
									thisFitness.setFitnessZValue(a,b,evaluatedValue);
									evaluatedFitness = thisFitness.getFitnessYValue(a,b) - evaluatedValue;
									thisFitness.setFitnessNormal(a,b,evaluatedFitness);
									totalFitness += evaluatedFitness;
									if(perfectFitness && evaluatedFitness != 0)
									{
										perfectFitness = false;
									}
									thisFitness.setFitnessSquare(a,b,thisFitness.getFitnessNormalSquared(a,b));
								}
								thisTree[a].setTotalFitness(Math.abs(totalFitness));
								if(perfectFitness)
								{
									//System.out.println("perfect fitness?");
									perfectFitnessPopulationNum = a;
								}
								//System.out.println(" ");
								//System.out.println("totalfitness=" + thisTree[a].getTotalFitness());
								//thisTree[a].postOrderTraversal(thisTreeRootNode[a]);
								//System.out.println(" " + thisTree[a].getBadTree());
								//thisFitness.printPopulation(a,trainingSetSize);
							}

							/*******************************************************/
							/*              EVALUATE THE POPULATION                */
							/*******************************************************/
							if(perfectFitnessPopulationNum != 0)
							{
								oTreeOut = thisTree[perfectFitnessPopulationNum];         
								sNodeOut = thisTreeRootNode[perfectFitnessPopulationNum]; 
								f=999999999;  //force exit
							}

							for(a=0;a<populationSize;a++)
							{
								if(thisTree[a].getHasDivideByZero() == true || 
								   thisTree[a].getHasAnXTerminal() == false || 
								   thisTree[a].getTotalFitness() > 999999)
								{
									thisTree[a].setBadTree(true);
								}
							}

							/*******************************************************/
							/*        DETERMINE HOW MANY GOOD TREES WE HAVE        */
							/*******************************************************/
							goodTrees = 0;
							for(a=0;a<populationSize;a++)
							{
								if(thisTree[a].getBadTree() == false)
								{
									goodTrees++;
								}
							}

							/*******************************************************/
							/*          CROSSOVER, MUTATE, AND REPRODUCE           */
							/*           A PERCENTAGE OF THE POPULATION            */
							/*******************************************************/
							if(f+1<maxGenerations)
							{	
								treesToCrossOver = (int)(goodTrees * (toCrossOver / 100));
								treesToMutate = (int)(goodTrees * (toMutate / 100));
								treesToReproduce = (int)(goodTrees * (toReproduce / 100));
								populationNumToCrossOver = 999999999;
								if(treesToCrossOver % 2 != 0)
								{
									treesToCrossOver = treesToCrossOver - 1;
								}
								for(a=0;a<populationSize;a++)
								{
									if(thisTree[a].getBadTree() == false)
									{
										whichEvolutionMethod = a % 3;
										if(whichEvolutionMethod == 0)
										{
											// Crossover
											if(treesToCrossOver <= 0)
											{
												//if no more to crossover, send to mutate
												whichEvolutionMethod = 1;
											}
											else if(populationNumToCrossOver == 999999999)
											{
												populationNumToCrossOver = a;
												treesToCrossOver--;
											}
											else
											{
												thisTree[a].crossOverTree(thisTreeRootNode[a],thisTreeRootNode[populationNumToCrossOver]);
												treesToCrossOver--;
												populationNumToCrossOver = 999999999;
											}
										}
										if(whichEvolutionMethod == 1)
										{
											// Mutate
											if(treesToMutate > 0)
											{
												thisTree[a].mutateTree(thisTreeRootNode[a]);
												treesToMutate--;
											}
											else
											{
												//if no more to mutate, send to reproduce
												whichEvolutionMethod = 2;
											}
										}
										if(whichEvolutionMethod == 2)
										{
									    	// Reproduce
											if(treesToReproduce > 0)
											{
												thisTree[a] = new BinaryTree();
												thisTreeRootNode[a] = new TwoChildNode();
												thisTreeRootNode[a] = thisTree[a].initializeBinaryTree(maxHeight);
												treesToReproduce--;
											}
										}
										thisTree[a].setGenerationNum(f+1);
									}
									else
									{
										thisTree[a] = new BinaryTree();
										thisTreeRootNode[a] = new TwoChildNode();
										thisTreeRootNode[a] = thisTree[a].initializeBinaryTree(maxHeight);
										thisTree[a].setGenerationNum(f+1);
									}
								}
							}
							/*******************************************************/
							/*               LOOK FOR EXIT CONDITION               */
							/*******************************************************/
							if(timeToQuit())
							{
								//System.out.println("Time to Quit");
								//System.out.println("Over predefined time.");
								//System.out.println("Generation passed: " + f);
								f=999999999;  //force exit
							}
							// WOULD LIKE TO DISPLAY THE PERIODIC RUNNING TIME HERE AFTER EACH GENERATION LOOP
							else
							{
								long lEndTime = System.currentTimeMillis();
							
								duration = lEndTime  - startTime;
									
								if (duration < 60000) 
								{
									lSec = (((long) duration) /1000); 
								}
								else 
								{
									lSec = ((long) duration % 60000)/1000;
								}
								lMillsec = ((long) duration) % 1000;
								lMin = ((long) duration )/ 60000 ;
								sDurationTemp = "Generation: " + f + ' ' + "\nTime elapsed: " +lMin+ " minute(s), "+ lSec + " second(s), " + lMillsec + ' ' + "milliseconds.";
								textAreaSol.setText(sDurationTemp);
							}
						}

						/*******************************************************/
						/* DIDN'T FIND PERFECT FITNESS SO DISPLAY BEST FITNESS */
						/*******************************************************/
						if(perfectFitnessPopulationNum == 0)
						{
							//System.out.println("didn't find perfect fitness");
							int bestFitnessPopulationNum = 0;
							double bestTotalFitness = 999999999;
							double thisTotalFitness;
							boolean thisTreeIsBad = false;
							for(a=0;a<populationSize;a++)
							{
								thisTotalFitness = thisTree[a].getTotalFitness();
								thisTreeIsBad = thisTree[a].getBadTree();
								//System.out.println(a + " " + thisTotalFitness + " " + thisTreeIsBad);
								if(!thisTreeIsBad)
								{
									if(thisTotalFitness < bestTotalFitness)
									{
										bestTotalFitness = thisTotalFitness;
										bestFitnessPopulationNum = a;
									}
								}
							}
							//System.out.println(bestFitnessPopulationNum);
							oTreeOut = thisTree[bestFitnessPopulationNum];         
							sNodeOut = thisTreeRootNode[bestFitnessPopulationNum]; 
						}
						
						///////////////////////////////
						endTime = System.currentTimeMillis();
						duration = endTime - startTime;
						
						if (duration > setting.getMaxRunTime() * 60000)
						{
							textFieldRunTime.setText("N/A");
							textFieldOutFitness.setText("N/A");
							textAreaSol.setText("Number of Generations passed: " + g + "\n\nExceed pre-defined maximum running time.\nNo solution found!");
						}	
						else 
						{
							// WOULD LIKE TO DISPLAY THE TIME IN MM:SS INSTEAD
							textFieldRunTime.setText(Long.toString(lMin) + ' ' + " minute(s), " + Long.toString(lSec)+ ' ' + "second(s), " + Long.toString(lMillsec) + ' ' + "milliseconds.");
							
							textFieldOutFitness.setText(Double.toString(oTreeOut.getTotalFitness()));
							// add information to output string
							sOut = sOut + "Full equation:  " + "\n";
							// WOULD LIKE TO DISPLAY THE REDUCED FINAL EQUATION INSTEAD OF THE FULL EQUATION
							sOut = sOut + "  " + oTreeOut.printOutputTree(sNodeOut) + "\n\n";
							
							sOut = sOut + "Reduced equation:  "+ "\n";
							oTreeOut.simplifyBinaryTree(sNodeOut, true);
							sOut = sOut + " " + oTreeOut.printOutputTree(sNodeOut);
												
							if (g < maxGenerations) 
							{ 
								g++;
								lblTargetFuncConfirm.setText("");
							}
							else 
							{
								lblTargetFuncConfirm.setForeground(Color.RED);
								lblTargetFuncConfirm.setText("Target Function Not Found. Please Try Again!");
							}
							sOut = sOut + ' ' + "\n\nGenerations passed: " + g; 												
							sOut = sOut + ' ' + "\tPopulation #: " + goodTrees; 
							//output to screen
							textAreaSol.setText(sOut);
						}
					} 
					finally 
					{
						endTime = System.currentTimeMillis();
					}
										
				}
				return;
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				bIsAnyEmpty = false;
				if (Double.parseDouble(textFieldMaxGen.getText()) == 0.0)
				{
					bIsRunForever = true;
				}
				if (
					utils.isFieldEmpty(textFieldX.getText())  
					|| utils.isFieldEmpty(textFieldPopSize.getText())
					|| utils.isFieldEmpty(textFieldMaxGen.getText()) 
					|| utils.isFieldEmpty(textFieldMaxTreeHeight.getText())
					|| utils.isFieldEmpty(textFieldMaxRunTime.getText()) 
					|| utils.isFieldEmpty(textFieldXOver.getText())
					|| utils.isFieldEmpty(textFieldMut.getText()) 
					|| utils.isFieldEmpty(textFieldTSize.getText())
					)
					{
						JOptionPane.showMessageDialog(frame,"Please complete input data.");
						bIsAnyEmpty = true;
					}
				else if (Double.parseDouble(textFieldPopSize.getText()) > 9999.00
						|| Double.parseDouble(textFieldPopSize.getText())  < 1.00) // population is more than 999
				{
					JOptionPane.showMessageDialog(frame,"Population Size must be > 0 and < 10,000");
					bIsAnyEmpty = true;
				}
				else if (Double.parseDouble(textFieldMaxGen.getText()) > 99999.00)
						//|| Double.parseDouble(textFieldMaxGen.getText()) < 0.00) // 
				{
					JOptionPane.showMessageDialog(frame,"Total number of generation must be < 100,000");
					bIsAnyEmpty = true;
				}
				else if ((Double.parseDouble(textFieldMaxTreeHeight.getText()) > 19.00)
					|| (Double.parseDouble(textFieldMaxTreeHeight.getText()) < 1.00))
				{
					JOptionPane.showMessageDialog(frame,"Max tree height must be > 0 and < 20");
					bIsAnyEmpty = true;
				}
				else if ((Double.parseDouble(textFieldMaxRunTime.getText()) > 15.00)
						|| (Double.parseDouble(textFieldMaxRunTime.getText()) < 1.00))
				{
					JOptionPane.showMessageDialog(frame,"Total running time must be > 0 and < 16");
					bIsAnyEmpty = true;
				}
				else if (
						(utils.isOverMaxProb(textFieldXOver.getText())) 
						|| (utils.isOverMaxProb(textFieldMut.getText()))
						|| (utils.isOverMaxProb(textFieldRepro.getText()))
						)
				{
					JOptionPane.showMessageDialog(frame,"(Crossover %, Mutation %, or Reproduction % must be >= 0 and <= 100)." 
							+ "\n Note: Crossover % + Mutation % + Reproduction % <= 100");
					bIsAnyEmpty = true;
				}
				else if (utils.isTotalOverMaxPro(textFieldXOver.getText(), textFieldMut.getText(), textFieldRepro.getText()))
				{
					JOptionPane.showMessageDialog(frame,"(Crossover % + Mutation % + Reproduction %) must be <= 100");
					bIsAnyEmpty = true;
				}
				else if (utils.isTotalEqualZero(textFieldXOver.getText(), textFieldMut.getText(), textFieldRepro.getText()))
				{
					JOptionPane.showMessageDialog(frame,"(Crossover % + Mutation % + Reproduction %) must be > 0");
					bIsAnyEmpty = true;
				}
				if (bIsAnyEmpty){
					return;
				}
				if (!bIsAnyEmpty) 
				{
					{
						String sYValues = "";
						sYValues = calculateY(textFieldX.getText());
						textFieldY.setText(sYValues);
					}
					
					setting.setPopSize(Integer.parseInt(textFieldPopSize.getText()));
					setting.setMaxGen(Integer.parseInt(textFieldMaxGen.getText()));
					setting.setMaxHeight(Integer.parseInt(textFieldMaxTreeHeight.getText()));
					setting.setMaxRunTime(Integer.parseInt(textFieldMaxRunTime.getText()));
					setting.setCrssOverProb(Float.parseFloat(textFieldXOver.getText()));
					setting.setMutationProb(Float.parseFloat(textFieldMut.getText()));
					setting.setTournamentSize(Integer.parseInt(textFieldTSize.getText()));
					setting.setReproductionProb(Float.parseFloat(textFieldRepro.getText()));
										
					String sTemp=""; //to hold an item and add it to the combobox
					sTemp = cmbInitTreeMethod.getSelectedItem().toString().toLowerCase();
					if (sTemp.equals("grow"))
					{
						setting.setInitMethod(1);
					}
					else if (sTemp.equals("full"))
					{
						setting.setInitMethod(2);
					}
					else  
					{
						setting.setInitMethod(3);
					}
					sTemp = cmbFitnessType.getSelectedItem().toString().toLowerCase();
					if (sTemp.equals("standard"))
					{
						setting.setFitnssType(1);
					}
					else 
					{
						setting.setFitnssType(2);
					}
					
					sTemp = cmbFitErrorType.getSelectedItem().toString().toLowerCase();
					if (sTemp.equals("squared"))
					{
						setting.setErrorType(1);
					}
					else if (sTemp.equals("scaled"))
					{
						setting.setErrorType(2);
					}
					else 
					{
						setting.setErrorType(3);
					}
					textAreaSol.setText("");
					textFieldOutFitness.setText("Progressing . . .");
					textFieldRunTime.setText("Progressing . . .");
					lblTargetFuncConfirm.setText("");
					System.out.println("Current G" + generationCount);
					return;
				}
			}
		});
		
	}

/**
 * Updates the GUI with a new intermediate solution.
 * 
 * @param solution the new solution
 * @param generation the current generation count
 * @param maxGenerations the maximum generations being run
 */

	/* 
	 *  This method use or initialize some instant variables 
	 */
	protected String calculateY(String sXValues)
	{
		tnData = new TrainingData();

		String xTokens[] = new String[0];
		String sRet ="";
		xTokens = utils.splitString(sXValues, "[,]");
		if (!xTokens[0].isEmpty())
		{
			//calculate Y
			trainingSetSize = xTokens.length;
			xValues = new double[trainingSetSize]; 
			yValues = new double[trainingSetSize];
			for (int i= 0; i< trainingSetSize; i++)
			{
				xTokens[i] = xTokens[i].trim();
				if (!xTokens[i].isEmpty()) 
				{
					xValues[i] = Double.parseDouble(xTokens[i]);
					yValues[i] = ((xValues[i] * xValues[i]) - 1)/2; //hard-code the formulas
					
					// Set x,y Values to the array of training data
					tnData.setTrainingSetSize(trainingSetSize);
					tnData.setTrainingDataSet(xValues, yValues);
				}
				sRet = sRet + Double.toString(yValues[i]);
				if (i < trainingSetSize -1)
				{
					sRet = sRet + ",";
				}		
			}
		}
		return sRet;
	}
	private boolean timeToQuit()
	{
		return System.currentTimeMillis() > endTime;
	}
}