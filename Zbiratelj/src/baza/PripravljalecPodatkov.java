package baza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;

import gui.GlavnoOkno;

public class PripravljalecPodatkov {
	
	private static Map<String, List<List<String>>> slovar;
	private static ArrayList<String> seznamZbirkZaIzbris;

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
	
	/**
	 * @return the seznamZbirkZaIzbris
	 */
	public static ArrayList<String> getSeznamZbirkZaIzbris() {
		return seznamZbirkZaIzbris;
	}
	
	public static void dodajZbirko(List<String> stolpci){
		List<List<String>> seznamSeznamov = new ArrayList<List<String>>();
		List<String> prvaVrstica = new ArrayList<String>();
		for (int i = 1; i < stolpci.size(); i++){
			prvaVrstica.add(stolpci.get(i));
		}
		seznamSeznamov.add(prvaVrstica);
		slovar.put(stolpci.get(0), seznamSeznamov);
		System.out.println(slovar);
	}
	
	public static void izbrisiZbirke(){
		//TODO po generiranju seznama pocakaj, kaj bo naredilo GlavnoOkno
		seznamZbirkZaIzbris = new ArrayList<String>();
		Map<JCheckBox, String> slovarCheckGumbov = GlavnoOkno.getSlovarCheckBoxZbirka();
		for (JCheckBox kljuc : slovarCheckGumbov.keySet()){
			if (kljuc.isSelected()){
				seznamZbirkZaIzbris.add(slovarCheckGumbov.get(kljuc));
			}
		}
	}
	
	public static void izbrisiElemente(){
		List<List<String>> seznamElementovZaIzbris = new ArrayList<List<String>>();
		Map<JCheckBox, List<String>> slovarCheckBoxElement = GlavnoOkno.getSlovarCheckBoxElement();
		for (JCheckBox kljuc : slovarCheckBoxElement.keySet()){
			if (kljuc.isSelected()){
				seznamElementovZaIzbris.add(slovarCheckBoxElement.get(kljuc));
			}
		}
		System.out.println(seznamElementovZaIzbris);
	}
}