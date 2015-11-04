package neoveille.parser.test2;
import java.io.IOException;

/**
 * 
 * Description: Uses TreeTagger tool for annotating Russian, French, Polish, Chinese, Portuguese texts. 
 * TreeTagger: http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/
 * 
 */

public class TreeTagger extends Tagger{

	public TreeTagger() {
		super();
	}
	
	@Override
	void runTagger(String inputFile, String outputVerticalizedDir, String outputXMLverticalizedDir, String treeTagger, String language) throws IOException, InterruptedException {
		  
        Process p;
		String ch,current_file,current_directory;
		
		Utils.saveNameOfFiles(inputFile,listOfFiles);
		for(int i=0; i<listOfFiles.size(); i++){

			/*uses treeTagger for each file*/
			current_file = listOfFiles.get(i).substring(listOfFiles.get(i).lastIndexOf("/"),listOfFiles.get(i).length()-4) + ".vrt";
			current_directory = outputVerticalizedDir + "/" + language + current_file;
			ch = "cat '" + listOfFiles.get(i) + "' | " + treeTagger + " > " + "'" + current_directory + "'";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
			p.waitFor();
	    
			/*annotates the sentences of .vrt file*/
			Utils.annotateSentences(outputXMLverticalizedDir,current_directory,language,current_file);
			
		}
		
		/*concatenates .vrt texts*/
		current_directory = outputXMLverticalizedDir + "/Temporary/" + language;
		Utils.saveNameOfFiles(current_directory,listOfXMLVerticalizedFiles);
		current_directory = outputXMLverticalizedDir + "/Concatenation/" + language + "/all.vrt";
		Utils.concatenateFiles(current_directory,listOfXMLVerticalizedFiles);
		
	}

	@Override
	void postProcessing() {
		
	}
	
}
