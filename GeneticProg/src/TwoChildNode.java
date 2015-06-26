public class TwoChildNode{
    protected Object data;
    protected TwoChildNode left,right;
    protected TerminalSet term = new TerminalSet();

    /****************/
    /* Constructors */
    /****************/
    public TwoChildNode(){
        data = null;
        left = right = null;
    }
    public TwoChildNode(Object d){
        data = d;
        left = right = null;
    }

    /***************/
    /* Get Methods */
    /***************/
    public Object getData(){
        return data;
    }
    public TwoChildNode getLeft(){
        return left;
    }
    public TwoChildNode getRight(){
    	return right;
    }

    /***************/
    /* Set Methods */
    /***************/
    public void setData(Object d){
        data = d;
    }
    public void setLeft(TwoChildNode l){
        left = l;
    }
    public void setRight(TwoChildNode r){
        right = r;
    }
    
    /*****************/
    /* Other Methods */
    /*****************/
    public TwoChildNode mutateOperatorNode(TwoChildNode thisNode)
    {
    	if(term.getRandomBoolean())
    	{
    		char operatorChar = term.getRandomOperator(false);
    		Character operatorCharacter = new Character(operatorChar);
    		thisNode.setData(operatorCharacter);
    	}
    	return thisNode;
    }
    public TwoChildNode mutateTerminalNode(TwoChildNode thisNode)
    {
    	if(term.getRandomBoolean())
    	{
    		char newTermChar = term.getRandomOperand();
    		Character newTermCharacter = new Character(newTermChar);
    		thisNode.setData(newTermCharacter);
    	}
    	return thisNode;
    }
    public TwoChildNode initializeNode(TwoChildNode thisNode, int maxTreeHeight, int currentTreeHeight)
    {
		char operatorChar = term.getRandomOperator(false);
    	Character operatorCharacter = new Character(operatorChar);
    	thisNode.setData(operatorCharacter);

    	if(maxTreeHeight == currentTreeHeight)
    	{
        	for(int a=0;a<2;a++)
        	{
        		TwoChildNode newTerminal = new TwoChildNode();
        		char newTermChar = term.getRandomOperand();
      			Character newTermCharacter = new Character(newTermChar);
       			if(a==0)
       			{
       				thisNode.setRight(newTerminal);
       				newTerminal.setData(newTermCharacter);
       			}
       			else
       			{
       				thisNode.setLeft(newTerminal);
       				newTerminal.setData(newTermCharacter);
       			}
        	}
    	}
    	else
    	{
    		for(int a=0;a<2;a++)
    		{
    			TwoChildNode newNode = new TwoChildNode();
    			if(a==0)
    				thisNode.setRight(newNode);
    			else
    				thisNode.setLeft(newNode);
        		newNode.initializeNode(newNode,maxTreeHeight,currentTreeHeight+1);
    		}
    	}
    	return thisNode;
    }
    public TwoChildNode initializeNode(TwoChildNode thisNode)
    {
		char operatorChar = term.getRandomOperator(true);
    	//System.out.println("operator = " + operatorChar);
		Character operatorCharacter = new Character(operatorChar);
    	thisNode.setData(operatorCharacter);

    	for(int a=0;a<2;a++)
    	{
    		TwoChildNode newTerminal = new TwoChildNode();
    		char newTermChar = term.getRandomOperand();
    		{
    			Character newTermCharacter = new Character(newTermChar);
    			if(a==0)
    			{
    				//System.out.println("right terminal = " + newTermChar);
    				thisNode.setRight(newTerminal);
    				newTerminal.setData(newTermCharacter);
    			}
    			else
    			{
    				//System.out.println("left terminal = " + newTermChar);
    				thisNode.setLeft(newTerminal);
    				newTerminal.setData(newTermCharacter);
    			}
    		}
    	}
    	return thisNode;
    }
    public String toString(){
        return ""+data;
    }
}