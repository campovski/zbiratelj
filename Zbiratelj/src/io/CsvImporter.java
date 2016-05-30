package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter {
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

	/**
	 * @return the datoteka
	 */
	public ArrayList<List<String>> getDatoteka() {
		return datoteka;
	}
}
