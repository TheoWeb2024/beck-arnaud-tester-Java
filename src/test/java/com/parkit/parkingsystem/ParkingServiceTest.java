package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.io.PrintStream;
import java.util.Date;
import java.util.function.BooleanSupplier;
import java.util.function.IntPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
	
    private static ParkingService parkingService;
	
	@Mock
	private static Ticket ticket;

    @Mock 
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;
    
   


    @BeforeEach
    private void setUpPerTest() {
        try {
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
            ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60*60*1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
      
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        } 
    } 
    
    @Test
    public void processExitingVehicleTest() throws Exception  {
  
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when (ticketDAO.getTicket(anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
    
        
        parkingService.processExitingVehicle();
        
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        
    }
    
    @DisplayName ("procedure d'un vehicule entrant dans le parking")  
    @Test
    public void testProcessIncomingVehicle(){
    	 
    	 when(inputReaderUtil.readSelection()).thenReturn(1);
         when (parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
        
         
         parkingService.processIncomingVehicle();
         
         verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
         
	}

	@Test
    public  void testprocessExitingVehicleTestUnableUpdate() throws Exception {
    	
		  when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		  when (ticketDAO.getTicket(anyString())).thenReturn(ticket);
		  when (ticketDAO.getNbTicket("ABCDEF")).thenReturn(1);
	      when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);
	            
	      parkingService.processExitingVehicle();
	        
	      verify(ticketDAO, Mockito.times(1)).getTicket("ABCDEF");
	      verify(ticketDAO, Mockito.times(1)).getNbTicket("ABCDEF");
	      verify(ticketDAO, Mockito.times(1)).updateTicket(any(Ticket.class));
    }
    
    @Test
    public void testGetNextParkingNumberIfAvailable(){
    	
    	 when(inputReaderUtil.readSelection()).thenReturn(1);
        // when (parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
         
         parkingService.getNextParkingNumberIfAvailable();
         
         verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
    	  
    } 
 
	@Test
    public void testGetNextParkingNumberIfAvailableParkingNumberNotFound() {
		
		when(inputReaderUtil.readSelection()).thenReturn(1);
        when (parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(0);
        
        parkingService.getNextParkingNumberIfAvailable();
        
        verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
        verify(inputReaderUtil, Mockito.times(1)).readSelection();
    
    }
	
    @Test
    public void testGetNextParkingNumberIfAvailableParkingNumberWrongArgument()  {
    	
    	when(inputReaderUtil.readSelection()).thenReturn(3);
        
        parkingService.getNextParkingNumberIfAvailable();
  
        verify(inputReaderUtil, Mockito.times(1)).readSelection();
    }
    
    @Test
    public void testGetNextParkingNumberIfAvailableForBike()  {
    	
    	when(inputReaderUtil.readSelection()).thenReturn(2);	
       // when (parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
        when (parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
             
         ParkingType result =  parkingService.getVehicleType();
         parkingService.getNextParkingNumberIfAvailable();
         
         verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
    	 assertEquals (ParkingType.BIKE, result);
    }
  
    
}

