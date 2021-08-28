//package com.designops.utility;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip. ZipEntry;
//import java.util.zip. ZipOutputStream;
//import org.springframework.stereotype.Component;
//@Component
//public class CustomFileUtils {
//private List <String> filelist;
//public CustomFileUtils() {
//filelist = new ArrayList<String> ();
//}
//public void zipit (File inputFile, String zipFile) {
//byte[] buffer = new byte[1024];
//String source = inputFile.getName();
//System.out.println("printing source in zipIt..."+source);
//FileOutputStream fos = null;
//ZipOutputStream zos = null;
//try {
//System.out.println("Printing zipFile location.."+zipFile);
//fos = new FileOutputStream(zipFile);
//zos = new ZipOutputStream(fos);
//System.out.println("Output to Zip : "+ zipFile);
//FileInputStream in = null;
//
//for (String file:inputFile.list()) {
//System.out.println("File Added: "+ file);
//ZipEntry ze = new ZipEntry(source + File separator + file);
//zos.putNextEntry(ze);
//try {
//in = new FileInputStream(inputFile.getAbsolutePath() + File separator + file);
//int len;
//while ((len = in .read(buffer)) > 0) {
//zos.write(buffer, 0, len);
//}
//}
//catch(Exception e)
//{
//e.printStackTrace();
//}
//finally {
//in.close();
//}
//}
//205.closeEntry();
//System.out.println("Folder successfully compressed");
//} catch (IOException ex) {
//ex.printStackTrace();
//} finally {
//try {
//zos.close();
//} catch (IOException e) {
//e.printStackTrace();
//}
//}
//}
//
//public void generateFileList(File node, String sourceFolder) {
//try
//{
//11 add file only
//if (node.isFile() {
//filelist.add(generateZipEntry(node.toString(), sourceFolder));
//}
//if (node.isDirectory() {
//String[] subNote = node.list();
//for (String filename: subNote) {
//generateFileList(new File(node, filename), sourceFolder);
//}
//}
//}
//catch(Exception e)
//{
//e.printStackTrace();
//}
//}
//private String generateZipEntry(String file, String sourceFile) {
//return file.substring(sourceFile.length() + 1, file.length();
//}
//}
//
