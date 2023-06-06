package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import it.polito.tdp.rivers.model.Event.EventType;

public class Simulatore {

	//STATO SISTEMA-MONDO
	private List<Double>flussiINGRESSO;
	
	//PARAMETRI INGRESSO
	private Model model = new Model();
	private River r;
	private double k;//valore inserito dall'utente
	private double Q;//capienza tot del bacino
	private double C;//volume occupato dall'acqua
	private List<Double>volumeOccupatoGiornoPerGiorno;
	private List<Double>flussiUSCITA;
	private double flussoOUTmin;//vincolo sul flusso in uscita da garantire
	
	//INDICATORI DI USCITA
	private int nGiorniNonSoddisfatti;//num giorni in cui non e stato erogato il flusso in uscita
	private double volumeMediaOccupata;//media giorno per giorno del volume occupato(per tenerne traccia conviene creare una lista di double di tutte le capienze giorno per giorno)
	
	//EVENTI E CODA DEGLI EVENTI
	private PriorityQueue<Event>queue;
	

	public Simulatore(double k, River r) {//al costruttore devo passare solo i valori inseriti dall'utente
		this.k = k;
		this.r = r;
		this.flussiINGRESSO =  new ArrayList<>();
	}
	
	private double convertM3SecondsToM3Day(double flowAvg) {
		
		return  flowAvg*60*60*24;
	}

	public void initialize() {
		this.queue = new PriorityQueue<Event>();
		for(Flow f : r.getFlows()){
			Double d = f.getFlow();
			this.flussiINGRESSO.add(d);
		}
		
		this.queue = new PriorityQueue<Event>();
		
		this.volumeOccupatoGiornoPerGiorno = new ArrayList<Double>();
		
		this.Q = k * 30 * convertM3SecondsToM3Day(r.getFlowAvg());
		this.C = Q / 2;
		this.flussoOUTmin = convertM3SecondsToM3Day(0.8 * r.getFlowAvg());
		this.nGiorniNonSoddisfatti = 0;
		for(Flow x : model.getFlussi(r)) {
			this.queue.add(new Event(x.getDay(), EventType.FLUSSO_INGRESSO, x.getFlow(), x.getRiver()));
		}
		System.out.println("Q: " + Q);

	}
	
	public void run(River r, double k) {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			LocalDate giorno = e.getGiorno();
			EventType type = e.getType();
			double flussoIN = e.getVolume();
			
			System.out.println(e.toString());
			
			switch(type) {
			case FLUSSO_INGRESSO:
				double fOut = flussoOUTmin;

				// C'è il 5% di possibilità che fOut sia 10 volte fOutMin
				if (Math.random() > 0.95) {
					fOut = 10 * flussoOUTmin;
					System.out.println("10xfOutMin");
				}
				
				System.out.println("fIn: " + convertM3SecToM3Day(flussoIN));
				System.out.println("fOut: " + fOut);

				// Aggiungo fIn a C
				C += convertM3SecToM3Day(flussoIN);

				// Se C è maggiore della capacità massima
				if (C > Q) {
					// Tracimazione
					// La quantità in più esce.
					C = Q;
				}

				if (C < fOut) {
					// Non riesco a garantire la quantità minima.
					nGiorniNonSoddisfatti++;
					// Il bacino comunque si svuota
					C = 0;
				} else {
					// Faccio uscire la quantità giornaliera
					C -= fOut;
				}
				// Mantengo un lista della capacità giornaliere del bacino
				volumeOccupatoGiornoPerGiorno.add(C);
				System.out.println("C: " + C + "'\n");

				break;
				
			}
		}
			// Calcolo la media della capacità
			for (Double d : volumeOccupatoGiornoPerGiorno) {
				volumeMediaOccupata += d;
			}
			volumeMediaOccupata = volumeMediaOccupata / volumeOccupatoGiornoPerGiorno.size();
	}
	
	public SimulationResult result() {
		return new SimulationResult(this.nGiorniNonSoddisfatti, volumeMediaOccupata);
	}
	
	public double convertM3SecToM3Day(double input) {
		return input * 60 * 60 * 24;
	}

	public double convertM3DayToM3Sec(double input) {
		return input / 60 / 60 / 24;
	}	
}