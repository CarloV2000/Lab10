package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Objects;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		FLUSSO_INGRESSO;
	}
	private LocalDate giorno;
	private EventType type;
	private double volume;
	public River r;
	
	public Event(LocalDate giorno, EventType type, double volume, River r) {
		super();
		this.giorno = giorno;
		this.type = type;
		this.volume = volume;
		this.r = r;
	}

	public River getR() {
		return r;
	}

	public LocalDate getGiorno() {
		return giorno;
	}

	public void setGiorno(LocalDate giorno) {
		this.giorno = giorno;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Nuovo flusso nel giorno :" + giorno + ", con un flusso pari a " + volume + "m^3";
	}

	@Override
	public int hashCode() {
		return Objects.hash(giorno, type);
	}

	@Override
	public int compareTo(Event o) {
		return this.giorno.compareTo(o.giorno);
	}

	
	
	
}
