package projekt.backend.bloky;

import java.io.Serializable;

import projekt.backend.Block;
import projekt.backend.Port;
import projekt.backend.Port.hodnoty;
/** 
 * 
 * @author David Dejmal (xdejma00)
 * pricte jeden metr
 */
public class Block_plus_jedna extends Block implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Block_plus_jedna(String name,int poradi) {
		super(name,poradi);	
		
		port_in = new Port[1];	//mnozstvi in/out portu
		port_out = new Port[1];
		
		//konstruktor kazdeho portu
		port_in[0]= new Port(this,"vstup-1",hodnoty.Metr);	
		port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
	}

	/** Vypocet blocku
	 * 
	 * pricte jednicku
	 */
	public void operace()
	{
		double result= port_in[0].see_hodnota();
		result=result+1;
		port_out[0].set_hodnota(result);
	}
}
