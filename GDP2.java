/**
 * GDP2 checkt of er genoeg parameters worden meegegeven en parsed deze naar de
 * correcte waardes. Vervolgens worden er met die waardes een Node aangemaakt
 * 
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class GDP2 {
	
	static final boolean HULP = false;
	static Node ini;
	
	/**
	 * De main methode, hierin kijken we of er wel genoeg parameters worden
	 * meegegeven.
	 * 
	 * @param args		Reeks parameters in string vorm.
	 */
	public static void main(String[] args)
	{
		if(args.length < 4)
			System.err.println("Geef ten minste vier parameters mee.");
		else
			maakNode(args);
		
		
	}
	
	/**
	 * Deze methode maakt van de strings de correcte waardes en maakt daarmee
	 * een node aan.
	 * 
	 * @param params   	De paramters in string vorm.
	 */
	private static void maakNode(String[] params)
	{
		int number = Integer.parseInt(params[0]);
		int port = Integer.parseInt(params[1]);
		int[] buren = new int[params.length-2];
		
		for(int t=0; t < params.length-2;t++)
			buren[t] = Integer.parseInt(params[t+2]);
		
		ini = new Node(number,port,buren);
	}

}
