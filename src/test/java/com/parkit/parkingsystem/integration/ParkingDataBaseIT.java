package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.Math.round;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {
    private FareCalculatorService fareCalculatorService;
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
   @Mock
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    
    //private Ticket ticket;
  

    @Mock
    private static InputReaderUtil inputReaderUtil;
    
    private Ticket ticket;
 //   private FareCalculatorService fareCalculatorService;
 
    

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() {
        try {
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            
            
      
            ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        } 
    } 

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar() throws Exception {
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO); 
    	parkingService.processIncomingVehicle();
 
 		ticketDAO.getTicket("ABCDEF"); 

	    assertEquals(0, ticket.getId());
	    verify(ticketDAO, Mockito.times(1)).getTicket("ABCDEF");
	    assertEquals(0, ticket.getPrice());

        //TODO: check that a ticket is actualy saved in DB and Parking table is updated with availability
    }

    @Test
    public void testParkingLotExit() throws Exception{
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processExitingVehicle();
        	
          when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		  when (ticketDAO.getTicket(anyString())).thenReturn(ticket);
		  when (ticketDAO.getNbTicket("ABCDEF")).thenReturn(1);
	      when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
	            
	      parkingService.processExitingVehicle();
	        
	      verify(ticketDAO, Mockito.times(1)).getTicket("ABCDEF");
	      verify(ticketDAO, Mockito.times(1)).getNbTicket("ABCDEF");
	      verify(ticketDAO, Mockito.times(1)).updateTicket(any(Ticket.class));
	      assertEquals( Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
 		 //assertEquals( Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
        //TODO: check that the fare generated and out time are populated correctly in the database
    }

    @Test
    public void testParkingLotExitRecurringUser() throws Exception{
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	 parkingService.processIncomingVehicle();
    	 Ticket ticket = new Ticket();
    
  
    	 if (ticket != null) {
	    	ticket.isDiscount();
	    	 return;
	     } else{
             System.out.println("Error occurred");
	     }
    	 
   	  when (ticketDAO.getTicket(anyString())).thenReturn(ticket);
     	//  parkingService.processExitingVehicle();
     
	      
	      assertFalse(ticket.isDiscount());
	    // assertEquals(Fare.CAR_RATE_PER_HOUR * 0.95, ticket.getPrice());
    	  }
    
     
}

