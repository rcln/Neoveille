package neoveille.parser.test8;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	
	
	//args[0] is a folder, which contains the following sub-folders:
        //ALL: has a file with all Greek texts and texts' annotations (e.g. <text id='4' newspaper='Taxydromos' year='2015' month='09' day='06'> ............. </text>)
        //Data_Cleaning: has files with Greek texts (one text per file) without structural and positional attributes. Every text is processed in order to remove unknown characters (non UTF-8 characters). 
        //Titles: has a file with texts' annotations such as "<text id='4' newspaper='Taxydromos' year='2015' month='09' day='06'>".
        //vrtTexts: has files with positional attributes (such as lemma and part-of-speech tags).
        //XMLvrtTexts: has files with positional and structural attributes (such as annotations for sentences and texts).
	//args[1] is a file with Greek texts from TAL server
	//args[2] is the password for calling "sudo" command lines 
	public static void main(String args[]) throws IOException, InterruptedException, URISyntaxException {
			
	     EncodeCorpora e = new EncodeCorpora();
	     e.callTagger(args[0], args[1], args[2]);
	
	}	

}
