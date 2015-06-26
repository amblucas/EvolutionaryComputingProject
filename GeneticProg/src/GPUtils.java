/* This class handles all utilities such as file I/O or check input string */

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.awt.event.KeyEvent;
//import javax.swing.JProgressBar;

public class GPUtils {
	
	Properties m_configFile = new Properties();
	String m_sConfigFileName = "settings.cfg";
	
	public GPUtils()
	{	
	}
	//read data from a configuration file
	public void readConfigFile()
	{
		m_configFile = new java.util.Properties();
		try {
		    InputStream iS = new FileInputStream(m_sConfigFileName);
		    m_configFile.load(iS);
		}
		catch(Exception eta){
		    eta.printStackTrace();
		}
	}
	
	public String getProperty(String key)
	{
		String sValue = m_configFile.getProperty(key);		
		return sValue;
	}

	// get key code enter to the X values edit box 
	// return true of the key is numeric and comma.
	public boolean isXInputValid(KeyEvent kEvent)
	{
		if ((kEvent.getKeyChar() >= '0' && kEvent.getKeyChar() <= '9') 
				 || kEvent.getKeyChar() == ',' || kEvent.getKeyChar() == '\b' 
					|| (kEvent.getKeyCode() == 127) || (kEvent.getKeyCode() == 27) || (kEvent.getKeyCode() == 10)
					|| (kEvent.getKeyCode() == 155) || (kEvent.getKeyCode() == 45) 
					|| (kEvent.getKeyCode() >= 35 && kEvent.getKeyCode() <= 40 )
					|| (kEvent.getKeyCode() >= 112 && kEvent.getKeyCode() <= 121)
					|| (kEvent.getKeyCode() >=16 && kEvent.getKeyCode() <= 20)
					//function key such as backspace, arrow and delete
					) 
		{
			return true;
		}
		else {
			return false;
		}
	}
	// get key code enter to the number box
	// return true of the key is numeric
	public boolean isNumInputValid(KeyEvent kEvent)
	{
		if ((kEvent.getKeyChar() >= '0' && kEvent.getKeyChar() <= '9') 
				 	|| kEvent.getKeyChar() == '\b' 
					|| (kEvent.getKeyCode() == 127) || (kEvent.getKeyCode() == 27)
					|| (kEvent.getKeyCode() == 155) || (kEvent.getKeyCode() == 10) //enter    
					|| (kEvent.getKeyCode() >= 35 && kEvent.getKeyCode() <= 40 )
					|| (kEvent.getKeyCode() >= 112 && kEvent.getKeyCode() <= 121)
					|| (kEvent.getKeyCode() >=16 && kEvent.getKeyCode() <= 20)
					//function key such as backspace, arrow and delete
					) 
		{
			return true;
		}
		else {
			return false;
		}
	}
	//if the field is empty return true otherwise return false
	public boolean isFieldEmpty(String sText){
		boolean bRes = true;
		if (sText.isEmpty()) bRes = true;
		else bRes = false;
		return bRes;
	}
	public boolean isOverMaxProb(String sText)
	{
		boolean bRes = true;
		if (Integer.parseInt(sText) > 100)
			bRes = true;
		else bRes = false;
		return bRes;
	}
	public boolean isTotalOverMaxPro(String sXOver, String sMutation, String sRepro)
	{
		boolean bRes = true;
		if ((Integer.parseInt(sXOver) + Integer.parseInt(sMutation) + Integer.parseInt(sRepro)) > 100)
			bRes = true;
		else bRes = false;
		return bRes;
	}
	
	public boolean isTotalEqualZero(String sXOver, String sMutation, String sRepro)
	{
		boolean bRes = true;
		if (Integer.parseInt(sXOver) + Integer.parseInt(sMutation) + Integer.parseInt(sRepro) == 0)
		{
			bRes = true;

		}
		else bRes = false;
		return bRes;
	}
	
	public String[] splitString(String sInput, String sDelim)
	{
		String[] sRet = new String[0];
		if (sInput.isEmpty()) return sRet;
		if (sDelim.isEmpty()) return sRet;
		sRet = sInput.split(sDelim);
		return sRet;
	}
	
	public boolean isNumber(String s)
	{
		if (s == null) return false;
		
		try {
			Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}