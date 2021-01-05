# Semestrální práce BIK-EJA

Repozitář semestrální práce předmětu BIK-EJA.

## Popis a vlastnosti projektu

* Projekt představuje rezervační systém s evidencí uživatelů a evidencí předmětů určených k rezervaci. 
* K dispozici je jednoduchá statistika rezervací a historie rezervací jednotlivých předmětů.
* Práci s předměty vyžaduje přihlášení evidovaného uživatele.
* Aplikace poskytuje jednoduché webové html rozhraní.
* Aplikace demonstruje především použití java technologii - nepředstavuje skutečný produkt.
* Serverová část (REST) a klientská je dostupná pod jednou aplikací. Restové rozhraní je pod adresou **/rest/*** a klientská část pod **/users***, **/items***.
* Obsažena hibernate validace entitních tříd. RESTové rozhraní obsluhuje jak validační chyby, tak další obecnější chyby (vrátí RestException, které se na straně klienta zpracuje).

## Popis balíčků

* **entity** - entitní tabulkové třídy pro JPA.
* **dto** - datové přihrádky určené k transferu dat (používá je např. REST api).
* **dao** - data access objects - repozitáře přistupující přímo k datům v databázi (pomocí JpaRepository ze spring data).
* **rest** - controllery poskytující REST api.
* **restProxy** - třídy konzumující REST api.
* **service** - třídy zajišťující business logiku.
* **session** - třída udržující stav přihlášeného uživatele.
* **client.controller** - servletové controllery webového klienta. 
* **client.view** - servletové views webového klienta.
* **client.ui** - jednoduché komponenty pro skládání html frontendu.

## Testy

Projekt obsahuje testy dao objektů a testy restového rozhraní.

## Databáze

Projekt používá databázi H2. Regulérní spuštění ukládá do souboru a testovací prostředí používá rozdílnou konfiguraci s in-memory módem.

## Sestavení a spuštění

Projekt používá buildovací nástroj Maven a framework Quarkus. Spustí se pomocí: 

```shell script
./mvnw compile quarkus:dev
```

Po spuštění je dostupná pod url **http://127.0.0.1:8080/users**
