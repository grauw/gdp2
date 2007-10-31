import java.io.Serializable;


public class ObjectMessage implements Serializable
{
	static final long serialVersionUID = 30769067412394123L;
	
	int value, ontvangenPort;
	
	public ObjectMessage(int value, int ontvangenPort)
	{
		this.ontvangenPort = ontvangenPort;
		this.value = value;
	}
}
