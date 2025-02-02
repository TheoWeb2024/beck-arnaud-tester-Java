package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

import junit.framework.Assert;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotDAOTest {
 
	private static final Logger logger = LogManager.getLogger("ParkingSpotDaoTest");
	public static DataBaseConfig dataBaseTestConfig = new DataBaseTestConfig();    
    private static ParkingSpotDAO parkingSpotDao;
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp(){
        ticketDAO = new TicketDAO();
        parkingSpotDao = new ParkingSpotDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;     
        parkingSpotDao.dataBaseConfig = dataBaseTestConfig;
    } 
    
    @BeforeEach
    private void setUpPerTest(){
    	parkingSpotDao.dataBaseConfig = dataBaseTestConfig;
    }   
    
	@Test
	public void testGetNextAvailableSlot() {
		ParkingSpotDAO nouveauSpotParking = new ParkingSpotDAO();
		TicketDAO ticketDAO = new TicketDAO();
		Ticket ticket = ticketDAO.getTicket("I_CAR_1");
			assertNotNull( ticket);
			//assertEquals(ticketDAO.getTicket("I_CAR_1"),ticket);
	}

	@Test
	public void testUpdateParking(){
			ParkingSpotDAO nouveauSpotParking = new ParkingSpotDAO();
			Ticket nouvellesDonneesTicket = new Ticket();
			ParkingSpot emplacementSuivant = new ParkingSpot(1,ParkingType.CAR,false);				        
			
		nouvellesDonneesTicket.setParkingSpot(emplacementSuivant);
		
		boolean isUpdate = parkingSpotDao.updateParking(emplacementSuivant);
		
			assertTrue(isUpdate);
			
			}		
	}