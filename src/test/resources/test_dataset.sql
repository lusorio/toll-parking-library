CREATE TABLE parking_slot (
  id UUID PRIMARY KEY,
  type VARCHAR(250) NOT NULL,
  code VARCHAR(250) NOT NULL,
  available BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO parking_slot (id, type, code, available) VALUES
('1', 'COMBUSTION', 'C1', false),
('2', 'COMBUSTION', 'C2', true),
('3', 'COMBUSTION', 'C3', true),
('4', 'ELECTRIC_20KW', 'E201', false),
('5', 'ELECTRIC_20KW', 'E202', false),
('6', 'ELECTRIC_20KW', 'E203', true),
('7', 'ELECTRIC_50KW', 'E501', false),
('8', 'ELECTRIC_50KW', 'E502', false),
('9', 'ELECTRIC_50KW', 'E503', false);

CREATE TABLE parking_registry (
    id UUID PRIMARY KEY,
    license_plate_number VARCHAR(250) NOT NULL,
    slot_id UUID NOT NULL,
    slot_code VARCHAR(250) NOT NULL,
    datetime_in TIMESTAMP NOT NULL,
    datetime_out TIMESTAMP,
    total DOUBLE
);

INSERT INTO parking_registry (id, license_plate_number, slot_id, slot_code, datetime_in) VALUES
('1', 'AA-111-AA', '1', 'C1', CURRENT_TIMESTAMP),
('2', 'AA-222-AA', '4', 'E201', CURRENT_TIMESTAMP),
('3', 'AA-333-AA', '5', 'E202', CURRENT_TIMESTAMP),
('4', 'AA-444-AA', '7', 'E501', CURRENT_TIMESTAMP),
('5', 'AA-555-AA', '8', 'E502', CURRENT_TIMESTAMP);
