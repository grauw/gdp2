import java.net.*;
import java.io.*;

/**
 * 
 * @author mjgraaf, lhost
 * @id-nr: 3117308,  
 *
 */
public class ServerThread extends Thread
{
	Node node;
	int portnr;
	int teller;
	
	/**
	 * 
	 * @param portnr
	 * @param node
	 */
	public ServerThread(int portnr, Node node)
	{
		this.portnr = portnr;
		this.node = node;
		this.teller = node.buren.length;
	}
	
	@Override
	public void run()
	{
		newServerListener();
	}
	
	/**
	 * 
	 *
	 */
	public void newServerListener() {
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
	
	/**
	 * 
	 * @return
	 */
	public synchronized int decreaseTeller() {
		return --this.teller;
	}
}
