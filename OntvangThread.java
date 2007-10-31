import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 
 * @author mjgraaf
 *
 */
public class OntvangThread extends Thread {
	
	Node node;
	Socket clientSocket;
	ServerThread serverThread;
	
	/**
	 * 
	 * @param clientSocket
	 * @param node
	 * @param serverThread
	 */
	public OntvangThread(Socket clientSocket, Node node, ServerThread serverThread)
	{
		this.clientSocket = clientSocket;
		this.node = node;
		this.serverThread = serverThread;
	}
	
	@Override
	public void run()
	{
		ontvangen(clientSocket);
	}
	
	/**
	 * 
	 * @param s
	 */
	public void ontvangen(Socket s) {
		if(GDP2.HULP)
			System.out.println("Connectie ontvangen op "+s.getLocalPort());
		
		try {
			InputStream istream = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(istream);
			ObjectMessage om = (ObjectMessage) ois.readObject();
			istream.close();
			
			node.counter.addTo(om.value);
			
			if(GDP2.HULP)
			{
				System.out.println("Ontvangen waarde:" + om.value);
				System.out.println("Counter: " + node.counter.getValue());
				System.out.println("om.ontvangenPort: " + om.ontvangenPort);
				System.out.println("node.hasParent: " + node.hasParent);
			}
			
			if (!node.getParent()) {
				node.parentPort = om.ontvangenPort;
				
				node.verzendBuren();
			}
			s.close();
			
			if (serverThread.decreaseTeller() == 0) {
				laatsteThread();
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 *
	 */
	public void laatsteThread() {
		node.verstuurNaarParent();
		if(node.port == 9999)
			System.out.println("Resultaat: " + node.counter.getValue());
	}

}
