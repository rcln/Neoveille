package testCzechTagger;
import java.io.IOException;
import java.util.LinkedList;

public class EncodeCorpora {

	public static LinkedList<String> listOfFiles;
	
	public EncodeCorpora(){
		listOfFiles = new LinkedList<String>();	
	}

	public void callTagger(String currentFolder, String inputFile, String MorphoDita) throws IOException, InterruptedException{

		String fileTexts = currentFolder + "/ALL/" + inputFile.substring(inputFile.lastIndexOf("/"),inputFile.length());
		String dirTexts = currentFolder + "/Texts/";
		String fileTitles = currentFolder + "/Titles/titles.txt";
		String vrtXMLDir = currentFolder + "/XMLvrtTexts/";
		String vrtDir = currentFolder + "/vrtTexts/";
		String fileConcatenation = currentFolder + "/Concatenation/" + fileTexts.substring(fileTexts.lastIndexOf("/"), fileTexts.length()-4) + ".vrt";
		String com;
		Process p;
		
		//copy a file from /../TXT/ folder of TAL server to /../ALL/ folder
		com = "cp " + inputFile + " " + fileTexts;
		p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
		p.waitFor();
		
		//divide the input file into several files (each text per file)
		Utils.divideTexts(fileTexts, dirTexts);
		
		//save names of texts 
		Utils.saveNameOfFiles(dirTexts,listOfFiles);
	
		//save texts annotations
	    Utils.saveTitles(fileTexts, fileTitles);
		
		//define structural and positional attributes using MorphoDita tagger
		for(int i=0; i<listOfFiles.size(); i++){
			System.out.println("File : " + listOfFiles.get(i));
			MorphoDitaTagger.runTagger(listOfFiles.get(i),vrtDir,vrtXMLDir, MorphoDita);	
		}

		//merge files, which have structural and positional attributes
		Utils.concatenateFiles(vrtXMLDir, fileTexts, fileTitles, fileConcatenation);		
	
		//encode corpora (compatible with CQPweb interface)
		createEncodedData(fileConcatenation);
		
	}
	
	public void createEncodedData(String fileConcatenation) throws  InterruptedException, IOException{		
		
		String com;
		Process p;
		
			String nameOfInputFile = fileConcatenation.substring(fileConcatenation.lastIndexOf("/"), fileConcatenation.length());
		
			com = "mkdir /opt/cwb/index/czechrepublic";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
		
			com = "cp " + fileConcatenation + " /opt/cwb/files/czechrepublic.vrt";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
		
			com =  "cwb-encode -d /opt/cwb/index/czechrepublic -f /opt/cwb/files/" + nameOfInputFile + " -R /opt/cwb/registry/czechrepublic -c utf8 -P lemma -P pos -S text:0+id+newspaper+year+month+day -S s -0 corpus";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();
        
			com = "cwb-make -D -V CZECHREPUBLIC";
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",com});
			p.waitFor();	
			
	}
	
}
