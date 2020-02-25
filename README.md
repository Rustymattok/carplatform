# Travis Build 
# Car - platform

This project for ORM research.
it includes 3 modules:
# Parsing Auto Ru
This module responsible for:
 - connection to www.Auto.ru by class ParsingDataBase and parsing it for the data:
 type of body vehicle
 type of brand vehicle
 type of model vehicle
- creating sql files for migrate data base by(liquibase)
- parsing www.Auto.ru by class ParsingMetaCar for last items in published. As a result - json file.
Finally this module allow to create base DATA for work with application
To Do list:
- link for image two types: cloud yandex and cloud auto.ru. Update links for cloud auto.ru
- check for exists files (all method with save data to file)
Problem not to solve:
- ParsingMetaCat doesn't work correct. Need realize filter for parsing meta.Meta for last items sometimes has
difference with the name of body,model.For current this was solved not including meta to items last list if
no equales to reference for DATA. Not target to edite it.
# Items Trade
This module not important for work common application.
This module responsible for:
- this is for sample to fill data for last items sales of car
- connect to DATA and fill it with data from json files.
To Do list:
 - in process
# Car Platform
Main module for application.
This module responsible for:
 - load web site and logic for activties
To Do list:
- web front end
- login page
- ORM connection to Data and synchronize with WEB
- connect to https://cloudinary.com/  for keep remote data of images by link in main data
under process
# How to use
Before use main module. You should inject First module(it responsoble for form Data)
```bash
liquibase:migrate
```
# Targets
Education tasks for Java Hibernate.
# Stack of technologies
Java EE 8, PostgreSQL 9.1, JSOUP, Hibernate, GSON, JavaScript , BrootStap