package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	

	public List<PowerOutages> getWorstCase(String nerc){
		
		String sql = "SELECT p.id AS id, p.nerc_id AS nerc_id, n.value AS value, p.date_event_began " + 
				"AS inizio, p.date_event_finished AS fine," + 
				" YEAR(p.date_event_began) AS anno, " + 
				" p.customers_affected AS c FROM poweroutages AS p, nerc AS n" + 
				"  WHERE n.id= p.nerc_id AND n.value = ? ORDER BY p.date_event_began ASC";
		
		List<PowerOutages> ris = new ArrayList<PowerOutages>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nerc);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				PowerOutages po = new PowerOutages(res.getInt("id"), new Nerc(res.getInt("nerc_Id"), res.getString("value")), 
						res.getTimestamp("inizio").toLocalDateTime(), res.getTimestamp("fine").toLocalDateTime(), 
						res.getInt("anno"), res.getInt("c"));
				
				ris.add(po);
			}
			
			conn.close();
			return ris;
		}catch(SQLException e ) {
			throw new RuntimeException(e);
		}
		
	}
}
