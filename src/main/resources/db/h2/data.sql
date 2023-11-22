INSERT INTO vets VALUES (default, 'James', 'Carter');
INSERT INTO vets VALUES (default, 'Helen', 'Leary');
INSERT INTO vets VALUES (default, 'Linda', 'Douglas');
INSERT INTO vets VALUES (default, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (default, 'Henry', 'Stevens');
INSERT INTO vets VALUES (default, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (default, 'radiology');
INSERT INTO specialties VALUES (default, 'surgery');
INSERT INTO specialties VALUES (default, 'dentistry');
INSERT INTO specialties VALUES (default, 'anesthesiology');
INSERT INTO specialties VALUES (default, 'allergy and immunology');
INSERT INTO specialties VALUES (default, 'pathology');
INSERT INTO specialties VALUES (default, 'neurology');
INSERT INTO specialties VALUES (default, 'plastic surgery');
INSERT INTO specialties VALUES (default, 'dermatology');
INSERT INTO specialties VALUES (default, 'radiology');

INSERT INTO specialties VALUES (default, 'emergency medicine');
INSERT INTO specialties VALUES (default, 'endocrinology, diabetes, and metabolism');
INSERT INTO specialties VALUES (default, 'family medicine/family practice');
INSERT INTO specialties VALUES (default, 'gastroenterology');
INSERT INTO specialties VALUES (default, 'geriatric medicine');
INSERT INTO specialties VALUES (default, 'gynecology');
INSERT INTO specialties VALUES (default, 'hematology');
INSERT INTO specialties VALUES (default, 'infectious disease');
INSERT INTO specialties VALUES (default, 'laboratory genetics and genomics');
INSERT INTO specialties VALUES (default, 'molecular genetic pathology');

INSERT INTO specialties VALUES (default, 'microsurgery');
INSERT INTO specialties VALUES (default, 'oncology');
INSERT INTO specialties VALUES (default, 'toxicology');
INSERT INTO specialties VALUES (default, 'movement disorders');
INSERT INTO specialties VALUES (default, 'neonatal-perinatal medicine');
INSERT INTO specialties VALUES (default, 'ophthalmology');
INSERT INTO specialties VALUES (default, 'nephrology');
INSERT INTO specialties VALUES (default, 'orthopaedic surgery');
INSERT INTO specialties VALUES (default, 'pediatrics');
INSERT INTO specialties VALUES (default, 'rhinology/nasal and sinus care');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (default, 'cat');
INSERT INTO types VALUES (default, 'dog');
INSERT INTO types VALUES (default, 'lizard');
INSERT INTO types VALUES (default, 'snake');
INSERT INTO types VALUES (default, 'bird');
INSERT INTO types VALUES (default, 'hamster');

INSERT INTO owners VALUES (default, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (default, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (default, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (default, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (default, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (default, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (default, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (default, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (default, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (default, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (default, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets VALUES (default, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets VALUES (default, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets VALUES (default, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets VALUES (default, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets VALUES (default, 'George', '2010-01-20', 4, 5);
INSERT INTO pets VALUES (default, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (default, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (default, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets VALUES (default, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets VALUES (default, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets VALUES (default, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets VALUES (default, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits VALUES (default, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits VALUES (default, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits VALUES (default, 8, '2013-01-03', 'neutered');
INSERT INTO visits VALUES (default, 7, '2013-01-04', 'spayed');

insert into medicine values (default, 'Aspirin', true);
insert into medicine values (default, 'Maxigan', true);
insert into medicine values (default, 'Prozak', false);
insert into medicine values (default, 'Tylenol', true);
insert into medicine values (default, 'Gabapentin', true);
insert into medicine values (default, 'Neosporin', false);
insert into medicine values (default, 'Xylitol', false);
