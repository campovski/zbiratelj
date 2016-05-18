package baza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PripravljalecPodatkov {
	
	private Map<String, List<List<String>>> slovar;
	
	public PripravljalecPodatkov(){
		slovar = new HashMap<String, List<List<String>>>();
		List<List<String>> seznamSeznamovFilmi = new ArrayList<List<String>>();
		List<String> seznamFilmiStolpci = new ArrayList<String>();
		seznamFilmiStolpci.add("Naslov");
		seznamFilmiStolpci.add("Leto");
		seznamSeznamovFilmi.add(seznamFilmiStolpci);
		List<String> seznamFilmi1 = new ArrayList<String>();
		seznamFilmi1.add("Star Wars");
		seznamFilmi1.add("2016");
		seznamSeznamovFilmi.add(seznamFilmi1);
		List<String> seznamFilmi2 = new ArrayList<String>();
		seznamFilmi2.add("Me Before You");
		seznamFilmi2.add("2016");
		seznamSeznamovFilmi.add(seznamFilmi2);
		List<String> seznamFilmi3 = new ArrayList<String>();
		seznamFilmi3.add("The Adventures of Sherlock Holmes");
		seznamFilmi3.add("1984");
		seznamSeznamovFilmi.add(seznamFilmi3);
		
		List<List<String>> seznamSeznamovFilmskiPlakati = new ArrayList<List<String>>();
		List<String> seznamFilmskiPlakatiStolpci = new ArrayList<String>();
		seznamFilmskiPlakatiStolpci.add("Naslov");
		seznamFilmskiPlakatiStolpci.add("Velikost");
		seznamSeznamovFilmskiPlakati.add(seznamFilmskiPlakatiStolpci);
		List<String> seznamFilmskiPlakati1 = new ArrayList<String>();
		seznamFilmskiPlakati1.add("Huston");
		seznamFilmskiPlakati1.add("Velik");
		seznamSeznamovFilmskiPlakati.add(seznamFilmskiPlakati1);
		List<String> seznamFilmskiPlakati2 = new ArrayList<String>();
		seznamFilmskiPlakati2.add("Othello");
		seznamFilmskiPlakati2.add("Srednji");
		seznamSeznamovFilmskiPlakati.add(seznamFilmskiPlakati2);
		
		slovar.put("Pivo", seznamSeznamovFilmi);
		slovar.put("Filmi", seznamSeznamovFilmi);
		slovar.put("Filmski plakati", seznamSeznamovFilmskiPlakati);
	}

	/**
	 * @return the slovar
	 */
	public Map<String, List<List<String>>> getSlovar() {
		return slovar;
	}	
}
