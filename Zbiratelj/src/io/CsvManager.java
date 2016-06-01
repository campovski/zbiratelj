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

import baza.PripravljalecPodatkov;

public class CsvManager {
	private ArrayList<List<String>> datoteka;

	@SuppressWarnings("resource")
	public void preberiCsv(File datoteka2) throws IOException, FileNotFoundException{
		datoteka = new ArrayList<List<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(datoteka2));
		String vrstica;
		while ((vrstica = reader.readLine()) != null){
			String[] vrstica1 = vrstica.split(",");
			List<String> vrstica2 = new ArrayList<String>();
			for (String beseda : vrstica1){
				vrstica2.add(beseda);
			}
			datoteka.add(vrstica2);
		}
	}
	
	public void narediCsv(String naslovDirektorija, List<String> zbirke) throws IOException{
		String osName = System.getProperty("os.name");
		
		PripravljalecPodatkov pripravljalec = new PripravljalecPodatkov();
		Map<String, List<List<String>>> slovar = pripravljalec.getSlovar();
		
		for (String zbirka : zbirke){
			String fileName = null;
			if (osName.equals("Linux") || osName.equals("Mac OS X")){
				fileName = naslovDirektorija+"/"+zbirka+".csv";
			}
			else if (osName.equals("Windows")){
				fileName = naslovDirektorija+"\\"+zbirka+".csv";
			}
			
			FileWriter writer = new FileWriter(fileName);
			List<List<String>> seznamElementov = slovar.get(zbirka);
			for (List<String> element : seznamElementov){
				for (int i = 0; i < element.size(); i++){
					writer.append(element.get(i));
					if (i < element.size() - 1){
						writer.append(",");
					}
				}
				writer.append("\n");
			}
			writer.flush();
			writer.close();
		}
	}

	/**
	 * @return the datoteka
	 */
	public ArrayList<List<String>> getDatoteka() {
		return datoteka;
	}
}
