/**
 * Een object dat vanaf een bepaalde waarde gesynchroniseerd aftelt
 * @author mjgraaf, lholst
 * @id-nr: 3117308, 0122173
 */
public class AfTeller {
	
	private int teller;
	
	/**
	 * Construct een teller object en initialiseert de beginwaarde van de teller
	 * @param teller Beginwaarde
	 */
	public AfTeller(int teller) {
		this.teller = teller;
	}
	
	/**
	 * Verlaagt de teller.
	 * @return Nieuwe waarde van de teller.
	 */
	public synchronized int verlaag() {
		return --this.teller;
	}

}
