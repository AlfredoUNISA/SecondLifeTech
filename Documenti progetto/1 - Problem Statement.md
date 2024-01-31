# 1. Problem Domain
Il progetto "SecondLifeTech" ha lo scopo di sviluppare un sito di e-commerce specializzato nella vendita di smartphone e tablet ricondizionati. Il sito offrirà una piattaforma completa per l'acquisto e la stima monetaria di dispositivi elettronici ricondizionati, promuovendo allo stesso tempo la sostenibilità ambientale attraverso il riutilizzo di dispositivi usati. Il sistema prodotto faciliterà l’interfacciamento dell’azienda con i propri clienti e possibili tali, questo tramite un’interfaccia intuitiva e facilitata.

# 2. Requisiti Funzionali
SecondLifeTech supporta quattro tipi utenti:
1.	I *Guest* sono degli utenti non registrati che devono essere in grado di: 
	- Visualizzare i dispositivi in vendita 
	- Visualizzare i dettagli di uno specifico dispositivo
	- Aggiungere dispositivi al carrello
	- Modificare o elminare le quantità dei dispositivi nel carrello
	- Valutare un proprio dispositivo usato
	- Registrarsi al sito
	- Fare il login per diventare *Clienti*

2.	I *Clienti* sono degli utenti registrati che possiedono gli stessi requisiti degli utenti *Guest* (tranne quello di fare il login), ma in aggiunta devono essere in grado di:
	- Finalizzare gli ordini
	- Effettuare il logout
	- Consultare e modificare il proprio profilo
	- Visualizzare gli ordini effettuare
	- Eliminare il proprio account

3.	I *Gestori Prodotti* devono essere in grado di: 
	- Aggiungere nuovi dispositivi in vendita
	- Modificare un prodotto in vendita
	- Eliminare un prodotto in vendita 
	- Modificare le immagini del banner nella pagina principale
	- Visualizzare gli ordini effettuati e gli specifici dettagli

4.	I *Gestori Utenti* devono essere in grado di:
	- Aggiungere nuovi utenti *Guest*, *Clienti* e *Gestori Prodotti*
	- Eliminare tutti i tipi di utenti esistenti nel sistema
	- Gestire tutti i tipi di utenti, in modo da risolvere eventuali problemi

I clienti registrati e non ed i gestori utilizzeranno due versioni differenti della stessa interfaccia utente.

# 3. Requisiti Non Funzionali
Il sistema deve:
1. Avere una interfacca semplice ed intuitiva, rendendo la navigazione del sito piacevole
2. Poter mantenere il suo corretto funzionamento anche nel caso di dati errati
3. Garantire la persistenza dei dati anche in casi di malfunzionamento
4. Garantire la sicurezza degli account degli utenti
5. Poter fornire il catalogo dei dispositivi all’utente in meno di cinque secondi
6. Supportare più utenti allo stesso tempo
7. Avere una taglia massima di 10 GB
8. Supportare l'inserimento di nuovi tipi di prodotti
9.  Essere conforme alle normative europee sulla privacy online

# 4. Ambiente
L’ambiente di destinazione del sistema prodotto è un qualsiasi sistema operativo Linux, oppure un cloud provider (come Amazon Web Services).

Essere accessibile da qualsiasi dispositivo e browser (che supportano cookies e Javascript)

# 5. Deliverables e Deadlines

- Requirement Analysis Document (RAD) - 31/01/2024 ~ 01/02/2024
- System Design Document (SDD) - 
- Object Design Document (ODD) - 
- Schema Database - 
- Codice - 
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

## 6.3.	Scenario 3 (S3) - *Guest* cerca un dispositivo
Nome: CercaDispositivo

Attori Partecipanti: Antonio:Guest

Flusso di Eventi:
1. Antonio vuole vedere se è in vendita sul sito un certo dispositivo
2. Dalla Home Page del sito, naviga verso la pagina dei Prodotti attraverso un pulsante
3. Una volta sulla pagina dei Prodotti, il sito presenta ad Antonio una serie di dispositivi, dove viene mostrata l'immagine, il nome ed il range di prezzo per ciascuno di essi
4. Antonio non trova il dispositivo che vuole in questa schermata, pertanto clicca sulla barra di ricerca, scrive "Google Pixel 4" e preme Invio
5. Il sito ritorna una lista di dispositivi con il nome simile a "Google Pixel 4" che Antonio può visualizzare

