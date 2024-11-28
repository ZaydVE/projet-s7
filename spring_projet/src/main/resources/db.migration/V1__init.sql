CREATE DATABASE IF NOT EXISTS db_projet;
USE db_projet;

DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS destination;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS offer;

CREATE TABLE IF NOT EXISTS user (
                                    user_id INT AUTO_INCREMENT PRIMARY KEY,
                                    lastname VARCHAR(45) NOT NULL,
                                    firstname VARCHAR(45) NOT NULL,
                                    phone_number VARCHAR(15) NOT NULL,
                                    email VARCHAR(45) NOT NULL,
                                    password VARCHAR(45) NOT NULL,
                                    created_at DATE NOT NULL,
                                    admin BIT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS destination (
                                           destination_id INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(45) NOT NULL,
                                           description VARCHAR(1000) NOT NULL,
                                           price FLOAT NOT NULL,
                                           continent VARCHAR(45) NOT NULL,
                                           country VARCHAR(45) NOT NULL,
                                           city VARCHAR(45) NOT NULL,
                                           type VARCHAR(45) NOT NULL,
                                           start_date DATE NOT NULL,
                                           end_date DATE NOT NULL,
                                           lien_image VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS booking (
                                       booking_id INT AUTO_INCREMENT PRIMARY KEY,
                                       booking_date DATE NOT NULL,
                                       nb_passengers INT NOT NULL,
                                       total_price FLOAT NOT NULL,
                                       user_id INT NOT NULL,
                                       destination_id INT NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES user(user_id),
                                       FOREIGN KEY (destination_id) REFERENCES destination(destination_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS review (
                                      review_id INT AUTO_INCREMENT PRIMARY KEY,
                                      rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                                      comment VARCHAR(1000) NOT NULL,
                                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      user_id INT NOT NULL,
                                      destination_id INT NOT NULL,
                                      FOREIGN KEY (user_id) REFERENCES user(user_id),
                                      FOREIGN KEY (destination_id) REFERENCES destination(destination_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS offer (
                                     offer_id INT AUTO_INCREMENT PRIMARY KEY,
                                     percentage_discount FLOAT NOT NULL,
                                     destination_id INT NOT NULL,
                                     FOREIGN KEY (destination_id) REFERENCES destination(destination_id)
) ENGINE=InnoDB;