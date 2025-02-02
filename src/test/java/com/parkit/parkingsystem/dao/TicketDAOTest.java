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
public class TicketDAOTest {
 
	private static final Logger logger = LogManager.getLogger("TicketDaoTest");
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
    	ticketDAO.dataBaseConfig = dataBaseTestConfig;
    }     
	
	@Test
	public void test_1_SaveTicketPremiereVoitureEmplacementUnVide(){
		int emplacementVide = parkingSpotDao.getNextAvailableSlot(ParkingType.CAR);
		if( emplacementVide != -1) {
			ParkingSpot emplacementSuivant = new ParkingSpot(emplacementVide,ParkingType.CAR,false);				        
			Ticket ticket = new Ticket();		
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(emplacementSuivant);
			ticket.setVehicleRegNumber("I_CAR_1");
			boolean isSave = ticketDAO.saveTicket(ticket);
			if(isSave) {			
				assertTrue(parkingSpotDao.updateParking(emplacementSuivant));
			}
		}else {
			logger.error("test_1_SaveTicketPremiereVoitureEmplacementUnVide, l'insert ne se fait pas car le parking est plein");
			assertTrue(false);
		}
	}	
	@Test
	public void test_2_SaveTicketImmatriculationTropLong(){
		int emplacementVide = parkingSpotDao.getNextAvailableSlot(ParkingType.CAR);
		if( emplacementVide != -1) {
			ParkingSpot emplacementSuivant = new ParkingSpot(emplacementVide,ParkingType.CAR,false);				        
			Ticket ticket = new Ticket();		
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(emplacementSuivant);
			ticket.setVehicleRegNumber("IMMAT_TROP_LONG");
			boolean isSave = ticketDAO.saveTicket(ticket);
			if(isSave) {
				assertTrue(parkingSpotDao.updateParking(emplacementSuivant));
			}else {
				assertTrue(!isSave);				
			}
		}else {
			logger.error("test_2_SaveTicketImmatriculationTropLong, l'insert ne se fait pas car le parking est plein");
			assertTrue(false);
		}		
	}	
	@Test
	public void test_3_SaveTicketDernierePlaceLibrePourVoiture(){
		int emplacementVide = parkingSpotDao.getNextAvailableSlot(ParkingType.CAR);
		if( emplacementVide != -1) {
			ParkingSpot emplacementSuivant = new ParkingSpot(emplacementVide,ParkingType.CAR,false);				        
			Ticket ticket = new Ticket();		
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(emplacementSuivant);
			ticket.setVehicleRegNumber("I_CAR_2");			
			boolean isSave = ticketDAO.saveTicket(ticket);
			if(isSave) {			
				assertTrue(parkingSpotDao.updateParking(emplacementSuivant));
			}
		}else {
			logger.info("test_3_SaveTicketDernierePlaceLibrePourVoiture, l'insert ne se fait pas car le parking est plein");
			assertTrue(true);
		}
	}
	
	@Test
	public void test_4_SaveTicketImmatriculationBIKE(){
		int emplacementVide = parkingSpotDao.getNextAvailableSlot(ParkingType.BIKE);
		if( emplacementVide != -1) {
			ParkingSpot emplacementSuivant = new ParkingSpot(emplacementVide,ParkingType.BIKE,false);				        
			Ticket ticket = new Ticket();		
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(emplacementSuivant);
			ticket.setVehicleRegNumber("I_MOTO_1");
			boolean isSave = ticketDAO.saveTicket(ticket);
			if(isSave) {
				assertTrue(parkingSpotDao.updateParking(emplacementSuivant));
			}
		}else {
			logger.error("test_4_SaveTicketImmatriculationBIKE, l'insert ne se fait pas car le parking est plein");
			assertTrue(false);
		}		
	}
	@Test
	public void test_5_SaveTicketImmatriculationBIKE2(){
		int emplacementVide = parkingSpotDao.getNextAvailableSlot(ParkingType.BIKE);
		if( emplacementVide != -1) {
			ParkingSpot emplacementSuivant = new ParkingSpot(emplacementVide,ParkingType.BIKE,false);				        
			Ticket ticket = new Ticket();		
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(emplacementSuivant);
			ticket.setVehicleRegNumber("I_MOTO_2");
			boolean isSave = ticketDAO.saveTicket(ticket);
			if(isSave) {
				assertTrue(parkingSpotDao.updateParking(emplacementSuivant));
			}
		}else {
			logger.error("test_5_SaveTicketImmatriculationBIKE2, l'insert ne se fait pas car le parking est plein");
			assertTrue(false);
		}		
	}
	@Test
	public void test_1_GetTicketCAREmplacement1(){ 
		TicketDAO ticketDAO = new TicketDAO();
		Ticket ticket = ticketDAO.getTicket("I_CAR_2");
		
	
			//assertNotNull( ticket);
			assertEquals("I_CAR_2",ticket.getVehicleRegNumber());
	}		
			
	@Test
	public void test_2_GetTicketCAR_IMMAT_INIT(){
		TicketDAO ticketDAO = new TicketDAO();
		Ticket ticket = ticketDAO.getTicket("IMMAT_INIT");

		assertEquals("IMMAT_INIT",ticket.getVehicleRegNumber());
			assertEquals(1, ticket.getId());
	}		
	
	@Test
	public void test_1_UpdateTicketCAREmplacement1(){
		TicketDAO ticketDAO = new TicketDAO();
		Ticket nouvellesDonneesTicket = new Ticket();

		nouvellesDonneesTicket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		nouvellesDonneesTicket.setPrice(0);
		nouvellesDonneesTicket.setOutTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		nouvellesDonneesTicket.setId(1);
		//ticketDAO.saveTicket(nouvellesDonneesTicket);
		//nouvellesDonneesTicket.setId(nouvellesDonneesTicket.getId());
		ticketDAO.updateTicket(nouvellesDonneesTicket);
		
		boolean isUpdate = ticketDAO.updateTicket(nouvellesDonneesTicket);
		
			assertTrue(isUpdate);
	}		
	
	@Test
	public void test_1_GetNbTicketCAREmplacement1(){
		TicketDAO ticketDAO = new TicketDAO();
		int ticket = ticketDAO.getNbTicket("I_CAR_1");
			assertNotNull( ticket);
		}	
	}
