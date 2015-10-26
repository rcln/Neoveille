import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import org.soaplab.getstarted.ilsp_nlp.IlspNlp;
import org.soaplab.getstarted.ilsp_nlp.IlspNlpService;
import org.soaplab.typedws.JobId;

/**
 * 
 * Description: Uses ILSP Tagger tool for annotating Greek texts.
 * 
 */

public class ILSPParserOfTexts extends ParserOfTexts{

	public ILSPParserOfTexts(LinkedList<String> listOfFiles) {
		super(listOfFiles);
	}

	@Override
	void runTagger(String inputFile, String outputVerticalizedText, String outputXMLverticalizedText, String treeTaggerFile) throws IOException, InterruptedException {
		
		String line = null;
		URLConnection con;
		InputStream is;
		String current_result,str;
		String []tokens,str1,str2,str3;
		int isPunctuation;
		BufferedReader br1,br2,br3;
		IlspNlpService service;
		IlspNlp port;
		JobId current_job;
		
		br1 = new BufferedReader(new FileReader(inputFile));
		while ((line = br1.readLine()) != null) {
		
			/*finds lemma and pos-tag for each sentence*/
			service = new IlspNlpService();
			port = service.getIlspNlpPort();
			
			if(line.length()>1){
				current_result = port.run("txt",null,line,null,null,"el",false);
				current_job = new JobId();
				current_job.setJobId(current_result);	
				port.waitfor(current_job);
				port.terminate(current_job);
			
				/*saves the results to verticalized files*/
				URL url = new URL("http://nlp.ilsp.gr/soaplab2-results//"+current_result+"_output");		
				con = url.openConnection();
				is = con.getInputStream();
				br2 = new BufferedReader(new InputStreamReader(is));
				while ((line = br2.readLine()) != null) {
					if(line.contains("<t ")){
						tokens = line.split("\\s+");
						str1 = tokens[3].split("\"");
						str2 = tokens[4].split("\"");
						str3 = tokens[5].split("\"");
						str = str1[1] + "\t" + str2[1] + "\t" + str3[1] + "\n";  
						Utils.writeInFile(outputVerticalizedText,str);		
					}
				}
				br2.close();
			}
		}
		br1.close();
		
		/*annotates the sentences of .vrt file*/
	    br3 = new BufferedReader(new FileReader(outputVerticalizedText));
	    Utils.writeInFile(outputXMLverticalizedText,"<s>\n");
	    isPunctuation = 0;
	    while((line = br3.readLine())!=null){ 
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
	 	br3.close();	
	}

	@Override
	void postProcessing() {
		
	}

}
