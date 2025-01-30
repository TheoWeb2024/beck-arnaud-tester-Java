package com.parkit.parkingsystem.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Fields;
import org.mockito.junit.jupiter.MockitoExtension;

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
import java.sql.*;

@ExtendWith(MockitoExtension.class)
class ParkingSpotDAOTest {
	@Mock
	public static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	
	@Mock
	private static DataBasePrepareService dataBasePrepareService;
	@Mock
    private static ParkingSpotDAO parkingSpotDAO;
	@Mock
    private static TicketDAO ticketDAO;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
	@Mock
    private ResultSet resultSet;
	@Mock
	private DataBaseConfig dataBaseConfig; 
    @Mock
    private static InputReaderUtil inputReaderUtil; //ajout
	
    
   @BeforeAll
    public static void setUp(){
	   ticketDAO = new TicketDAO(); //ajout
        parkingSpotDAO = new ParkingSpotDAO(); 
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }
    
    @BeforeEach
    public void setUpPerTest() throws Exception{
    	dataBasePrepareService.clearDataBaseEntries();      
    } 
    
	@Test
	public void testGetNextAvailableSlot() throws SQLException, ClassNotFoundException{
	   ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO(); //ajout
	  

		
    /*  when(connection.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);  */
      // when (parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(null);
      
        
       parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR); //()Parking
        
       	assertEquals(-1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));	 
	}

	@Test
	public void testUpdateParking(){
		//ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		parkingSpot.isAvailable();
		parkingSpotDAO.updateParking(any(ParkingSpot.class));
		//parkingSpotDAO.updateParking(parkingSpot);
		assertEquals(0,parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)); 
		assertFalse(parkingSpotDAO.updateParking(parkingSpot));
		
	}
	
	@AfterAll
	public static void tearDown() {
		dataBasePrepareService.clearDataBaseEntries();
	}
}
	