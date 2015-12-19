#-*- coding: UTF-8 -*-
"""This program generates monthly RSS text files for each country. It takes two arguments:
- the root directory of each rss feeds (for example from /home/userrss/datarss/TXT => France)
- the country/language (ie france, etc. it will be used to prefix the files)
It generates txt files for each month (example : france_2015-06.txt) on the same directory
where is this program)
To be done : check if the program has already generated some files before loading so as to generate 
and parse only new files every month!
""" 
import glob, os.path, sys, re
from datetime import datetime

#  on liste les fichiers dans un repertoire donne (recherche egalement sous-rep)
liste_files = glob.glob(sys.argv[1] + '/*/*')
country = sys.argv[2]
print len(liste_files)
corpus={} ## structure d 'accueil des corpus par mois
# corpus[year-date]=[liste des fichiers, taille totale]

# on parcoure chacun des fichiers et on affiche les metadonnees
for f in liste_files:
	sizef= int(os.path.getsize(f)) # taille du fichier
	mtime = os.path.getmtime(f) # date creation du fichier
	timestp = str(datetime.fromtimestamp(mtime)) # date plus lisible
	yearmonth = timestp.split("-") # on recupere annee + mois qui sert de cle
	try: # on recupere la valeur - liste si la cle existe
		listdata = corpus[yearmonth[0]+ "-" +yearmonth[1]]
		listfiles = listdata[0]
		size=listdata[1]
	except KeyError: # pas de cle : on initialise la valeur liste
		listfiles = []
		size = 0
		listdata = [listfiles,size]
	listfiles.append(f) # on ajoute le fichier a la liste
	size = size + sizef # on additionne la taille du fichier courant a la taille totale des fichiers de cette liste
	corpus[yearmonth[0]+ "-" +yearmonth[1]]=[listfiles, size]


# ecriture des resultats + creation des fichiers par mois
for key in corpus:
	print key + ":"
	data = corpus[key]
	print int(data[1]) # taille du corpus (en ko)
	print len(data[0])# nbre de fichiers
	# on ouvre le fichier en mode 'a' comme append pour ajouter les differents fichiers
	with open(country + "_" + key + ".txt", "a") as sortie:
		for f in data[0]:# on parcoure les differents fichiers, on les lit et on ecrit dans sortie
			with open(f,"r") as input:
				contents = input.read()
        		sortie.write(contents)
	
		

