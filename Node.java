/**
 * 
 * @author mjgraaf, lholst
 *
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
		else {
//			VerstuurNaarParent();
		}
	}
	
	/**
	 * 
	 *
	 */
	public void printNode()
	{
		System.out.println("--------------------");
		System.out.println("Eigen poort:"+port);
		System.out.println("Value: "+value);
		System.out.println("ParentPort: " +parentPort);
		System.out.println("Has a Parent: "+hasParent);
		System.out.println("--------------------");
	}
	
	/**
	 * 
	 * @param x
	 */
	public void sleeper(int x)
	{
		try{
			Thread.sleep(x);
		} catch(Exception e) {}
	}
	
	/**
	 * 
	 *
	 */
	public void wachtOpConnectie()
	{
		ServerThread ot = new ServerThread(port, this);
		ot.start();
	}
	
	/**
	 * 
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
	 * 
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
	 * 
	 * @return
	 */
	public synchronized boolean getParent() {
		boolean hasParent = this.hasParent;
		this.hasParent = true;
		return hasParent;
	}
}
