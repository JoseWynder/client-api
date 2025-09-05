insert into clients (name, cpf, birth_date, email, phone, created_at, updated_at) values
('José Wynder', '12345678901', '2005-11-09', 'jose@neoapp.com', '43999999999', now(), now()),
('Marieli', '23456789012', '2000-12-20', 'marieli@neoapp.com', '28999999999', now(), now());

insert into users (username, password, is_admin) values
('NeoApp', '$2a$10$dMMtkGloCWkLFBM6a3s8U.01Ck8c52psZTlC6fzT5/iGQOeuheRMK', false);
