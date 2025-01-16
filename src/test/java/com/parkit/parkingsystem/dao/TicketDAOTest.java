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
	public static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	@Mock
	private static DataBasePrepareService dataBasePrepareService;
	@Mock
    private static ParkingSpot parkingSpot;
	@Mock
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp(){
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    } 
    
    @BeforeEach
    private void setUpPerTest(){
    	dataBasePrepareService.clearDataBaseEntries();
    	 ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
    } 
    
	
	@Test
	public void testSaveTicket(){
		Ticket ticket = new Ticket();
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("AZERTY");
		ticketDAO.getTicket("AZERTY");
		ticketDAO.saveTicket(ticket);
		//assertNotNull(ticketDAO.getTicket("AZERTY")); 
		   verify(ticketDAO, Mockito.times(1)).getTicket("AZERTY");
		
		
	
		
	}
	
	
}
