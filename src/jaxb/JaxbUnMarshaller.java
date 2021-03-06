package jaxb;

import model.generated.*;

import javax.xml.bind.*;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

import java.io.*;
import java.util.List;

public class JaxbUnMarshaller {
	public void unMarshall(File xmlDocument) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("model.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File("people.xsd"));
			unMarshaller.setSchema(schema);
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller.unmarshal(xmlDocument);

			PeopleType people = peopleElement.getValue();
			List<PersonType> persons = people.getPerson();
			
			// printing in console
			for (int i = 0; i < persons.size(); i++) {
				PersonType person = persons.get(i);
				ActivitypreferenceType activity = person.getActivitypreference();
				System.out.println("----- some person information -----");
				System.out.printf("%-20s%s%n", "complete name: ", person.getFirstname()+" "+person.getLastname());
				System.out.printf("%-20s%s%n", "birthday: ", person.getBirthdate());
				System.out.printf("%-20s%s%n", "activity: ", activity.getName()+" at "+activity.getPlace());
			}
		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] argv) {
		File xmlDocument = new File("src/people.xml");
		JaxbUnMarshaller jaxbUnmarshaller = new JaxbUnMarshaller();
		jaxbUnmarshaller.unMarshall(xmlDocument);
	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}
