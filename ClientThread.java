import java.net.*;
import java.io.*;

public class ClientThread extends Thread
{
	int port;
	int value;
	int parentPort;
	int ownPort;
	
	public ClientThread(int port, int value, int parentPort, int ownPort)
	{
		this.port = port;
		this.value = value;
		this.parentPort = parentPort;
		this.ownPort = ownPort;
	}
	
	public void run()
	{
		verzendBericht();
	}
	
	public void verzendBericht()
	{
		boolean amChild = false;
		
		if(parentPort == port)
			amChild = true;
		
		try
		{
			Socket sendTo = new Socket("localhost", port) ;
			OutputStream ostream = sendTo.getOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(ostream);
			os.flush();
			
			System.out.println("Verstuurde waarde: " + value);
			os.writeObject(new ObjectMessage(value, amChild, ownPort));
			
			os.flush();
			ostream.close();
		}
		catch(IOException q)
		{
			try
			{
				Thread.sleep(200);
				verzendBericht();
			}
			catch(InterruptedException e)
			{}
		}
	}

}
