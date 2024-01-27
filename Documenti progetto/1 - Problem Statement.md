# 1. Problem Domain
Il progetto “SecondLifeTech” ha lo scopo di sviluppare un sito di e-commerce specializzato nella vendita di smartphone e tablet ricondizionati. Il sito offrirà una piattaforma completa per l'acquisto e la stima monetaria di dispositivi elettronici ricondizionati, promuovendo allo stesso tempo la sostenibilità ambientale attraverso il riutilizzo di dispositivi usati. Il sito dovrà essere altamente user-friendly, sicuro ed efficiente. Il sistema prodotto faciliterà l’interfacciamento dell’azienda con i propri clienti e possibili tali, questo tramite un’interfaccia intuitiva e facilitata.

# 2. Functional Requirements
SecondLifeTech supporta quattro tipi utenti:
1.	I *Guest* devono essere in grado di visualizzare i dispositivi in vendita, visualizzare i dettagli di uno specifico dispositivo, aggiungere dispositivi al carrello, modificare le quantità dei dispositivi nel carrello, valutare un proprio dispositivo usato, registrarsi e fare login per diventare clienti
2.	I *Clienti* possiedono gli stessi requisiti degli utenti *Guest* ma in aggiunta devono essere in grado di finalizzare gli ordini, effettuare logout, consultare il proprio profilo, visualizzare gli ordini eseguiti, eliminare il proprio account
3.	I *Gestori Prodotti* devono essere in grado di aggiungere nuovi dispositivi in vendita, modificare un prodotto in vendita, eliminare un prodotto in vendita, modificare il banner della pagina principale, visualizzare gli ordini effettuati e gli specifici dettagli
4.	I *Gestori Utenti* devono essere in grado di aggiungere nuovi clienti di tipo Gestore, eliminare utenti di tipo Gestore, visualizzare gli ordini effettuati e gli specifici dettagli

# 3. Nonfunctional Requirements
1. L’interfaccia del sistema dev’essere semplice ed intuitiva rendendo la navigazione molto più fluida
2. Il sistema deve poter mantenere il suo corretto funzionamento anche nel caso di dati errati
3. Il sistema, su richiesta, deve poter fornire il catalogo all’utente in meno di due secondi
4. Il sistema deve garantire un grado di sicurezza e privacy verso gli account degli utenti

# 4. Ambiente
L’ambiente di destinazione del sistema prodotto sarà un Web Server Apache Tomcat sfruttando un Database MySQL per la memorizzazione dei dati degli utenti, delle transazioni, dei prodotti e dei carrelli. Sarà quindi possibile per qualsiasi dispositivo connesso ad una rete di connettersi al server e usufruire delle funzioni del sistema.

# 5.	Scenarios

## 5.1.	Scenario 1 (S1) – *Guest* registra il proprio account e diventa *Cliente*
L’utente visitatore (*Guest*) Mario Rossi, vuole registrare un account così da poter avere la possibilità di effettuare acquisti in seguito. Mario dalla Home Page clicca sul tasto “Accedi”, una volta sulla pagina di Login in mancanza di un account, clicca su “Registrati qui”. Questo tasto conduce Mario ad un form da riempire nel quale deve specificare i vari campi Nome, Cognome, Data di nascita, Indirizzo, E-mail, Password e Numero di telefono. Premendo sul tasto “Conferma”, Mario viene avvisato del corretto inserimento dei dati e viene rimandato alla pagina di Login, invitato dal sito ad eseguire l’accesso con l’account appena creato.

## 5.2.	Scenario 2 (S2) – *Cliente* aggiunge un prodotto al carrello
La *Cliente* Martina Pisani vuole cercare un iPhone 12 e salvarlo nel carrello per poterlo acquistare in seguito. Dalla homepage, clicca sul tasto “Accedi” e compila il form con i dati richiesti e clicca sul tasto “Login”. Per errore Martina ha inserito incorrettamente i dati, viene avvisata dal sito ed è invitata a riprovare l’accesso. Reinserisce i dati con più attenzione e clicca nuovamente su “Login”. Stavolta il sistema le garantisce l’accesso. Martina preme sul tasto “Prodotti” che la porta su una pagina nella quale le vengono richiesti quali dispositivi cercasse tramite un form. Martina inserisce, nel campo di ricerca, il nome del telefono, nel campo brand sceglie Apple e clicca “Conferma”. La pagina si aggiorna mostrandole il telefono da lei cercato indicando il range di prezzo degli iPhone 12 che il sito vende. Martina clicca su “Dettagli” del dispositivo mostrato. Questo la porta su una pagina in cui è possibile scegliere la configurazione desiderata. Sceglie quindi, tramite gli appositi selettori un iPhone 12 con 256GB di spazio interno, 4GB di RAM, uno schermo da 6.1”, il colore Nero e condizioni “Ottimo”. Sceglie opportunamente la quantità da lei desiderata, un solo telefono, e clicca il tasto “Aggiungi al carrello”, venendo avvisata dal sistema che l’aggiunta al carrello è andata a buon fine.

