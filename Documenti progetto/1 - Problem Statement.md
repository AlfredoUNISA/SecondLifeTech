# 1. Problem Domain
Il progetto "SecondLifeTech" ha lo scopo di sviluppare un sito di e-commerce specializzato nella vendita di smartphone e tablet ricondizionati. Il sito offrirà una piattaforma completa per l'acquisto e la stima monetaria di dispositivi elettronici ricondizionati, promuovendo allo stesso tempo la sostenibilità ambientale attraverso il riutilizzo di dispositivi usati. Il sito dovrà essere altamente user-friendly, sicuro ed efficiente. Il sistema prodotto faciliterà l’interfacciamento dell’azienda con i propri clienti e possibili tali, questo tramite un’interfaccia intuitiva e facilitata.

# 2. Requisiti Funzionali
SecondLifeTech supporta quattro tipi utenti:
1.	I *Guest* sono dei clienti non registrati che devono essere in grado di: 
	- Visualizzare i dispositivi in vendita 
	- Visualizzare i dettagli di uno specifico dispositivo
	- Aggiungere dispositivi al carrello
	- Modificare le quantità dei dispositivi nel carrello
	- Valutare un proprio dispositivo usato
	- Registrarsi al sito
	- Fare il login per diventare *Clienti*

2.	I *Clienti* sono dei clienti registrati che possiedono gli stessi requisiti degli utenti *Guest* (tranne quello di fare il login), ma in aggiunta devono essere in grado di:
	- Finalizzare gli ordini
	- Effettuare il logout
	- Consultare e modificare il proprio profilo 
	- Visualizzare gli ordini eseguiti
	- Eliminare il proprio account

3.	I *Gestori Prodotti* devono essere in grado di: 
	- Aggiungere nuovi dispositivi in vendita
	- Modificare un prodotto in vendita
	- Eliminare un prodotto in vendita 
	- Modificare le immagini del banner nella pagina principale
	- Visualizzare gli ordini effettuati e gli specifici dettagli

4.	I *Gestori Utenti* devono essere in grado di:
	- Aggiungere nuovi utenti *Guest*, *Clienti* e *Gestori Prodotti*
	- Eliminare utenti *Guest*, *Clienti* e *Gestori Prodotti* esistenti nel sistema
	- Gestire tutti i tipi di utenti, in modo da risolvere eventuali problemi

I clienti registrati e non ed i gestori utilizzeranno due versioni differenti della stessa interfaccia utente.

# 3. Requisiti Non Funzionali
Il sistema deve:
1. Avere una interfacca semplice ed intuitiva, rendendo la navigazione del sito piacevole
2. Essere accessibile da qualsiasi dispositivo e browser (che supportano cookies e Javascript)
3. Poter mantenere il suo corretto funzionamento anche nel caso di dati errati
4. Deve poter fornire il catalogo dei dispositivi all’utente in meno di cinque secondi
5. Deve garantire la sicurezza degli account degli utenti
6. Deve avere una taglia massima di 10 GB
7. Essere conforme alle normative europee sulla privacy online

# 4. Ambiente
L’ambiente di destinazione del sistema prodotto è un qualsiasi sistema operativo Windows o Linux, oppure un cloud provider (come Amazon Web Services).

# 5. Deliverables e Deadlines

- Requirement Analysis Document (RAD) - 31/01/2024 ~ 01/02/2024
- System Design Document (SDD) - 
- Object Design Document (ODD) - 
- Schema Database - 
- Test Planning Documents - 
- Test Execution Documents - 

# 6.	Scenari

## 6.1.	Scenario 1 (S1) – *Guest* registra il proprio account
Nome: RegistraNuovoCliente

Attori Partecipanti: Mario:Guest

Flusso di Eventi:
1. Mario vuole registrarsi al sito, in modo da poter avere la possibilità di effettuare acquisti in futuro
2. Dalla Home Page del sito, naviga verso la pagina di Login/Registrazione attraverso un pulsante
3. Una volta sulla pagina di Login, in mancanza di un account, preme il pulsante per registrarsi
4. Questo porta Mario ad una pagina con un form, che riempie inserendo le sue informazioni: 
   - Nome: Mario
   - Cognome: Rossi
   - Data di nascita: 05/07/1998
   - Indirizzo: Via Roma 12, Napoli
   - E-mail: marioRoss1@libero.it
   - Password: 123rossi
   - Numero di telefono: 012 345 6789
