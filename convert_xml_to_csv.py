# -*- coding: utf-8 -*-
#Converts XML files to CSV files. (for Apache Solr)

import sys
import csv

def create_csv_files (file_input,file_output):

        csvfile = open(file_output,'wt')
	fieldnames = ('id_corpus','id_text','newspaper','DD-MM-YYYY','id_sentence', 'id_token','token','pos','lemma')
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()

        with open(file_input,'r') as xmlfile:
                for line in xmlfile:
                        if line.startswith('<text'):

                                attr_text = line.split(' ')
                                id_text = attr_text[1].split('=')[1].split('\'')[1]
                                newspaper = attr_text[2].split('=')[1].split('\'')[1]
                                year = attr_text[3].split('=')[1].split('\'')[1]
                                month = attr_text[4].split('=')[1].split('\'')[1]
                                day = attr_text[5].split('=')[1].split('\'')[1]
			
			elif line.startswith('<corpus'): 

				attr_corpus = line.split(' ')
				id_corpus = attr_corpus[1].split('=')[1].split('\'')[1]

			elif line.startswith('<s'):

				attr_sentence = line.split(' ')
				id_sentence = attr_sentence[1].split('=')[1].split('\'')[1]

                        elif line.startswith('<token'):

                                attr_token = line.split(' ')
                                id_token = attr_token[1].split('=')[1].split('\'')[1]

                                a = attr_token[2].split('=')[1].split('\"')[1]
				a = a.replace('\'','_quotes_')
				a = a.replace('\"','_quotes_')
				a = a.replace(',','_comma_')

				if id_corpus == 'pl' or id_corpus == 'ru' or id_corpus == 'fr':
					b = attr_token[4].split('lemma=')[1].split('\"')[1]
					token = attr_token[4].split('lemma=')[1].split('\"')[2][1:].split('</token>')[0]

				elif id_corpus == 'tch':
					b = attr_token[4].split('pos=')[1].split('\"')[1]
                                        token = attr_token[4].split('pos=')[1].split('\"')[2][1:].split('</token>')[0]

				elif id_corpus == 'gr':
             				b = attr_token[3].split('lemma=')[1].split('\"')[1]
                                        token = attr_token[3].split('lemma=')[1].split('\"')[2][1:].split('</token>')[0]
				elif id_corpus == 'ch':
					token = attr_token[2].split('=')[1].split('\"')[2][1:].split('</token>')[0]			
	
				if id_corpus == 'tch':
					a = a.replace('\'','_quotes_')
                                        a = a.replace('\"','_quotes_')
                                        a = a.replace(',','_comma_')

				elif id_corpus == 'pl' or id_corpus == 'ru' or id_corpus == 'fr' or id_corpus == 'gr':
 					b = b.replace('\'','_quotes_')
                                	b = b.replace('\"','_quotes_')
                                	b = b.replace(',','_comma_')

				token = token.replace('\'','_quotes_')
                                token = token.replace('\"','_quotes_')
                                token = token.replace(',','_comma_')
				
				if id_corpus == 'tch':
					writer.writerow({'id_corpus': id_corpus, 'id_text': id_text, 'newspaper': newspaper,'DD-MM-YYYY': day + "-" + month + "-" + year,'id_sentence': id_sentence, 'id_token': id_token, 'token': token,'pos': b, 'lemma': a})
			
				elif id_corpus == 'ch':
					writer.writerow({'id_corpus': id_corpus, 'id_text': id_text, 'newspaper': newspaper,'DD-MM-YYYY': day + "-" + month + "-" + year,'id_sentence': id_sentence, 'id_token': id_token, 'token': token,'pos': a, 'lemma': 'null'})

				else:
					writer.writerow({'id_corpus': id_corpus, 'id_text': id_text, 'newspaper': newspaper,'DD-MM-YYYY': day + "-" + month + "-" + year,'id_sentence': id_sentence, 'id_token': id_token, 'token': token,'pos': a, 'lemma': b})			

			
        csvfile.close()
	xmlfile.close()

create_csv_files(sys.argv[1],sys.argv[2])


