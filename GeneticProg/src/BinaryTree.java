import java.util.Stack;
import java.text.*;

public class BinaryTree{
    private TwoChildNode root;
    private Stack<Double> treeStack = new Stack<Double>();
    private Stack<Object> simplifyStack = new Stack<Object>();
    private int populationNum = 0;
    private int generationNum = 0;
    private double leftTerminal = 0;
    private double rightTerminal = 0;
    private double totalFitness = 0;
    private boolean hasDivideByZero = false;
    private boolean hasAnXTerminal = false;
    private boolean badTree = false;
    private GPUtils utils = new GPUtils();
	
    
    /****************/
    /* Constructors */
    /****************/
    public BinaryTree()
    {
        setRoot(null);
    }
    public BinaryTree(Object o)
    {
        setRoot(new TwoChildNode(o));
    }
    public BinaryTree(int thisPopulationNum, int thisGenerationNum)
    {
    	setRoot(null);
    	populationNum = thisPopulationNum;
    	generationNum = thisGenerationNum;
    }
    
    /***************/
    /* Get Methods */
    /***************/
    public boolean getBadTree()
    {
        return this.badTree;
    }
    public boolean getHasAnXTerminal()
    {
        return this.hasAnXTerminal;
    }
    public boolean getHasDivideByZero()
    {
        return this.hasDivideByZero;
    }
    public Object getData()
    {
        if(!isEmpty())
            return getRoot().getData();
        return null;
    }
    public TwoChildNode getLeft()
    {
        if(!isEmpty())
            return getRoot().getLeft();
        return null;
    }
    public TwoChildNode getRight()
    {
        if(!isEmpty())
            return getRoot().getRight();
        return null;
    }
    public double getTotalFitness()
    {
        return totalFitness;
    }
    public int getPopulationNum()
    {
        return populationNum;
    }
    public int getGenerationNum()
    {
        return generationNum;
    }
    protected TwoChildNode getRoot()
    {
        return root;
    }
    
    /***************/
    /* Set Methods */
    /***************/
    public void setBadTree(boolean thisBadTree)
    {
        this.badTree = thisBadTree;
        this.totalFitness = 99999999999999999999.9999999999;
    }
    public void setHasAnXTerminal(boolean thisHasAnXTerminal)
    {
        this.hasAnXTerminal = thisHasAnXTerminal;
    }
    public void setHasDivideByZero(boolean thisHasDivideByZero)
    {
        this.hasDivideByZero = thisHasDivideByZero;
    }
    public void setData(Object o)
    {
        if(!isEmpty())
            getRoot().setData(o);
    }
    public void setPopulationNum(int thisPopulationNum)
    {
        populationNum = thisPopulationNum;
    }
    public void setGenerationNum(int thisGenerationNum)
    {
        generationNum = thisGenerationNum;
    }
    protected void setRoot(TwoChildNode r)
    {
        root = r;
    }
    public void setTotalFitness(double thisTotalFitness)
    {
        totalFitness = thisTotalFitness;
    }
    
