package  testCzechTagger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Utils{

	public static void writeInFile(String outputFile, String text) throws IOException{
        
        File file = new File(outputFile);
        BufferedWriter bw;
         
        if (!file.exists()) {
                file.createNewFile();
        }    
        
        bw = new BufferedWriter(new FileWriter(file,true));
        bw.write(text);
        bw.close();
     
    }    
	
	public static void saveNameOfFiles(String inputDirectory, LinkedList<String> list){
		
		File folder = new File(inputDirectory);
		for(File file : folder.listFiles()){
				list.add(file.getAbsolutePath());
		}
		
	}
	
	public static void annotateSentences(String vrtDir, String vrtXMLDir) throws IOException{
		
		String []tokens;
		String line,lastLine = null;
		int isPunctuation = 0;
	
		BufferedReader br = new BufferedReader(new FileReader(vrtDir));	
		
		Utils.writeInFile(vrtXMLDir,"<s>\n");
		
		while((line = br.readLine())!=null){ 
			
			tokens = line.split("\\s+");
			
			if(tokens.length==3){
				lastLine = line;
				if(tokens[0].equals(".") || tokens[0].equals("?") || tokens[0].equals("!")){
					Utils.writeInFile(vrtXMLDir, line + "\n");
					isPunctuation = 1;
				}else{
					if(isPunctuation == 1){
						Utils.writeInFile(vrtXMLDir,"</s>\n");	 
						Utils.writeInFile(vrtXMLDir,"<s>\n");
					}
					Utils.writeInFile(vrtXMLDir,line + "\n");
					isPunctuation = 0;
				}
			}
		}
		
		tokens = lastLine.split("\\s+");
		if(tokens[0].equals(".") || tokens[0].equals("?") || tokens[0].equals("!"))
			Utils.writeInFile(vrtXMLDir,"</s>\n");
		else{
			Utils.writeInFile(vrtXMLDir,".\t.\tZ:-------------\n");
			Utils.writeInFile(vrtXMLDir,"</s>\n");
		}
        br.close();
        
	}
	
	
	public static void divideTexts(String inputFile, String outputDir) throws IOException{
		
		String line, currentFile = null;
		String []tokens, tokens_id;
		int counter;
		
		BufferedReader br = new BufferedReader(new FileReader(inputFile));		
		while ((line = br.readLine()) != null) {
	
			if(line.startsWith("<text ")){
				
				tokens = line.split(" ");
				tokens_id = tokens[1].split("=");
				tokens_id[1] = tokens_id[1].replaceAll("'", "");
				counter = Integer.parseInt(tokens_id[1]);
				currentFile = outputDir + inputFile.substring(inputFile.lastIndexOf("/")+1,inputFile.length()-4) + "_" + counter + ".txt";
			
			}else if(!line.startsWith("<text ") && !line.startsWith("</text>") && !line.startsWith("<corpus ") && !line.startsWith("</corpus>")){
			
				Utils.writeInFile(currentFile, line + "\n");
			
			}
			
		}
		br.close();
    }
	
	
	public static void saveTitles(String inputFile, String outputFile) throws IOException{
		
		String line;
		BufferedReader br = new BufferedReader(new FileReader(inputFile));		
		while ((line = br.readLine()) != null) {
			if(line.startsWith("<text ") || line.startsWith("<corpus ") ){
				Utils.writeInFile(outputFile, line + "\n");
			}
		}
		br.close();
		
	}
	
	public static void concatenateFiles(String vrtXMLDir, String inputFileTexts, String inputFileTitles, String outputFileConcatenation) throws IOException{
		
		String line1,currentFile,line2;
		String []tokens, tokens_id;
		BufferedReader br1 = null, br2 = null;

		br1 = new BufferedReader(new FileReader(inputFileTitles));
		while((line1 = br1.readLine())!=null){
			if(line1.startsWith("<text ")){	
				
				tokens = line1.split(" ");
				tokens_id = tokens[1].split("=");
				tokens_id[1] = tokens_id[1].replaceAll("'", "");
				currentFile =  vrtXMLDir + inputFileTexts.substring(inputFileTexts.lastIndexOf("/"),inputFileTexts.length()-4) + "_" + tokens_id[1] + ".vrt";
				
				File f = new File(currentFile);
				if(f.exists()){
					
					Utils.writeInFile(outputFileConcatenation,line1 +"\n");
					
					br2 = new BufferedReader(new FileReader(currentFile));
					while((line2 = br2.readLine())!=null){
						Utils.writeInFile(outputFileConcatenation,line2 + "\n");	
					}
					br2.close();
			 		Utils.writeInFile(outputFileConcatenation,"</text>\n");
			 		
			 		
				}//if
				
			}else if(line1.startsWith("<corpus ")){
					Utils.writeInFile(outputFileConcatenation,line1 + "\n");								
			}//if
		}//while
		
		Utils.writeInFile(outputFileConcatenation,"</corpus>\n");
		br1.close();
		
	}
	
}
