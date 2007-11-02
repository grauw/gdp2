import java.net.*;
import java.io.*;

/**
 * Luistert op een ServerSocket, en verwerkt de ontvangen berichten in nieuwe threads.
 * 
 * @author mjgraaf, lhost
 * @id-nr: 3117308, 0122173
 *
 */
public class ServerThread extends Thread
{
	private Node node;
	private int portnr;
	
	/**
	 * Constructor
	 * @param portnr Het poortnummer voor het ServerSocket.
	 * @param node Het node-object.
	 */
	public ServerThread(int portnr, Node node)
	{
		this.portnr = portnr;
		this.node = node;
	}
	
	@Override
	public void run()
	{
		newServerListener(portnr, node);
	}
	
	/**
	 * Luistert op een nieuw ServerSocket, en start een OntvangThread op om
	 * elk ontvangen bericht te verwerken. Stopt zodra er net zoveel berichten zijn
	 * ontvangen als er buren zijn.
	 * @param portnr Het poortnummer voor het ServerSocket.
	 * @param node Het node-object.
	 */
	public void newServerListener(int portnr, Node node) {
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(portnr);
			for(int i = 0; i < node.buren.length; i++) {
				Socket clientSocket = serverSocket.accept();
				if(GDP2.HULP)
					System.out.println("clientSocket: " + clientSocket.toString());
				OntvangThread ot = new OntvangThread(clientSocket, node);
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
}
