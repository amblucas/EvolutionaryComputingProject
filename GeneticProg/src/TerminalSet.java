import java.util.Random;

public class TerminalSet {

	public TerminalSet()
	{		
	}

	public char getRandomOperator (boolean noDivision)
	{
		Random rand = new Random();
		int tmp = 0;
		if (noDivision)
		{
			tmp = rand.nextInt(3);
		}
		else
		{
			tmp = rand.nextInt(4);
		}
		switch(tmp) 
		{
       		case 0:
       			return '+';
       		case 1:
       			return '-';
       		case 2:
       			return '*';
       		case 3:
       			return '/';
		}
		return ' ';
	}

	public char getRandomOperand ()
	{
		Random rand = new Random();
		int temp = rand.nextInt(11);
		switch(temp)
		{
			case 0:
				return '0';
			case 1:
				return '1';
			case 2:
				return '2';
			case 3:
				return '3';
			case 4:
				return '4';
			case 5:
				return '5';
			case 6:
				return '6';
			case 7:
				return '7';
			case 8:
				return '8';
			case 9:
				return '9';
			case 10:
				return 'x';
		}
		return ' ';
	}

	public boolean getRandomBoolean ()
	{
		Random rand = new Random();
		int temp = rand.nextInt(2);
		switch(temp)
		{
			case 0:
				return false;
			case 1:
				return true;
		}
		return true;
	}
}