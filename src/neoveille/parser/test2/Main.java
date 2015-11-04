package neoveille.parser.test2;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	
	public static void main(String args[]) throws IOException, InterruptedException, URISyntaxException {
		
		if(args.length == 6){

			Tagger p = null;
			if(args[0].equals("French") || args[0].equals("Chinese") || args[0].equals("Polish") || args[0].equals("Russian") || args[0].equals("Portuguese"))
				p = new  TreeTagger();
			else if(args[0].equals("Czech"))
				p = new MorphoDitaTagger();
			else if(args[0].equals("Greek"))
				p = new ILSPTagger();
			
			if(p!=null){
				p.runTagger(args[1], args[2], args[3], args[4], args[0]);
				p.createEncodedData(args[3], args[5], args[0]);
			}
			
		}
	}	

}