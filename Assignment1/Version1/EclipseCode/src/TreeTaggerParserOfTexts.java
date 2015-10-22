
/**
 * 
 * Description: Uses TreeTagger tool for annotating Russian, French, Polish, Chinese, Czech, Portuguese texts. 
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
	void runTagger(String inputFile, String outputSentence, String outputVerticalizedText, String outputXMLverticalizedText, String treeTaggerFile) throws IOException, InterruptedException {
		  
        Process p;
		String ch, line1, line2, line3;
		String []tokens;
		int isPunctuation;
		BufferedReader br1,br2,br3;
		
		br1 = new BufferedReader(new FileReader(inputFile));
		while ((line1 = br1.readLine())!=null){
		
			/*uses treeTagger for each sentence*/
			ch = "echo ' " + line1 + " ' " + "| " + treeTaggerFile + " > " + outputSentence;  
	        p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
	        p.waitFor();
	    
	        br2 = new BufferedReader(new FileReader(outputSentence));
	        while((line2 = br2.readLine())!=null){ 
	        	Utils.writeInFile(outputVerticalizedText, line2 + "\n");
	        }
	        br2.close();   
	     
	        /*annotates the sentences of .vrt file*/
	        br3 = new BufferedReader(new FileReader(outputSentence));
	        Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	        isPunctuation = 0;
	        while((line3 = br3.readLine())!=null){ 
	        	
	        	tokens = line3.split("\\s+");
	        	if(tokens[0].equals(".") || tokens[0].equals("!") || tokens[0].equals("?")){
	        		Utils.writeInFile(outputXMLverticalizedText, line3 + "\n");
	        		isPunctuation = 1;
	        	}else{
	        		if(isPunctuation == 1){
	        			Utils.writeInFile(outputXMLverticalizedText,"<\\s>\n");	 
	        			Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	        		}
	        		Utils.writeInFile(outputXMLverticalizedText, line3 + "\n");
	        		isPunctuation = 0;
	        	}
	        }
	        br3.close();
	 	}
	 	br1.close();	
	}

	@Override
	void postProcessing() {
		// TODO Auto-generated method stub	
	}
	
}
