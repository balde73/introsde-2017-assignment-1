package jaxb;

import model.generated.*;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JaxbMarshaller {
	
	public void generateJsonDocument(File jsonDocument) {
		
	}
	
	public void generateXMLDocument(File xmlDocument) throws ParseException, DatatypeConfigurationException {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("model.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			ObjectFactory factory = new ObjectFactory();

			PeopleType people = factory.createPeopleType();
			ActivitypreferenceType activity = factory.createActivitypreferenceType();
			activity.setId(200);
			activity.setName("running");
			activity.setDescription("running with friends");
			activity.setPlace("Roomba Park");
			XMLGregorianCalendar dateActivity = createDate("2017-11-08");
			activity.setStartdate(dateActivity);

			PersonType person1 = factory.createPersonType();
			person1.setId(1);
			person1.setFirstname("Federico");
			person1.setLastname("Baldessari");
			person1.setBirthdate(createDate("1993-08-09"));
			person1.setActivitypreference(activity);
			
			PersonType person2 = factory.createPersonType();
			person2.setId(2);
			person2.setFirstname("Giovanni");
			person2.setLastname("Parisi");
			person2.setBirthdate(createDate("1993-10-11"));
			person2.setActivitypreference(activity);
			
			PersonType person3 = factory.createPersonType();
			person3.setId(3);
			person3.setFirstname("Erika");
			person3.setLastname("Minardi");
			person3.setBirthdate(createDate("1993-01-09"));
			person3.setActivitypreference(activity);

			List<PersonType> persons = people.getPerson();
			
			persons.add(person1);
			persons.add(person2);
			persons.add(person3);
			

			JAXBElement<PeopleType> peopleElement = factory.createPeople(people);
			marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument));
			marshaller.marshal(peopleElement, System.out);

		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (JAXBException e) {
			System.out.println(e.toString());
		}
	}
	
	private XMLGregorianCalendar createDate(String dateString) throws ParseException, DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date d = format.parse(dateString);
		c.setTime(d);
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return date2;
	}

	public static void main(String[] argv) throws ParseException, DatatypeConfigurationException {
		String xmlDocument = "3people.xml";
		String type = "json";
		JaxbMarshaller jaxbMarshaller = new JaxbMarshaller();
		jaxbMarshaller.generateXMLDocument(new File(xmlDocument));
	}
}
