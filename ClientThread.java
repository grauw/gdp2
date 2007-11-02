import java.net.*;
import java.io.*;

/**
 * ClientThread verzendt een bericht naar een buur en geeft daarin mee zijn
 * eigen poort en een waarde. Deze waarde staat op 0 als het gaat om het
 * versturen van berichten die bedoeld zijn om de parent van de buur te worden,
 * de waarde heeft de daadwerkelijke waarde van de node (plus van diens kinderen)
 * als het gaat om een bericht naar de parent van de node.
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class ClientThread extends Thread
{
	private int port;
	private int value;
	private int ownPort;
	
	/**
	 * Lege constructor voor als het object niet geïnitialiseerd hoeft te worden
	 */
	public ClientThread() {
	}
	
	/**
	 * Initialiseert de parameters voor de aanroep van verzendBericht in deze thread.
	 * @param port Poort waar het bericht heen gestuurd wordt.
	 * @param value Waarde die meegestuurd wordt. 
	 * @param ownPort Eigen poortnummer van de node die het bericht verzend.
	 */
	public ClientThread(int port, int value, int ownPort)
	{
		this.port = port;
		this.value = value;
		this.ownPort = ownPort;
	}
	
	@Override
	public void run()
	{
		verzendBericht(port, value, ownPort);
	}
	
	/**
	 * Verstuurt een bericht naar een poort.
	 * @param port De poort waar het bericht heen gestuurd wordt.
	 * @param value Waarde die meegestuurd wordt. 
	 * @param ownPort Eigen poortnummer van de node die het bericht verzend.
	 */
	public void verzendBericht(int port, int value, int ownPort)
	{		
		try
		{
			Socket sendTo = new Socket("localhost", port) ;
			OutputStream ostream = sendTo.getOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(ostream);
			os.flush();
			
			os.writeObject(new ObjectMessage(value, ownPort));
			
			os.flush();
			ostream.close();
			
			if (GDP2.HULP)
				System.out.println("Verstuurde waarde: " + value);
		}
		catch(IOException q)
		{
			try
			{
				Thread.sleep(200);
				verzendBericht(port, value, ownPort);
			}
			catch(InterruptedException e)
			{}
		}
	}
}
