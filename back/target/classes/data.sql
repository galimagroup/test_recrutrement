-- Suppression des données existantes
DELETE FROM cart_items;
DELETE FROM cart;
DELETE FROM users;
DELETE FROM product;

-- Insertion des produits
INSERT INTO product (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at)
VALUES 
('PROD-012', 'Café en grains', 'Café arabica torréfié', '/assets/cafe.jpg', 'Alimentation', 14.50, 50, 'CAFEGRAIN', 8, 'INSTOCK', 5, 1648646400000, 1648646400000),
('PROD-013', 'Chaise Scandinave', 'Chaise design en bois', '/assets/chaise.png', 'Meubles', 79.99, 20, 'CHAISSCANDI', 9, 'INSTOCK', 5, 1648646400000, 1648646400000),
('PROD-014', 'Sac à dos', 'Sac à dos urbain et pratique', '/assets/sac.jpg', 'Accessoires', 39.99, 30, 'SACURBAIN', 3, 'INSTOCK', 5, 1648646400000, 1648646400000),
('PROD-015', 'Téléviseur Sharp', 'TV LED 43 pouces Sharp', '/assets/sharp-televiseur.jpg', 'Électronique', 299.99, 12, 'SHARP43LED', 10, 'INSTOCK', 5, 1648646400000, 1648646400000),
('PROD-016', 'Veste en jean', 'Veste tendance pour homme', '/assets/veste.jpg', 'Vêtements', 59.99, 25, 'VESTEJEAN', 6, 'INSTOCK', 5, 1648646400000, 1648646400000);

-- Création d'un utilisateur admin
INSERT INTO users (email, password, username, firstname)
VALUES ('admin@admin.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'admin', 'Admin');  -- Le mot de passe est 'password'
