# -*- coding: utf-8 -*-
import sys

#it adds annotations for sentences and counters for sentences and tokens to Chinese texts.

def add_annotations(file_input,file_output):

        f_output = open(file_output,'w')

        with open(file_input,"r") as f_input:

                isEndOfSentence = 0
		isStartOfSentence = 0

                for line in f_input:
			
                        if line.startswith("<token"):

                                counter_tokens = counter_tokens + 1

                                str1 = line.split("</token>")
                                startword = str1[0].rfind(">") + 1
                                str2 = str1[0][startword:]

                                if str2 == '。' or str2 == '？' or str2 == '！':

					if isEndOfSentence == 0:
                                        	newline = "<token " + "token_id=\'" + str(counter_tokens) + "\'" + line.split('<token')[1]
                                        	f_output.write(newline)
						f_output.write("</s>\n")
    						isEndOfSentence = 1
                                                isStartOfSentence = 0
                                     
                                else:

                                        if isEndOfSentence == 1:
                                                counter_sentences = counter_sentences + 1
                                                counter_tokens = 1
                                                f_output.write("<s " + "sentence_id=\'" + str(counter_sentences) + "\'>\n")
						isStartOfSentence = 1

                                       
                                        newline = "<token " + "token_id=\'" + str(counter_tokens) + "\'" + line.split('<token')[1]
                                        f_output.write(newline)
                                	isEndOfSentence = 0

	                elif line.startswith("<text"):

                                counter_sentences = 1
                                counter_tokens = 0
                                f_output.write(line)
                                f_output.write("<s " + "sentence_id=\'" + str(counter_sentences) + "\'>\n")
				isEndOfSentence = 0
				isStartOfSentence = 1
				
                        elif line.startswith("</text>"):
				
				if isEndOfSentence == 0 and isStartOfSentence == 1:
					f_output.write("</s>\n")
				f_output.write(line)
				isEndOfSentence = 0
				isStartOfSentence = 0
				
			else:
				f_output.write(line)

	f_output.close()
	f_input.close()

add_annotations(sys.argv[1],sys.argv[2])



