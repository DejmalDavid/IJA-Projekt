package projekt.backend;

/** Pokusna trida pro ucel 2.ukolu
 * 
 * @author David Dejmal (xdejma00)
 */
public class Block_plus_jedna extends Block{

	public Block_plus_jedna(String name) {
		super(name);	
		
		port_in = new Port[1];	//mnozstvi in/out portu
		port_out = new Port[1];
		
		//konstruktor kazdeho portu
		port_in[0]= new Port(this,"vstup-1","odpor");	
		port_out[0]= new Port(this,"vystup-1","odpor");
	}

	/** Vypocet blocku
	 * 
	 * Tato operace musi zarucit ze na kazdy vystupni port bude pridelena hodnota!!!
	 * 
	 */
	public void operace()
	{
		double result= port_in[0].see_hodnota();
		result=result+1;
		port_out[0].set_hodnota(result);
	}
}
