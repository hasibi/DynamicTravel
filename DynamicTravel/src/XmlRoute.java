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

public class XmlRoute {
	static final String TripList = "TripList";
	static final String Trip = "Trip";
	static final String Leg = "Leg";
	static final String Name = "name";
	static final String Origin = "Origin";
	static final String Destination = "Destination";
	static final String Id = "id";
	static final String Time = "time";
	static final String Track = "track";

	//@SuppressWarnings({ "unchecked", "null" })
	public static List<Trip> parseXml(String configFile) {
		List<Trip> trips = new ArrayList<Trip>();
		try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			StartElement startElement ;
			XMLEvent event ;
			Iterator<Attribute> attributes;
			while (eventReader.hasNext()) {
				List<Route> routes = null;
				while(eventReader.hasNext()) {
					event = eventReader.nextEvent();
					if(event.isEndElement() && event.asEndElement().getName().getLocalPart()== TripList)
						return trips;
					else if(event.isStartElement() && event.asStartElement().getName().getLocalPart()== Trip){
						routes = new ArrayList<Route>();
						break;
					}
				}
				while(eventReader.hasNext()){
				// parse all routes in a trip
					event = eventReader.nextEvent();
					if(event.isEndElement() && event.asEndElement().getName().getLocalPart()== Trip)
						break;
					if(event.isStartElement() && event.asStartElement().getName().getLocalPart()== Leg){
						Route route = new Route();
						startElement = event.asStartElement();
						// Read Name of vehicle in a route
						attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(Name)) {
								route.setLine(attribute.getValue());
								break;
							}
						}
						// Read fromId, departure time and track of a route
						event = readUntillTag(eventReader, Origin);
						startElement = event.asStartElement();
						attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(Id)) {
								route.setFromId(attribute.getValue());
								continue;
							}
							if (attribute.getName().toString().equals(Time)) {
								route.setDepartureTime(attribute.getValue());
								continue;
							}
							if (attribute.getName().toString().equals(Track)) {
								route.setTrack(attribute.getValue());
								continue;
							}
						}
						// Read toId and track of a route
						event = readUntillTag(eventReader, Destination);
						startElement = event.asStartElement();
						attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals(Id)) {
								route.setToId(attribute.getValue());
								continue;
							}
							if (attribute.getName().toString().equals(Time)) {
								route.setArrivalTime(attribute.getValue());
								continue;
							}
						}
						// If reach the end of an item element, add it to the list
						event = eventReader.nextEvent();
						while((!event.isEndElement()) ||
								(event.asEndElement().getName().getLocalPart()!= Leg)){
							event = eventReader.nextEvent();
						}
						if(!route.getLine().equals("Walk"))
							routes.add(route);
					}
				}
				Trip trip = new Trip(routes);
				trips.add(trip);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return trips;
	}

	private static XMLEvent readUntillTag(XMLEventReader eventReader, String tagName)
			throws XMLStreamException {
		XMLEvent event = eventReader.nextEvent();
		while((!event.isStartElement()) ||
				(event.asStartElement().getName().getLocalPart()!= tagName)){
			event = eventReader.nextEvent();
		}
		return event;
	}
}