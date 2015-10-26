import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 * Description: Uses MorphoDita tool for annotating Cezch texts.
 * 
 */

public class MorphoDitaParserOfTexts extends ParserOfTexts{

	public MorphoDitaParserOfTexts(LinkedList<String> listOfFiles) {
		super(listOfFiles);
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
			}
		}
		
		br.close();		
	}

	
	@Override
	void runTagger(String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String morphoDitaTaggerFile) throws IOException, InterruptedException {
		
		Process p;
		String ch,line,tmpOutputVerticalizedText;
		String []tokens;
		int isPunctuation;
		BufferedReader br;
		
			tmpOutputVerticalizedText = outputVerticalizedText.substring(0,outputVerticalizedText.lastIndexOf("/")); 
			tmpOutputVerticalizedText = tmpOutputVerticalizedText + "/tmpOutputText.vrt";
			
			/*uses MorphoDita Tagger for text*/
			ch = morphoDitaTaggerFile + "/./run_tagger " + " --input=untokenized --output=vertical "  +  morphoDitaTaggerFile + "/czech-morfflex-pdt-131112-pos_only.tagger " + inputFile + ":" + tmpOutputVerticalizedText;
	        p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
	        p.waitFor();
	     
	        /*change the order of lemma and pos-tags at verticalized files*/
	        changeOrderOfLemmaAndPOStags(tmpOutputVerticalizedText, outputVerticalizedText);
	        
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
