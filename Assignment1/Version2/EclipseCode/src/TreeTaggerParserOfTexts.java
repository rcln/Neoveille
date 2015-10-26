
/**
 * 
 * Description: Uses TreeTagger tool for annotating Russian, French, Polish, Chinese, Portuguese texts. 
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class TreeTaggerParserOfTexts extends ParserOfTexts{

	public TreeTaggerParserOfTexts(LinkedList<String> listOfFiles) {
		super(listOfFiles);
	}
	
	@Override
	void runTagger(String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String treeTaggerFile) throws IOException, InterruptedException {
		  
        Process p;
		String ch,line;
		String []tokens;
		int isPunctuation;
		BufferedReader br;
		
		/*uses treeTagger for a document*/
		ch = "cat " + inputFile + " | " + treeTaggerFile + " > " + outputVerticalizedText;
	    p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
	    p.waitFor();
	    
	    /*annotates the sentences of .vrt file*/
	    br = new BufferedReader(new FileReader(outputVerticalizedText));
	    Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	    isPunctuation = 0;
	    while((line = br.readLine())!=null){ 
	       tokens = line.split("\\s+");
	       if(tokens[0].equals(".") || tokens[0].equals("!") || tokens[0].equals("?")){
	        		Utils.writeInFile(outputXMLverticalizedText, line + "\n");
	        		isPunctuation = 1;
	       }else{
	        	if(isPunctuation == 1){
	        		Utils.writeInFile(outputXMLverticalizedText,"<\\s>\n");	 
	        		Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	        	}
	        	Utils.writeInFile(outputXMLverticalizedText, line + "\n");
	        	isPunctuation = 0;
	       }
	 	}
	 	br.close();	
	}

	@Override
	void postProcessing() {
		
	}
	
}
