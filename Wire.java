package projekt.backend;

import java.io.Serializable;

/** Predstavuje propoj mezi bloky
 * 
 * Tyto propoje budou ulozeny v globalnim kontejneru
 * Kazdy Port muze figurovat pouze v jednom propoji
 * 
 * @author David Dejmal (xdejma00)
 */
public class Wire  implements Serializable{	

	private Port port_start;	//zacatek
	private Port port_end;		//konec	
	private String name;
	
	public Wire() {
			port_start = null;
			port_end = null;
			name = null;
	}

	/** Propoji porty 
	 * 
	 * Pokud jsou proty jinych typu nebo uz jsou propojeny
	 * vraci false, jinak true.
	 * 
	 * @param in vstupni Port
	 * @param out vystupni Port
	 * @return zda byla opreca uspesna
	 */
	public boolean connect_ports(Port in,Port out)	//projeni
	{
		if((in == null) || (out == null))
		{
			return false;
		}
		if(in.see_typ() != out.see_typ())
		{
			return false;
		}
		if(in.connect || out.connect)
		{
			return false;
		}
		port_start=in;
		port_end=out;
		port_start.connect=port_end.connect=true;
		name=port_start.see_parent().see_name()+"-"+port_end.see_parent().see_name();
		return true;
	}
	
	/** Vraci zacatek propoje
	 * 
	 * @return pocatecni Port
	 */
	public Port start()	
	{
		return port_start;
	}
	
	/** Vraci konec propoje
	 * 
	 * @return	koncovy Port
	 */
	public Port end()
	{
		return port_end;
	}
	
	/**Vraci jmeno propoje
	 * 
	 * @return jmeno
	 */
	public String see_name()
	{
		return name;
	}
}

