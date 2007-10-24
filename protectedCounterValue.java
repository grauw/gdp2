
public class protectedCounterValue
{
	int value;
	
	public protectedCounterValue(int value)
	{
		this.value = value;
	}
	
	synchronized void addTo(int x)
	{
		value = value + x;
	}

}
