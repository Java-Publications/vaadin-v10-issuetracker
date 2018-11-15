#Datenbank Zugriff

Ich würde den Zugriff auf die DB klarer Schichten:

  1. Service Implementierung: Liefert die Geschäftslogik
  1. Datenzugriff Interface und Implementierung (Port/Adapter)
  1. JPA Enteties und Repostitory - technischer Zugriff auf die DB - Queries, Mapping, etc.


1. und 2. Arbeiten auf den Buisses Objekten. Das Mapping passiert in 2.

Die spannende Frage ist, wo bringst Du die Transaktionen unter - einerseits sind sich technisch - also 3. auf der anderen Seite geht dir das JPA lazy Loading dann kaputt, wenn die die Objekte komplexer sind. Ausserdem ist es in gewisserweise auch eine Geschäftsentscheidung was Atomar ist und was nicht. Also würde ich pragmatisch dazu tendieren sie im allgemeinen nach 2. zu legen: Lesen und schreiben eines ganzen Objektgraphs ist dann atomar und man hat keine Abhängikeit zu JPA im Buissness code.