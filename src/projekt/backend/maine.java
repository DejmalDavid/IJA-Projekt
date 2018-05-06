package projekt.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import projekt.backend.Port.hodnoty;

public class maine {

	public static void main(String[] args){

		System.out.println("Start:");
		Schema plan1 = new Schema("jmeno000");
		//pridame bloky
		plan1.add_start_block("nula", 1,hodnoty.Metr);
		plan1.add_start_block("jedna",10,hodnoty.Metr);
		plan1.add_block("dva","soucet",hodnoty.Metr);
		plan1.add_block("tri","rozdil",hodnoty.Metr);
		plan1.add_start_block("ctyri",200,hodnoty.Metr);
		//pridame propoje
		plan1.add_propoj("nula", "vystup-1", "dva","vstup-1");
		plan1.add_propoj("jedna", "vystup-1", "dva","vstup-2");
		plan1.add_propoj("dva", "vystup-1", "tri","vstup-1");
		plan1.add_propoj("ctyri", "vystup-1", "tri","vstup-2");
		//test swap
		//plan1.swap_items(3, 4);
		//kontrola
		if(plan1.have_cykles())
		{
			System.out.println("Nasli jsme cykly nebo pozdni vazbu!!!");
		}
		//demo
		System.out.println("Start demo:");
		if(plan1.full_demo()==false)
		{
			System.out.println("NAN");
		}
		//krokovani
		plan1.start_mode();
		plan1.step_demo();
		plan1.step_demo();
		plan1.step_demo();
		plan1.help_vypis();
		
		Wire help;
		help=plan1.find_wire("jedna-dva");
		System.out.println("hodnota spoje jedna-dva :"+help.start().see_hodnota());
		//mazani
		plan1.delete_propoj("jedna-dva");
		plan1.delete_blok("tri");
		
		//najdu spoj a vypisu jeho hodnotu

		
		//kontrola vstupu
		plan1.all_connect();
		plan1.help_vypis();
		//ukladani
		
		Schema plan2=null;
		save(plan1,"ahoj.schema");
		plan2=load("ahoj.schema");		
		
		System.out.print(plan2.see_name());
		plan2.help_vypis();
		
		}
	
	/**ulozi schema do souboru
	 * 
	 * @param uloz schema k ulozeni
	 * @param name jmeno souboru bez pripony
	 */
	static private void save(Schema uloz,String name)
	{
		try 
		{
			File soubor = new File(name+".schema");
			FileOutputStream f = new FileOutputStream(soubor);
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(uloz);

			o.close();
			f.close();
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
		catch (IOException e) 
		{
			System.out.println("Error initializing stream");
			e.printStackTrace();
	 
		}
	}
	/**nacte schema do promene
	 * 
	 * @param name jmeno souboru bez pripony
	 * @return nactene schema
	 */
	static private Schema load(String name)
	{
		Schema result;
		try 
		{
			
			FileInputStream fi = new FileInputStream(new File(name+".schema"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			result = (Schema) oi.readObject();

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		} catch (IOException e) {
			System.out.println("Error initializing stream");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
			return result;
		}
	
}
