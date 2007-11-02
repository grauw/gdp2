/**
 * De klasse ProtectedCounterValue houdt voor de node bij wat zijn waarde is,
 * plus de waardes van de kinderen.
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class ProtectedCounterValue
{
	int value;
	
	/**
	 * De constructor.
	 * @param value		De waarde van de node die hem aanmaakt.
	 */
	public ProtectedCounterValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * Telt de waarde x erbij op.
	 * @param x		De waarde die bij value moet worden opgeteld.
	 */
	public void addTo(int x)
	{
		if (x != 0)
			addToSynch(x);
	}
	
	/**
	 * Telt de waarde x erbij op.
	 * @param x		De waarde die bij value moet worden opgeteld.
	 */
	private synchronized void addToSynch(int x) {
		value = value + x;
	}
	
	/**
	 * Returned de waarde van de klasse.
	 * @return		value
	 */
	public synchronized int getValue()
	{
		return value;
	}

}
