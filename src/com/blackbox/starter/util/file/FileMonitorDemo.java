//package com.blackbox.starter.util.file;
//
//import com.blackbox.starter.Config;
//import com.blackbox.starter.controllers.EventController;
//import org.apache.commons.io.monitor.FileAlterationMonitor;
//import org.apache.commons.io.monitor.FileAlterationObserver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//
//public class FileMonitorDemo {
//
//	// Get the user home directory to be monitored
//	private static final String FOLDER = new Config().getEventFolder();
//
//	// The monitor will perform polling on the folder every 30 seconds
//	private static final long pollingInterval = 30 * 1000;
//
//
//
//
//	public FileMonitorDemo(EventController eventController) throws Exception {
//        System.out.println(FOLDER);
//		System.out.println("FileMonitorDemo starting");
//        // Change this to match the environment you want to watch.
//		final File directory = new File(FOLDER);
//
//		// Create a new FileAlterationObserver on the given directory
//		FileAlterationObserver fao = new FileAlterationObserver(directory);
//
//        FileAlterationListenerImpl fileAlterationListener = new FileAlterationListenerImpl();
//        fileAlterationListener.setEventController(eventController);
//		// Create a new FileAlterationListenerImpl and pass it the previously created FileAlterationObserver
//		fao.addListener(fileAlterationListener);
//
//		// Create a new FileAlterationMonitor with the given pollingInterval period
//		final FileAlterationMonitor monitor = new FileAlterationMonitor(
//				pollingInterval);
//
//		// Add the previously created FileAlterationObserver to FileAlterationMonitor
//		monitor.addObserver(fao);
//
//		// Start the FileAlterationMonitor
//		monitor.start();
//
//		System.out.println("Starting monitor (" + FOLDER
//				+ "). \"Press CTRL+C to stop\"");
//	}
//
//}
