public class Node
{
	int value, port, parentPort = -1;
	int[] buren;
	boolean isInitiator = false;

	boolean hasParent = false;
	protectedCounterValue counter;
	
	
	public Node (int value, int port, int[] buren)
	{
		this.value = value;
		this.port = port;
		this.buren = buren;
		
		counter = new protectedCounterValue(value);
		
		if (port == 9999) {
			isInitiator = true;
			parentPort = -2;
		}
		
		WachtOpConnectie();
		
		if (isInitiator) {
			//Initiator mag geen parent hebben dus wordt deze alvast op true gezet
			hasParent = true;
			

			
			VerzendBuren();

			//We moeten wachten totdat alle threads zeker weten klaar zijn.
			System.out.println("Resultaat: " + counter.getValue());
		}
		else {
//			VerstuurNaarParent();
		}
	}
	
	public void printNode()
	{
		System.out.println("--------------------");
		System.out.println("Eigen poort:"+port);
		System.out.println("Value: "+value);
		System.out.println("ParentPort: " +parentPort);
		System.out.println("Has a Parent: "+hasParent);
		System.out.println("--------------------");
	}
	
	public void sleeper(int x)
	{
		try{Thread.sleep(x);}catch(Exception e){}
	}
	
	public void WachtOpConnectie()
	{
		ServerThread ot = new ServerThread(port, this);
		ot.start();
	}
	
	public void VerstuurNaarParent()
	{
		if (isInitiator)
			return;
		
		ClientThread t;
		
		try
		{
			System.out.println("counterValue: " + counter.getValue());
			t = new ClientThread(parentPort, counter.getValue(), parentPort, port);
			t.verzendBericht();
		}
		catch(Exception e)
		{}
	}
	
	public void VerzendBuren()
	{
		int y;
		if(parentPort==-1||parentPort ==-2)
			y = 2000;
		else
			y = parentPort;
		for (int buur: buren)
		{
			System.out.println("Buur: " + buur);
			System.out.println("ParentPort: " + parentPort);
			if(buur != parentPort)
			{	
				Thread t;
				try
				{
					System.out.println("Buur != parentPort: " + buur);
					t = new ClientThread(buur, 0, y, port);
					t.start();
				}
				catch(Exception e)
				{}
			}
		}
	}
}
