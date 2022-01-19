
# Progetto universitario 

Questo è un proggetto universitario,fatto con scopo di apprendimento di alcune strutture riguardanti la Programmazione ad Oggetti.
Esso viene suddiviso in due parti principali.Nella prima parte,vengono fatte delle chiamate ai server del Twitter,in modo tale da ottenere 
dei dati dalle rispettive API,mentre nella seconda,questi dati vengono elaborati a partire dalle chiamate effettuate al server interno.
I dati elaborati vengono restituiti sotto forma di un JSONObject.

## Appendix

Any additional information goes here


## Installation

I seguenti comandi vi serviranno per installare(creare) un file .jar grazie al quale sarà possibile avviare il server
senza l'utilizzo di un ambiente di sviluppo,passando direttamente ad un tool per le richieste HTTP (es:Postman).

nota: questo è un proggetto maven,per questo motivo prima di utilizzare i seguenti comandi,assicurarsi di aver installato
Apache Maven sul vostro dispositivo,configurando le variabili d'ambiente.

Per semplificare alcuni passaggi,una volta scaricato con git il contenuto del proggetto,posizionarsi nella cartella PrUni,quindi usando il comando "cmd" all'interno del
 percorso (path) attivare il prompt dei comandi.Quindi eseguire i seguenti:

```bash
  mvn package
```
al termine del proccesso che si svolgerà automaticamente grazie alle dipendenze del <build> definite nel pom.xml,nella cartella target verrano generati 2 file .jar.Quindi eseguire il seguente:

```bash
  java -jar target/PrUni-0.0.1-SNAPSHOT.jar
```    

nota: per l'utilizzo di alcune chiamate,una volta scaricato il proggetto,è necessario creare un file all'interno della cartella PrUni, chiamandolo :"AutentificationFile.json" ,ed inserire sotto forma di un JSONObject le chiavi (rispettivamente:bearer,apiKey,apiKeySecret). 
## Acknowledgements

 - [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
 - [Awesome README](https://github.com/matiassingers/awesome-readme)
 - [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)


## API Reference

#### La parte iniziale della chiamata
```http
localhost:8080/
```

#### Get tweetsSearch 

Ricerca dei tweet in base alla query,nonchè parola chiave.

```http
  GET /tweetsSearch
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
|    `q`    | `string` | **Required**. è obbligatoria una parola chiave (query) in base allaquale viene fatta la ricerca(es:calcio) |
| `count`   | `int`    | **Optional**. count è un valore da 1 a 100 (default 10),esso rappresenta la quantità dei tweet che verranno trovati |
|`result_type`| `string` | **Optional**. esistono 3 tipi di risultati : *mixed*; *recent*;*popular* . Di default viene usato il *popular*|
|`lang`| `string` | **Optional**. linguaggi utilizzati sono quelli *eu*,quindi è possibile sostiture il parametro con es: *it*|
|`include_entities`| `boolean` | **Optional**. vengono incluse varie entità.Impostato su *false* di default(nel nostro caso non sono rilevanti)|

#### Get tweetMetrics  

Viene effettuata una ricerca di uno specifico tweet in base al suo *id* ( è possibile trovare i vari id con tweetsSearch ).

```http
  GET /tweetMetrics 
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. è obbligatorio l'*id* di un tweet per la sua ricerca.Esiste un *id* di default (per la prova iniziale)|
| `tweet.fields`| `string` | **Optional**.  possibili parametri: *attachments, author_id, context_annotations, created_at, entities,  in_reply_to_user_id,referenced_tweets, source, public_metrics*(default)*, text, withheld*|
| `expansions`| `string` | **Optional**.  possibili parametri: *attachments.media_keys, author_id* (default) *, entities.mentions.username, geo.place_id, in_reply_to_user_id, referenced_tweets.id*|

#### Get prova 

Una volta effettuata una tweetsSearch,è possibile ottenere informazioni riguardanti soltanto le metriche publiche di quei tweet

```http
  GET /prova
```

#### Get filtraSearch

Dopo aver effettuato una tweetsSearch è possibile filtrare il contenuto per parametro ed una certa quantità di quel parametro.

```http
  GET /filtraSearch
```


| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|  `operator` | `string`| **Optional** esistono 2 operatori: *moreThan* (default); *lessThan* |
|  `quantity` | `int`| **Optional** quantità di like o retweet secondo la quale bisogna filtrare (default 100).Non può essere negativa |
|  `nameParam` | `string`| **Required** è possibile effettuare il filtro secondo i *like* o *retweet* |

il JSONObject restituito in questo caso contiene un Array filtrato con i tweet ed un oggetto contenete la percentuale dell'efficenza del filtro

#### Get trovaMinMax

Questa chiamata trova il tweet contenente il numero massimo o il numero minimo di like o retweet in base alla scelta.

```http
  GET /trovaMinMax
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|  `min_Max` | `string`| **Optional** la scelta e tra: *Max* (default); *min* |
| `nameParam`| `string`| **Required** i parametri disponibili sono i *like* o *retweet*|


## Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.


## Tech Stack

**Client:** React, Redux, TailwindCSS

**Server:** Node, Express


## Running Tests

To run tests, run the following command

```bash
  npm run test
```
