import java.net.*;
import java.io.*;

public class ServerThread extends Thread
{
	Node node;
	Socket RecieveFrom;
	Socket parentSocket = null;
	int portnr;
	int teller;
	
	public ServerThread(int portnr, Node node)
	{
		this.portnr = portnr;
		this.node = node;
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
				
				Ontvangen(clientSocket);
			}
			node.VerstuurNaarParent();
			serverSocket.close();
			if(GDP2.HULP)
				System.out.println("serverSocket closed.");
			
			if(node.port == 9999)
				System.out.println("Resultaat: " + node.counter.getValue());
		} 
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
	
	public void Ontvangen(Socket s) {
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
			
			if (!node.hasParent) {
				node.hasParent = true;
				node.parentPort = om.ontvangenPort;
				
				parentSocket = s;
				
				node.VerzendBuren();
			}
			s.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
