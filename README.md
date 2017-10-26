# First Assignment
## Identification
Student: Federico Baldessari
Email: <federico.baldessari@studenti.unitn.it>
## Project

### About the code
The project is divided into 3 packages:
* `default package`: it contains `XMLPeopleReader.java` that loads the `people.xml` file and parses it using XPath to implement methods like _getActivityDescription_, _getActivityPlace_, printing all people in the list with details and more.
* `jaxb`: it contains `JaxbMarshaller.java`, `JaxbUnMarshaller.java`, `JsonMarshaller.java` that does the marshalling and un-marshalling
* `model.generated`: it contains classes generated with _JAXB XJC_ from `people.xsd` that are used by `JaxbMarshaller`, `JaxbUnMarshaller` and `JsonMarshaller`

### About the task
* 1. _"Extend the example above to include 20 people (maybe your friends with fake names)"_
  The result can be see in `src/people.xml`
* 2. _"Use XPath to implement methods like getActivityDescription and getActivityPlace. Use the person ID as parameter of the getters."_
  The functions are inside `XMLPeopleReader.java`
* 3. _"Make a function that prints all people in the list with details."_
  The function is inside `XMLPeopleReader.java`. It was decided to print the people in the form:
  ```
  ------ Person info -------
  -> person
  firstname           Elia
  lastname            Tasini
  birthdate           1993-01-02
  -> activitypreference
  name                Running
  description         Sprint Running
  place               Harena Stadium
  startdate           2017-09-13T11:50:00.0
  --------------------------
  ```
  using the private function _printListPerson_ and _printPerson_
* 4. _"Make a function that accepts the person id as parameter and prints the ActivityPreference of the person with that id."_
  The function is inside `XMLPeopleReader.java`. It was decided to use the same print format to print people details showing only activitypreference information
* 5. _"Make a function which accepts a date and an operator (=, > , <) as parameters and prints people which preferred activity start_date fulfill that condition (i.e., startdate>2017-13-10, startdate=2017-13-10, etc.)"_
  The function is inside `XMLPeopleReader.java`
* 6. _"Create the XML schema XSD file for the example XML document of people."_
  The XSD file can be found in the root of the project `people.xsd`
* 7. _"Write a java application that does the marshalling and un-marshalling using classes generated with JAXB XJC."_
  In the package `jaxb`: `JaxbMarshaller.java` and `JaxbUnMarshaller.java` uses classes generated with JAXB XJC inside package `model.generated`. `JaxbUnMarshaller.java` generates a file _3people.xml_ in the root
* 8. _"Make your java application to convert also JSON (Create 3 persons using java and marshal them to JSON)"_
  In the package `jaxb`: `JsonMarshaller` uses library _jackson_ to generate file _3people.json_. This is possible because classes in `model.generated` uses JAXB annotation and Jackson allow use of JAXB annotations as an alternative (in addition to or instead of) standard Jackson annotations.

## Execution
First clone the repository to a location of your choice by running:
```
git clone https://github.com/balde73/sde-FirstAssignment
cd sde-FirstAssignment
```
Now you are ready to execute
```
ant execute.evaluation
```
To make thing cleaner it is also possible to run a single step of the evaluation like:
```
// As default ant compiles and downloads everything needed
ant
// 1. Runs instruction 3 based on Lab 3
ant execute.step1
// 2. Runs instruction 4 based on Lab 3 with id = 5
ant execute.step2
// 3. Runs instruction 5 based on Lab 3 with startdate > 2017-10-13
ant execute.step3
// 4. > Runs instruction 7 based on Lab 4, executing marshaling to XML. Create 3 persons using java and marshal them to XML
ant execute.step4
// 5. > Runs instruction 7 based on Lab 4, executing un-marshaling from XML
ant execute.step5
// 6. > Runs instruction 8 based on Lab 4, executing marshaling to JSON. Create 3 persons using java and marshal them to JSON
ant execute.step6
```