//This class stores/retrieves all properties regarding settings or configs
// To add a property, remember to add to methods 'Set' and 'Get'

public class Settings {
	int m_PopSize;
	int m_MaxGen;
	int m_MaxRunTime, m_MaxHeight;
	float m_CrssOverProb, m_MutationProb, m_ReproductionProb;
	int m_TourSize; 
	int m_InitMethod; //1: Full, 2: Growth, 3:Mix
	int m_ErrorType;  //1: Square, 2:Error , 3:scaled
	int m_MaxStringLen;
	int m_FitnessType;
	
	public Settings()
	{
		//
	}

	//Population Size
	public void setPopSize(int iPopSize)
	{
		m_PopSize = iPopSize;
	}
	public int getPopSize()
	{
		return m_PopSize;
	}
	
	//Max Generation
	public void setMaxGen(int iMaxGen)
	{
		m_MaxGen = iMaxGen;
	}
	public  int getMaxGen()
	{
		return m_MaxGen;
	}

	//Max running time (minutes)
	public void setMaxRunTime(int iMaxRunTime)
	{
		m_MaxRunTime = iMaxRunTime;
	}
	public  int getMaxRunTime()
	{
		return m_MaxRunTime;
	}
	
	//Max Height
	public void setMaxHeight(int iMaxHeight)
	{
		m_MaxHeight = iMaxHeight;
	}
	public  int getMaxHeight()
	{
		return m_MaxHeight;
	}
	
	//Crossover probability
	public void setCrssOverProb(float flCrossOver)
	{
		m_CrssOverProb = flCrossOver;
	}
	public  float getCrssOverProb()
	{
		return m_CrssOverProb;
	}
	
	//Mutation
	public void setMutationProb(float flMutation)
	{
		m_MutationProb = flMutation;
	}
	public  float getMutationProb()
	{
		return m_MutationProb;
	}
	
	//Reproduction
	public void setReproductionProb(float flReproduction)
	{
		m_ReproductionProb = flReproduction;
	}
	public  float getReproductionProb()
	{
		return m_ReproductionProb;
	}
	
	//Tournament
	public void setTournamentSize(int iTourSize)
	{
		m_TourSize = iTourSize;
	}
	public  int getTournamentSize()
	{
		return m_TourSize;
	}
	
	//Init Method
	public void setInitMethod(int iInitMethod)
	{
		m_InitMethod = iInitMethod;
	}
	public  int getInitMethod()
	{
		return m_InitMethod ;
	}
	
	//ErrorType
	public void setFitnssType(int iFitType)
	{
		m_FitnessType = iFitType;
	}
	public  int getFitnessType()
	{
		return m_FitnessType;
	}
	
	//ErrorType
	public void setErrorType(int iErrorType)
	{
		m_ErrorType = iErrorType;
	}
	public  int getErrorType()
	{
		return m_ErrorType;
	}
	
	//Max String Len
	public void setMaxStringLen(int iMaxStrLen)
	{
		m_MaxStringLen = iMaxStrLen;
	}
	public  int getMaxStringLen()
	{
		return m_MaxStringLen;
	}
}