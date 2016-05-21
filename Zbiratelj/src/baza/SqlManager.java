/**
 * 
 */
package baza;

import java.util.List;

/**
 * @author nina
 * Razred, ki bo posrednik med PripravljalecPodatkov in SQL bazo.
 */
public class SqlManager {
	protected static void dodajZbirko(List<String> stolpci){
		// dobi seznam [imeZbirke : stolpci] iz PripravljalecPodatkov in ga poslje naprej v SQL
	}
	
	protected static void odstraniZbirke(List<String> zbirke){
		// dobi imeZbirke, ki jo odstrani iz SQL
	}
	
	protected static void dodajElemente(String imeZbirke, List<List<String>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga poslje naprej v SQL (v pravo zbirko)
	}
	
	protected static void odstraniElemente(String imeZbirke, List<List<String>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga odstrani iz SQL (iz prave zbirke)
	}
	
}
