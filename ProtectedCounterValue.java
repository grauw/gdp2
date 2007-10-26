
public class ProtectedCounterValue
{
	int value;
	
	public ProtectedCounterValue(int value)
	{
		this.value = value;
	}
	
	synchronized void addTo(int x)
	{
		value = value + x;
	}
	
	synchronized int getValue()
	{
		return value;
	}

}