    /*****************/
    /* Other Methods */
    /*****************/
    protected void crossOverTree(TwoChildNode n1, TwoChildNode n2)
    {
    	// crossover method
    	TwoChildNode crossOverNodeLeft = new TwoChildNode();
    	crossOverNodeLeft = n1.getLeft();
    	TwoChildNode crossOverNodeRight = new TwoChildNode();
    	crossOverNodeRight = n2.getRight();
    	n1.setLeft(crossOverNodeRight);
    	n2.setRight(crossOverNodeLeft);
    }
    protected void mutateTree(TwoChildNode n)
    {
    	TwoChildNode leftChildNode = new TwoChildNode();
    	TwoChildNode rightChildNode = new TwoChildNode();
    	leftChildNode = n.getLeft();
    	rightChildNode = n.getRight();
    	if(leftChildNode == null && rightChildNode == null)
    	{
    		n.mutateTerminalNode(n);
    	}
    	else
    	{
    		this.mutateTree(leftChildNode);
    		this.mutateTree(rightChildNode);
    		n.mutateOperatorNode(n);
    	}
    }
    protected double evaluateTree(TwoChildNode n, double xValue, boolean rootNode)
    {
        double result = 0;
        Object o = new Object();
        String nodeData = new String();

       	if(n == null)
            return 0;
        this.evaluateTree(n.getLeft(), xValue, false);
        this.evaluateTree(n.getRight(), xValue, false);
        o = n.getData();
        nodeData = o.toString();
        if (nodeData.equals("/") || nodeData.equals("*") ||
       	    nodeData.equals("+") || nodeData.equals("-"))
        {
        	rightTerminal = treeStack.pop();
        	//System.out.print("pop" + rightTerminal);
        	leftTerminal = treeStack.pop();
        	//System.out.print("pop" + leftTerminal);
        	if(nodeData.equals("/"))
        	{
        		if(rightTerminal == 0)
        		{
        			//System.out.print("push" + (leftTerminal / 0.000000001));
        			this.setBadTree(true);
        			treeStack.push(leftTerminal / 0.000000001);
        		}
        		else
        		{
        			//System.out.print("push" + (leftTerminal / rightTerminal));
                	treeStack.push(leftTerminal / rightTerminal);
        		}
        	}
        	else if(nodeData.equals("+"))
        	{
        		//System.out.print("push" + (leftTerminal + rightTerminal));
            	treeStack.push(leftTerminal + rightTerminal);
        	}
        	else if(nodeData.equals("-"))
        	{
        		//System.out.print("push" + (leftTerminal - rightTerminal));
            	treeStack.push(leftTerminal - rightTerminal);
        	}
        	else if(nodeData.equals("*"))
        	{
        		//System.out.print("push" + (leftTerminal * rightTerminal));
            	treeStack.push(leftTerminal * rightTerminal);
        	}
        }
        else if (nodeData.equals("x"))
        {
        	//System.out.print("replace x with" + Double.toString(xValue) + "and push");
        	this.setHasAnXTerminal(true);
        	treeStack.push(xValue);
        }
        else
        {
        	//System.out.print("push" + nodeData);
        	if (nodeData.equals("0"))
        		treeStack.push(0.0);
        	else if (nodeData.equals("1"))
        		treeStack.push(1.0);
        	else if (nodeData.equals("2"))
        		treeStack.push(2.0);
        	else if (nodeData.equals("3"))
        		treeStack.push(3.0);
        	else if (nodeData.equals("4"))
        		treeStack.push(4.0);
        	else if (nodeData.equals("5"))
        		treeStack.push(5.0);
        	else if (nodeData.equals("6"))
        		treeStack.push(6.0);
        	else if (nodeData.equals("7"))
        		treeStack.push(7.0);
        	else if (nodeData.equals("8"))
        		treeStack.push(8.0);
        	else if (nodeData.equals("9"))
        		treeStack.push(9.0);
        }
        
        if(rootNode)
        {
        	result = treeStack.pop();
        	//System.out.println("pop final result" + result);
        }
        return result;
    }
    public void insertLeft(TwoChildNode p,Object o)
    {
        if((p != null) && (p.getLeft() == null))
            p.setLeft(new TwoChildNode(o));
    }
    public void insertRight(TwoChildNode p,Object o)
    {
        if((p != null) && (p.getRight() == null))
            p.setRight(new TwoChildNode(o));
    }
    public TwoChildNode initializeBinaryTree(int maxTreeHeight)
    {
    	TwoChildNode rootNode = new TwoChildNode();
		rootNode = rootNode.initializeNode(rootNode, maxTreeHeight, 1);
		return rootNode;
    }
    public void inOrderTraversal()
    {
        inOrderTraversal(getRoot());
    }
    protected void inOrderTraversal(TwoChildNode p)
    {
        if(p == null)
            return;
        inOrderTraversal(p.getLeft());
        System.out.print(p.getData()+" ");
        inOrderTraversal(p.getRight());
    }
    public TwoChildNode insertRightTerminal()
    {
		/*******************************************************/
		/*                   RIGHT TERMINAL                    */
		/*******************************************************/
    	TwoChildNode rightChild = new TwoChildNode();
		rightChild = insertRightTerminal();
		return rightChild;
    }
    public TwoChildNode insertLeftTerminal()
    {
		/*******************************************************/
		/*                    LEFT TERMINAL                    */
		/*******************************************************/
    	TwoChildNode leftChild = new TwoChildNode();
		leftChild = insertLeftTerminal();
		return leftChild;
    }
    public boolean isEmpty()
    {
        return getRoot() == null;
    }
    public void printNode(int mode)
    {
        if(mode == 1) preOrderTraversal();
        else if(mode == 2) inOrderTraversal();
        else if(mode == 3) postOrderTraversal();
    }
    public void postOrderTraversal()
    {
        postOrderTraversal(getRoot());
    }
    protected void postOrderTraversal(TwoChildNode p)
    {
        if(p == null)
            return;
       	postOrderTraversal(p.getLeft());
       	postOrderTraversal(p.getRight());
        System.out.print(p.getData()+" ");
    }
    public void preOrderTraversal()
    {
        preOrderTraversal(getRoot());
    }
    protected void preOrderTraversal(TwoChildNode p)
    {
        if(p == null)
            return;
        System.out.print(p.getData()+" ");
        preOrderTraversal(p.getLeft());
        preOrderTraversal(p.getRight());
    }
    /*
     *  Print the Tree 
     */
    protected String printOutputTree(TwoChildNode p)
    {
    	String sRes= "";
        if(p == null)
            return "";
        if (p.getLeft() == null && p.getRight() == null) 
        {
        	if ( p.getData().toString().substring(0,1).equals("-"))
        	{
        		sRes= sRes+ "(" + p.getData().toString() + ")";
        	}
        	else 
        	{
        		sRes= sRes+ p.getData().toString();
        	}
        }
        else 
        {
        	sRes = sRes + "(";
        	sRes= sRes+ printOutputTree(p.getLeft());
        	sRes= sRes+ p.getData().toString();
        	sRes= sRes+ printOutputTree(p.getRight());
        	sRes= sRes+ ")";
        }
        return sRes;
    }
    
