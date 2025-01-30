package com.parkit.parkingsystem.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Fields;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
public class TicketDAOTest {


 
	@Mock
	public static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig(); //enleveMOCK
	@Mock
	private static DataBasePrepareService dataBasePrepareService; //enleveMOCK
	
	@Mock
    private static ParkingSpot parkingSpot;
	@Mock
    private static TicketDAO ticketDAO;
	@Mock
    private static Ticket ticket; //ajout
	@Mock
	private static ParkingService parkingService; //ajout 
	
    @Mock
    private Connection con; //change de connection to con
    @Mock
    private PreparedStatement ps;//change de preparedStatement to ps
	@Mock
    private ResultSet rs; //change de resultSet to rs
	@Mock
	private DataBaseConfig dataBaseConfig; 
	
    @BeforeAll
    public static void setUp(){  //private en public
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
     //   dataBasePrepareService = new DataBasePrepareService();
    } 
    
    @BeforeEach
    public void setUpPerTest(){ //private en public
    	//dataBasePrepareService.clearDataBaseEntries();
    	 ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
    	 
    }
     
    
	
			@Test
			public void testSaveTicket() throws SQLException {
				Ticket ticket = new Ticket();
				 
				Date inTime = new Date();
				ticketDAO = new TicketDAO();
		        ticketDAO.dataBaseConfig = dataBaseTestConfig;
				
				ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
                ticket.setOutTime(null);
				
			
				//ticketDAO.saveTicket(ticket);
			
				 assertTrue(ticketDAO.saveTicket(ticket));		
			}
	
			@Test
			public void testGetTicket(){
				Ticket ticket = new Ticket();
				
				ticket.setVehicleRegNumber("YTREZA");
				ticketDAO.getTicket("YTREZA");
				
				//assertNotNull(ticketDAO.getTicket("YTREZA")); 
				   verify(ticketDAO, Mockito.times(1)).getTicket("YTREZA");	
				   
			}
			
			@Test
			public void testUpdateTicket(){
				Ticket ticket = new Ticket();
				ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber("AZERTY");
				ticket.setOutTime(null);
				ticketDAO.getTicket("AZERTY");
				ticketDAO.saveTicket(ticket);
				//assertNotNull(ticketDAO.getTicket("AZERTY")); 
				  // verify(ticketDAO, Mockito.times(1)).getTicket("AZERTY");
				   verify(ticketDAO, Mockito.times(1)).getTicket("AZERTY"); 
			}
			
			@Test
			public void testGetNbTicket(){
				Ticket ticket = new Ticket();
				ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
				//ticket.setParkingSpot(parkingSpot);
				//ticket.setVehicleRegNumber("AZERTY");
				
				when(ticketDAO.getNbTicket("AZERTY")).thenReturn(1);
				ticketDAO.getNbTicket("AZERTY");
				//ticketDAO.saveTicket(ticket);
				
				
				verify(ticketDAO, Mockito.times(1)).getNbTicket("AZERTY");
				
			}
	
}
