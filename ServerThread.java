import java.net.*;
import java.io.*;

public class ServerThread extends Thread
{
	Node node;
	Socket RecieveFrom;
	int portnr;
	int teller;
	
	public ServerThread(int portnr, Node node)
	{
		this.portnr = portnr;
		this.node = node;
		this.teller = node.buren.length;
	}
	
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(portnr);
			for(int i = 0; i < node.buren.length; i++) {
				Socket clientSocket = serverSocket.accept();
				if(GDP2.HULP)
					System.out.println("clientSocket: " + clientSocket.toString());
				OntvangThread ot = new OntvangThread(clientSocket, node, this);
				ot.start();
			}
			serverSocket.close();
			if(GDP2.HULP)
				System.out.println("serverSocket closed.");
			
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized int decreaseTeller() {
		return --this.teller;
	}
}
