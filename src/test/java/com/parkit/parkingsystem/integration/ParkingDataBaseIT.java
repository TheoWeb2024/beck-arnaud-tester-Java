package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
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

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    @Spy
    private static ParkingSpotDAO parkingSpotDAO;
    @Spy
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    
    private Ticket ticket;
  

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
       when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar() throws Exception {
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO); 
    	parkingService.processIncomingVehicle();
    	 
		  when(inputReaderUtil.readSelection()).thenReturn(1);
		  when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
	      when (parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
	      when (parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
	      when (ticketDAO.getTicket(anyString())).thenReturn(ticket);    
	      when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
	       

	         verify(ticketDAO, Mockito.times(1)).getTicket("ABCDEF");
	         verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
	         verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
	         assertNotNull(ticket.getInTime());
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
 		 assertEquals( Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
        //TODO: check that the fare generated and out time are populated correctly in the database
    }

    @Test
    public void testParkingLotExitRecurringUser() throws Exception{
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
    	Ticket ticket = ticketDAO.getTicket("ABCDEF");
    	  when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		  when (ticketDAO.getTicket(anyString())).thenReturn(ticket);
		  when (ticketDAO.getNbTicket("ABCDEF")).thenReturn(1);
	      when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
	     
    	
    	parkingService.processIncomingVehicle(); 
    	parkingService.processExitingVehicle();
   
    		 assertTrue(ticket.discount());
    		 assertEquals( Fare.CAR_RATE_PER_HOUR * 0.95, ticket.getPrice());
    		 assertEquals( Fare.BIKE_RATE_PER_HOUR * 0.95, ticket.getPrice());
    		 assertThrows(Exception.class, () -> {
    	    	   ParkingSpot updateTicket = parkingService.getNextParkingNumberIfAvailable();
    	    });
    		  }
      }
