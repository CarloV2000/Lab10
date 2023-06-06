package it.polito.tdp.rivers.model;

public class SimulationResult {
	private Integer nGiorniNonSoddisfatti;
	private Double capacitaMedia;
	
	public SimulationResult(Integer nGiorniNonSoddisfatti, Double capacitaMedia) {
		super();
		this.nGiorniNonSoddisfatti = nGiorniNonSoddisfatti;
		this.capacitaMedia = capacitaMedia;
	}
	
	public Integer getnGiorniNonSoddisfatti() {
		return nGiorniNonSoddisfatti;
	}
	public Double getCapacitaMedia() {
		return capacitaMedia;
	}
	
	
}
