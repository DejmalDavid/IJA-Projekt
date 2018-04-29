package projekt.backend;

public class Block_start_meter extends Block {
	
	private double hodnota;
	
    public Block_start_meter(String name,int poradi,double vstup) {
        super(name,poradi);

        port_in = new Port[2];	//mnozstvi in/out portu
        port_out = new Port[1];

        //konstruktor kazdeho portu
        port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
        
        hodnota=vstup;
    }

    /** Vypocet blocku
     *
     * Tato operace musi zarucit ze na kazdy vystupni port bude pridelena hodnota!!!
     *
     */
    public void operace()
    {
        double result= hodnota;
        port_out[0].set_hodnota(result);
    }
}
