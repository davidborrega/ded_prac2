# Disseny d'Estructures de Dades - Pràctica 2

  

## Autor

  

**David Borrega Borrella**

Enginyeria Informàtica - Curs 2022/2023 Tardor.

  

## Introducció

  

Aquest projecte inclou la definició, implementació i el joc de proves de l'aplicació **Sport4EventsClub** amb l'objectiu principal de gestionar els esdeveniments esportius que organitza el club amb la seva versió extesa corresponent al desenvolupament de les operacions de la Pràctica 2.

  

Per a poder desenvolupar el projecte, s'han elaborat les següents tasques d'acord amb la solució de la **PAC2**:

  

- Implementació de **SportEvents4ClubImpl** de la interfície **SportEvents4Club**.

- Definició i implementació de les diferents entitats.

- Definició i implementació de les excepcions.

- Definició i implementació de nous TADS.

- Definició i implementació del joc de proves **SportEvents4ClubPR1Test**.

- Definició i implementació del joc de proves **SportEvents4ClubPR2Test**.

- Definició i implementació del joc de proves **SportEvents4ClubPR2TestPlus**.

- Definició i implementació de joc de proves adicionals.

Tota la implementació s'ha realizat conforme la signatura inicial de la interfície proposada.

  

## Estructura

El projecte s'estructura en dues principals parts:

  

-  **src**: estructura principal del projecte.

-  *excepcions*: directori que llista les diferents excepcions del projecte (per a més detall, veure apartat *Excepcions*).

-  *model*: directori que llista totes les entitats que s'inclouen en el projecte (per a més detall, veure apartat *Models*).

-  *util*: directori que llista les diferents utilitats utilitzades en l'aplicació. Inclou les noves TAD implementades.

-  **test**: estructura del joc de proves.

  

### Interfície

La interfície a implementar és **SportEvents4Club**. Per tal de respectar la definició inicial i la signatura dels diferents mètodes que l'inclouen, no s'ha fet cap modificació sobre aquesta.

  

Al llarg del projecte s'han definit alguns comentaris sobre el codi amb l'etiqueta *ToDo* proposant alguna modificació de cara a pròximes versions de l'aplicació.

  

### Models

Els models que s'han implementat són els que s'han proposat en l'enunciat de la pràctica:

  

-  **Player**: entitat corresponent al jugador.

-  **OrganizingEntity**: entitat corresponent als organitzadors d'esdeveniments.

-  **SportEvent**: entitat corresponent als esdeveniments esportius.

-  **File**: entitat corresponent a la fitxa de l'esdeveniment.

-  **Rating**: entitat corresponent a la valoració d'un esdeveniment.

-  **Attender**: entitat corresponent a la informació de l'asistent en un esdeveniment.

-  **Enrollment**: entitat corresponent a la inscripció d'un jugador en un esdeveniment.

-  **Role**: entitat corresponent al rol que obtindrà un treballador per a realitzar unes determinades tasques.

-  **Worker**: entitat corresponent a un treballador que realitzarà unes determinades tasques.

-  **Post**: entitat corresponent al missatge que es registra per a una determinada acció o publicació d'un jugador.
  

Totes aquestes entitats tenen en comú:

  

- Definició d'un constructor accessible, on s'hi passen tots els camps per paràmetre.

- Definició de getters i setters.

- Mètodes públics i privats adicionals per satisfer diferents necessitats del projecte.

#### Player

Emmagatzema una llista encadenada d'esdeveniments esportius.

#### OrganizingEntity

Emmagatzema una llista encadenada d'esdeveniments esportius.

#### SportEvents

- Emmagatzema una cua d'inscripcions de jugadors.

- Emmagatzema una llista encadenada de valoracions.

  

### Tipus de TADS utilitzats

Per al desenvolupament correcte de la implementació **SportEvents4ClubImpl** és necessari utilitzar la llibreria de *TADS* que ofereix l'assignatura.

Per una banda, s'utilitzen els següents TAD ja existents en la llibreria:

-  **LinkedList**  *<edu.uoc.ds.adt.sequential>*: per a la implementació de llistes encadenades.

-  **QueryArrayImpl** <*edu.uoc.ds.adt.sequential*>: per a la implementació de les cues.

  

D'una altra, s'han creat nous TAD:

  

-  **OrderedVector**  *<uoc.ds.pr.util>*: per a la implementació de vector ordenat per a gestionar els millors esdeveniments esportius. *Implementa els TAD: FiniteContainer.*

