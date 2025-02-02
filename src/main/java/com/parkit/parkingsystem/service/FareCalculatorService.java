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

    public void calculateFare(Ticket ticket, boolean isDiscount){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        } 
 
        long inHour = ticket.getInTime().getTime(); 
        long outHour = ticket.getOutTime().getTime(); 
 
        long durationMillis = outHour - inHour;
        float duration = (durationMillis / 3600000) + (durationMillis % 3600000 / 60000 / 60.f);  
        
        if (duration <= 0.5) {
        	ticket.setPrice(0);
        }else {
        	switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR); 
                break;
            } 
            case BIKE: {
            	ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");  
        	}
        	  ticket.setPrice((double) round(ticket.getPrice() * 1000) / 1000);
        }
        	if(isDiscount) {     
            	ticket.setPrice(ticket.getPrice() * 0.95);    
            
        	}	
        	
        }
     
}
