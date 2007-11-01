/**
 * Een Node gaat wachten op een connectie. Pas als er een eerste connectie is
 * ontvangen gaat hij berichten versturen naar al zijn buren, behalve de parent.
 * De uitzondering hierop is uiteraard de initiator, die al direct begint met
 * het versturen van de berichten naar al zijn buren. Als er vervolgens van alle
 * buren wat is ontvangen, dan wordt er een bericht naar de parent verstuurd,
 * met de waarde van de node en diens kinderen.
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 
 */
public class Node
{
	int value, port, parentPort = -1;
	int[] buren;
	boolean isInitiator = false;

	boolean hasParent = false;
	ProtectedCounterValue counter;
	
	/**
	 * 
	 * @param value
	 * @param port
	 * @param buren
	 */
	public Node (int value, int port, int[] buren)
	{
		this.value = value;
		this.port = port;
		this.buren = buren;
		
		counter = new ProtectedCounterValue(value);
		
		if (port == 9999) {
			isInitiator = true;
			parentPort = -2;
		}
		
		if(GDP2.HULP)
			printNode();
		
		wachtOpConnectie();
		
		if (isInitiator) {
			//Initiator mag geen parent hebben dus wordt deze alvast op true gezet
			hasParent = true;
			
			verzendBuren();
		}
	}
	
	/**
	 * Deze methode print de waarden van de node uit als hulpmiddel. De buren en
	 * de counter zijn hieruit gelaten, omdat deze op andere plekken ook al
	 * geprint worden.
	 * 
	 */
	public void printNode()
	{
		System.out.println("--------------------");
		System.out.println("Eigen poort:"+port);
		System.out.println("Value: "+value);
		System.out.println("ParentPort: " +parentPort);
		System.out.println("Has a Parent: "+hasParent);
		System.out.println("Is the initiator: "+isInitiator);
		System.out.println("--------------------");
	}
	
	/**
	 * Deze methode maakt een nieuwe ServerThread en start deze, zodat hij gaat
	 * luisteren op zijn poort naar nieuwe binnenkomende connecties.
	 * 
	 */
	public void wachtOpConnectie()
	{
		ServerThread ot = new ServerThread(port, this);
		ot.start();
	}
	
	/**
	 * Deze methode stuurt de waarde van de node, plus de waardes van de
	 * kinderen, door naar de Parent van de node. Als de node de initiator is
	 * dan wordt dat afgevangen en wordt er niks verstuurd.
	 * 
	 */
	public void verstuurNaarParent()
	{
		if (isInitiator)
			return;
		
		ClientThread t;
		
		try
		{
			if(GDP2.HULP)
				System.out.println("counterValue: " + counter.getValue());
			t = new ClientThread(parentPort, counter.getValue(), port);
			// niet nodig nieuwe thread te starten
			t.verzendBericht();
		}
		catch(Exception e)
		{}
	}
	
	/**
	 * Deze methode verstuurd een bericht met de waarde 0, naar alle buren van
	 * de node. We sturen de waarde 0, zodat deze zorgeloos kan worden opgeteld
	 * bij de counter van de buur node en dat dit niet speciaal hoeft te worden
	 * afgevangen. Hierdoor hoeven we geen extra variabele mee te geven in de
	 * boodschap.
	 * 
	 */
	public void verzendBuren()
	{
		for (int buur: buren)
		{
			if(GDP2.HULP)
			{	
				System.out.println("Buur: " + buur);
				System.out.println("ParentPort: " + parentPort);
			}
			if(buur != parentPort)
			{	
				Thread t;
				try
				{
					if(GDP2.HULP)
						System.out.println("Buur != parentPort: " + buur);
					t = new ClientThread(buur, 0, port);
					t.start();
				}
				catch(Exception e)
				{}
			}
		}
	}
	
	/**
	 * In deze methode wordt de parent opgevraagd van de node en wordt deze op
	 * true gezet.
	 * 
	 * @return Geeft terug of de node een parent heeft.
	 */
	public synchronized boolean getParent() {
		boolean hasParent = this.hasParent;
		this.hasParent = true;
		return hasParent;
	}
}
