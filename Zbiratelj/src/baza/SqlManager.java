/**
 * 
 */
package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nina
 * Razred, ki je posrednik med GlavnoOkno in NarisiZbirkoWindow ter MySQL serverjem.
 */
public class SqlManager {
	
	/**
	 * Metoda, ki iz baze prebere in vrne seznam vseh zbirk.
	 * @return zbirke
	 */
	public static List<String> beriBazo(){
		List<String> zbirke = new ArrayList<String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			Statement statement = con.createStatement();
			ResultSet zbirkeRS = statement.executeQuery("SELECT zbirke_ime FROM zbirke");
			
			while (zbirkeRS.next()){
				zbirke.add(zbirkeRS.getString("zbirke_ime"));
			}
			con.close();
		}
		catch (SQLException e){
			System.out.println("SQLException! beriBazo");
		}
		catch (ClassNotFoundException e){
			System.out.println("Class Not Found Exception! beriBazo");
		}
		return zbirke;
	}

	/**
	 * Metoda, ki vrne vse stolpce za doloceno zbirko.
	 * @param zbirka
	 * @return stolpci
	 */
	public static List<String> beriZbirkoStolpci(String zbirka){
		List<String> stolpci = new ArrayList<String>();
		Map<Integer, String> stolpciMap = new HashMap<Integer, String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			ResultSet stolpciRS = con.createStatement().executeQuery("SELECT * FROM stolpci WHERE stolpci_zbirka = " + "'" + zbirka + "'");
			while (stolpciRS.next()){
				stolpciMap.put(stolpciRS.getInt("stolpci_stevilka"), stolpciRS.getString("stolpci_ime"));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		for (int i = 0; i < stolpciMap.size(); i++){
			stolpci.add(stolpciMap.get(i));
		}
		
		return stolpci;
	}

	/**
	 * Metoda, ki iz baze prebere vse podatke za doloceno zbirko.
	 * @param zbirka
	 * @return elementi
	 */
	public static Map<Integer, Map<String, String>> beriZbirkoPodatki(String zbirka){
		Map<Integer, Map<String, String>> elementi = new HashMap<Integer, Map<String, String>>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			ResultSet elementiRS = con.createStatement().executeQuery("SELECT * FROM podatki WHERE podatki_zbirka = " + "'" + zbirka + "'");
			while (elementiRS.next()){
				String stolpec = elementiRS.getString("podatki_stolpec");
				String vsebina = elementiRS.getString("podatki_vsebina");
				Integer elementId = elementiRS.getInt("podatki_element");
				
				if (elementi.containsKey(elementId)){
					elementi.get(elementId).put(stolpec, vsebina);
				}
				else {
					Map<String, String> element = new HashMap<String, String>();
					element.put(stolpec, vsebina);
					elementi.put(elementId, element);
				}
			}
			con.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return elementi;
	}

	/**
	 * Metoda, ki doda element v bazo.
	 * @param zbirka
	 * @param element
	 * @return
	 */
	public static int dodajElement(String zbirka, Map<String, String> element){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			con.prepareStatement("INSERT INTO elementi (elementi_zbirka) VALUES ('" + zbirka + "')").executeUpdate();
			ResultSet idRS = con.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
			idRS.next();
			int id = idRS.getInt("LAST_INSERT_ID()");
			for (String stolpec : element.keySet()){
				con.prepareStatement("INSERT INTO podatki (podatki_element,podatki_zbirka,podatki_stolpec,podatki_vsebina) VALUES ('" + id + "', '" +  zbirka + "', '" + stolpec + "', '" + element.get(stolpec) + "')").executeUpdate();
			}
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			return 2;
		}
		return 0;
	}

	/**
	 * Metoda, ki doda zbirko v bazo.
	 * @param zbirka
	 * @param stolpci
	 * @return
	 */
	public static int dodajZbirko(String zbirka, List<String> stolpci){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			con.prepareStatement("INSERT INTO zbirke(zbirke_ime) VALUES ( '" + zbirka + "')").executeUpdate();
			
			int i = 0;
			for (String stolpec : stolpci){
				con.prepareStatement("INSERT INTO stolpci (stolpci_zbirka,stolpci_ime,stolpci_stevilka) VALUES ( '" + zbirka + "', '" + stolpec + "', '" + i + "')").executeUpdate();
				i++;
			}
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
		catch (ClassNotFoundException e){
			return 2;
		}
		return 0;
	}

	/**
	 * Metoda, ki element izbrise iz baze.
	 * @param id
	 * @return
	 */
	public static int izbrisiElement(int id){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			con.prepareStatement("DELETE FROM elementi WHERE elementi_id = ('" + id + "')").executeUpdate();
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			return 2;
		}
		return 0;
	}

	/**
	 * Metoda, ki zbirko zapise v bazo.
	 * @param zbirka
	 * @return
	 */
	public static int izbrisiZbirko(String zbirka){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "testdb" + "?", "testuser", "password");
			con.prepareStatement("DELETE FROM zbirke WHERE zbirke_ime = ('" + zbirka + "')").executeUpdate();
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			return 1;
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
}
