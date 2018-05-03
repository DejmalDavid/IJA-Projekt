package projekt.backend;

import java.io.Serializable;

/** Trida simulujici port
 * 
 * @author David Dejmal (xdejma00)
 */
public class Port  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Block parent;	
	private String nazev;
	private hodnoty typ;
	protected boolean connect;		//verejny propoj
	private double hodnota;
	public enum hodnoty {
		 Metr,	//delka v metrech
		 Metr_2,	// plocha v ctverecnich metrech
		 Metr_3	//objem v metrech krychlovych
		};
	
	/**Nastaveni portu
	 * @param Rodicovsky block
	 * @param nazev portu
	 * @param typ validni hodnoty
	 */
	public Port(Block parent,String nazev,hodnoty typ)
	{
		this.parent=parent;
		this.nazev = nazev;
		this.typ=typ;
		hodnota=Double.NaN;
		connect=false;
	}
	
	/**Nastavi Hodnotu portu
	 * 
	 * @param value hodnota portu
	 */
	public void set_hodnota(double value)
	{
		hodnota=value;
	}
	
	/** Vraci hodnotu portu
	 * 
	 * @return hodnota portu
	 */
	public double see_hodnota()
	{
		return hodnota;
	}
	
	
	/**Vraci rodicovsky objekt
	 * 
	 * @return Rodicovsky objekt
	 */
	public Block see_parent()
	{
		return parent;
	}
	
	/** Vraci nazev objektu
	 * 
	 * @return Nazev objektu
	 */
	public String see_nazev()
	{
		return nazev;
	}
	
	/**Vraci typ portu
	 * 
	 * @return typ validni hodnoty portu
	 */
	public hodnoty see_typ()
	{
		return typ;
	}
	
	
	//pro lepsi praci s kontejnery
	
	public int hashCode() {
		return nazev.hashCode();	
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Port)	
		{
			Port a = (Port) obj;
			if(this.see_parent().equals(a.see_parent()))
			{
				if(this.see_nazev().equals(a.see_nazev()))	//maji stejna jmena
				{
					
					return true;	//znamena ze jsou si rovne
				}
			}

		}
		return false;	
	}

	
}
