# zbiratelj
Program v Javi, namenjen vsem zbirateljem, ki so izgubljeni v svojih (pre)mnogih zbirkah. Program omogoča po meri narediti zbirke, ki se potem shranijo v podatkovno bazo.

Navodila za uporabo: Za delovanje programa Zbiratelj potrebujemo MySQL, kar na Linuxu naredimo z ukazom `sudo apt-get install mysql-server`. Nato ustvarimo bazo "testdb" s shemo, opisano v datoteki zbirke.sql. User je "testuser" z geslom "password". Zaenkrat deluje program samo, če imamo tako poimenovano bazo in userja. Ko imamo bazo z zgornjo shemo, poženemo program in že smo nared, da dodamo v bazo prvo zbirko.

Opomba: Potrebno je prenesti MySql Java Connector v5.1.39, dostopen je na naslovu (https://dev.mysql.com/downloads/connector/j/).
