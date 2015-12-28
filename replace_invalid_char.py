# -*- coding: utf-8 -*-

# Use also this command line ---> perl -pi -e 's/[^\x9\xA\xD\x20-\x{d7ff}\x{e000}-\x{fffd}\x{10000}-\x{10ffff}]//g' current_file.xml
# it replaces invalid characters of XML files.

import sys
import string
import re

def replace_invalid_char(filename):

	f_output = open(filename + 'previous.xml','w')	
	with open(filename,"r") as f_input:
		for line in f_input:
			if line.startswith('<token'):	
				str1 = line.split('\"')

				if len(str1) == 5:	
					str2 = str1[4].split('</token>\n')				
					str3 = str2[0][1:]
					str3 = str3.replace('<','o')
					str3 = str3.replace('>','o')
					#str3 = str3.replace('\xef','o')
					f_output.write(str1[0] + "\"" + str1[1] + "\"" + " " + str1[2] + "\"" + str1[3] + "\"" + ">" + str3 + "</token>\n")

				elif len(str1) == 3:
					str2 = str1[2].split('</token>\n')
					str3 = str2[0][1:]
					str3 = str3.replace('<','o')
					str3 = str3.replace('>','o')
                                        #str3 = str3.replace('\xef','o')
                                        f_output.write(str1[0] + "\"" + str1[1] + "\"" + ">" + str3 + "</token>\n")
			else:

				f_output.write(line)	


	f_output.close()
	f_input.close()

replace_invalid_char(sys.argv[1])
