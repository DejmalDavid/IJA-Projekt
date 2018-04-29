package projekt.backend;

public class maine {

	public static void main(String[] args){

		System.out.println("Start:");
		Schema plan1 = new Schema("jmeno");
		//pridame bloky
		plan1.add_start_block("jedna", 1);
		plan1.add_start_block("dva",10);
		plan1.add_block("tri",1);
		plan1.add_block("ctyri", 0);
		//pridame propoje
		plan1.add_propoj("jedna", "vystup-1", "tri","vstup-1");
		plan1.add_propoj("tri", "vystup-1", "ctyri","vstup-1");
		plan1.add_propoj("dva", "vystup-1", "ctyri","vstup-2");
		//test swap
		plan1.swap_items(1, 20);
		//kontrola
		if(plan1.have_cykles())
		{
			System.out.println("Nasli jsme cykly!!!");
		}
		//demo
		System.out.println("Start demo:");
		if(plan1.full_demo()==false)
		{
			System.out.println("NAN");
		}
	}
}
