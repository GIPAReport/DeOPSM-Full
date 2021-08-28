package com.designops.utility;

import java.util.Arrays;
import java.util.List;

public class Constant {

	
	public static String fromMail = "adidesignops@gmail.com";
	public static String host = "localhost";
	public static String password = "admin123";
	public static String randomPassword = "admin123";
	public static String defaultRoLeName="Subscriber";
	public static List<String> tenantColorList =
	Arrays.asList("#01b2ac", "#B02925","#679521", "#1A4A76", "#6C1A66", "#5A5714", "#553015", "#293838");
	public static List<String> tenantNameList=
	Arrays.asList("CS Digital", "RMPlus", "Wealth Engine", "Flowable", "DDH", "Score", "PAM", "Other");
	public static String elasticURL = "http://10.202.43.79:9200/";
	public static List<String> contentTypelist = Arrays.asList(".7z", ".bz2", ".bin", ".class", ".com", ".dat", ".deb",	".dll", ".dmg", ".ear",	".exe", ".gz", ".iso", ".jar", ".lz", ".mdb", ".mp3", ".mp4", ".msi", ".msp",".nupkg",".0", ".ocx", ".pkg", ".pyc", ".rar", ".rpm", ".rz" ,".so", ".tar", ".tgz", ".war", ".wav", ".wim", ".xz",".zip");
	public static String getTenantsForUserWithAdminRoleURL = "http://gbld9035018.eu.hedani.net:8080/marketplace-2.4.2/tenantsByAdmin/";
	public static String getTenantByTenantIdURL = "http://gbld9035018.eu.hedani.net:8080/marketplace-2.4.2/tenant/";
	public static String getALlArifactsByTenantIdURL = "http://gbld9035018.eu.hedani.net:8080/marketplace-2.4.2/artifact/";
	public static String getUserByNameURL = "http://gb1d9035018.eu.hedani.net:8080/marketplace-2.4.2/users/";
	public static String downloadURL = "http://gb1d9035018.eu.hedani.net:8080/marketplace-2.4.2/ downloadFolder FromIT/";
	public static String rootPath="/marketplace-2.4.2/index.html";
	public static String odysseyUserName = "ngummada";
	public static String odysseyPassword = "Gixt9012";
	public static String artifactURL = "http://odyssey.apps.csintra.net/";
	public static String repoName="artifactory/libs-snapshot-local/com/csg/iwm/designops/DesOps/1.0.0-SNAPSHOT/";
	public static String bitbucketURL = "http://odyssey.apps.csintra.net/bitbucket/scm/desops/designopsui.git";
	public static String extractedDestination = "/cs/csdbb/tomcat/extract_zip_1/";
	public static String uploadTempFolder = "/cs/csdbb/tomcat/upload_repo";
	public static String downloadTempFolder = "/cs/csdbb/tomcat/download_repo/";
	public static String gitBranch = "CS-Digital";
	public static String gitCommitMessage = "DESOPS-3 adding file";
	public static String outputZipFolder = "/cs/csdbb/tomcat/extract_zip_2/";
	public static String downloadBitbucketURL = "https://odyssey.apps.csintra.net/bitbucket/projects/DESOPS/repos/designopsui/raw/";

}
