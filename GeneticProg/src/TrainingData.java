/* 
 * This class handles all behaviors and keep properties related to Training Data
 */
public class TrainingData {
	private double m_arrTrainingSet[][]; //the array that keeps training data 
	private int m_iTrainingSetSize;

    /****************/
    /* Constructors */
    /****************/
	TrainingData() 
	{
		// nothing
	}
	
    /***************/
    /* Get Methods */
    /***************/
	public double[][] getTrainingDataSet()
	{
		return m_arrTrainingSet;
	}
	public int getTrainingSetSize() 
	{
		return m_iTrainingSetSize;
	}
	
    /***************/
    /* Set Methods */
    /***************/
	public void setTrainingDataSet(double [] xValues, double [] yValues) {
		int iTrainingSetSize = getTrainingSetSize();
		m_arrTrainingSet = new double [iTrainingSetSize][2];
		for (int i=0; i <iTrainingSetSize; i++){
				m_arrTrainingSet[i][0] = xValues[i];
				m_arrTrainingSet[i][1] = yValues[i];
		}
		//for (int i=0; i<iTrainingSetSize; i++){
		//	for (int j=0; j<2; j++){
		//		System.out.println("Training Set "+i+','+j+ " :"+ m_arrTrainingSet[i][j]);
		//	}
		//}
	}
	public void setTrainingSetSize(int iTrainingSetSize) {
		m_iTrainingSetSize = iTrainingSetSize;
	}
}
