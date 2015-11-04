package neoveille.parser.test2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.soaplab.getstarted.ilsp_nlp.IlspNlp;
import org.soaplab.getstarted.ilsp_nlp.IlspNlpService;
import org.soaplab.typedws.JobId;

/**
 * 
 * Description: Uses ILSP Tagger tool for annotating Greek texts.
 * ILSP tagger: http://nlp.ilsp.gr/ws/
 * 
 */

public class ILSPTagger extends Tagger{

	public ILSPTagger() {
		super();
	}

	@Override
	void runTagger(String inputFile, String outputVerticalizedDir, String outputXMLverticalizedDir, String morphoDitaTagger,String language) throws IOException, InterruptedException {
		
		String line = null;
		URLConnection con;
		InputStream is;
		String current_result,current_file,current_directory,str;
		String []tokens,str1,str2,str3;
		BufferedReader br1,br2;
		IlspNlpService service;
		IlspNlp port;
		JobId current_job;
		
		Utils.saveNameOfFiles(inputFile,listOfFiles);
		for(int i=0; i<listOfFiles.size(); i++){
	
			/*saves text of each file*/
			str = "";
			br1 = new BufferedReader(new FileReader(listOfFiles.get(i)));
			while ((line = br1.readLine()) != null) {
				str = line + "\n" + str;
			}	
			br1.close();
			
			/*finds lemma and pos-tag for each file*/
			service = new IlspNlpService();
			port = service.getIlspNlpPort();
			current_result = port.run("txt",null,str,null,null,"el",false);
			current_job = new JobId();
			current_job.setJobId(current_result);	
			port.waitfor(current_job);
			port.terminate(current_job);
			
			/*saves the results to .vrt files*/
			URL url = new URL("http://nlp.ilsp.gr/soaplab2-results//" + current_result + "_output");		
			con = url.openConnection();
			is = con.getInputStream();
			br2 = new BufferedReader(new InputStreamReader(is));
			current_file = listOfFiles.get(i).substring(listOfFiles.get(i).lastIndexOf("/"),listOfFiles.get(i).length()-4) + ".vrt";
			current_directory = outputVerticalizedDir + "/" + language + current_file;
		
			while ((line = br2.readLine()) != null) {
				if(line.contains("<t ")){
					tokens = line.split("\"");
					str = tokens[3] + "\t" + tokens[5] + "\t" + tokens[7] + "\n";  
					Utils.writeInFile(current_directory,str);		
				}
			}
			br2.close();
		
			/*annotates the sentences of each .vrt file*/
			Utils.annotateSentences(outputXMLverticalizedDir,current_directory,language,current_file);
			
		}
		
		/*concatenates .vrt texts*/
		current_directory = outputXMLverticalizedDir + "/Temporary/" + language;
		Utils.saveNameOfFiles(current_directory,listOfXMLVerticalizedFiles);
		current_directory = outputXMLverticalizedDir + "/Concatenation/" + language + "/all.vrt";
		Utils.concatenateFiles(current_directory,listOfXMLVerticalizedFiles);

	}

	@Override
	void postProcessing() {
		
	}
}
