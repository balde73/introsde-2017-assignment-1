package jaxb;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.*;
import model.generated.ActivitypreferenceType;
import model.generated.ObjectFactory;
import model.generated.PeopleType;
import model.generated.PersonType;

public class JsonMarshaller {
	
	public void generateJSONDocument(File jsonDocument) throws IOException, ParseException, DatatypeConfigurationException {
        
        ObjectFactory factory = new ObjectFactory();
        PeopleType people = factory.createPeopleType();
		
        ActivitypreferenceType activity = factory.createActivitypreferenceType();
		activity.setId(200);
		activity.setName("running");
		activity.setDescription("running with friends");
		activity.setPlace("Roomba Park");
		activity.setStartdate(createDate("2017-11-08T10:30:00"));

		PersonType person1 = factory.createPersonType();
		person1.setId(1);
		person1.setFirstname("Federico");
		person1.setLastname("Baldessari");
		person1.setBirthdate(createSimpleDate("1993-08-09"));
		person1.setActivitypreference(activity);
		
		PersonType person2 = factory.createPersonType();
		person2.setId(2);
		person2.setFirstname("Giovanni");
		person2.setLastname("Parisi");
		person2.setBirthdate(createSimpleDate("1993-10-11"));
		person2.setActivitypreference(activity);
		
		PersonType person3 = factory.createPersonType();
		person3.setId(3);
		person3.setFirstname("Erika");
		person3.setLastname("Minardi");
		person3.setBirthdate(createSimpleDate("1993-01-09"));
		person3.setActivitypreference(activity);

		List<PersonType> persons = people.getPerson();
		
		persons.add(person1);
		persons.add(person2);
		persons.add(person3);
        
        // configuration
		ObjectMapper mapper = new ObjectMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		mapper.setDateFormat(df);
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        // result
        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(jsonDocument, people);
	}
	
	private XMLGregorianCalendar createDate(String dateString) throws ParseException, DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d = format.parse(dateString);
		c.setTime(d);
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return date2;
	}
	
	private XMLGregorianCalendar createSimpleDate(String dateString) throws ParseException, DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = format.parse(dateString);
		c.setTime(d);
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return date2;
	}

    public static void main(String[] args) throws JAXBException, IOException, ParseException, DatatypeConfigurationException {
    	String jsonDocument = "3people.json";
		JsonMarshaller jaxbMarshaller = new JsonMarshaller();
		jaxbMarshaller.generateJSONDocument(new File(jsonDocument));
    	
    }
}