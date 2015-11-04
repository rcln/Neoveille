package neoveille.parser.test2;
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
			if(file.isDirectory())
				saveNameOfFiles(file.getAbsolutePath(), list);
			else
				list.add(file.getAbsolutePath());
		}
		
	}
	
	public static void concatenateFiles(String outputFile, LinkedList<String> list) throws IOException{
		
		String currentFile, currentLine, lastLine = "";
		BufferedReader br = null;
		String []tokens;
		
		for(int i=0; i<list.size(); i++){
			
			currentFile = list.get(i);
			br = new BufferedReader(new FileReader(currentFile));	
			while((currentLine=br.readLine())!=null){
				lastLine = currentLine; 
				Utils.writeInFile(outputFile,currentLine + "\n");	
			}
			
			/*European and Chinese punctuation*/
			tokens = lastLine.split("\\s+");
			if(tokens[0].equals(".") || tokens[0].equals("。") || tokens[0].equals("!") || tokens[0].equals("?") || tokens[0].equals("！") || tokens[0].equals("？"))
				Utils.writeInFile(outputFile,"<\\s>\n");
			
		}
		
	}
    
	
	public static void annotateSentences(String outputXMLverticalizedDir, String directory, String language, String file) throws IOException{
		
		String []tokens;
		String line,current_directory;
		BufferedReader br = new BufferedReader(new FileReader(directory));
		current_directory = outputXMLverticalizedDir + "/Temporary/" + language + file;
		Utils.writeInFile(current_directory,"<s>\n");
		int isPunctuation = 0;
		
		while((line = br.readLine())!=null){ 
        	
			tokens = line.split("\\s+");
			if(tokens.length==3 || tokens.length==2){
				/*Chinese and European full-stop, question mark and exclamation mark*/
				if(tokens[0].equals(".") || tokens[0].equals("。") || tokens[0].equals("!") || tokens[0].equals("?") || tokens[0].equals("！") || tokens[0].equals("？")){
					Utils.writeInFile(current_directory, line + "\n");
					isPunctuation = 1;
				}else{
					if(isPunctuation == 1){
						Utils.writeInFile(current_directory,"<\\s>\n");	 
						Utils.writeInFile(current_directory,"<s>\n");
					}
					Utils.writeInFile(current_directory,line + "\n");
					isPunctuation = 0;
				}
			}
			
		}
        br.close();
	}
	
}
