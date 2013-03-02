Odyno DataBus 
============
Odyno DataBus is a J2SE DataBus component, OSGi R4 compliant, for the management of a logical databus. 
The Odyno DataBus is a subsystem that transfers Java-Object between components inside a computer, or between computers. 
I have implemented the publish-subscribe pattern with the possibility to filter by types or by aspect of types.
Here is a short list of features supported by the Odyno DataBus:
* Odyno DataBus - API    : The API of Odyno DataBus library.
* Odyno DataBus - Memory : Light and easy implementation of the "OdynoDatabus-API" to be used in projects involving a single JavaVM
* Odyno DataBus - Net    : Clustered and distributed implementation of OdynoDataBase, for multiple and distributed Java VM.
* Odyno DataBus - Tools  : Just Sniffer Tool

Build status
------------

* Master [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=master)](https://travis-ci.org/Odyno/OdynoDataBus)
* Development [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=development)](https://travis-ci.org/Odyno/OdynoDataBus)


Example of usage of "Odyno DataBus - Memory"
--------------------------------------------
This example start Karaf OSGi container and deploy the implementation of "Odyno Data Bus - Memory".

**Step A)** Download karaf
	
	wget http://apache.panu.it/karaf/2.3.0/apache-karaf-2.3.0.tar.gz
	tar xvzf apache-karaf-2.3.0.tar.gz 
	cd apache-karaf-2.3.0/

**Step B)** Open the karaf config file named	

	etc/org.ops4j.pax.url.mvn.cfg 

**Step C)** Search the "org.ops4j.pax.url.mvn.repositories" key and adds the repository of compiled bundles

	org.ops4j.pax.url.mvn.repositories= \
		[...]
		[...]
		[...], \
		http://www.staniscia.net/repository/release/, \
		http://www.staniscia.net/repository/snapshot/@snapshots@noreleases

**Step D)** Run Karaf 

	./bin/karaf

**Step E)** Install those bundles with pax url syntax

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-api/0.1.1-SNAPSHOT
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-mem/0.0.2-SNAPSHOT

**Step F)** That's all, now test it width the extra bundle

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.0.1-SNAPSHOT


Example of usage of "Odyno DataBus - Net"
-----------------------------------------
This example start Karaf OSGi container and deploy the "Odyno Data Bus - Net" implementation.
Follow the steps of last example and replace the "Step E" with the follower step

**Step E)** Install those bundles with pax url syntax

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-api/0.1.1-SNAPSHOT
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-net/0.0.1-SNAPSHOT
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.0.1-SNAPSHOT

**Step F)** Repeat the steps into another PC. When you have finished you can tests the instances with the extra bundle

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.0.1-SNAPSHOT

So, when you send data in to the bus all instance of Odyno DataBus receive the message and print it on display... That's all!

