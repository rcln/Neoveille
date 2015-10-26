
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public abstract class ParserOfTexts  {
	
	public static LinkedList<String> listOfFiles;
	
	/** 
	 * 
	 * Class constructor specifying a list of .txt files
	 * @param listOfFiles is a list with files' names
	 * 
	 * */
	public ParserOfTexts(LinkedList<String> listOfFiles){
		this.listOfFiles = listOfFiles;
	}
	
	/** 
	 * 
	 * Saves the names of text files of a directory into a linked list.
	 * @param inputDirectory the name of a directory, which contains the list of text files. 
	 * 
	 * */
	public void saveNameOfFiles(String inputDirectory){
		File folder = new File(inputDirectory);
		for(File f : folder.listFiles()){
			if(f.isDirectory())
				saveNameOfFiles(f.getAbsolutePath());
			else
				listOfFiles.add(f.getAbsolutePath());
		}
	}
	
	
	/**
	 *  
	 * Concatenates text files.
	 * @param listOfFiles the list of text files
	 * @param outputFile the location of text file, which has the sentences of several text files.
	 * @throws IOException if cannot be read
	 * 
	 * */
	public void concatenateFiles(LinkedList<String> listOfFiles, String outputFile) throws IOException{
		String currentFile, currentLine;
		BufferedReader br = null;
		
		for(int i=0; i<listOfFiles.size(); i++){
			currentFile = listOfFiles.get(i);
			br = new BufferedReader(new FileReader(currentFile));	
			while((currentLine=br.readLine())!=null){
				Utils.writeInFile(outputFile,currentLine +"\n");	
			}
		}
	}
	
	/**
	 *   
	 * Saves the context, part of speech tags and lemma of words using a tagger (e.g. treeTagger) into verticalized files (.vrt file)
	 * @param inputFile the location of file, which has the sentences of all text files
	 * @param outputVerticalizedFile the location of .vrt file  
	 * @param outputXMLverticalizedFile the location of .vrt file with XML format 
	 * @param treeTaggerFile the location of treetagger package, which is used for each language 
	 *  
	 * */
	
	abstract void runTagger(String inputFile, String outputVerticalizedFile, String outputXMLverticalizedFile, String taggerFile) throws IOException, InterruptedException ;
	
	abstract void postProcessing();
	
	/**
	 *   
	 * Encodes the corpora.
	 * @param outputEncodedCorporaFile the location of encoded corpora.
	 * @param inputXMLverticalizedFile the location of .vrt file with XML format. 
	 * @throws IOException if the process cannot execute the command line (an I/O error occurs)
	 * @throws InterruptedException  if the current thread is interrupted by another thread while it is waiting
	 * 
	 * 
	 * */
	public void createEncodedData(String inputXMLverticalizedFile,String outputEncodedCorporaFile) throws  InterruptedException, IOException{
		
		String ch = "cwb-encode -d" + outputEncodedCorporaFile + " -f " + inputXMLverticalizedFile + " -R /usr/local/share/cwb/registry/example -P pos -P lemma -S s";  
        Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",ch});
        p.waitFor();
	
	}

	public static LinkedList<String> getListOfFiles() {
		return listOfFiles;
	}

	public static void setListOfFiles(LinkedList<String> listOfFiles) {
		ParserOfTexts.listOfFiles = listOfFiles;
	}
	
}