    /* **************************
     * simplify a binary tree
     ************************** */
    protected double simplifyBinaryTree(TwoChildNode node, boolean rootNode)
    {
    	
    	Double dValue = 0.0;
    	Object leftTerminalNode = new Object();
    	Object rightTerminalNode = new Object ();
    	if(node == null)
            return 0;
    	    
    	this.simplifyBinaryTree(node.getLeft(), false);
    	this.simplifyBinaryTree(node.getRight(), false);
    	    	
    	Object o = new Object();
        o = node.getData();
        String nodeData = new String();
        nodeData = o.toString();
     
        if (nodeData.equals("/") || nodeData.equals("*") ||
       	    nodeData.equals("+") || nodeData.equals("-"))
        {
        	rightTerminalNode = simplifyStack.pop();
        	leftTerminalNode = simplifyStack.pop();
        	
        	if (utils.isNumber(rightTerminalNode.toString())
        			&& utils.isNumber(leftTerminalNode.toString())) 
        	{
        		if(nodeData.equals("/"))
        		{
        			if (Double.parseDouble(rightTerminalNode.toString()) == 0.0)
        			{
        				dValue = Double.parseDouble(leftTerminalNode.toString()) / 0.000000001;
        			}
        			else 
    				{
        				dValue = Double.parseDouble(leftTerminalNode.toString()) / Double.parseDouble(rightTerminalNode.toString()); 
    				}
	        	}
        		else if(nodeData.equals("+"))
        		{
	        		dValue = Double.parseDouble(leftTerminalNode.toString()) + Double.parseDouble(rightTerminalNode.toString()); 
	        	}
        		else if(nodeData.equals("-"))
        		{
	        		dValue = Double.parseDouble(leftTerminalNode.toString()) - Double.parseDouble(rightTerminalNode.toString()); 
	        	}
		        else if(nodeData.equals("*"))
		        {
		        	dValue = Double.parseDouble(leftTerminalNode.toString()) * Double.parseDouble(rightTerminalNode.toString()); 
		        	
		        }
        		DecimalFormat df = new DecimalFormat("#.##");
        	    node.setData(df.format(dValue));
        		node.left = null;
        		node.right = null;
        		
        		simplifyStack.push(df.format(dValue));
        	}
        	else 
        	{
        		if ((leftTerminalNode.toString().equals("+") || leftTerminalNode.toString().equals("-") || leftTerminalNode.toString().equals("*") || leftTerminalNode.toString().equals("/"))
        				|| (rightTerminalNode.toString().equals("+") || rightTerminalNode.toString().equals("-") || rightTerminalNode.toString().equals("*") || rightTerminalNode.toString().equals("/"))
        			){
        			simplifyStack.push(o);
        		}else {
        			if (leftTerminalNode.toString().equals(rightTerminalNode.toString())) { //leftTerminalNode=x ; rightTerminalNode=x
                		if(nodeData.equals("/"))
                		{
                			node.setData("1");
        			        node.left = null;
        			        node.right = null;
        			        simplifyStack.push("1");
        		         
        	        	}
                		else if(nodeData.equals("-"))
                		{
                			node.setData("0");
        			        node.left = null;
        			        node.right = null;
        			        simplifyStack.push("0");
                		}
                		else if(nodeData.equals("+"))
                		{
                			node.setData("2*x");
        			        node.left = null;
        			        node.right = null;
        			        simplifyStack.push("2*x");
        	        	}else {
        	        		simplifyStack.push(o); 
        	        	}
        			}else {
            			if (leftTerminalNode.toString().equals("1") && rightTerminalNode.toString().equals("x") ) {
            		        if(nodeData.equals("*"))
            		        {
            		        	node.setData("x");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("x");
            		        }else {
            		        	simplifyStack.push(o); 
            		        }
            			}
            			else if (leftTerminalNode.toString().equals("0") && rightTerminalNode.toString().equals("x")) {
                    		if(nodeData.equals("/") || nodeData.equals("*") )
                    		{
                    			node.setData("0");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("0");
            		         
            	        	}
                    		else if(nodeData.equals("+"))
                    		{
                    			node.setData("x");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("x");
            		        }
                    		else if(nodeData.equals("-"))
                    		{
                    			node.setData("-x");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("-x");
            	        	}
            			}
            			else if (leftTerminalNode.toString().equals("x") && rightTerminalNode.toString().equals("1")) {
            				if(nodeData.equals("/") || nodeData.equals("*"))
                    		{
                    			node.setData("x");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("x");
            		         
            	        	}
                    		else { // nodeData=+,-
                    			simplifyStack.push(o);
            		        }
            			}
            			else if (leftTerminalNode.toString().equals("x") && rightTerminalNode.toString().equals("0")) {
            				if(nodeData.equals("/"))
                    		{
                    			node.setData("x/0.000000001");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("x/0.000000001");
            					
            	        	}
                    		else if(nodeData.equals("+") || nodeData.equals("-"))
                    		{
                    			node.setData("x");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("x");
            			    }
            		        else if(nodeData.equals("*"))
            		        {
            		        	node.setData("0");
            			        node.left = null;
            			        node.right = null;
            			        simplifyStack.push("0");
            			    }
            			}
            			else {
            				simplifyStack.push(o);
            			}        				
        			}
        		}
        	}
        }
        else
        {
        	simplifyStack.push(o);
        }
        
    	return 1;
	}
}