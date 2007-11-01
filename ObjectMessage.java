import java.io.Serializable;

/**
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 
 */
public class ObjectMessage implements Serializable
{
	static final long serialVersionUID = 30769067412394123L;
	
	int value, ontvangenPort;
	
	/**
	 * 
	 * @param value
	 * @param ontvangenPort
	 */
	public ObjectMessage(int value, int ontvangenPort)
	{
		this.ontvangenPort = ontvangenPort;
		this.value = value;
	}
}
