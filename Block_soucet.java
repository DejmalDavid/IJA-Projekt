package projekt.backend;

/**
 *
 * @author Michal Beranek (xberan38)
 */
public class Block_soucet extends Block{

    public Block_soucet(String name,int poradi) {
        super(name,poradi);

        port_in = new Port[2];	//mnozstvi in/out portu
        port_out = new Port[1];

        //konstruktor kazdeho portu
        port_in[0]= new Port(this,"vstup-1",hodnoty.Metr);
        port_in[1]= new Port(this,"vstup-2",hodnoty.Metr);
        port_out[0]= new Port(this,"vystup-1",hodnoty.Metr);
    }

    /** Vypocet blocku
     *
     * Tato operace musi zarucit ze na kazdy vystupni port bude pridelena hodnota!!!
     *
     */
    public void operace()
    {
        double result= port_in[0].see_hodnota() + port_in[1].see_hodnota();
        port_out[0].set_hodnota(result);
    }
}