package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private List<River>allRivers;
	private RiversDAO dao = new RiversDAO();
	private List<Flow>misurazioni;

	public Model () {
		allRivers = dao.getAllRivers();
	}
	/**
	 * i seguenti 4 metodi sono per compilare i campi nel Controller
	 * @param r è il fiume inserito dall'utente
	 * @return
	 */
	public LocalDate trovoPrimaData(River r) {
		misurazioni = this.dao.prendiFlussiDelFiume(r);
		LocalDate dataPrima = this.misurazioni.get(0).getDay();
		
		return dataPrima;
	}
	
	public LocalDate trovoUltimaData(River r) {
		misurazioni = this.dao.prendiFlussiDelFiume(r);
		String s = "";
		LocalDate dataUltima = this.misurazioni.get(misurazioni.size()-1).getDay();
		
		return dataUltima;
	}
	
	public int trovoNumeroMisurazioni(River r) {
		misurazioni = this.dao.prendiFlussiDelFiume(r);
		int numeroMisurazioni = misurazioni.size();
		return numeroMisurazioni;
	}
	
	public double trovoMediaFlusso(River r) {
		misurazioni = this.dao.prendiFlussiDelFiume(r);
		int numeroMisurazioni = misurazioni.size();
		double sommaFlusso = 0.0;
		for(Flow x : misurazioni) {
			sommaFlusso += x.getFlow();
		}
		double mediaFlusso = sommaFlusso/numeroMisurazioni;
		r.setFlowAvg(mediaFlusso);
		
		return mediaFlusso;
	}
	
	public List<Flow>getFlussi(River r){
		return dao.prendiFlussiDelFiume(r);
	}

	
	public List<River> getAllRivers() {
		return allRivers;
	}
	
	public SimulationResult simula(River river, double k) {
		Simulatore sim = new Simulatore(k, river); 
		sim.initialize();
		sim.run(river, k);
		SimulationResult res = sim.result();
		return res;
	}
			
}
