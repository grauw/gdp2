/**
 * Een Node gaat wachten op een connectie, dit gebeurd door middel van een
 * ServerThread. Alleen de initiator verstuurd ook meteen al naar alle buren
 * berichten om van hun de parent te worden. In de klasse zit verder ook nog de
 * methode om naar je Parent het bericht met de node waarde en de waardes van de
 * kinderen te sturen.
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class Node
{
	int value;
	int port;
	int parentPort = -1;
	int[] buren;
	boolean hasParent = false;
	ProtectedCounterValue counter;	// Een counter die de waardes van de node en diens kinderen bewaard.
	AfTeller burenTeller;			// Een teller die bijhoudt of van alle buren al wat ontvangen is 
	
	/**
	 * De constructor van de node. Hier worden de waardes geinitialiseerd en word er op een connectie gewacht.
	 * De initiator verzend de berichten naar de buren.
	 * @param value   De waarde van de node.
	 * @param port    De poort van de node.
	 * @param buren   Alle buren van de node.
	 */
	public Node (int value, int port, int[] buren)
	{
		this.value = value;
		this.port = port;
		this.buren = buren;
		
		counter = new ProtectedCounterValue(value);
		burenTeller = new AfTeller(buren.length);
		
		if(GDP2.HULP)
			printNode();
		
		wachtOpConnectie();		//Het wachten op de connectie van de eerste buur
		
		if (isInitiator()) {		//Als het de initiator is verzend naar buren
			
			hasParent = true;	//Initiator kan geen parent hebben, dus wordt deze alvast op true gezet.
			
			verzendBuren();
		}
	}
	
	public boolean isInitiator() {
		return port == 9999;
	}
	
	/**
	 * Deze methode print de waarden van de node uit als hulpmiddel. De buren en
	 * de counter zijn hieruit gelaten, omdat deze op andere plekken ook al
	 * geprint worden.
	 */
	public void printNode()
	{
		System.out.println("--------------------");
		System.out.println("Eigen poort:"+port);
		System.out.println("Value: "+value);
		System.out.println("ParentPort: " +parentPort);
		System.out.println("Has a Parent: "+hasParent);
		System.out.println("Is the initiator: "+isInitiator());
		System.out.println("--------------------");
	}
	
	/**
	 * Deze methode maakt een nieuwe ServerThread en start deze, zodat hij gaat
	 * luisteren op zijn poort naar nieuwe binnenkomende connecties.
	 */
	public void wachtOpConnectie()
	{
		ServerThread ot = new ServerThread(port, this);
		ot.start();
	}
	
	/**
	 * Stuur de opgetelde waarde van de node en de waardes van de kinderen door
	 * naar de parent van de node. Als de node de initiator is dan wordt er niks
	 * verstuurd, en wordt in plaats daarvan het resultaat geprint.
	 */
	public void verstuurNaarParent()
	{
		if (isInitiator()) {
			System.out.println("Resultaat: " + counter.getValue());
			return;
		}
		
		ClientThread t;
		
		try
		{
			if(GDP2.HULP)
				System.out.println("counterValue: " + counter.getValue());
			// niet nodig nieuwe thread te starten
			t = new ClientThread();
			t.verzendBericht(parentPort, counter.getValue(), port);
		}
		catch(Exception e)
		{}
	}
	
	/**
	 * Deze methode verstuurt een bericht naar de buur-nodes om van deze nodes
	 * hun parent te worden, als deze nog geen paren hebben. De waarde die in
	 * het bericht wordt meegegeven is 0, zodat deze zorgeloos kan worden
	 * opgeteld bij de counter van de buurnode. Hierdoor hoeven we geen extra
	 * variabele mee te geven in de boodschap.
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
			if(buur != parentPort)		//Hierdoor wordt er niet naar de parent verstuurd.
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
	 * In deze methode wordt getest of deze node al een parent heeft,
	 * en daarna wordt geset dat deze een parent heeft (“test and set”).
	 * 
	 * @return Geeft terug of de node al een parent had.
	 */
	public synchronized boolean getParent() {
		boolean hasParent = this.hasParent;
		this.hasParent = true;
		return hasParent;
	}
}
