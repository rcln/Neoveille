import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils{

	

	/**
	 *   
	 * Writes into a specific file.
	 * @param pathOfOutputFile the location of the file.
	 * @param text is the text, which is written in the file.
	 * @throws IOException if an I/O error occurs
	 * 
	 * */
	
	public static void writeInFile(String pathOfOutputFile,String text) throws IOException{
        
        File file = new File(pathOfOutputFile);
        BufferedWriter bw;
         
        if (!file.exists()) {
                file.createNewFile();
        }    
        
        bw = new BufferedWriter(new FileWriter(file,true));
        bw.write(text);
        bw.close();
     
    }    
     
}
