/**
 * 
 */
package baza;

import java.util.List;

/**
 * @author nina
 *
 */
public class SqlManager {
	public void dodajZbirko(List<String> stolpci){
		// dobi seznam [imeZbirke : stolpci] iz PripravljalecPodatkov in ga poslje naprej v SQL
	}
	
	public void odstraniZbirke(List<String> zbirke){
		// dobi imeZbirke, ki jo odstrani iz SQL
	}
	
	public void dodajElemente(String imeZbirke, List<List<String>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga poslje naprej v SQL (v pravo zbirko)
	}
	
	public void odstraniElemente(String imeZbirke, List<List<String>> elementi){
		// dobi imeZbirke in seznam elementov (dolzine enake kot je prvaVrstica), ki ga odstrani iz SQL (iz prave zbirke)
	}
	
}
