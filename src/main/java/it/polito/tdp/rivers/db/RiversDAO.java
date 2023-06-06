package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	/**
	 * metodo per creare tutti gli oggetti flow(flusso) del fiume passato come parametro
	 * @param r è il fiume passato dall'utente nella cmbBox
	 * @return
	 */
	public List<Flow> prendiFlussiDelFiume(River r) {
		
		final String sql = "SELECT DAY, flow, river "
				+ "FROM flow "
				+ "WHERE river = ? "
				+ "ORDER BY DAY ";

		List<Flow> result = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				LocalDate d = res.getDate("DAY").toLocalDate();
				double flow = res.getDouble("flow");
				
				Flow f = new Flow(d, flow, r);
				result.add(f);
			}

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public List<Double> getFlussiDatoFiume(River r) {
		// TODO Auto-generated method stub
		return null;
	}
}
