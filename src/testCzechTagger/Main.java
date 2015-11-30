package  testCzechTagger;
import java.io.IOException;
import java.net.URISyntaxException;

import testCzechTagger.EncodeCorpora;

public class Main {

	
	//args[0] is a folder, which contains the following sub-folders:
    //ALL: has a file with Czech texts and texts' annotations (e.g. <text id='1' newspaper='Blesk' year='2015' month='09' day='16'>............. </text>)
    //Texts: has files with Czech texts (one text per file) without structural and positional attributes.  
    //Titles: has a file with texts' and corpus annotations such as <text id='1' newspaper='Blesk' year='2015' month='09' day='16'> and <corpus id='tch'>.
    //vrtTexts: has files with positional attributes (such as lemma and part-of-speech tags).
    //XMLvrtTexts: has files with positional and structural attributes (such as annotations for sentences and texts).
    //args[1] is a file with Czech texts from TAL server
	//args[2] location of morphoDita tool (/.../MorphoDita/morphodita-master/src/)
	public static void main(String args[]) throws IOException, InterruptedException, URISyntaxException {
		
	     EncodeCorpora e = new EncodeCorpora();
	     e.callTagger(args[0], args[1], args[2]);
	
	}	
	
}
