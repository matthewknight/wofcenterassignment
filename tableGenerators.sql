DROP TABLE IF EXISTS Vehicle;
DROP TABLE IF EXISTS Owner;

CREATE TABLE Owner
(
  firstName TEXT NOT NULL,
  lastName  TEXT NOT NULL,
  email TEXT PRIMARY KEY,
  password  TEXT NOT NULL
);

CREATE TABLE Vehicle (
  plate VARCHAR(8) PRIMARY KEY,
  make TEXT,
  model TEXT,
  fuelType TEXT,
  odometer INTEGER,
  manufactureYear INTEGER,
  ownerEmail TEXT,
  FOREIGN KEY (ownerEmail) REFERENCES Owner(email)
);