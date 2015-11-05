
package neoveille.parser.test2;
import java.io.IOException;
import java.util.LinkedList;

public abstract class Tagger  {
	
	public static LinkedList<String> listOfFiles;
	public static LinkedList<String> listOfXMLVerticalizedFiles;

	public Tagger(){
		listOfFiles = new LinkedList<String>();
		listOfXMLVerticalizedFiles = new LinkedList<String>();
	}
	
	public void createEncodedData(String inputXMLverticalizedDir,String outputEncodedCorporaFile, String language) throws  InterruptedException, IOException{	
		String current_directory = inputXMLverticalizedDir  + "/Concatenation/" + language + "/all.vrt";
		String ch = "cwb-encode -d" + outputEncodedCorporaFile + "/" + language + " -f " + current_directory + " -R /usr/local/share/cwb/registry/all -P pos -P lemma -S s";  
        Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
        p.waitFor();
	}
	
	public static LinkedList<String> getListOfFiles() {
		return listOfFiles;
	}

	public static void setListOfFiles(LinkedList<String> listOfFiles) {
		Tagger.listOfFiles = listOfFiles;
	}

	public static LinkedList<String> getListOfXMLVerticalizedFiles() {
		return listOfXMLVerticalizedFiles;
	}

	public static void setListOfXMLVerticalizedFiles(
			LinkedList<String> listOfXMLVerticalizedFiles) {
		Tagger.listOfXMLVerticalizedFiles = listOfXMLVerticalizedFiles;
	}

	abstract void runTagger(String inputFile, String outputVerticalizedDir, String outputXMLverticalizedDir, String taggerFile, String language) throws IOException, InterruptedException;
	
	abstract void postProcessing();
			
}