-  **OrderedVectorDictionary**  *<uoc.ds.pr.util>*: per a la implementació de vector ordenat per a gestionar els esdeveniments esportius. *Implementa els TAD: DictionaryArrayImpl, FiniteContainer.*

  
  

### Excepcions

S'han implementat diferents excepcions per a satisfer els requeriments de la definició inicial de la interfície ***Sport4EventsClub**. Totes elles, extenen d'una excepció principal anomenada ***DSException***:

```

DSException

├── AttenderAlreadyExistsException

├── AttenderNotFoundException

├── LimitExceededException

├── NoAttendersException

├── NoFilesException

├── NoFollowersException

├── NoFollowingException

├── NoPostsException

├── NoRatingsException

├── NoSportEventsException

├── NoSubstitutesException

├── NoWorkersException

├── OrganizingEntityNotFoundException

├── PlayerNotFoundException

├── PlayerNotInSportEventException

├── SportEventNotFoundException

├── WorkerAlreadyAssignedException

├── WorkerNotFoundException

```

## Modificacions / Actualitzacions

  

### SportEvents4ClubImpl

- Per tal de poder satisfer les necessitats de la definició inicial, s'han generat nous TADS (*veure apartat Tipus de TADS utilitzats per a més detall*).

- Per a poder gestionar el número d'elements existents en cada un dels TAD que s'utilitzen en la implementació (dit d'altre manera, *contador d'elements*), es fan servir variables de tipus ***int*** per al control:

-  *numPlayers*: número de jugadors que s'han afegit en el sistema.

-  *numOrganizingEntities*: número d'entitats organitzadores que s'han afegit en el sistema.

-  *totalFiles*: número de fitxes pendents d'aprovar que s'han afegit en el sistema.

-  *rejectedFiles*: número de fitxes rebutjades que s'han afegit en el sistema.

-  *numRoles*: número de rols que s'han afegit en el sistema.

-  *numWorkers*: número de treballadors que s'han afegit en el sistema.

### SportEvent

Per tal de calcular la ordenació dels diferents TADs utilitzats per als esdeveniments esportius (en aquest cas, *OrderedVector* i *OrderVectorDictionary*) es fa servir el *comparator* de Java.
Es defineixen dos tipus de comparadors:

 - ***String comparator*** (COMPARATOR): utilitzem el comparador String per a comparar i ordenar els identificadors dels esdeveniments esportius que trobarem en el vector ordenat.
 - ***Rating comparator*** (COMPARATOR_BEST_SPORTEVENT): utilitzem el comparador de la classe SportEvent amb el mètode anomenat rating, que calcula la mitjana de valoracions d'un esdeveniment en concret. Ens ordenarà en funció de la valoració mitjana de l'esdeveniment.

  

## Joc de proves
La versió inicial del projecte tenia implementat els jocs de proves SportEvents4ClubPR1Test i ResourceUtilTest els quals han servit de guía per a la implementació i el desenvolupament de la pràctica. 
Anem a detallar cada un dels jocs de proves:
### SportEvents4ClubPR1Test:

 - ***addPlayerTest*** (*throws LimitExceededException*): comprova si s'afegeixen jugadors en el vector de jugadors.
 - ***addOrganizingEntityTest***(*throws LimitExceededException*): comprova si s'afegeixen entitats organitzadores en el vector d'entitats organitzadores.
 - ***addFileTest*** (*throws DSException*): comprova si s'afegeixen fitxes en la cua de fitxes.
 - ***updateFileTest*** (*throws DSException*): comprova si s'actualitza l'estat de la fitxa en qüestió i, si desencadena altres accions com la creació o no d'esdeveniments esportius.
 - ***signUpEventTest*** (*throws DSException*): comprova si es completen les inscripcions dels jugadors en els diferents esdeveniments esportius, les seves excepcions i si en cas d'arribar al màxim, s'inscriuen com a reserva.
 -  ***getEventsByOrganizingEntityTest*** (*throws DSException*): comprova si donada una entitat organitzativa, existeixen events esportius.
 - ***getAllEventsTest*** (*throws DSException*):  comprova si existeixen events esportius.
 - ***getSportEventsByPlayer***(*throws DSException*):  comprova si donat un jugador, existeixen events esportius.
 - ***mostActivePlayer***(*throws DSException*): comprova el jugador més participatiu del sistema.
 - ***addRatingAndBestEventTest***(*throws DSException*): comprova si es dóna d'alta una valoració en el vector ordenat per, posteriorment, comprovar si s'actualitza el vector ordenat de valoracions per la seva ponderació.