## 5.3.	Scenario 3 (S3) – *Guest* vuole finalizzare un acquisto
Il *Guest* Cristiano Robertino vuole finalizzare l’acquisto di due prodotti che ha inserito nel suo carrello temporaneo; quindi, dalla pagina del carrello clicca il tasto “Finalizza Ordine”. Dato che è un utente *Guest* il sito invita a Cristiano di accedere o di creare un nuovo account per continuare. Dopo aver creato un nuovo account, Cristiano diventa un utente *Cliente* ed i prodotti che si trovavano nel carrello temporaneo vengono trasferiti al carrello permanente dell’utente. Dalla pagina del carrello clicca nuovamente il tasto per finalizzare l’ordine, ma non ha alcun indirizzo di spedizione o fatturazione registrato nel suo account; quindi, il sito gli chiede di inserirne uno, avendo l’opzione di salvarlo per utilizzi futuri. Inserisce i dati della sua carta di credito per il pagamento e, dopo una verifica andata a buon fine (da un sistema esterno), l’ordine viene effettuato e Cristiano viene riportato su una schermata di riepilogo.

## 5.5.	Scenario 4 (S4) – *Gestore Prodotti* aggiunge un prodotto	  
Al gestore dei prodotti, Arsenio Fanucci, è stato assegnato il compito di aggiungere al catalogo il nuovissimo smartphone iPhone 15 Pro di colore Blu, 6GB di RAM, 128GB di memoria e 6,12’’ di display in condizioni Ottime. Arsenio effettua il Login con successo, clicca su “Pannello Admin” che gli presenta una schermata con le possibili funzioni. Arsenio clicca su “Aggiungi Prodotto”, questo pulsante porta il gestore ad una pagina contenente una form nel quale carica la foto del dispositivo e compila tutti i campi in maniera appropriata con Nome: iPhone 15 Pro, Display: 6,12, Storage: 128, RAM: 6, Quantità: 1, Colore: Blu, Marca: Apple, Categoria: Smartphone, Condizione: Ottimo e Anno: 2023. E preme conseguentemente il tasto “Aggiungi”. Alla pressione del tasto, viene reindirizzato ad una pagina che riepiloga l’aggiunta del prodotto.

## 5.5.	Scenario 5 (S5) – *Gestore Utenti* rimuove un *Gestore Prodotti*
Il gestore degli utenti, Lucilla Nucci, ha il compito di rimuovere la sua ex-collega Liliana Bianchi dal ruolo di *Gestore Prodotti*, causa pensionamento. Lucilla, effettua correttamente il Login al sistema con la sua mail aziendale, e premendo sul tasto “Pannello Admin” le vengono presentate le funzioni per Aggiungere o Rimuovere un gestore. Clicca su “Rimuovi Gestore”, e le viene mostrato un form nel quale può inserire l’e-mail aziendale di Liliana. E preme il tasto “Conferma”. Il sito allora si aggiorna con l’esito dell’operazione.

## 5.6.	Scenario 6 (S6) – Utente ottiene una stima dell’usato
L’Utente Antonio Roghi vuole ottenere una stima monetaria di un suo vecchio dispositivo, con la speranza di poterlo venderlo all’azienda SecondLifeTech. Dalla Home Page clicca il tasto “Valuta il tuo usato”. In seguito, gli viene presentata la schermata con un selettore nel quale può inserire la marca del dispositivo, la pagina si aggiorna mostrando un selettore con tutti i possibili dispositivi valutabili, compilato anche questo campo, la pagina si aggiorna mostrando i selettori per condizione e per spazio interno. Riempiti tutti i campi presenti, Antonio può cliccare su “Valuta” e la pagina si aggiorna mostrando il dispositivo e la sua valutazione in euro.



# 6. Criteri di accettazione/vincoli

# 7. Deliverables e Deadlines

