package StopGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XmlStop {
	static final String StopLocation = "StopLocation";
	static final String StopId = "id";
	static final String StopName = "name";
	static final String Track = "track";

	public static List<Stop> parseXml(String configFile) {
		List<Stop> stops = new ArrayList<Stop>();
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			Stop stop = null;

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have a item element we create a new item
					if (startElement.getName().getLocalPart() == (StopLocation)) {
						stop = new Stop();
						// We read the attributes from this tag and add the date
						// attribute to our object
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(StopName)) {
								String[] value = attribute.getValue().split(", "); 
								stop.setStopName(value[0]);
								stop.setRegion(value[1]);
								//if(!stop.getRegion().equals("Göteborg"))
									//break;
							}
							else if(attribute.getName().toString().equals(StopId)){
								stop.setStopId(attribute.getValue());
							}
							else if(attribute.getName().toString().equals(Track)){
								stop.setTrack(attribute.getValue());
							}
						}
						if(stop.getRegion().equals("Göteborg"))
							stops.add(stop);
					}
				}
			}
/*
					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart()
								.equals(StopName)) {
							event = eventReader.nextEvent();
							stop.setStopName(event.asCharacters().getData());
							continue;
						}
					}
					if (event.asStartElement().getName().getLocalPart()
							.equals(County)) {
						event = eventReader.nextEvent();
						stop.setRegion(refine(event.asCharacters().getData()));
						continue;
					}
				}
				// If we reach the end of an item element we add it to the list
				if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart() == (Item)) {
						stops.add(stop);
					}
				}
			}*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return stops;
	}
	
	// converts from upper cate to lower case. ex. GOTHENBURG -> Gothenburg
	/*private static String refine(String s){
		return  s.charAt(0)+s.substring(1).toLowerCase();
	}*/
}


