PRAGMA foreign_keys = ON;

CREATE TABLE role (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role_id INTEGER NOT NULL,
    calendar_sync_enabled BOOLEAN DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE company (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL UNIQUE,
    name TEXT NOT NULL,
    cif TEXT NOT NULL UNIQUE,
    api_key TEXT UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE parking (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    company_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    opening_time TEXT,
    closing_time TEXT,
    FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE parking_spot (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    parking_id INTEGER NOT NULL,
    spot_number TEXT NOT NULL,
    is_available BOOLEAN DEFAULT 1,
    FOREIGN KEY (parking_id) REFERENCES parking(id)
);

CREATE TABLE dynamic_price (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    parking_id INTEGER NOT NULL,
    min_price REAL NOT NULL,
    max_price REAL NOT NULL,
    day_of_week INTEGER,
    start_hour TEXT NOT NULL,
    end_hour TEXT NOT NULL,
    FOREIGN KEY (parking_id) REFERENCES parking(id)
);

CREATE TABLE reservation (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    parking_spot_id INTEGER NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    price REAL NOT NULL,
    status TEXT DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE','COMPLETED','CANCELLED','NO_SHOW')),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spot(id)
);

CREATE TABLE sanction (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    reservation_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    amount REAL NOT NULL,
    reason TEXT NOT NULL,
    paid BOOLEAN DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservation(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_company_email ON company(email);
CREATE INDEX idx_reservation_user ON reservation(user_id);
CREATE INDEX idx_reservation_spot ON reservation(parking_spot_id);
CREATE INDEX idx_parking_company ON parking(company_id);