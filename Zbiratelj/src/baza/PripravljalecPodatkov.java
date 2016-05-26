package baza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gui.GlavnoOkno;

/**
 * @author campovski
 * Razred PripravljalecPodatkov skrbi za zvezo med uporabniskim vmesnikom in SqlManager-jem.
 * Tako sta njegovi nalogi priprava podatkov za posiljanje v bazo in dajanja ukazov, kaj naj
 * SqlManager naredi s podatki. Prav tako lahko PripravljalecPodatkov zahteva podatke iz baze,
 * torej jih lahko od tam tudi dobi.
 */
public class PripravljalecPodatkov {
	
	private static Map<String, List<List<String>>> slovar;
	private static Map<String, List<List<String>>> elementiZaDodajanje = new HashMap<String, List<List<String>>>();

	/**
	 * Naredi testni slovar. Mogoce v prihodnosti se kaj drugega...
	 */
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
		seznamFilmskiPlakati1.add("Houston we have a problem");
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
	 * @param stolpci
	 * Metoda dobljen seznam doda v slovar, pri cemer je prvi element stolpcev
	 * ime zbirke, ostali pa so imena stolpcev. Pred tem zbirko se doda v bazo.
	 */
	public static void dodajZbirko(List<String> stolpci){
		List<List<String>> seznamSeznamov = new ArrayList<List<String>>();
		List<String> prvaVrstica = new ArrayList<String>();
		for (int i = 1; i < stolpci.size(); i++){
			prvaVrstica.add(stolpci.get(i));
		}
		seznamSeznamov.add(prvaVrstica);
		
		SqlManager.dodajZbirko(stolpci);
		
		slovar.put(stolpci.get(0), seznamSeznamov);
	}
	
	/**
	 * Metoda se pozene v novem vlaknu, ki po branju JCheckBox-ov pocaka, ali
	 * bo uporabnik potrdil izbris. Ce ga, nadaljujemo, drugace se vlakno ustavi.
	 */
	public static void izbrisiZbirke(List<String> zbirke){
		SqlManager.odstraniZbirke(zbirke);
		System.out.println(zbirke);
	}
	
	public static void dodajElement(String zbirka, List<String> element){
		if (elementiZaDodajanje.containsKey(zbirka)){
			if (!elementiZaDodajanje.get(zbirka).contains(element)){
				elementiZaDodajanje.get(zbirka).add(element);
			}
		}
		else {
			List<List<String>> dodanElement = new ArrayList<List<String>>();
			dodanElement.add(element);
			elementiZaDodajanje.put(zbirka, dodanElement);
		}
		System.out.println(elementiZaDodajanje);
	}
	
	public static void dodajElemente(){
		if (!elementiZaDodajanje.isEmpty()){
			SqlManager.dodajElemente(elementiZaDodajanje);
			System.out.println("To so dodani elementi: " + elementiZaDodajanje);
			elementiZaDodajanje.clear();
		}
	}
	
	/**
	 * Metoda iz JCheckBoxov prebere, katere elemente je potrebno izbrisati. Ker elementi
	 * niso tako nevarni za izbris, kakor zbirke, tu ni potrebno preverjati, ali res zelimo
	 * izbris ali ne.
	 */
	public static void izbrisiElemente(List<List<String>> elementi){
		SqlManager.odstraniElemente(GlavnoOkno.getZbirkaZaRisanje(), elementi);
		System.out.println(elementi);
	}

	/**
	 * @return slovar
	 */
	public Map<String, List<List<String>>> getSlovar() {
		return slovar;
	}
}