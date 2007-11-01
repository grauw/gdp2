/**
 * 
 * @author mjgraaf
 * @id-nr: 3117308, 
 */
public class ProtectedCounterValue
{
	int value;
	
	/**
	 * 
	 * @param value
	 */
	public ProtectedCounterValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * 
	 * @param x
	 */
	synchronized void addTo(int x)
	{
		value = value + x;
	}
	
	/**
	 * 
	 * @return
	 */
	synchronized int getValue()
	{
		return value;
	}

}
