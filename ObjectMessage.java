import java.io.Serializable;


public class ObjectMessage implements Serializable
{
	static final long serialVersionUID = 30769067412394123L;
	
	int value;
	boolean amChild;
	
	public ObjectMessage(int value)
	{
		this.value = value;
	}
}
