package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.lang.Math.round;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;


public class FareCalculatorService { 
	public void calculateFare(Ticket ticket) {
		calculateFare(ticket,false);
	}

    public void calculateFare(Ticket ticket, boolean discount){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        } 
 
        long inHour = ticket.getInTime().getTime(); 
        long outHour = ticket.getOutTime().getTime(); 
 
        long durationMillis = outHour - inHour;
        float duration = (durationMillis / 3600000) + (durationMillis % 3600000 / 60000 / 60.f);    // passage de millisecondes en heures ; % pour le reste de la division entière ; f pour float
        duration = (float) ((double) round(duration * 100) / 100);    // cast en double avec arrondi et cast encore en float pour ne pas avoir une valeur trop longue
        
        if (duration <= 0.5) {
        	ticket.setPrice(0);
        }else {
        	switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
               // ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR); 
                break;
            } 
            case BIKE: {
               // ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
            	ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");  
        	}
        	if(discount) {     // mise en place de la condition de discount = false par défaut
            	ticket.setPrice(ticket.getPrice() * 0.95);    // passage de la methode avec 5% de remise
        }	
        	
        }
        //ticket.setPrice((double) round(ticket.getPrice() * 1000) / 1000); // arrondi à 3 décimales
    }
}
