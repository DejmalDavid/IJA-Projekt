package projekt;

import projekt.backend.Schema;

public class maine {

	public static void main(String[] args){

		System.out.println("Start:");
		Schema plan1 = new Schema("jmeno");
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
		plan1.swap_items(3, 4);
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
		
		//mazani
		plan1.delete_propoj("jedna-dva");
		plan1.delete_blok("tri");
		
		//kontrola vstupu
		plan1.all_connect();
		plan1.help_vypis();
		
		
		}
}
