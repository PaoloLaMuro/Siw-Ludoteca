-- Inserisci un utente
INSERT INTO users (id, name, surname, email)
VALUES (1, 'Mario', 'Rossi', 'mario.rossi@email.com');

-- Inserisci una Casa Produttrice (logo_id nullo)
INSERT INTO casa_produttrice (id, nome, testo, anno_pubblicazione, logo_id)
VALUES (1, 'Nintendo', 'Storica azienda giapponese di videogiochi.', 1889, NULL);

-- Inserisci un Videogioco (copertina_id nullo)
INSERT INTO videogioco (id, titolo, genere, descrizione, data_uscita, pegi, casa_produttrice_id, copertina_id)
VALUES (1, 'The Legend of Zelda: Breath of the Wild','ADVENTURE','Storica azienda giapponese di videogiochi.','2017-03-03','PEGI_12',1,NULL);

-- Inserisci una recensione (autore = utente, videogioco = videogioco)
INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (1,'Recensione Zelda','Capolavoro assoluto, gameplay innovativo!',5,1,1);

-- Inserisci un secondo Videogioco (copertina_id nullo)
INSERT INTO videogioco (id, titolo, genere, descrizione, data_uscita, pegi, casa_produttrice_id, copertina_id)
VALUES (2, 'Super Mario Odyssey','ADVENTURE','Un viaggio colorato e divertente nel mondo di Mario.','2017-10-27','PEGI_12',1,NULL);

-- Inserisci una recensione per il secondo videogioco
INSERT INTO recensione (id, titolo, testo, voto, videogioco_id, utente_id)
VALUES (2,'Recensione Mario','Divertente e creativo, platform di alta qualità!',4,2,1);