INSERT INTO parking_slot (id, type, code, available)
VALUES ('1', 'COMBUSTION', 'C1', false),
    ('2', 'COMBUSTION', 'C2', true),
    ('3', 'COMBUSTION', 'C3', true),
    ('4', 'ELECTRIC_20KW', 'E201', true),
    ('5', 'ELECTRIC_20KW', 'E202', true),
    ('6', 'ELECTRIC_20KW', 'E203', true),
    ('7', 'ELECTRIC_50KW', 'E501', true),
    ('8', 'ELECTRIC_50KW', 'E502', true),
    ('9', 'ELECTRIC_50KW', 'E503', true);

INSERT INTO parking_registry (id, license_plate_number, slot_id, datetime_in)
VALUES ('99', 'AA-111-AA', '1', CURRENT_TIMESTAMP);