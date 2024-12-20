-- Migration V4 : Changer le type de la colonne description en TEXT
USE db_projet;

ALTER TABLE destination MODIFY COLUMN description TEXT;