# -*- coding: utf-8 -*-

import sys

#Adds counters for sentencces and tokens to Polish, Russian, Czech and Greek texts.

def add_annotations(file_input,file_output):

        f_output = open(file_output,'w')
        with open(file_input,"r") as f_input:
                for line in f_input:
			
	                if line.startswith("<text"):

                       		counter_sentence = 0

				f_output.write(line)

			elif line.startswith("<s"):		

				counter_token = 0
				counter_sentence = counter_sentence + 1

				newline = "<s " + "sentence_id=\'" + str(counter_sentence) + "\'>\n"
                                f_output.write(newline)

  			elif line.startswith("<token"):

                                counter_token = counter_token + 1

                                newline = "<token " + "token_id=\'" + str(counter_token) + "\'" + line.split('<token')[1]
                                f_output.write(newline)

			else:
				f_output.write(line)
	

add_annotations(sys.argv[1],sys.argv[2])



