package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	
	private List<PowerOutages> best;
	private int nClienti ;
	String nerc;
	
	PowerOutageDAO podao;
	
	public Model() {
		podao = new PowerOutageDAO();
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> trova(String nerc, int nOre, int nAnni){
		
		List<PowerOutages> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		nClienti = 0;
		
		cerca(parziale, 0, nerc, nOre, nAnni);
		
		return best;
	}

	private void cerca(List<PowerOutages> parziale, int liv, String nerc, int nOreMax, int nAnni) {
		// CASO TERMINALE		
		if(nClienti < calcolaClienti(parziale)) {
				nClienti = calcolaClienti(parziale);
				best = new ArrayList<PowerOutages>(parziale);
		}
		
		for(PowerOutages po : podao.getWorstCase(nerc) ) {
			
			if(!parziale.contains(po)) {
				parziale.add(po);
				
				if(aggiuntaValida( po, parziale, nAnni, nOreMax)) {
					cerca(parziale, liv+1, nerc, nOreMax, nAnni);
				}
				
				parziale.remove(po);
			}
		}
		
	}

	private boolean aggiuntaValida(PowerOutages po, List<PowerOutages> parziale, int nAnni, int nOreMax) {
		// TODO Auto-generated method stub
		boolean valido = false;
		if(calcolaAnni(parziale) <= nAnni ) {
			if(calcolaNOre(parziale) <= nOreMax)
				valido = true;
		}
		
		return valido;
	}

	private int calcolaAnni(List<PowerOutages> parziale) {
		// TODO Auto-generated method stub
		return (parziale.get(parziale.size()-1).getAnno())-(parziale.get(0).getAnno())+1;
	}

	private int calcolaNOre(List<PowerOutages> parziale) {
		// TODO Auto-generated method stub
		int nOre = 0;
		for(PowerOutages po : parziale)
			nOre+=po.getDurata();
		
		return nOre;
	}

	private int calcolaClienti(List<PowerOutages> parziale) {
		// TODO Auto-generated method stub
		int somma = 0;
		for(PowerOutages po : parziale)
			somma+=po.getClienti();
		return somma;
	}

}
