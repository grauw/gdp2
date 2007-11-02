import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * OntvangThread wordt gestart zodra er een connectie is ontvangen.
 * OntvangThread haalt vervolgens de ObjectMessage binnen en verwerkt deze.
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class OntvangThread extends Thread {
	
	private Node node;
	private Socket clientSocket;
	
	/**
	 * Constructor
	 * @param clientSocket 	Het clientsocket om van te ontvangen
	 * @param node 			De node zelf
	 */
	public OntvangThread(Socket clientSocket, Node node)
	{
		this.clientSocket = clientSocket;
		this.node = node;
	}
	
	@Override
	public void run()
	{
		ontvangen(clientSocket, node);
	}
	
	/**
	 * Ontvangt een ObjectMessage van een socket en probeert de counter met de
	 * waarde van de ObjectMessage te verhogen. Als dit het eerste ontvangen
	 * pakket van de node is dan wordt hij parent en verstuurt hij berichten
	 * naar alle buren, als het het laatste pakket is dat ontvangen moet worden,
	 * dan verstuurt hij een bericht naar de parent.
	 * 
	 * @param s     De socket
	 * @param node  De node zelf
	 */
	public void ontvangen(Socket s, Node node) {
		if(GDP2.HULP)
			System.out.println("Connectie ontvangen op "+s.getLocalPort());
		
		try {
			InputStream istream = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(istream);
			ObjectMessage om = (ObjectMessage) ois.readObject();
			istream.close();
			
			if(GDP2.HULP)
				System.out.println("Counter before: " + node.counter.getValue());
			
			node.counter.addTo(om.getValue());
			
			if(GDP2.HULP)
			{
				System.out.println("Ontvangen waarde:" + om.getValue());
				System.out.println("Counter after: " + node.counter.getValue());
				System.out.println("om.ontvangenPort: " + om.getOntvangenPort());
				System.out.println("node.hasParent: " + node.hasParent);
			}
			
			if (!node.getParent()) {
				node.parentPort = om.getOntvangenPort();
				
				node.verzendBuren();
			}
			s.close();
			
			// Als dit het laatste ontvangen bericht is, verstuur resultaat dan naar parent 
			if (node.burenTeller.verlaag() == 0) {
				node.verstuurNaarParent();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
