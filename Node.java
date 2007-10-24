public class Node
{
	int value, port, parentPort = -1;
	int[] buren;
	int[] children;
	boolean isInitiator = false;
	boolean isParent = false;
	
	
	public Node (int value, int port, int[] buren)
	{
		this.value = value;
		this.port = port;
		this.buren = buren;
		if (port == 9999) {
			isInitiator = true;
			parentPort = -2;
		}
		
		ServerThread ot = new ServerThread(port, this);
		ot.start();
		
		if (isInitiator) {
			VerzendBuren();
		}
	}
	
	public void VerzendBuren()
	{
		for (int buur: buren)
		{
			Thread t;
			try
			{
				t = new ClientThread(buur, value, buren);
				t.start();
			}
			catch(Exception e)
			{}
		}
		
		// aggregeer alle return values en eigen value en stuur door naar parent
	}
	
	public void PaasTerug()
	{

	}
	
	public void WordParent()
	{
		
	}
}
