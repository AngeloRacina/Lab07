package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());

		
		for(PowerOutages po : model.trova("MAIN", 80, 1))
		System.out.println(po.toString());
	}

}
