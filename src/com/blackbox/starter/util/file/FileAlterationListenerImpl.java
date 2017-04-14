//package com.blackbox.starter.util.file;
//
//import com.blackbox.starter.controllers.EventController;
//import org.apache.commons.io.monitor.FileAlterationListener;
//import org.apache.commons.io.monitor.FileAlterationObserver;
//
//import java.io.File;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.Date;
//
///**
// * @author ashraf
// *
// */
//public class FileAlterationListenerImpl implements FileAlterationListener {
//
//    private EventController eventController;
//
//    public void setEventController(EventController eventController) {
//        this.eventController = eventController;
//    }
//
//    @Override
//	public void onStart(final FileAlterationObserver observer) {
//		System.out.println("The FileListener has started on "
//				+ observer.getDirectory().getAbsolutePath());
//	}
//
//	@Override
//	public void onDirectoryCreate(final File directory) {
//		System.out.println(directory.getAbsolutePath() + " was created.");
//	}
//
//	@Override
//	public void onDirectoryChange(final File directory) {
//		System.out.println(directory.getAbsolutePath() + " wa modified");
//	}
//
//	@Override
//	public void onDirectoryDelete(final File directory) {
//		System.out.println(directory.getAbsolutePath() + " was deleted.");
//	}
//
//	@Override
//	public void onFileCreate(final File file) {
//		System.out.println(file.getAbsoluteFile() + " was created.");
//		System.out.println("1. length: " + file.length());
//		System.out
//				.println("2. last modified: " + new Date(file.lastModified()));
//		System.out.println("3. readable: " + file.canRead());
//		System.out.println("4. writable: " + file.canWrite());
//		System.out.println("5. executable: " + file.canExecute());
//
//        try {
//            eventController.setEvent(file);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//	@Override
//	public void onFileChange(final File file) {
//		System.out.println(file.getAbsoluteFile() + " was modified.");
//		System.out.println("1. length: " + file.length());
//		System.out
//				.println("2. last modified: " + new Date(file.lastModified()));
//		System.out.println("3. readable: " + file.canRead());
//		System.out.println("4. writable: " + file.canWrite());
//		System.out.println("5. executable: " + file.canExecute());
//        try {
//            eventController.setEvent(file);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//	}
//
//	@Override
//	public void onFileDelete(final File file) {
//		System.out.println(file.getAbsoluteFile() + " was deleted.");
//	}
//
//	@Override
//	public void onStop(final FileAlterationObserver observer) {
//		System.out.println("The FileListener has stopped on "
//				+ observer.getDirectory().getAbsolutePath());
//	}
//
//}
