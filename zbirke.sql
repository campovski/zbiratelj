CREATE TABLE zbirke (
       zbirke_ime VARCHAR(100) NOT NULL,
       PRIMARY KEY (zbirke_ime)
);

CREATE TABLE stolpci (
       stolpci_ime VARCHAR(100) NOT NULL,
       stolpci_zbirka VARCHAR(100),
       FOREIGN KEY (stolpci_zbirka) REFERENCES zbirke(zbirke_ime) ON DELETE CASCADE,
       PRIMARY KEY (stolpci_ime, stolpci_zbirka)
);

CREATE TABLE elementi (
       elementi_zbirka VARCHAR(100) NOT NULL,
       elementi_id INTEGER AUTO_INCREMENT,
       PRIMARY KEY (elementi_id),
       FOREIGN KEY (elementi_zbirka) REFERENCES zbirke(zbirke_ime) ON DELETE CASCADE
);

CREATE TABLE podatki (
       podatki_element INT,
       FOREIGN KEY (podatki_element) REFERENCES elementi(elementi_id) ON DELETE CASCADE,
       podatki_stolpec VARCHAR(100) NOT NULL,
       podatki_zbirka VARCHAR(100) NOT NULL,
       podatki_vsebina VARCHAR(100),
       FOREIGN KEY (podatki_stolpec, podatki_zbirka) REFERENCES stolpci (stolpci_ime, stolpci_zbirka) ON DELETE CASCADE
);
