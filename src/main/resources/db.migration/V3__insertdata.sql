USE db_projet;

INSERT INTO user (lastname, firstname, phone_number, email, password, created_at, admin)
VALUES
    ('Doe', 'John', '0123456789', 'john.doe@example.com', '$argon2id$v=19$m=60000,t=10,p=1$iqY7Hr8gLoA0w7UCDl19Pg$CpwZq1jEVpVGsT+HAt+1Ie22rhKSGCuf5wxplS1etAY', '2024-01-01', 0),
    ('Smith', 'Jane', '0987654321', 'jane.smith@example.com', '$argon2id$v=19$m=60000,t=10,p=1$qwMILStl/uXF2E70mv5gOQ$Y4puFZgr4fkrseJZEf3YbSbipK4MZjbxD8tp9ZnGzuU', '2024-01-02', 1);

INSERT INTO review (rating, comment, user_id, destination_id)
VALUES
    (5, 'Magnifique expérience, je recommande vivement cette destination !', 1, 1),
    (3, 'L’endroit était correct, mais le service pourrait être amélioré.', 1, 2);

INSERT INTO offer (percentage_discount, destination_id)
VALUES
    (15.5, 1),
    (25.0, 2);