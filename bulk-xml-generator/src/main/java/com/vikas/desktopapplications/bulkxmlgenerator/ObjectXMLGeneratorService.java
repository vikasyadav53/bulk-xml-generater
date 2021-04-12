package com.vikas.desktopapplications.bulkxmlgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.vikas.desktopapplications.bulkxmlgenerator.schemas.Employee;
import com.vikas.desktopapplications.bulkxmlgenerator.schemas.ObjectFactory;

public class ObjectXMLGeneratorService {

	Boolean controller = false;
	RandomObjectFiller r;
	StringBuffer output;
	Object e1;
	String fileNamePrefix;

	public ObjectXMLGeneratorService(Boolean controller, RandomObjectFiller r) {
		this.controller = controller;
		this.r = r;
		output = new StringBuffer();
		fileNamePrefix = new String("");
	}
	
	public void setFileNamePrefix(String fileNamePrefix) {
		this.fileNamePrefix = fileNamePrefix;
	}

	public synchronized void generateObject() throws Exception {
		while (controller) {
			wait();
		}
		e1 = r.createAndFill(Employee.class);
		output.append(r.toString());
		controller = !controller;
		notify();
	}

	public synchronized void generateXML() throws InterruptedException {
		while (!controller) {
			wait();
		}
		if (e1 instanceof Employee) {
			Employee e2 = (Employee) e1;
			try {
				ObjectFactory objFactory = new ObjectFactory();
				JAXBContext contextObj = JAXBContext.newInstance(ObjectFactory.class);
				JAXBElement<Employee> jaxbElement = objFactory.createEmployee(e2);
				Marshaller marshallerObj = contextObj.createMarshaller();
				marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshallerObj.marshal(jaxbElement, new FileOutputStream("C://Users//Vikas Yadav//xmls//" +fileNamePrefix+ "question.xml"));
			} catch (JAXBException | FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(false);
		}
		controller = !controller;
		notify();
	}

	public String getOutput() {
		return output.toString();
	}

}
