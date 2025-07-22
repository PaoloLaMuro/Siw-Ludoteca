-- Inserisci utenti
INSERT INTO users (id, name, surname, email)
VALUES (1, 'Mario', 'Rossi', 'mario.rossi@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (2, 'Anna', 'Bianchi', 'anna.bianchi@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (3, 'Luca', 'Verdi', 'luca.verdi@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (4, 'Giulia', 'Ferrari', 'giulia.ferrari@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (5, 'Marco', 'Romano', 'marco.romano@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (6, 'Francesca', 'Conti', 'francesca.conti@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (7, 'Alessandro', 'Ricci', 'alessandro.ricci@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (8, 'Chiara', 'Galli', 'chiara.galli@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (9, 'Davide', 'Martini', 'davide.martini@email.com');

INSERT INTO users (id, name, surname, email)
VALUES (10, 'Sara', 'Colombo', 'sara.colombo@email.com');

-- Inserisci una Casa Produttrice (logo_id nullo)
INSERT INTO casa_produttrice (id, nome, testo, anno_pubblicazione, logo_id)
VALUES (1, 'Nintendo', 'Storica azienda giapponese di videogiochi.', 1889, NULL);

-- Inserisci un Videogioco (copertina_id nullo)
INSERT INTO videogioco (id, titolo, genere, descrizione, data_uscita, pegi, casa_produttrice_id, copertina_id)
VALUES (1, 'The Legend of Zelda: Breath of the Wild','ADVENTURE','Storica azienda giapponese di videogiochi.','2017-03-03','PEGI_12',1,NULL);

-- Inserisci recensioni per The Legend of Zelda: Breath of the Wild (tutti i 10 utenti)
INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (1,'Capolavoro assoluto','Un gioco che ridefinisce completamente il genere open world. Libertà totale di esplorazione!',5,1,1);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (2,'Esperienza magica','Ogni angolo di Hyrule nasconde una sorpresa. Grafica mozzafiato e gameplay perfetto.',5,1,2);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (3,'Innovativo e coinvolgente','Il sistema di fisica e chimica del gioco è incredibile. Ore e ore di divertimento garantito.',4,1,3);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (4,'Un mondo da vivere','Non è solo un gioco, è un mondo in cui perdersi. La colonna sonora è sublime.',5,1,4);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (5,'Zelda al suo meglio','Dopo anni di attesa, Nintendo ha creato il miglior Zelda di sempre. Semplicemente perfetto.',5,1,5);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (6,'Avventura epica','Ogni missione è una scoperta. Il level design è fenomenale e la storia coinvolgente.',4,1,6);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (7,'Libertà senza limiti','Finalmente un open world fatto bene. Si può affrontare in mille modi diversi.',5,1,7);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (8,'Emozioni pure','Un gioco che sa emozionare ad ogni passo. I puzzle dei Santuari sono geniali.',4,1,8);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (9,'Rivoluzionario','Ha cambiato il mio modo di vedere i videogiochi open world. Esperienza indimenticabile.',5,1,9);

INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (10,'Meraviglioso','Grafica, gameplay, storia: tutto è curato nei minimi dettagli. Un must have assoluto.',5,1,10);

-- Inserisci un secondo Videogioco (copertina_id nullo)
INSERT INTO videogioco (id, titolo, genere, descrizione, data_uscita, pegi, casa_produttrice_id, copertina_id)
VALUES (2, 'Super Mario Odyssey','ADVENTURE','Un viaggio colorato e divertente nel mondo di Mario.','2017-10-27','PEGI_12',1,NULL);

-- Inserisci una recensione per il secondo videogioco
INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (2,'Recensione Mario','Divertente e creativo, platform di alta qualità!',4,2,1);