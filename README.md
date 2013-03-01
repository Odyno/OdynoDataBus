Odyno DataBus 
============
Odyno DataBus is a J2SE DataBus component, OSGi R4 compliant, for the management of a logical databus. It's . The databus is a component that provides a virtual bus on which to entrust their data and where it is possible to listen to retrieve data.
 Will I implement the publish-subscribe pattern with the possibility to filter the data not only types but also for subcategories of types.
Here is a short list of features supported by the Odyno DataBus:
* Odyno DataBus - API    : The API of Odyno DataBus library.
* Odyno DataBus - Memory : Light and easy implementation of the "OdynoDatabus-API" to be used in projects involving a single JavaVM
* Odyno DataBus - Net    : Clustered and distributed implementation of OdynoDataBase, for multiple and distributed Java VM.
* Odyno DataBus - Tools  : Just Tools

Build Status
------------

* Master [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=master)](https://travis-ci.org/Odyno/OdynoDataBus)
* Development [![Build Status](https://travis-ci.org/Odyno/OdynoDataBus.png?branch=development)](https://travis-ci.org/Odyno/OdynoDataBus)


Example Of Usage
----------------

This example start Karaf OSGi end deploy the simple implementation of "Odyno DataBus - Memory".
Download karaf
	
	wget http://apache.panu.it/karaf/2.3.0/apache-karaf-2.3.0.tar.gz
	tar xvzf apache-karaf-2.3.0.tar.gz 
	cd apache-karaf-2.3.0/

Add the repository url into karaf config file	

	etc/org.ops4j.pax.url.mvn.cfg 

add the repository

	org.ops4j.pax.url.mvn.repositories= \
	... , \
	http://www.staniscia.net/repository/release/, \
	http://www.staniscia.net/repository/snapshot/@snapshots@noreleases

Now run Karaf

	./bin/karaf debug

and install this bundle with pax url syntax

	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-api/0.1.1-SNAPSHOT
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-mem/0.0.2-SNAPSHOT
	karaf@root> install -s mvn:net.staniscia.as/odyno-databus-tools/0.0.1-SNAPSHOT

That's all.

