public class Fitness {
    private double[][] fitnessXValue;
    private double[][] fitnessYValue;
    private double[][] fitnessZValue;
    private double[][] fitnessNormal;
    private double[][] fitnessSquare;
    
    public Fitness()
    {
    	fitnessXValue = new double[0][0];
    	fitnessYValue = new double[0][0];
    	fitnessZValue = new double[0][0];
    	fitnessNormal = new double[0][0];
    	fitnessSquare = new double[0][0];
    }
    public Fitness(int populationSize, int trainingSetSize)
    {
    	fitnessXValue = new double[populationSize][trainingSetSize];
    	fitnessYValue = new double[populationSize][trainingSetSize];
    	fitnessZValue = new double[populationSize][trainingSetSize];
    	fitnessNormal = new double[populationSize][trainingSetSize];
    	fitnessSquare = new double[populationSize][trainingSetSize];
    }
    
    /***************/
    /* Get Methods */
    /***************/
    public double getFitnessXValue(int populationNum, int trainingSetNum)
    {
    	return fitnessXValue[populationNum][trainingSetNum];
    }
    public double getFitnessYValue(int populationNum, int trainingSetNum)
    {
    	return fitnessYValue[populationNum][trainingSetNum];
    }
    public double getFitnessZValue(int populationNum, int trainingSetNum)
    {
    	return fitnessZValue[populationNum][trainingSetNum];
    }
    public double getFitnessNormal(int populationNum, int trainingSetNum)
    {
    	return fitnessNormal[populationNum][trainingSetNum];
    }
    public double getFitnessNormalSquared(int populationNum, int trainingSetNum)
    {
    	return fitnessNormal[populationNum][trainingSetNum] * fitnessNormal[populationNum][trainingSetNum];
    }
    public double getFitnessSquare(int populationNum, int trainingSetNum)
    {
    	return fitnessSquare[populationNum][trainingSetNum];
    }
    
    /***************/
    /* Set Methods */
    /***************/
    public void setFitnessXValue(int populationNum, int trainingSetNum, double xValue)
    {
    	fitnessXValue[populationNum][trainingSetNum] = xValue;
    }
    public void setFitnessYValue(int populationNum, int trainingSetNum, double yValue)
    {
    	fitnessYValue[populationNum][trainingSetNum] = yValue;
    }
    public void setFitnessZValue(int populationNum, int trainingSetNum, double zValue)
    {
    	fitnessZValue[populationNum][trainingSetNum] = zValue;
    }
    public void setFitnessNormal(int populationNum, int trainingSetNum, double result)
    {
    	fitnessNormal[populationNum][trainingSetNum] = result;
    }
    public void setFitnessSquare(int populationNum, int trainingSetNum, double result)
    {
    	fitnessSquare[populationNum][trainingSetNum] = result;
    }
    
    /*****************/
    /* Other Methods */
    /*****************/
    public void printFitness(int populationSize, int trainingSetSize)
    {
    	for(int i=0;i<populationSize;i++)
    	{
    		for(int j=0;j<trainingSetSize;j++)
    		{
    			System.out.print(" x=" + Double.toString(fitnessXValue[i][j]) +
    				    	     " y=" + Double.toString(fitnessYValue[i][j]) +
    				    	     " y=" + Double.toString(fitnessZValue[i][j]) +
    					         " e=" + Double.toString(fitnessNormal[i][j]) +
    					         " s=" + Double.toString(fitnessSquare[i][j]));
        		System.out.println(" ");
    		}
    	}
    }
    public void printPopulation(int populationNum, int trainingSetSize)
    {
    	for(int j=0;j<trainingSetSize;j++)
    	{
    		System.out.print(" x=" + Double.toString(fitnessXValue[populationNum][j]) +
    				    	 " y=" + Double.toString(fitnessYValue[populationNum][j]) +
    				    	 " z=" + Double.toString(fitnessZValue[populationNum][j]) +
    					     " e=" + Double.toString(fitnessNormal[populationNum][j]) +
    					     " s=" + Double.toString(fitnessSquare[populationNum][j]));
    		System.out.println(" ");
    	}
    }
}