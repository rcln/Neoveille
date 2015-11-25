package neoveille.parser.test8;
import java.io.IOException;
import java.util.LinkedList;

public class EncodeCorpora {

	public static LinkedList<String> listOfFiles;
	
	public EncodeCorpora(){
		listOfFiles = new LinkedList<String>();	
	}

	public void callTagger(String currentFolder, String inputFile, String linuxPassword) throws IOException, InterruptedException{

		String fileTexts = currentFolder + "/ALL/" + inputFile.substring(inputFile.lastIndexOf("/"),inputFile.length());
		String dirTexts = currentFolder + "/Data_Cleaning/";
		String fileTitles = currentFolder + "/Titles/titles.txt";
		String vrtXMLDir = currentFolder + "/XMLvrtTexts/";
		String vrtDir = currentFolder + "/vrtTexts/";
		String fileConcatenation = currentFolder + "/Concatenation/" + fileTexts.substring(fileTexts.lastIndexOf("/"), fileTexts.length()-4) + ".vrt";
		String com;
		Process p;
		
		//copy a file from /../TXT/ folder of TAL server to /../ALL/ folder
		com = "echo " + linuxPassword + " | sudo -S -k cp " + inputFile + " " + fileTexts;
		p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
		p.waitFor();
		
		//divide the input file into several files (each text per file)
		//clean texts from unknown characters (non UTF-8 characters)
		Utils.divideAndCleanTexts(fileTexts, dirTexts);
		
		//save names of texts (after cleaning texts)
		Utils.saveNameOfFiles(dirTexts,listOfFiles);
	
		//save text annotations
	    Utils.saveTitles(fileTexts, fileTitles);
		
		//define structural and positional attributes using ILSP_NLP tagger
		for(int i=0; i<listOfFiles.size(); i++){
			ILSPTagger.runTagger(listOfFiles.get(i),vrtDir,vrtXMLDir);	
		}

		//merge files, which have structural and positional attributes
		Utils.concatenateFiles(vrtXMLDir, fileTexts, fileTitles, fileConcatenation);		
	
		//encode corpora (compatible with CQPweb interface)
		createEncodedData(fileConcatenation,linuxPassword);
		
	}
	
	public void createEncodedData(String fileConcatenation, String linuxPassword) throws  InterruptedException, IOException{		
		
		String com;
		Process p;
		
			String nameOfInputFile = fileConcatenation.substring(fileConcatenation.lastIndexOf("/"), fileConcatenation.length());
		
			com = "echo " + linuxPassword + " | sudo -S -k mkdir /opt/cwb/index/greece";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
		
			com = "echo " + linuxPassword + " | sudo -S -k cp " + fileConcatenation + " /opt/cwb/files/greece.vrt";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
		
			com =  "echo " + linuxPassword + " | sudo -S -k cwb-encode -d /opt/cwb/index/greece -f /opt/cwb/files/" + nameOfInputFile + " -R /opt/cwb/registry/greece -c utf8 -P pos -P lemma -S text:0+id+newspaper+year+month+day -S s -0 corpus";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
        
			com = "echo " + linuxPassword + " | sudo -S -k cwb-make -D -V GREECE";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();	
			
	}
	
	
}