### ResourceUtilTest
Aquest joc de proves es basa en avaluar el nivell de permisos donat un paràmetre de tipus byte en el mètode *getFlag* de la classe d'utilitat ResourceUtil.
Cada un dels casos de prova de **ResourceUtilTest** té definit un número de paràmetres d'entrada diferent. Els casos són els següents:

- ***hasFlagTest1***
- ***hasFlagTest2***
- ***hasFlagTest3***
- ***hasFlagTest4***

 Addicionalment, s'ha creat una nova classe per al joc de proves adicionals sobre la implementació de **SportEvents4ClubImpl**, on s'han realitzat totes aquelles proves adicionals que no s'han contemplat en el joc de proves inicial.

 ### SportEvents4ClubPR1AdditionalTest:
- ***testAddPlayerLimitExceeded*** (*throws LimitExceededException*): comprova si hi ha excés de jugadors en el vector a l'hora de fer el alta.
- ***testAddOrganizingEntityLimitExceeded*** (*throws LimitExceededException*): comprova si hi ha excés d'entitats organitzatives en el vector a l'hora de fer el alta.
- ***testAddFileOrganizingEntityNotFound***: comprova si existeix l'entitat organitzativa a l'hora de fer l'alta.
- ***testUpdateFileFilesNotFound***: comprova si pot tractar fitxes en el cas que no hi hagin donades d'alta
- ***testAddSportEventFullContainer*** (*throws LimitExceededException*): comprova si hi ha excés en el vector ordenat d'esdeveniments esportius.
- ***testAddRatingSportEventNotFound***: comprova si existeix esdeveniment esportiu a l'hora d'afegir una nova valoració.
- ***testAddRatingPlayerNotFound*** (*throws LimitExceededException*): comprova si existeix jugador a l'hora d'afegir una nova valoració.
- ***testAddRatingPlayerNotInSportEventException***: comprova si el jugador ha estat inscrit a un determinat esdeveniment a l'hora d'afegir una nova valoració.
- ***testGetRatingsSportEventNotFound***: comprova si existeix esdeveniment esportiu a l'hora de retornar les valoracions.
- ***testMostActivePlayerWithPlayerNotFound***: comprova si existeix jugadors en el vector per tal de mostrar el més participatiu.
- ***testBestSportEventWithSportEventNotFoundException***: comprova si existeixen esdeveniments esportius en el vector ordenat de millor esdeveniment.
- ***testSignUpEventPlayerNotFound***: comprova si existeix jugador a l'hora de fer la inscripció.
- ***testSignUpEventSportEventNotFound***: comprova si existeix esdeveniment esportiu a l'hora de fer la inscripció.
- ***testGetEventsByPlayerNoSportEventsFound***: comprova si existeixen esdeveniments esportius en el jugador.

En quant a la implementació del TAD de vector ordenat, s'ha preparat un joc de proves adicionals a la classe de prova **OrderedVectorTest**.
 ### OrderedVectorTest:
- ***testAddandIsFull***: comprova quan es dona d'alta un element i si ha excés.
- ***testIsEmpty***: comprova si el vector està buit o no.
- ***testGetElementSizeAndValues***: comprova el tamany del vector.
- ***testCheckInAlphabeticOrder***: comprova que es realitzi la ordenació alfabètica en l'alta d'elements en el vector.
- ***testUpdateAndCheckInAlphabeticOrder***: comprova que es realitzi la ordenació alfabètica en l'update d'elements en el vector.
- ***testDeleteAndCheckOrder***: comprova que es realitzi la ordenació alfabètica en la baixa d'elements en el vector.

En el següent apartat s'inclouen diferents captures de pantalla referents al resultat dels diferents jocs de proves utilitzats.
## Annex
### Resultat dels jocs de proves:
 - /screenshoot/SportEvents4ClubPR1Test.jpg
 - /screenshoot/SportEvents4ClubPR2Test.jpg
 - /screenshoot/SportEvents4ClubPR2TestPlus.jpg
 - /screenshoot/DicctionaryOrderedVectorTest.jpg
 - /screenshoot/LevelHelperTest.png
 - /screenshoot/OrderedVectorTest.png
 - /screnshoot/ResourceUtilTest.png 
 - /screenshoot/AllTest.png
