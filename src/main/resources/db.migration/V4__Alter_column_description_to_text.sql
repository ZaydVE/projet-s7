-- Migration V4 : Changer le type de la colonne description en TEXT
ALTER TABLE destination MODIFY COLUMN description TEXT;