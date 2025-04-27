CREATE TABLE stores (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    store_type VARCHAR(32) NOT NULL
);

CREATE TABLE courier_locations (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    courier_id VARCHAR(255) NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    timestamp TIMESTAMP(6) NOT NULL,
    is_store_entry BOOLEAN NOT NULL
);

CREATE INDEX courier_locations_courier_id_idx ON courier_locations(courier_id);