5. Mario preme il tasto di conferma
6. Viene avvisato del corretto inserimento dei dati e viene rimandato alla pagina di Login

## 6.2. Scenario 2 (S2) - *Guest* fa il Login e diventa *Cliente*
Nome: LoginCliente

Attori Partecipanti: Roberto:Cliente

Flusso di Eventi:
1. Roberto vuole accedere al sito per acquistare quello che aveva nel carrello
2. Dalla Home Page del sito, naviga verso la pagina di Login/Registrazione attraverso un pulsante
3. Una volta sulla pagina di Login, compila il form con i seguenti dati:
	- E-mail: rober12@gmail.com
	- Password: abcddf123
4. Per sbaglio, Roberto ha inserito incorrettamente la password e viene avvisato dal sito di un errore sui dati inseriti
5. Reinserisce i dati con più attenzione ed invia nuovamente il form
6. Stavolta il sistema gli garantisce l’accesso, avvisandolo del successo del Login
7. Viene mandato sulla Home Page, dove il pulsante di Login/Registrazione è sostituito da un pulsante per accedere al suo profilo

## 6.3.	Scenario 3 (S3) - *Cliente Generico* cerca un dispositivo
Nome: CercaDispositivo

Attori Partecipanti: Antonio:ClienteGenerico

Flusso di Eventi:
1. Antonio vuole vedere se è in vendita sul sito un certo dispositivo
2. Dalla Home Page del sito, naviga verso la pagina dei Prodotti attraverso un pulsante
3. Una volta sulla pagina dei Prodotti, il sito presenta ad Antonio una serie di dispositivi, dove viene mostrata l'immagine, il nome ed il range di prezzo per ciascuno di essi
4. Antonio non trova il dispositivo che vuole in questa schermata, pertanto clicca sulla barra di ricerca, scrive "Google Pixel 4" e preme Invio
5. Il sito ritorna una lista di dispositivi con il nome simile a "Google Pixel 4" che Antonio può visualizzare

## 6.4.	Scenario 4 (S4) – *Cliente Generico* visualizza i dettagli di un prodotto
Nome: VisualizzaDettagliProdotto

Attori Partecipanti: Martina:ClienteGenerico

Flusso di Eventi:
1. Martina è interessata ad un "Iphone 8" che ha individuato nella pagina Prodotti del sito e vuole osservarne le caratteristiche
2. Dalla pagina della visualizzazione dei prodotti, Martina clicca sul dispositivo
3. Viene spostata su una pagina che contiene l'immagine, tutte le informazioni e tutte le opzioni disponibili/non disponibili per il dispositivo "Iphone 8"

## 6.5.	Scenario 5 (S5) – *Cliente Generico* aggiunge un prodotto al carrello
Nome: AggiungiAlCarrello

Attori Partecipanti: Martina:ClienteGenerico

Flusso di Eventi:
1. Martina sta osservando le caratteristiche di un "Iphone 12" e decide di volerlo aggiungere al carrello per poterlo osservare in seguito
2. Scegle la configurazione desiderata del dispositivo tramite gli appositi selettori:
   - Spazio Interno: 256GB
   - RAM: 4GB
   - Dimensione Schermo: 6.1"
   - Colore: Nero
   - Condizioni: "Ottimo"
3. Sceglie opportunamente la quantità da lei desiderata, un solo telefono, e clicca il tasto per aggiungere la configurazione al suo carrello
4. Il sistema porta Martina al suo carrello, dove è presente la configurazione di "Iphone 12" scelta da lei in quel momento, gli altri dispositivi inseriti in precedenza ed il costo totale

## 6.6.	Scenario 6 (S6) – *Guest* vuole finalizzare un acquisto
Nome: FinalizzaOrdine

Attori Partecipanti: Luigi:Guest

