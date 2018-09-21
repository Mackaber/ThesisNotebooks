This README file contains the instructions for using the AMICorpus Social Roles.
If you use the data, please cite the following article:

Sapru, A.; Bourlard, H., "Automatic Recognition of Emergent Social Roles in Small Group Interactions," Multimedia, IEEE Transactions on , vol.17, no.5, pp.746,760, May 2015



The Corpus includes the following material:

folders:scenario/nonscenario
Content: Social roles of meeting participants  (groundtruth)
Format: csv


-----

Naming conventions:

The files are named in the following way:

meetid.csv

where

meetid: Meeting ID in the AMI Corpus (For example, social roles in meeting 'IS1004b'  recorded at Idiap  are given in file  'IS1004b.csv')


-----

Format of meetid.csv file:

Column 1: Meeting ID
Column 2: Clip ID (For example, Clip ID 'IS1004b_181_212' corresponds to a windowed slice of meeting which starts at 181 seconds (181 seconds elapsed since the begining of the meeting) and ends at 212 seconds
Column 3: Start time of the clip (in seconds) 
Column 4: End time of the clip (in seconds) 
Column 5: Participant ID (In each AMI meeting, participants are indexed using labels such as, 'A','B','C','D')
Column 6: Social Role label of a participant 


Note: Each participant has a unique social role for the duration of clip, for example in meeting 'IS1004b', for clip 'IS1004b_181_212' and participant 'A', the social role of 'A' from 181 seconds to 212 seconds is Gatekeeper(Ga).

