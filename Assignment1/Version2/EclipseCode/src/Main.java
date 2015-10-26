
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;


public class Main {
	
	/**
	 * 
	 * @param args[0] language 
	 * @param args[1] the name of a directory, which contains the list of text files (e.g. /home/user/NeoveilleProject/Texts)
	 * @param args[2] the location of text file, which has the sentences of all text files (e.g./home/user/NeoveilleProject/Features/Concatenation/ALL.txt)
	 * @param args[3] the location of .vrt file  (e.g. /home/user/NeoveilleProject/Features/verticalizedText/outputText.vrt)
	 * @param args[4] the location of .vrt file with XML format (e.g. /home/user/NeovilleProject/Features/XMLVerticalizedText/outputText.vrt)
	 * @param args[5] the script which runs the tagger (e.g. /home/user/NeoveilleProject/TreeTagger/cmd/tree-tagger-french)
	 * @param args[6] the location of encoded corpora (e.g. /home/user/NeovilleProject/EncodedCorpora/corpora/data/example)  
	 * @throws IOException is an exception error from "concatenateFiles" function
	 * @throws InterruptedException is an exception error from "runTagger" function
	 * @throws URISyntaxException 
	 * 
	 */
	
	public static void main(String args[]) throws IOException, InterruptedException, URISyntaxException {
		
		if(args.length == 7){
	
			LinkedList<String> listOfFiles = new LinkedList<String>();
			ParserOfTexts p = null;
			
			if(args[0].equals("French") || args[0].equals("Chinese") || args[0].equals("Polish") || args[0].equals("Russian") || args[0].equals("Portuguese"))
				p = new TreeTaggerParserOfTexts(listOfFiles);
			else if(args[0].equals("Czech"))
				p = new MorphoDitaParserOfTexts(listOfFiles);
			else if(args[0].equals("Greek"))
				p = new ILSPParserOfTexts(listOfFiles);
			
			if(p!=null){
				p.saveNameOfFiles(args[1]);
				p.concatenateFiles(listOfFiles,args[2]);
				p.runTagger(args[2],args[3],args[4],args[5]);
				p.createEncodedData(args[4],args[6]);
			}
			
		}
		
		
	}	

}
