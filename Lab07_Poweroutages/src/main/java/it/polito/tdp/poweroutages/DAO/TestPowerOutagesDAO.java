package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

import it.polito.tdp.poweroutages.model.PowerOutages;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			for(PowerOutages po : dao.getWorstCase("MAIN"))
				System.out.println(po.toString()+"\n") ;

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
