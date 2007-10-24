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
	}
	
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(portnr);
		} 
		catch (IOException e)
		{
		}
		
		Socket clientSocket = null;
		try
		{
			clientSocket = serverSocket.accept();
			Ontvangen(clientSocket);
		}
		catch (IOException e)
		{
		}
		
		try
		{
			clientSocket.close();
		}
		catch (IOException e)
		{
		}
		
		
	}
	
	public void Ontvangen(Socket s) {
		System.out.println("Connectie ontvangen van "+s.getPort()+" op "+s.getLocalPort());
		
		ObjectMessage om = null;
		try {
			InputStream istream = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(istream);
			om = (ObjectMessage) ois.readObject();
			istream.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		if (!node.isParent) {
			node.isParent = true;
			
			node.VerzendBuren();
		} else {
			// return object die zegt "ik ben niet jouw kind".
			try
			{
				OutputStream ostream = s.getOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(ostream);
				ObjectMessage om2 = new ObjectMessage(0);
				om2.amChild = false;
				os.writeObject(om2);
				ostream.close();
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
		}
	}
}
