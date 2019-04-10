Was ist Ticket2Rock?
====================

Ticket2Rock ist die Beispielanwendung des Buchs "EJB 3.1 professionell" (dpunkt.verlag).
Es implementiert eine einfache Webanwendung zur Onlinebuchung von Tickets für Rockkonzerte auf 
Basis von EJB 3.1 und JavaServer Faces. Weitere Informationen zur Anwendung finden sich auf der 
Webseite zum Buch [http://ejb3buch.de].


Wie kann ich Ticket2Rock benutzen?
==================================

Schnellstart
------------

Um Ticket2Rock zu kompilieren und auszuführen ist Maven (am Besten in der Version 3) [http://maven.apache.org/download.html]
sowie ein JBoss Applikationsserver [http://download.jboss.org/jbossas/6.1/jboss-as-distribution-6.1.0.Final.zip]
notwendig.

Um die Anwendung zu bauen und zu deployen müssen sie im Projektverzeichnis also diese Befehle ausführen:

1. 'mvn package'
2. Unter Linux/Mac 'export JBOSS_HOME=/path/to/jboss-6.1.0.Final' bzw. unter Windows setzen Sie die Variable in der Systemsteuerung.
3. Die erforderliche Queue im JBoss installieren (s.u.).
3. JBoss starten.
4. 'mvn jboss:hard-deploy'

Ticket2Rock ist nun unter [http://localhost:8080/ticket2rock/] zu erreichen.


JBoss
-----

Wir haben die Anwendung mit JBoss 6.0.0 und JBoss 6.1.0 Final getestet.

Im JBoss muss eine Queue namens 'java:/queue/ticket2rock' eingerichtet sein, um die Stornierung der 
Ticketbestellung mittels JMS-Nachricht durchführen zu können. Dafür muss in der entsprechenden 
Konfigurationsdatei '$JBOSS_HOME/server/default/deploy/hornetq/hornetq-jms.xml' folgende Queue 
eingetragen werden:
	
   <queue name="ticket2rock">
      <entry name="java:/queue/ticket2rock" />
   </queue>

Bekannte Stolpersteine:

Umlautfehler in der Anwendung enstehen dadurch, dass die Datei 'import.sql' im Standardencoding 
der Plattform eingelesen wird. Starten des JBoss mit der Java-Option '-Dfile.encoding=ISO-8859-1' 
hilft. Die Option ist am besten in der Datei '$JBOSS_HOME/bin/run.conf' (bzw. run.conf.bat) zu
ergänzen.


Maven
-----

Für Maven-Neulinge hier die wichtigsten Befehle in der Übersicht:

* 'mvn package' kompiliert die Anwendung, läßt alle Unittests laufen und erstellt das war.
* 'mvn clean' kaum zu glauben: Löscht alle Buildartefakte! ;)

Für die folgenden Befehle muss die Umgebungsvariable JBOSS_HOME gesetzt werden:

* 'mvn integration-test' startet die Integrationstests (Vorher muss der JBoss gestartet werden)
* 'mvn jboss:hard-deploy' deployt die Anwendung.
* 'mvn jboss:hard-undeploy' undeployt die Anwendung.


IDE
---

Das Projekt kann mit Maven gebaut und gestartet werden und läßt sich in alle bekannten IDE's integrieren.
Netbeans und IntelliJ bringen die Maven-Unterstützung von Haus aus mit. Unter Eclipse läßt sich das 
m2Eclipse-Plugin über die Update-Site [http://download.eclipse.org/technology/m2e/releases] nachrüsten.
Dann läßt sich das Projekt als "Maven Project" in die IDE importieren.

Wir empfehlen, vor dem Import das Projekt einmal mit 'mvn package' zu bauen, damit u.a. alle 
benötigten Bibliotheken erstmalig geladen werden (das geht schneller als über Eclipse).


Sonstiges
=========

Integrationstest
----------------

Statt eines Embedded Containers verwenden wir Arquillian [http://www.jboss.org/arquillian] für den
Integrationstest. Arquillian deployt die Tests auf dem konfigurierten JavaEE-Container und führt 
diese dort aus. Dieser Ansatz gefällt uns sehr gut, da er den Code auf der Plattform testet, auf der 
er später auch ausgeführt wird.

Bekannte Stolpersteine:

Die Integrationstests laufen teilweise auf Fehler, wenn die Anwendung gleichzeitig deployt ist. 
Damit alle Tests erfolgreich sind, einfach die Anwendung undeployen.


Ruby Client
-----------

Der Ruby Client benötigt eine Bibliothek, die Sie einfach über das Paketmanagement von Ruby installieren können:

   "gem install soap4r --include-dependencies"
   
In der aktuellen Ruby Version (1.9.1) führt das zu einem Fehler (die Lösung findet sich hier: 
http://railsforum.com/viewtopic.php?id=41231). In der Version 1.8 funktioniert es aber noch.


Lizenz
------

Die Anwendung steht unter der GPL v2 (siehe LICENSE-Datei).
