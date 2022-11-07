insert into Gpu (
    id,
    model,
    cost,
    manufacture,
    graphicProcessor,
    memoryCapacity,
    manufactureOfProcessor,
    typeOfMemory,
    connectionInterface
)

values
    (1, 'PowerColor AMD Radeon R7 240', 3799, 'PowerColor', 'Radeon R7 240', 2, 'AMD', 'GDDR5', 'PCI-E 3.0'),
    (2, 'KFA2 GeForce GT 710 PASSIVE', 4099, 'KFA2', 'GeForce GT 710', 1, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (3, 'Palit GeForce GT 710 Silent LP', 4399, 'Palit', 'GeForce GT 710', 2, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (4, 'KFA2 GeForce GT 730', 4699, 'KFA2', 'GeForce GT 730', 2, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (5, 'Palit GeForce GT 730 Silent LP', 4699, 'Palit', 'GeForce GT 730', 2, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (6, 'GIGABYTE GeForce GT 710 LP v2.0', 4999, 'GIGABYTE', 'GeForce GT 710', 2, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (7, 'GIGABYTE GeForce GT 710 Silent LP ', 4999, 'GIGABYTE', 'GeForce GT 710', 2, 'GeForce', 'GDDR5', 'PCI-E 2.0'),
    (8, 'MSI GeForce GT 730 Silent LP', 5199, 'MSI', 'GeForce GT 730', 2, 'GeForce', 'GDDR3', 'PCI-E 2.0'),
    (9, 'GIGABYTE GeForce GT 710 LP', 5499, 'GIGABYTE', 'GeForce GT 710', 2, 'GeForce', 'GDDR5', 'PCI-E 2.0') ON CONFLICT DO NOTHING;

insert into Cpu (
    id,
    model,
    cost,
    manufacture,
    socket,
    family,
    numberOfCores,
    year,
    typeOfMemory,
    frequency,
    technicalProcess
)

values
    (1, 'AMD PRO A6-8580', 1599, 'AMD', 'AM4', 'AMD PRO A6', 2, 2021, 'DDR4', 3.8, 28),
    (2, 'AMD A6-9500E', 1650, 'AMD', 'AM4', 'AMD A6', 2, 2016, 'DDR4', 3, 28),
    (3, 'AMD FX-4300', 1650, 'AMD', 'AM3+', 'AMD FX', 4, 2012, 'DDR3', 3.8, 32),
    (4, 'AMD A6-9400 OEM', 1799, 'AMD', 'AM4', 'AMD A6', 2, 2019, 'DDR4', 3.4, 28),
    (5, 'AMD A6-9500 OEM', 1999, 'AMD', 'AM4', 'AMD A6', 2, 2016, 'DDR4', 3.5, 28),
    (6, 'AMD PRO A6-8570', 2299, 'AMD', 'AM4', 'AMD PRO A6', 2, 2018, 'DDR4', 3.8, 28) ON CONFLICT DO NOTHING;

insert into Ram (
    id,
    model,
    cost,
    manufacture,
    typeOfMemory,
    memoryCapacity,
    frequency,
    numberOfModules
)

values
    (1, 'Hynix HY5PS1G831BFP-S6C', 599, 'Hynix', 'DDR2', 1, 800, 1),
    (2, 'AMD Radeon R5 Entertainment Series', 750, 'AMD Radeon', 'DDR3', 2, 1600, 1),
    (3, 'AMD Radeon R3 Value Series', 899, 'AMD Radeon', 'DDR3', 4, 1333, 1),
    (4, 'AMD Radeon R5 Entertainment Series', 899, 'AMD Radeon', 'DDR3', 4, 1600, 1),
    (5, 'AMD Radeon R7 Performance Series', 899, 'AMD Radeon', 'DDR4', 4, 2133, 1),
    (6, 'AMD Radeon R5 Entertainment Series', 950, 'AMD Radeon', 'DDR3L', 4, 1600, 1),
    (7, 'AMD Radeon R7 Performance Series', 950, 'AMD Radeon', 'DDR4', 4, 2666, 1),
    (8, 'AMD Radeon R9 Gamer Series', 950, 'AMD Radeon', 'DDR4', 4, 3200, 1),
    (9, 'Crucial', 950, 'Crucial', 'DDR3L', 2, 1600, 1),
    (10, 'QUMO', 950, 'QUMO', 'DDR2', 4, 2400, 1),
    (11, 'AMD Radeon R3 Value Series', 999, 'AMD Radeon', 'DDR2', 2, 800, 1),
    (12, 'AMD Radeon R5 Entertainment Series', 999, 'AMD Radeon', 'DDR3', 4, 1600, 1),
    (13, 'AMD Radeon R9 Gamer Series', 999, 'AMD Radeon', 'DDR4', 4, 3200, 1),
    (14, 'Silicon Power', 999, 'Silicon Power', 'DDR3L', 4, 1600, 1),
    (15, 'AFOX', 1099, 'AFOX', 'DDR4', 4, 2666, 1),
    (16, 'Apacer', 1099, 'Apacer', 'DDR4', 4, 2666, 1),
    (17, 'KingSpec', 1099, 'KingSpec', 'DDR4', 4, 2666, 1),
    (18, 'KingSpec', 1099, 'KingSpec', 'DDR4', 4, 2666, 1),
    (19, 'KingSpec', 1099, 'KingSpec', 'DDR4', 4, 3200, 1),
    (20, 'QUMO', 1099, 'QUMO', 'DDR3', 4, 1600, 1),
    (21, 'Patriot Signature Line', 1150, 'Patriot', 'DDR4', 4, 2666, 1),
    (22, 'Apacer', 1199, 'Apacer', 'DDR3', 4, 1600, 1),
    (23, 'Goodram', 1199, 'Goodram', 'DDR3', 4, 1333, 1),
    (24, 'OCPC V-SERIES', 1199, 'OCPC', 'DDR4', 4, 2666, 1),
    (25, 'Patriot Signature Line', 1199, 'Patriot', 'DDR3', 4, 1333, 1) ON CONFLICT DO NOTHING;