INSERT INTO car (id, brand, model, manufacture_year, category, transmission, price, is_available)
VALUES
    (1, 'TOYOTA', 'Camry', 2020, 'STANDARD', 'AUTO', 3000, 'true'),
    (2, 'BMW', '7 series', 2022, 'STANDARD', 'AUTO', 3300, 'true'),
    (3, 'LEXUS', 'LX 570', 2023, 'PREMIUM', 'AUTO', 7000, 'false'),
    (4, 'LEXUS', 'LS 350', 2023, 'PREMIUM', 'AUTO', 5000, 'true');
SELECT SETVAL('car_id_seq', (SELECT MAX(id) FROM car));