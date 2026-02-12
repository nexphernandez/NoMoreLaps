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
    enabled BOOLEAN NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_role (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE company (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL UNIQUE,
    name TEXT NOT NULL,
    cif TEXT NOT NULL UNIQUE,
    corporate_email TEXT UNIQUE,
    phone TEXT,
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
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE
);

CREATE TABLE parking_spot (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    parking_id INTEGER NOT NULL,
    spot_number TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parking_id) REFERENCES parking(id) ON DELETE CASCADE,
    UNIQUE(parking_id, spot_number)
);

CREATE TABLE dynamic_price (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    parking_id INTEGER NOT NULL,
    day_of_week INTEGER NOT NULL, -- 0=Sunday ... 6=Saturday
    start_hour TEXT NOT NULL,
    end_hour TEXT NOT NULL,
    min_price REAL NOT NULL,
    max_price REAL NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parking_id) REFERENCES parking(id) ON DELETE CASCADE
);


CREATE TABLE reservation (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    parking_spot_id INTEGER NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    total_price REAL NOT NULL,
    status TEXT NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, COMPLETED, CANCELLED, NO_SHOW
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spot(id) ON DELETE CASCADE
);

CREATE TABLE sanction (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    reservation_id INTEGER NOT NULL UNIQUE,
    user_id INTEGER NOT NULL,
    amount REAL NOT NULL,
    reason TEXT NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservation(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_user_role_user ON user_role(user_id);
CREATE INDEX idx_company_user ON company(user_id);
CREATE INDEX idx_parking_company ON parking(company_id);
CREATE INDEX idx_reservation_user ON reservation(user_id);
CREATE INDEX idx_reservation_spot ON reservation(parking_spot_id);
CREATE INDEX idx_reservation_time ON reservation(start_time, end_time);
CREATE INDEX idx_sanction_user ON sanction(user_id);