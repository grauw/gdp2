
public class ProtectedCounterValue_GAY
{
	int value;
	
	public ProtectedCounterValue_GAY(int value)
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
