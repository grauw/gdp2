/**
 * 
 * @author mjgraaf, lholst
 *
 */
public class GDP2 {
	
	static final boolean HULP = true;
	static Node ini;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		if(args.length < 4)
			System.out.println("Geef ten minste vier parameters mee.");
		else
			maakNode(args);
		
		
	}
	
	/**
	 * 
	 * @param params
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
