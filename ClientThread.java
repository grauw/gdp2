import java.net.*;
import java.io.*;

public class ClientThread extends Thread
{
	int port;
	int value;
	int[] buren;
	
	public ClientThread(int port, int value, int[] buren)
	{
		this.port = port;
		this.value = value;
		this.buren = buren;
	}
	
	public void run()
	{
		verzendBericht();
	}
	
	private void verzendBericht()
	{
		try
		{
			Socket sendTo = new Socket("localhost", port) ;
			OutputStream ostream = sendTo.getOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(ostream);
//			os.flush();
			
			os.writeObject(new ObjectMessage(value));
			
//			os.flush();
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