## 6.4.	Scenario 4 (S4) – *Guest* visualizza i dettagli di un prodotto
Nome: VisualizzaDettagliProdotto

Attori Partecipanti: Martina:Guest

Flusso di Eventi:
1. Martina è interessata ad un "Iphone 8" che ha individuato nella pagina Prodotti del sito e vuole osservarne le caratteristiche
2. Dalla pagina della visualizzazione dei prodotti, Martina clicca sul dispositivo
3. Viene spostata su una pagina che contiene l'immagine, tutte le informazioni e tutte le opzioni disponibili/non disponibili per il dispositivo "Iphone 8"

## 6.5.	Scenario 5 (S5) – *Guest* aggiunge un prodotto al carrello
Nome: AggiungiAlCarrello

Attori Partecipanti: Martina:Guest

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
3. Dato che Luigi è un utente *Guest*, il sistema lo notifica che solamente i clienti registrati (*Cliente*) possono finalizzare gli ordini, mostrandogli anche un pulsante per accedere o per creare un nuovo account
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

## 6.7.	Scenario 7 (S7) – *Gestore Prodotti* aggiunge un prodotto
Nome: GestoreAggiungeProdotto

Attori Partecipanti: Arsenio:GestoreProdotti

Flusso di Eventi:
1. Ad Arsenio è stato assegnato il compito di aggiungere al catalogo un nuovissimo smartphone iPhone 15 Pro
2. Arsenio preme sul tasto per accedere, inserisce correttamente i dati ed effettua il login
3. Arsenio preme sul tasto per accedere al pannello gestori e viene spostato sulla pagina 
4. Arsenio preme sul tasto per accedere al pannello per l'aggiunta di un prodotto e viene spostato sulla pagina richiesta che mostra un form
5. Arsenio aggiunge la foto del dispositivo tramite il tasto per caricarla e compila il form:
	- Nome: "iPhone 15 Pro"
	- Display: "6,12"
	- Storage: "128"
	- RAM: "6"
	- Quantità: "1"
	- Colore: "Blu"
	- Marca: "Apple" 
	- Categoria: "Smartphone" 
	- Condizione: "Ottimo" 
	- Anno: "2023"
6. Arsenio preme il tasto per confermare l'aggiunta e viene spostato sulla pagina dei dettagli del nuovo prodotto aggiunto

## 6.8.	Scenario 8 (S8) – *Gestore Utenti* rimuove un *Gestore Prodotti*
Nome: GestoreRimuoveGestore

Attori Partecipanti: Lucilla:GestoreProdotti

Flusso di Eventi:
1. Lucilla ha il compito di rimuovere la sua ex-collega Liliana dal ruolo di *Gestore Prodotti*, causa pensionamento. 
2. Lucilla, effettua correttamente il Login al sistema con la sua e-mail aziendale
3. Preme sul tasto per accedere al Pannello Admin
4. Il sistema gli presenta le funzioni per Aggiungere o Rimuovere un utente 
5. Lucilla clicca sul pulsante per rimuovere un utente
6. Il sistema la sposta sulla pagina di Rimozione Account, dove può inserire un'e-mail
7. Lucilla inseisce l'e-mail aziendale di Liliana e preme il tasto di conferma 
8. Il sito mostra un avviso di conferma di rimozione, specificando le informazioni basilari (Nome: "Liliana", Cognome: "Verdi", Ruolo: "*Gestore Prodotti*")
9. Lucilla conferma l'operazione 
10. Il sito si aggiorna con l’esito dell’operazione

## 6.9.	Scenario 9 (S9) – Utente ottiene una stima dell’usato
Nome: UtenteOttieneStimaUsato

Attori Partecipanti: Claudio:Guest

