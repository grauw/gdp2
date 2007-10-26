import java.io.Serializable;


public class ObjectMessage implements Serializable
{
	static final long serialVersionUID = 30769067412394123L;
	
	int value, ontvangenPort;
	boolean amChild;
	
	public ObjectMessage(int value, boolean amChild, int ontvangenPort)
	{
		this.ontvangenPort = ontvangenPort;
		this.value = value;
		this.amChild = amChild;
	}
}
