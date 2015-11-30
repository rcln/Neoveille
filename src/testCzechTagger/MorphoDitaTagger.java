package  testCzechTagger;
import java.io.IOException;

public class MorphoDitaTagger{

	public MorphoDitaTagger() {
	
	}

	public static void runTagger(String currentFile, String vrtDir, String vrtXMLDir, String morphoDitaTagger) throws IOException, InterruptedException {
		
		  String vrtFile, com;
		  Process p;

		  vrtFile = currentFile.substring(currentFile.lastIndexOf("/"),currentFile.length()-4) + ".vrt";  
	      vrtDir = vrtDir + vrtFile;
		  vrtXMLDir = vrtXMLDir + vrtFile;
		
		  com = morphoDitaTagger + "/./run_tagger " + " --input=untokenized --output=vertical " +  morphoDitaTagger + "/czech-morfflex-pdt-131112-raw_lemmas.tagger-best_accuracy '" + currentFile + "':'" + vrtDir + "'";
		  p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
		  p.waitFor();
		  
		  Utils.annotateSentences(vrtDir,vrtXMLDir);
		  			 						
	}		
	
}
