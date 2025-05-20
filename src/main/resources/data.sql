-- Insert test tables if they don't exist
INSERT INTO mesa (numero, lugares, status)
SELECT 1, 4, 'DISPONIVEL'
WHERE NOT EXISTS (SELECT 1 FROM mesa WHERE numero = 1);

INSERT INTO mesa (numero, lugares, status)
SELECT 2, 6, 'DISPONIVEL'
WHERE NOT EXISTS (SELECT 1 FROM mesa WHERE numero = 2);

INSERT INTO mesa (numero, lugares, status)
SELECT 3, 2, 'DISPONIVEL'
WHERE NOT EXISTS (SELECT 1 FROM mesa WHERE numero = 3);

INSERT INTO mesa (numero, lugares, status)
SELECT 4, 8, 'DISPONIVEL'
WHERE NOT EXISTS (SELECT 1 FROM mesa WHERE numero = 4);

INSERT INTO mesa (numero, lugares, status)
SELECT 5, 4, 'DISPONIVEL'
WHERE NOT EXISTS (SELECT 1 FROM mesa WHERE numero = 5); 