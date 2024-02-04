INSERT INTO category (category_id, name)
SELECT 1, 'default'
    WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE category_id = 1
);
