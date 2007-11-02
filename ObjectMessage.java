import java.io.Serializable;

/**
 * Een object dat een bericht bevat om te verzenden
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class ObjectMessage implements Serializable
{
	static final long serialVersionUID = 30769067412394123L;
	
	private int value;
	private int ontvangenPort;
	
	/**
	 * De constructor van ObjectMessage.
	 * @param value	Waarde die meegestuurd wordt. 
	 * @param ontvangenPort	De poort van degene die de ObjectMessage verstuurd.
	 */
	public ObjectMessage(int value, int ontvangenPort)
	{
		this.ontvangenPort = ontvangenPort;
		this.value = value;
	}
	
	/**
	 * Get de value
	 * @return value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get de ontvangenPort
	 * @return ontvangenPort
	 */
	public int getOntvangenPort() {
		return this.ontvangenPort;
	}
}
