DROP DATABASE biblioteca;
CREATE DATABASE biblioteca;

Use biblioteca;
UPDATE usuario
SET rol = 'ADMIN'
WHERE email = 'Admin@admin.com';
