package com.vikas.desktopapplications.bulkxmlgenerator;

public class XMLGenerator implements Runnable {
	private ObjectXMLGeneratorService oxgService;
	
	public XMLGenerator(ObjectXMLGeneratorService oxgService) {
		this.oxgService = oxgService;
	}

	@Override
	public void run() {
		try {
			String threadName = Thread.currentThread().getName();
			oxgService.setFileNamePrefix(threadName);
			oxgService.generateXML();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
