# Dokumentasjon

# Beskrivelse av appen

Appen min er et casino som per dags dato inneholder en meny hvor man kan logge seg inn samt ta inn og ut penger. Brukere lagres i en properties fil som inneholder alle brukerne og saldoene. Når man logger inn oppdateres informasjonen som representeres. Når man er logget inn kan man gå inn på Blackjack og spille. Blackjack fungerer etter vanlige spilleregler. På Blackjack kan man velge hvor mye man vil spille for pluss at man kan gå tilbake til menyen. Tenker kanskje å legge til flere spill senere om jeg føler meg inspirert.

# Diagram 

- deck: List<String>
- playerHand: List<String>
- dealerHand: List<String>
- user: User
----------------------------
+ Blackjack(user: User)
+ startRound(bet: double): void
+ endRound(bet: double): String
+ playerHit(): void
+ dealerTurn(): void
+ calculateHandValue(hand: List<String>): int
+ isPlayerBusted(): boolean
+ isDealerBusted(): boolean
+ determineWinner(): String
+ getPlayerHand(): List<String>
+ getDealerHand(): List<String>
+ getUser(): User

- resetGame(): void
- generateDeck(): List<String>
- drawCard(): String

# Spørsmål

1. Prosjektet dekker flere områder fra pensum. Prosjektet har innkapsling i alle filene så bare funksjoner som skal være tilgjengelige utenfor er tilgjengelige og at variabler som kun kan endres i funksjonen kan endres. Har også lært å bruke final for variabler som ikke skal kunne endres.

Prosjektet delegerer som for eksempel hvordan controller delegerer logikk til blackjack-klassen. Begge kontrollerne delegerer logikk. Blackjack deleger også oppgaver til User ved at User tar seg av saldoendringene i BlackJack.

Prosjektet har filhåndtering ved propertiesfilen og funksjonene som skriver til og henter fra. Denne brukes ved at informasjonen blir hentet til user som håndeterer endringene før den igjen blir sendt til propertiesfilen.

Unntakshåndtering ved try og catch som jeg har brukt i mange av filene. Dette gjøres for å ikke krasje filene pluss at man kan skrive ut feilmeldingene ved den lagdrede variabelen e.

Prosjektet har også flere steder validering. Et eksempel på dette er at det er validering av at man ikke tar ut for mye penger fra kontoen. Om man tar ut for mye penger blir det sendt en feilmelding. Om man prøver å ta ut eller legge til negative verdier blir det også tatt. Samme gjelder om man prøver å ta ut en ikketall verdi som "tjue"

2. Prosjeketet tar ikke i bruk arv, men det kan være nyttig for utvidding. Kan for eksempel ha en Game.java superklasse som alle spill arver fra. Det kan spare meg for mer kode og lage en ryddigere og mer effektiv og gjenbrukbar kode.

3. Prosjektet følger Model-View-Controller (MVC)-prinsippet på en gjennomført måte. Vi har skilt applikasjonen i tre hoveddeler: modell, visning og kontroller.

Modell: Klassene User, Blackjack og PropertiesData håndterer all logikk og data. User har ansvar for saldo og validering, Blackjack for selve spillreglene og rundene, og PropertiesData for lagring og lasting av brukerdata til fil.

View (FXML): Alle visninger er laget i .fxml-filer, uten innebygd logikk eller styling. All stil er lagt i egne .css-filer. Dette skaper et klart skille mellom struktur og presentasjon.

Controller: Klassene MainMenuController og BlackjackController håndterer brukerinteraksjon og videreformidler input til modellene. De inneholder lite logikk selv, og delegerer ansvar til modellene, slik det er anbefalt.

4. UserTest.java:

    void getUsername_shouldReturnCorrectUsername(): tester for at man kan lage en ny bruker og at navnet samsvarer 

    void changeBalance_shouldAddMoney(): Tester at man kan legge til penger i en konto

    void changeBalance_shouldSubtractMoney(): Tester at man kan trekke fra penger fra en konto

    void changeBalance_shouldThrowExceptionWhenOverdrawn(): Tester at den ikke kan trekke mer penger enn kontoen har.

   BlackjackTest.java:

    void startRound_shouldDeductBetFromBalance(): Sjekker at man fjerner nøyaktig hvor mye et bet er fra saldoen

    void playerHit_shouldAddCardToPlayerHand(): Sjekker at man får et kort til på hånda når man hitter

    void dealerTurn_shouldStopAt17OrMore(): Sjekker at dealer stopper om han får 17 eller mer i verdi på hånda

    void determineWinner_shouldReturnPlayerWhenDealerBusts(): Sjekker at når dealeren buster så vinner spiller

   PropertiesDataTest.java:

    void shouldAddAndRetrieveUser(): Sjekker at man kan etablere en bruker og at de har en startsaldo på 0.

    void shouldUpdateAndRetrieveBalance(): Sjekker at man kan legge til penger og at det funker.

    void shouldReflectUserInNewInstanceAfterSave(): Sjekker at lagringen funker som den skal.
