Odyno DataBus 
============
Odyno DataBus is a J2SE DataBus component, OSGi R4 compliant, for the management of a logical databus. 
The Odyno DataBus is a subsystem that transfers Java-Object among components inside a computer, or among computers. 
I have implemented the publish-subscribe pattern with the possibility to filter by types or by aspect of types.
Here it is a short list of features supported by the Odyno DataBus:
* Odyno DataBus - API    : The API of Odyno DataBus library.
* Odyno DataBus - Memory : Light and easy implementation of the "OdynoDatabus-API" to be used in projects involving a single JavaVM
* Odyno DataBus - Net    : Clustered and distributed implementation of OdynoDataBase, for multiple and distributed Java VM.
* Odyno DataBus - Tools  : Just Sniffer Tool

Build status
------------

* Master [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=master)](https://travis-ci.org/Odyno/OdynoDataBus)
* Development [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=development)](https://travis-ci.org/Odyno/OdynoDataBus)


Example of usage of "Odyno DataBus - Memory" [(Video)](http://youtu.be/damaPMoM-yE)
-------------------------------------------- 
This example shows how to start Karaf OSGi container and to deploy the implementation of "Odyno Data Bus - Memory".


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

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-api/0.1.1
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-mem/0.1.1

**Step F)** That's all, now you can test it width the extra bundle

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.1.1


Example of usage of "Odyno DataBus - Net" [(Video)](http://youtu.be/4szo0uVo-qs)
-----------------------------------------
This example starts Karaf OSGi container and deploys the "Odyno Data Bus - Net" implementation.
Follow the steps of last example and replace the "Step E" with the following step

**Step E)** Install those bundles with pax url syntax

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-api/0.1.1
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-net/0.1.1
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.1.1

**Step F)** Repeat the steps into another PC. When you finished you can test the instances with the extra bundle

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.1.1

So, when you send data in to the bus all instance of Odyno DataBus receive the message and print it on display... That's all!

