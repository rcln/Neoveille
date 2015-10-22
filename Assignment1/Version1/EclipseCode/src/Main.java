import java.io.IOException;
import java.util.LinkedList;

public class Main {
	
	/**
	 * 
	 * @param args[0] the name of a directory, which contains the list of text files (e.g. /home/user/NeoveilleProject/Texts)
	 * @param args[1] the location of text file, which has the sentences of all text files (e.g./home/user/NeoveilleProject/Features/Concatenation/ALL.txt)
	 * @param args[2] the location of text file that has the annotations of a sentence (e.g. /home/user/NeovilleProject/TreeTagger/outputTreeTagger/outputSentence.txt)
	 * @param args[3] the location of .vrt file  (e.g. /home/user/NeoveilleProject/Features/verticalizedText/outputText.vrt)
	 * @param args[4] the location of .vrt file with XML format (e.g. /home/user/NeovilleProject/Features/XMLVerticalizedText/outputText.vrt)
	 * @param args[5] the script which runs the tagger (e.g. /home/user/NeoveilleProject/TreeTagger/cmd/tree-tagger-french)
	 * @param args[6] the location of encoded corpora (e.g. /home/user/NeovilleProject/EncodedCorpora/corpora/data/example)  
	 * @throws IOException is an exception error from "concatenateFiles" function
	 * @throws InterruptedException is an exception error from "runTagger" function
	 * 
	 */
	
	public static void main(String args[]) throws IOException, InterruptedException {
		
		if(args.length == 7){
			
			LinkedList<String> listOfFiles = new LinkedList<String>();
			ParserOfTexts p = new TreeTaggerParserOfTexts(listOfFiles);
			p.saveNameOfFiles(args[0]);
			p.concatenateFiles(listOfFiles,args[1]);
			p.runTagger(args[1],args[2],args[3],args[4],args[5]);
			p.createEncodedData(args[4],args[6]);
			
		}
		
	}	

}