Flusso di Eventi:
1. Dalla Home Page, Claudio clicca il tasto "Valuta il tuo usato"
2. Il sistema lo porta su una pagina di Valutazione Usato, dove gli viene presentato un form che si aggiorna con ogni informazione che inserisce 
3. Claudio inserisce nei selettori del form: 
   - Marca: "Samsung"
   - Nome: "S22"
   - Spazio Interno: "128"
   - RAM: "8"
   - Condizione: "Accettabile"
4. Dopo aver compilato tutti i campi, Claudio preme sul pulsante di valutazione
5. La pagina si aggiorna mostrando una valutazione in euro, con l'opzione di contattare il negozio
6. Claudio sceglie di contattare il negozio e viene reindirizzato nella pagina Contatti

## 6.10. Scenario 10 (S10) – *Cliente* effettua Logout
Nome: Logout

Attori Partecipanti: Franco:Cliente

Flusso di Eventi:
1. Franco ha deciso di effettuare il logout
2. Franco clicca sul tasto per accedere al pannello di controllo del proprio account
3. Il sistema lo porta sulla pagina di Visualizzazione Account contenente i suoi dati (come il suo nome, cognome, email, numero di telefono)
4. Franco clicca sul tasto per effettuare il Logout
5. Il sistema lo sposta sulla Home Page, dove il pulsante per accedere al suo account diventa un pulsante di login/registrazione


## 6.11. Scenario 11 (S11) – *Cliente* modifica il proprio profilo
Nome: ModificaProfiloCliente

Attori Partecipanti: Giovanni:Cliente

Flusso di Eventi:
1. Giovanni vuole modificare il numero di telefono collegato al suo account del sito
2. Clicca sul tasto per accedere al pannello di controllo del proprio account e viene spostato sulla pagina Visualizzazione Account
3. Giovanni clicca sul tasto per modificare le su informazioni sulla scheda delle informazioni generali
4. Le informazioni sullo schermo sono diventate modificabili
5. Giovanni sostituisce il vecchio numero telefonico con quello nuovo e preme il tasto per salvare le informazioni
6. La pagina si aggiorna con i nuovi dati inseriti


## 6.12. Scenario 12 (S12) – *Cliente* elimina il proprio account
Nome: EliminaProfiloCliente

Attori Partecipanti: Erminio:Cliente

Flusso di Eventi:
1. Erminio vuole eliminare il proprio account
2. Clicca sul tasto per accedere la pannello di controllo del proprio account e viene spostato sulla pagina account
3. Erminio scorre la pagina e clicca sul tasto per eliminare il proprio account
4. Il sistema chiede conferma ad Erminio per l'eliminazione, avvisandolo che l'account non sarà più recuperabile
5. Erminio preme il tasto per confermare e viene spostato sulla homepage come visitatore


## 6.13. Scenario 13 (S13) – *Cliente* visualizza i propri ordini
Nome: VisualizzaOrdini

Attori Partecipanti: Carlo:Cliente

Flusso di Eventi:
1. Carlo dalla Home Page clicca sull tasto per accedere al pannello di controllo del suo profilo
2. Carlo scorre la pagina e clicca sul tasto per visualizzare l'ordine
3. Viene mostrata una lista degli ordini effettuati e clicca sull'ordine più recente
4. A Carlo vengono mostrati i dettagli sugli articoli ordinati, le quantità, la data dell'ordine e il prezzo pagato

## 6.14. Scenario 14 (S14) – *Cliente* contatta l'azienda
Nome: ClienteContatta

Attori Partecipanti: Marco:Cliente

Flusso di Eventi:
1. Mario ha necessità di contattare l'azienda per contrattare sull'usato valutato sul sito
2. Mario clicca sul tasto per contattare l'azienda e viene spostato su una pagina
3. La pagina presenta le informazioni dell'azienda e un form da compilare
4. Mario inserisce nel form
   - Email: "mario.rossi72@hotmail.it"
   - Messaggio: "Salve, il vostro sito ha valutato il mio iPhone 12 per 500 euro, sarei interessato alla vendita, è possibile organizzare un incontro in negozio per parlare un po' del prezzo? Grazie e buona giornata" 
