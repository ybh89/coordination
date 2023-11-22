INSERT INTO brand (id, name) VALUES
(1, 'A'),
(2, 'B'),
(3, 'C'),
(4, 'D'),
(5, 'E'),
(6, 'F'),
(7, 'G'),
(8, 'H'),
(9, 'I')
;

INSERT INTO product (id, brand_id, category, price) VALUES
(1, 1, 'TOP', 11200),
(2, 1, 'OUTWEAR', 5500),
(3, 1, 'PANTS', 4200),
(4, 1, 'SNEAKERS', 9000),
(5, 1, 'BAG', 2000),
(6, 1, 'HAT', 1700),
(7, 1, 'SOCKS', 1800),
(8, 1, 'ACCESSORIES', 2300),
(9, 2, 'TOP', 10500),
(10, 2, 'OUTWEAR', 5900),
(11, 2, 'PANTS', 3800),
(12, 2, 'SNEAKERS', 9100),
(13, 2, 'BAG', 2100),
(14, 2, 'HAT', 2000),
(15, 2, 'SOCKS', 2000),
(16, 2, 'ACCESSORIES', 2200),
(17, 3, 'TOP', 10000),
(18, 3, 'OUTWEAR', 6200),
(19, 3, 'PANTS', 3300),
(20, 3, 'SNEAKERS', 9200),
(21, 3, 'BAG', 2200),
(22, 3, 'HAT', 1900),
(23, 3, 'SOCKS', 2200),
(24, 3, 'ACCESSORIES', 2100),
(25, 4, 'TOP', 10100),
(26, 4, 'OUTWEAR', 5100),
(27, 4, 'PANTS', 3000),
(28, 4, 'SNEAKERS', 9500),
(29, 4, 'BAG', 2500),
(30, 4, 'HAT', 1500),
(31, 4, 'SOCKS', 2400),
(32, 4, 'ACCESSORIES', 2000),
(33, 5, 'TOP', 10700),
(34, 5, 'OUTWEAR', 5000),
(35, 5, 'PANTS', 3800),
(36, 5, 'SNEAKERS', 9900),
(37, 5, 'BAG', 2300),
(38, 5, 'HAT', 1800),
(39, 5, 'SOCKS', 2100),
(40, 5, 'ACCESSORIES', 2100),
(41, 6, 'TOP', 11200),
(42, 6, 'OUTWEAR', 7200),
(43, 6, 'PANTS', 4000),
(44, 6, 'SNEAKERS', 9300),
(45, 6, 'BAG', 2100),
(46, 6, 'HAT', 1600),
(47, 6, 'SOCKS', 2300),
(48, 6, 'ACCESSORIES', 1900),
(49, 7, 'TOP', 10500),
(50, 7, 'OUTWEAR', 5800),
(51, 7, 'PANTS', 3900),
(52, 7, 'SNEAKERS', 9000),
(53, 7, 'BAG', 2200),
(54, 7, 'HAT', 1700),
(55, 7, 'SOCKS', 2100),
(56, 7, 'ACCESSORIES', 2000),
(57, 8, 'TOP', 10800),
(58, 8, 'OUTWEAR', 6300),
(59, 8, 'PANTS', 3100),
(60, 8, 'SNEAKERS', 9700),
(61, 8, 'BAG', 2100),
(62, 8, 'HAT', 1600),
(63, 8, 'SOCKS', 2000),
(64, 8, 'ACCESSORIES', 2000),
(65, 9, 'TOP', 11400),
(66, 9, 'OUTWEAR', 6700),
(67, 9, 'PANTS', 3200),
(68, 9, 'SNEAKERS', 9500),
(69, 9, 'BAG', 2400),
(70, 9, 'HAT', 1700),
(71, 9, 'SOCKS', 1700),
(72, 9, 'ACCESSORIES', 2400)
;

INSERT INTO lowest_price_product_by_category (product_id, category, brand_name, price, version)
SELECT p.id, p.category, b.name, p.price, 0
FROM
    product p
INNER JOIN brand b ON p.brand_id = b.id
INNER JOIN (
    SELECT
        category,
        MAX(id) AS max_id
    FROM
        product
    WHERE
        (category, price) IN (
            SELECT
                category,
                MIN(price)
            FROM
                product
            GROUP BY
                category
        )
    GROUP BY
        category
) AS min_price_max_id ON p.id = min_price_max_id.max_id;

