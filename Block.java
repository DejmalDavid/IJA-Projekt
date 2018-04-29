package projekt.backend;

/** Obecny blok
 * @author David Dejmal (xdejma00)
 */
public abstract class Block {

	private String name;
	protected Port port_in[];	
	protected Port port_out[];
	private Integer poradi;
	
	
	public Block(String name,int poradi)
	{	
		this.name =name;
		this.poradi = poradi;
	}
	
	/**Vraci jmeno bloku
	 * 
	 * @return Jmeno bloku
	 */
	public String see_name()
	{
		return name;
	}
	
	public Integer see_poradi()
	{
		return poradi;
	}
	
	public void set_poradi(int poradi)
	{
		this.poradi=poradi;
	}
	
	/** Vraci vstupni Port se zadanym jmenem
	 * 
	 * @param jmeno Portu
	 * @return	vstupni port
	 */
	public Port return_start_port(String jmeno)
	{
		for(Port port: port_in)
		{
			if (jmeno == port.see_nazev())
			{
				return port;
			}
		}
		return null; 		
	}
	
	/** Vraci vystupni Port se zadanym jmenem
	 * 
	 * @param jmeno Portu
	 * @return vystupni port
	 */
	public Port return_end_port(String jmeno)
	{
		for(Port port: port_out)
		{
			if (jmeno == port.see_nazev())
			{
				return port;
			}
		}
		return null; 		
	}
	
	/** Vraci pole vystupnich portu
	 * 
	 * @return pole vystupnich portu
	 */
	public Port[] return_end_ports()
	{
		return port_out; 		
	}
	

	public abstract void operace();
	
	public int hashCode() {
		return poradi.hashCode();	
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Block)	
		{
			Block a = (Block) obj;
				if(this.see_poradi().equals(a.see_poradi()))	//maji stejne poradi
				{					
					return true;	//znamena ze jsou si rovne
				}
		}
		return false;	
	}
}