5. Mario preme invia e viene spostato alla pagina Home con un avviso di corretto invio

## 6.15. Scenario 15 (S15) – *Gestore Prodotti* modifica un prodotto
Nome: ModificaProdotto

Attori Partecipanti: Arsenio:GestoreProdotti

Flusso di Eventi:
1. Arsenio deve modificare il prezzo di un "iPhone 12"
2. Preme sul tasto per accedere, inserisce correttamente i dati ed effettua il login
3. Dato che è un *Gestore Prodotti*, verrà spostato sulla pagina Dashboard Gestore Prodotti
4. Il sistema lo sposta sulla pagina Pannello Gestori
5. Arsenio preme il tasto sulla sidebar per visualizzare tutti i prodotti in vendita ed inserisce nella barra di ricerca "iPhone 12"
6. La tabella si aggiorna con i risultati
7. Arsenio clicca sul dispositivo cercato
8. Il sistema lo sposta sulla pagina di Modifica Prodotto, che mostra un form pre-compilato con i dati del dispositivo
9. Arsenio modifica il prezzo a "500"
10. Arsenio preme il tasto per confermare la modifica
11. Viene avvisato del successo dell'operazione e viene spostato sulla pagina dei dettagli del nuovo prodotto aggiunto

## 6.16. Scenario 16 (S16) – *Gestore Prodotti* elimina un prodotto
Nome: EliminaProdotto

Attori Partecipanti: Arsenio:GestoreProdotti

Flusso di Eventi:
1. Arsenio deve eliminare un dispositivo "iPhone 12"
2. Preme sul tasto per accedere, inserisce correttamente i dati ed effettua il login
3. Dato che è un *Gestore Prodotti*, verrà spostato sulla pagina Dashboard Gestore Prodotti
4. Il sistema lo sposta sulla pagina Pannello Gestori
5. Arsenio preme il tasto sulla sidebar per visualizzare tutti i prodotti in vendita ed inserisce nella barra di ricerca "iPhone 12"
6. La tabella si aggiorna con i risultati
7. Arsenio clicca sul dispositivo cercato
8. Il sistema lo sposta sulla pagina di Modifica Prodotto, che mostra un form pre-compilato con i dati del dispositivo
9. Arsenio preme il tasto per eliminare il prodotto selezionato
10. Viene avvisato del successo dell'operazione e viene spostato nella pagina di visualizzazione dei prodotti

## 6.17. Scenario 17 (S17) – *Gestore Utenti* aggiunge un Gestore
Nome: GestoreAggiungeGestore

Attori Partecipanti: Sigismondo:Gestore Utenti

Flusso di Eventi:
1. Sigismondo è stato incaricato, in seguito all'assunzione di Ascanio Ferri come *Gestore Prodotti* di creargli un account
2. Sigismondo preme sul tasto per accedere al pannello gestore e viene spostato sulla pagina
3. Sigismondo clicca sul tasto per aggiungere un utente e viene spostato su una pagina con un form
4. Sigismondo riempie il form:
	- Utente: "Gestore Prodotti"
	- Nome: "Ascanio"
	- Cognome: "Ferri"
	- Indirizzo: "Via G. Marconi, Positano"
	- Email: "A.Ferri1982@alice.it"
	- Numero: "3243753477"	
5. Sigismondo preme sul tasto per confermare l'inserimento
6. Il sistema avvisa Sigismondo che la password verrà autogenerata ed inviata ad Ascanio per email

## 6.18. Scenario 18 (S18) – *Gestore Prodotti* modifica il banner
Nome:GestoreModificaBanner

Attori Partecipanti: Marcella:Gestore Prodotti

Flusso di Eventi:
1. Il *Gestore Prodotti* Marcella è stata incaricata di modificare il banner per motivi di marketing
2. Marcella accede al pannello di controllo del Gestore e preme sul tasto per modificare il banner
3. Viene spostata su una nuova pagina contenente un form per caricare l'immagine
4. Marcella inserisce l'immagine "MagicoBisestile.jpeg" e preme il tasto conferma
5. Viene spostata sulla home con il banner modificato
