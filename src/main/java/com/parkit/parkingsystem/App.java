package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.InteractiveShell;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class App {
    private static final Logger logger = LogManager.getLogger("App");
    public static void main(String args[]){
        logger.info("Initializing Parking System");
        InteractiveShell.loadInterface();
     
    
        
        
        
    }
 
    }

