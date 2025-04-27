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


CREATE TABLE store_entrances (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    courier_id VARCHAR(255) NOT NULL,
    store_id UUID NOT NULL,
    distance_meters DOUBLE PRECISION NOT NULL,
    entrance_time TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_store_entrance_store FOREIGN KEY (store_id) REFERENCES stores(id)
);

CREATE INDEX courier_locations_courier_id_idx ON courier_locations(courier_id);
CREATE INDEX store_entrances_courier_id_idx ON store_entrances(courier_id);
CREATE INDEX store_entrances_store_id_idx ON store_entrances(store_id);
CREATE INDEX store_entrances_time_idx ON store_entrances(entrance_time);