package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import baza.SqlManager;

public class CsvManager {
	private ArrayList<List<String>> datoteka;

	/**
	 * Prebere izbran CSV v seznam datoteka.
	 * @param f
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void preberiCsv(File f) throws IOException, FileNotFoundException{
		datoteka = new ArrayList<List<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(f));
		
		String vrstica;
		while ((vrstica = reader.readLine()) != null){
			String[] vrstica1 = vrstica.split(",");
			// TODO String[] vrstica1 = vrstica.split("\\|");
			List<String> vrstica2 = new ArrayList<String>();
			for (String beseda : vrstica1){
				vrstica2.add(beseda);
			}
			datoteka.add(vrstica2);
		}
		
		reader.close();
	}
	
	/**
	 * Naredi CSV datoteko izbrane zbirke v izbran direktorij. 
	 * @param naslovDirektorija
	 * @param zbirke
	 * @throws IOException
	 */
	public void narediCsv(String naslovDirektorija, List<String> zbirke) throws IOException{
		
		String osName = System.getProperty("os.name");
		
		for (String zbirka : zbirke){
			String fileName = null;
			if (osName.equals("Linux") || osName.equals("Mac OS X")){
				fileName = naslovDirektorija+"/"+zbirka+".csv";
			}
			else if (osName.equals("Windows")){
				fileName = naslovDirektorija+"\\"+zbirka+".csv";
			}
			
			File f = new File(fileName);
			if (f.exists() && !f.isDirectory()){
				f.delete();
			}
			
			FileWriter writer = new FileWriter(fileName);
			Map<Integer, Map<String, String>> izbranaZbirka = SqlManager.beriZbirkoPodatki(zbirka);
			int stevilo_stolpcev = SqlManager.beriZbirkoStolpci(zbirka).size();
			
			int n = 0;
			for (String stolpec : SqlManager.beriZbirkoStolpci(zbirka)){
				writer.append(stolpec);
				n++;
				if (n < stevilo_stolpcev){
					writer.append(",");
				}
			}
			writer.append("\n");
			n = 0;
			
			for (int i : izbranaZbirka.keySet()){
				Map<String, String> element = izbranaZbirka.get(i);
				for (String stolpec : SqlManager.beriZbirkoStolpci(zbirka)){
					writer.append(element.get(stolpec));
					n++;
					if (n < stevilo_stolpcev){
						writer.append(",");
					}
				}
				writer.append("\n");
				n = 0;
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @return datoteka
	 */
	public ArrayList<List<String>> getDatoteka() {
		return datoteka;
	}
}
