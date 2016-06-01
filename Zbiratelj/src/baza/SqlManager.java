/**
 * 
 */
package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nina
 * Razred, ki je posrednik med PripravljalecPodatkov in MySQL serverjem.
 */
public class SqlManager {
	private String database;
	private String url;
	private String username;
	private String password;
	private Map<String, List<List<String>>> baza; // tu bomo prebrali ResultSet, kljuc je zbirka, torej tabela
	
	/**
	 * @param user
	 * @param pd
	 */
	public SqlManager(String user, String pd){
		database = "testdb";
		url = "jdbc:mysql://localhost:3306/" + database + "?";
		username = user;
		password = pd;
		
		try {
			beriBazo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda, ki prebere izbrano bazo in nastavi slovar baza.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected void beriBazo() throws ClassNotFoundException, SQLException{
		Map<String, ResultSet> bazniSlovarRS = new HashMap<String, ResultSet>();
		baza = new HashMap<String, List<List<String>>>();
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, username, password);
		ResultSet tabele = con.createStatement().executeQuery("SELECT table_name FROM information_schema.tables");
		con.close();
		
		while (tabele.next()){
			String tabela = tabele.getString(0);
			ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM "+database+"."+tabela);
			bazniSlovarRS.put(tabela, resultSet);
		}
		
		for (String imeZbirke : bazniSlovarRS.keySet()){
			List<List<String>> zbirka = new ArrayList<List<String>>();
			ResultSet zbirkaRS = bazniSlovarRS.get(imeZbirke);
			int steviloStolpcev = zbirkaRS.getMetaData().getColumnCount();
			
			while (zbirkaRS.next()){
				List<String> element = new ArrayList<String>();
				for (int i = 0; i < steviloStolpcev; i++){
					element.add(zbirkaRS.getString(i));
				}
				zbirka.add(element);
			}
			baza.put(imeZbirke, zbirka);
		}
	}
	
	protected void dodajZbirko(List<String> stolpci){
		// dobi seznam [imeZbirke : stolpci] iz PripravljalecPodatkov in ga poslje naprej v SQL
	}
	
	protected void odstraniZbirke(List<String> zbirke){
		// dobi imeZbirke, ki jo odstrani iz SQL
	}
	
	protected void dodajElemente(Map<String, List<List<String>>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga poslje naprej v SQL (v pravo zbirko)
	}
	
	protected void odstraniElemente(String imeZbirke, List<List<String>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga odstrani iz SQL (iz prave zbirke)
	}

	/**
	 * @return baza
	 */
	public Map<String, List<List<String>>> getBaza() {
		return baza;
	}
}
