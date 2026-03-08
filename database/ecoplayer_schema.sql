Drop database ecoplayer;

CREATE DATABASE IF NOT EXISTS ecoplayer;
USE ecoplayer;

DROP TABLE IF EXISTS delivery;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS recycle_item;
DROP TABLE IF EXISTS materials;
DROP TABLE IF EXISTS customer;


CREATE TABLE customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    customer_contact VARCHAR(15) NOT NULL,
    customer_date DATE NOT NULL
);


CREATE TABLE materials (
    material_id INT PRIMARY KEY AUTO_INCREMENT,
    material_name VARCHAR(100) NOT NULL UNIQUE,
    price_per_kg DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE recycle_item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    material_id INT NOT NULL,
    item_kg DOUBLE NOT NULL,
    item_price DOUBLE NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (material_id) REFERENCES materials(material_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE product (
    product_id VARCHAR(20) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    material_id INT,
    kg_per_unit DOUBLE NOT NULL DEFAULT 0,
    selling_price DOUBLE NOT NULL DEFAULT 0,
    stock_quantity INT NOT NULL DEFAULT 0,
    FOREIGN KEY (material_id) REFERENCES materials(material_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(20) NOT NULL,
    customer_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    total_price DOUBLE NOT NULL DEFAULT 0,
    order_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    invoice VARCHAR(100),
    payment_value DOUBLE NOT NULL,
    payment_date DATE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE delivery (
    delivery_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    details_location VARCHAR(255) NOT NULL,
    delivery_date DATE,
    status VARCHAR(50) DEFAULT 'PENDING',
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO materials (material_name, price_per_kg) VALUES
('Plastic', 50.00),
('Paper', 30.00),
('Glass', 25.00),
('Metal', 150.00),
('Aluminum', 200.00),
('Copper', 800.00),
('Cardboard', 20.00),
('E-Waste', 100.00),
('Rubber', 40.00),
('Textile', 35.00);

INSERT INTO customer (customer_name, customer_contact, customer_date) VALUES
('John Doe', '0771234567', '2024-01-15'),
('Jane Smith', '0772345678', '2024-01-20'),
('Bob Wilson', '0773456789', '2024-02-01');

INSERT INTO recycle_item (material_id, item_kg, item_price, date) VALUES
(1, 100.0, 5000.00, '2024-01-10'),  -- Plastic
(2, 50.0, 1500.00, '2024-01-15'),   -- Paper
(3, 75.0, 1875.00, '2024-01-20'),   -- Glass
(4, 30.0, 4500.00, '2024-02-01');   -- Metal

INSERT INTO product (product_id, product_name, material_id, kg_per_unit, selling_price, stock_quantity) VALUES
('P001', 'Plastic Chair', 1, 2.0, 1500.00, 0),
('P002', 'Paper Bag', 2, 0.5, 50.00, 0),
('P003', 'Glass Bottle', 3, 0.3, 200.00, 0);

CREATE OR REPLACE VIEW material_stock AS
SELECT 
    m.material_id,
    m.material_name,
    m.price_per_kg,
    COALESCE(SUM(r.item_kg), 0) as total_kg
FROM materials m
LEFT JOIN recycle_item r ON m.material_id = r.material_id
GROUP BY m.material_id, m.material_name, m.price_per_kg;

SHOW TABLES;

