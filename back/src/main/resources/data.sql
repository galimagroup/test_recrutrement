-- Suppression des données existantes
DELETE FROM cart_items;
DELETE FROM cart;
DELETE FROM users;
DELETE FROM product;

-- Insertion des produits
INSERT INTO product (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at)
VALUES 
('PROD-001', 'Smartphone XYZ', 'Un smartphone puissant avec écran 6.5"', 'https://picsum.photos/200/300', 'Électronique', 599.99, 50, 'REF001', 1, 'INSTOCK', 4, 1648646400000, 1648646400000),
('PROD-002', 'Laptop Pro', 'Ordinateur portable pour professionnels', 'https://picsum.photos/200/300', 'Électronique', 1299.99, 5, 'REF002', 2, 'LOWSTOCK', 5, 1648646400000, 1648646400000),
('PROD-003', 'Casque Audio', 'Casque sans fil avec réduction de bruit', 'https://picsum.photos/200/300', 'Accessoires', 199.99, 0, 'REF003', 3, 'OUTOFSTOCK', 4, 1648646400000, 1648646400000),
('PROD-004', 'Tablette 10"', 'Tablette légère et performante', 'https://picsum.photos/200/300', 'Électronique', 399.99, 25, 'REF004', 4, 'INSTOCK', 3, 1648646400000, 1648646400000),
('PROD-005', 'Montre Connectée', 'Montre intelligente avec suivi santé', 'https://picsum.photos/200/300', 'Accessoires', 299.99, 15, 'REF005', 5, 'INSTOCK', 4, 1648646400000, 1648646400000);

-- Création d'un utilisateur admin
INSERT INTO users (email, password, username, firstname)
VALUES ('admin@admin.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin', 'Admin');  -- Le mot de passe est 'password'
