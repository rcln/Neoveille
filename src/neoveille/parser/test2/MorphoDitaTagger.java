package neoveille.parser.test2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * Description: Uses MorphoDita tool for annotating Czech texts.
 * MOrphoDita: http://ufal.mff.cuni.cz/morphodita
 * 
 */

public class MorphoDitaTagger extends Tagger{

	public MorphoDitaTagger() {
		super();
	}

	public static void changeOrderOfLemmaAndPOStags(String tmpOutputVerticalizedText, String outputVerticalizedText) throws IOException{
		
		String line,newline;
		String []tokens;
		
		BufferedReader br = new BufferedReader(new FileReader(tmpOutputVerticalizedText));
		
		while((line = br.readLine())!=null){
			tokens = line.split("\\s+");
			if(tokens.length==3){
				newline = tokens[0] + "\t" + tokens[2] + "\t" + tokens[1] + "\n";
				Utils.writeInFile(outputVerticalizedText,newline);
			}else
				Utils.writeInFile(outputVerticalizedText,line + "\n");
		}
		
		br.close();		
	}

	
	@Override
	void runTagger(String inputFile, String outputVerticalizedDir, String outputXMLverticalizedDir, String morphoDitaTagger,String language) throws IOException, InterruptedException {
		
		Process p;
		String ch, current_file, current_directory, tmp_current_directory;
		
		Utils.saveNameOfFiles(inputFile,listOfFiles);
		for(int i=0; i<listOfFiles.size(); i++){
			
			/*uses MorphoDita Tagger for each file*/
			current_file = listOfFiles.get(i).substring(listOfFiles.get(i).lastIndexOf("/"),listOfFiles.get(i).length()-4) + ".vrt";
			current_directory = outputVerticalizedDir + "/" + language + current_file;
			ch = morphoDitaTagger + "/./run_tagger " + " --input=untokenized --output=vertical " +  morphoDitaTagger + "/czech-morfflex-pdt-131112-raw_lemmas.tagger-best_accuracy " + "'" + listOfFiles.get(i) + "'" + ":" + "'" + current_directory + "'";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
			p.waitFor();
	     
			/*annotates the sentences of each .vrt file*/
			Utils.annotateSentences(outputXMLverticalizedDir,current_directory,language,current_file);
			
		}    
			
		current_directory = outputXMLverticalizedDir + "/Temporary/" + language;
		Utils.saveNameOfFiles(current_directory,listOfXMLVerticalizedFiles);
		
		tmp_current_directory = outputXMLverticalizedDir + "/Concatenation/" + language + "/tmp_all.vrt";
		current_directory = outputXMLverticalizedDir + "/Concatenation/" + language + "/all.vrt";
		
		/*concatenates .vrt texts*/
		Utils.concatenateFiles(tmp_current_directory,listOfXMLVerticalizedFiles);
		/*change the order of lemma and pos-tags at verticalized files*/
		changeOrderOfLemmaAndPOStags(tmp_current_directory, current_directory);

	 }
	

	@Override
	void postProcessing() {
		
	}

	
}
