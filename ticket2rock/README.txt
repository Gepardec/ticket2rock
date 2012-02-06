Was ist Ticket2Rock?
====================

Ticket2Rock ist die Beispielanwendung des Buchs "EJB 3.1 professionell" (dpunkt.verlag).
Es implementiert eine einfache Webanwendung zur Onlinebuchung von Tickets f�r Rockkonzerte auf 
Basis von EJB 3.1 und JavaServer Faces. Weitere Informationen zur Anwendung finden sich auf der 
Webseite zum Buch [http://ejb3buch.de].


Wie kann ich Ticket2Rock benutzen?
==================================

Schnellstart
------------

Um Ticket2Rock zu kompilieren und auszuf�hren ist Maven (am Besten in der Version 3) [http://maven.apache.org/download.html]
sowie ein JBoss Applikationsserver [http://download.jboss.org/jbossas/6.1/jboss-as-distribution-6.1.0.Final.zip]
notwendig.

Um die Anwendung zu bauen und zu deployen m�ssen sie im Projektverzeichnis also diese Befehle ausf�hren:

1. 'mvn package'
2. Unter Linux/Mac 'export JBOSS_HOME=/path/to/jboss-6.1.0.Final' bzw. unter Windows setzen Sie die Variable in der Systemsteuerung.
3. Die erforderliche Queue im JBoss installieren (s.u.).
3. JBoss starten.
4. 'mvn jboss:hard-deploy'

Ticket2Rock ist nun unter [http://localhost:8080/ticket2rock/] zu erreichen.


JBoss
-----

Wir haben die Anwendung mit JBoss 6.0.0 und JBoss 6.1.0 Final getestet.

Im JBoss muss eine Queue namens 'queue/ticket2rock' eingerichtet sein, um die Stornierung der 
Ticketbestellung mittels JMS-Nachricht durchf�hren zu k�nnen. Daf�r muss in der entsprechenden 
Konfigurationsdatei '$JBOSS_HOME/server/default/deploy/hornetq/hornetq-jms.xml' folgende Queue 
eingetragen werden:
	
   <queue name="ticket2rock">
      <entry name="/queue/ticket2rock" />
   </queue>

Bekannte Stolpersteine:

Umlautfehler in der Anwendung enstehen dadurch, dass die Datei 'import.sql' im Standardencoding 
der Plattform eingelesen wird. Starten des JBoss mit der Java-Option '-Dfile.encoding=ISO-8859-1' 
hilft. Die Option ist am besten in der Datei '$JBOSS_HOME/bin/run.conf' (bzw. run.conf.bat) zu
erg�nzen.


Maven
-----

F�r Maven-Neulinge hier die wichtigsten Befehle in der �bersicht:

* 'mvn package' kompiliert die Anwendung, l��t alle Unittests laufen und erstellt das war.
* 'mvn clean' kaum zu glauben: L�scht alle Buildartefakte! ;)

F�r die folgenden Befehle muss die Umgebungsvariable JBOSS_HOME gesetzt werden:

* 'mvn integration-test' startet die Integrationstests (Vorher muss der JBoss gestartet werden)
* 'mvn jboss:hard-deploy' deployt die Anwendung.
* 'mvn jboss:hard-undeploy' undeployt die Anwendung.


IDE
---

Das Projekt kann mit Maven gebaut und gestartet werden und l��t sich in alle bekannten IDE's integrieren.
Netbeans und IntelliJ bringen die Maven-Unterst�tzung von Haus aus mit. Unter Eclipse l��t sich das 
m2Eclipse-Plugin �ber die Update-Site [http://download.eclipse.org/technology/m2e/releases] nachr�sten.
Dann l��t sich das Projekt als "Maven Project" in die IDE importieren.

Wir empfehlen, vor dem Import das Projekt einmal mit 'mvn package' zu bauen, damit u.a. alle 
ben�tigten Bibliotheken erstmalig geladen werden (das geht schneller als �ber Eclipse).


Sonstiges
=========

Integrationstest
----------------

Statt eines Embedded Containers verwenden wir Arquillian [http://www.jboss.org/arquillian] f�r den
Integrationstest. Arquillian deployt die Tests auf dem konfigurierten JavaEE-Container und f�hrt 
diese dort aus. Dieser Ansatz gef�llt uns sehr gut, da er den Code auf der Plattform testet, auf der 
er sp�ter auch ausgef�hrt wird.

Bekannte Stolpersteine:

Die Integrationstests laufen teilweise auf Fehler, wenn die Anwendung gleichzeitig deployt ist. 
Damit alle Tests erfolgreich sind, einfach die Anwendung undeployen.


Ruby Client
-----------

Der Ruby Client ben�tigt eine Bibliothek, die Sie einfach �ber das Paketmanagement von Ruby installieren k�nnen:

   "gem install soap4r --include-dependencies"
   
In der aktuellen Ruby Version (1.9.1) f�hrt das zu einem Fehler (die L�sung findet sich hier: 
http://railsforum.com/viewtopic.php?id=41231). In der Version 1.8 funktioniert es aber noch.


Lizenz
------

Die Anwendung steht unter der GPL v2 (siehe LICENSE-Datei).
