import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 * Description: Uses TreeTagger tool for annotating Greek texts.
 * 
 */

public class AUEBParserOfTexts extends ParserOfTexts{

	public AUEBParserOfTexts(LinkedList<String> listOfFiles) {
		super(listOfFiles);
		// TODO Auto-generated constructor stub
	}

	@Override
	void runTagger(String inputFile, String temporaryOutput,
			String outputVerticalizedFile, String outputXMLverticalizedFile,
			String treeTaggerFile) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	void postProcessing() {
		// TODO Auto-generated method stub
		
	}

}
