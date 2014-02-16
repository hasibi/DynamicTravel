DynamicTravel: Adaptable GF-based Question-Answering system
=============


This program is a multi-lingual QA system, which is designed for public transportation domain. The system uses Grammatical Framework (GF) grammars to communicate with users in natural language. In order to present up-to-date travel information, GF grammars are updated automatically and during system execution. Current system is connected to Gothenburg transportation API and supports both English and Swedish. Here is some examples of communication using this system:

─	User: I want to go from Chalmers to Valand today at 11:30

─	System: Take tram number 7 from Chalmers track A to Valand track A at 11:31

A user can also record some commands and use them in later conversion:

─	User: work means Chalmers on Monday at 7:30
─	User: home means Valand
─	User: Jag vill åka från hem till jobbet 
(I want to go from home to work)

─	System: Ta spårvagn nummer 10 från Valand läge B till Chalmers  kl 07:33
(Take tram number 10 from Valand track B to Chalmers at 07:33)

More description about this work can be found at:
http://link.springer.com/chapter/10.1007%2F978-3-642-32612-7_7

More information about Grammatical Framework(GF) can be found at:
http://www.grammaticalframework.org/
