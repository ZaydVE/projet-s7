USE db_projet;

INSERT INTO user (lastname, firstname, phone_number, email, password, created_at, admin)
VALUES
    ('Doe', 'John', '0123456789', 'john.doe@example.com', 'password123', '2024-01-01', 0),
    ('Smith', 'Jane', '0987654321', 'jane.smith@example.com', 'securepass456', '2024-01-02', 1);

INSERT INTO review (rating, comment, user_id, destination_id)
VALUES
    (5, 'Magnifique expérience, je recommande vivement cette destination !', 1, 1),
    (3, 'L’endroit était correct, mais le service pourrait être amélioré.', 1, 2);

INSERT INTO offer (percentage_discount, destination_id)
VALUES
    (15.5, 1),
    (25.0, 2);