Flusso di Eventi:
1. Luigi vuole finalizzare l’acquisto di due dispositivi che ha inserito nel suo carrello temporaneo
2. Dalla pagina del carrello clicca il tasto per Finalizzare l'ordine 
3. Dato che Luigi è un utente *Guest*, il sistema lo notifica che solamente i clienti registrati (*Cliente*) possno finalizzare gli ordini, mostrandogli anche un pulsante per accedere o per creare un nuovo account
4. Luigi, preme il pulsante per fare il Login e si identifica
5. Dopo aver eseguito l'accesso, Luigi diventa un *Cliente* ed i prodotti che si trovavano nel carrello temporaneo vengono trasferiti al carrello permanente del cliente
6. Ritornando alla pagina del carrello, clicca nuovamente il tasto per finalizzare l’ordine 
7. Luigi viene mandato ad una pagina di Finalizzazione
8. La pagina mostra che il cliente non ha alcun indirizzo di spedizione/fatturazione registrato e non ha alcun un metodo di pagamento registrato
9. Luigi preme un pulsante per aggiungere un nuovo indirizzo ed inserisce nel form:
   - Indirizzo: "Via Dante Alighieri 2, Napoli"
   - Imposta come indirizzo predefinito: [✓]
10. La pagina mostra che l'indirizzo è stato aggiornato
11. Luigi preme un pulsante per aggiungere un nuovo metodo di pagamento (carta di credito) ed inserisce nel form:
    - Numero: 5531222376949217
    - Scadenza: 11/2025
    - Codice: 891
    - Proprietario: Luigi Rossi
    - Imposta come metodo di pagamento predefinito: [✓]
12. La pagina mostra che il metodo di pagamento è stato aggiornato
13. Luigi clicca il pulsante per procedere con il pagamento
14. Dopo una verifica andata a buon fine, l’ordine viene effettuato e Luigi viene riportato sulla schermata dei suoi ordini, dove si trova l'ordine appena creato

## 6.6.	Scenario 4 (S4) – *Gestore Prodotti* aggiunge un prodotto	  
Al gestore dei prodotti, Arsenio Fanucci, è stato assegnato il compito di aggiungere al catalogo il nuovissimo smartphone iPhone 15 Pro di colore Blu, 6GB di RAM, 128GB di memoria e 6,12’’ di display in condizioni Ottime. Arsenio effettua il Login con successo, clicca su "Pannello Admin" che gli presenta una schermata con le possibili funzioni. Arsenio clicca su "Aggiungi Prodotto", questo pulsante porta il gestore ad una pagina contenente una form nel quale carica la foto del dispositivo e compila tutti i campi in maniera appropriata con Nome: iPhone 15 Pro, Display: 6,12, Storage: 128, RAM: 6, Quantità: 1, Colore: Blu, Marca: Apple, Categoria: Smartphone, Condizione: Ottimo e Anno: 2023. E preme conseguentemente il tasto "Aggiungi". Alla pressione del tasto, viene reindirizzato ad una pagina che riepiloga l’aggiunta del prodotto.

## 6.6.	Scenario 5 (S5) – *Gestore Utenti* rimuove un *Gestore Prodotti*
Il gestore degli utenti, Lucilla Nucci, ha il compito di rimuovere la sua ex-collega Liliana Bianchi dal ruolo di *Gestore Prodotti*, causa pensionamento. Lucilla, effettua correttamente il Login al sistema con la sua mail aziendale, e premendo sul tasto "Pannello Admin" le vengono presentate le funzioni per Aggiungere o Rimuovere un gestore. Clicca su "Rimuovi Gestore", e le viene mostrato un form nel quale può inserire l’e-mail aziendale di Liliana. E preme il tasto "Conferma". Il sito allora si aggiorna con l’esito dell’operazione.

## 6.6.	Scenario 6 (S6) – Utente ottiene una stima dell’usato
L’Utente Antonio Roghi vuole ottenere una stima monetaria di un suo vecchio dispositivo, con la speranza di poterlo venderlo all’azienda SecondLifeTech. Dalla Home Page clicca il tasto "Valuta il tuo usato". In seguito, gli viene presentata la schermata con un selettore nel quale può inserire la marca del dispositivo, la pagina si aggiorna mostrando un selettore con tutti i possibili dispositivi valutabili, compilato anche questo campo, la pagina si aggiorna mostrando i selettori per condizione e per spazio interno. Riempiti tutti i campi presenti, Antonio può cliccare su "Valuta" e la pagina si aggiorna mostrando il dispositivo e la sua valutazione in euro.

