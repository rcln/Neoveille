
*/src/neoveille/parser/test8

Description
    - annotations for sentences
    - annotations for lemma and pos-tags 
    - encodes corpora in order to be compatible with IMS workbench corpus.
  	
Tool
    - ILSP_NLP : http://nlp.ilsp.gr/ws/

*/src/neoveille/parser/test2

Description

   - ILSPTagger.java :  finds the pos-tags and lemmas for Greek texts 
   
   - MorphoDitaTagger.java : finds the pos-tags and lemmas for Czech texts
   
   - TreeTagger.java : finds the pos-tags and lemmas for French, Polish, Russian, Chinese and Portugese texts

   - Tagger.java : it encodes the corpora in order to be compatible with IMS Corpus workbench and contains an abstract      
                  method, which is implemeted by ILSPTagger, MorphoDitaTagger and TreeTagger 
                  classes.

   - Main.java :  
   
   - Utils.java :
   
Tools 

 - TreeTagger : http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/
 - MorphoDita : https://ufal.mff.cuni.cz/morphodita/install
 - ILSP_NLP : http://nlp.ilsp.gr/ws/
 - IMS Corpus WorkBench : http://cwb.sourceforge.net/download.php


