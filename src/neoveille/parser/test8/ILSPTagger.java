package neoveille.parser.test8;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.soaplab.getstarted.ilsp_nlp.IlspNlp;
import org.soaplab.getstarted.ilsp_nlp.IlspNlpService;
import org.soaplab.typedws.JobId;

public class ILSPTagger{

	public ILSPTagger() {
	
	}

	public static void runTagger(String currentFile, String vrtDir, String vrtXMLDir) throws IOException {
		
		String line1 = null, line2 = null, current_result, str, vrtFile;
		HttpURLConnection con;
		InputStream is;
		String []tokens;
		BufferedReader br1,br2;
		IlspNlpService service;
		IlspNlp port;
		JobId current_job;	
		int code;

		    vrtFile = currentFile.substring(currentFile.lastIndexOf("/"),currentFile.length()-4) + ".vrt";  
			vrtDir = vrtDir + vrtFile;
			vrtXMLDir = vrtXMLDir + vrtFile;
	
		 	service = new IlspNlpService();
		 	port = service.getIlspNlpPort();
			
			br1 = new BufferedReader(new FileReader(currentFile));
			while ((line1 = br1.readLine()) != null) {
	
			     String s = line1.trim();
				 if(s.length()>0){	
					 	
					 	current_result = port.run("txt",null,line1,null,null,"el",false);
					 	current_job = new JobId();
					 	current_job.setJobId(current_result);	
					 	port.waitfor(current_job);
					 	port.clear(current_job);
					
					 	URL url = new URL("http://nlp.ilsp.gr/soaplab2-results//" + current_result + "_output");		
					 	con = (HttpURLConnection) url.openConnection();
					
					 	code = con.getResponseCode();
					 	if(code >= 400){
					 		
					 		System.out.println("ERROR CODE: " + code);
					 		is = con.getErrorStream();
					 	
					 	}else{
					 		
					 		is = con.getInputStream();
					 		br2 = new BufferedReader(new InputStreamReader(is));
					 		while ((line2 = br2.readLine()) != null){
					 			if(line2.contains("<t ")){
					 				tokens = line2.split("\"");
					 				str = tokens[3] + "\t" + tokens[5] + "\t" + tokens[7] + "\n";
					 				Utils.writeInFile(vrtDir,str);		
					 			}
					 		}
					 		is.close();
					 		br2.close();
					 		
					 		Utils.annotateSentences(vrtDir,vrtXMLDir);
					 		
					 	}
				}//if 
			}//while
			
			br1.close();
			
	}		
}
