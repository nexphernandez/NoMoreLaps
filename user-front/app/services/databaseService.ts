import * as SQLite from 'expo-sqlite';

/**
 * Service to handle local SQLite storage for offline-first capabilities.
 */
class DatabaseService {
  private db: SQLite.SQLiteDatabase | null = null;
  private initPromise: Promise<void> | null = null;

  /**
   * Initializes the database and creates tables if they don't exist.
   */
  async init(): Promise<void> {
    if (this.initPromise) return this.initPromise;

    this.initPromise = (async () => {
      try {
        this.db = await SQLite.openDatabaseAsync('nomorelaps_v3_defensa.db');

        await this.db.execAsync(`
          PRAGMA journal_mode = WAL;
          
          CREATE TABLE IF NOT EXISTS parkings (
            id INTEGER PRIMARY KEY NOT NULL,
            name TEXT,
            address TEXT,
            latitude REAL,
            longitude REAL,
            data TEXT
          );

          CREATE TABLE IF NOT EXISTS parking_spots (
            id INTEGER PRIMARY KEY NOT NULL,
            parkingId INTEGER,
            number INTEGER,
            state INTEGER,
            data TEXT
          );

          CREATE TABLE IF NOT EXISTS user_reservations (
            id INTEGER PRIMARY KEY NOT NULL,
            data TEXT
          );

          CREATE TABLE IF NOT EXISTS user_sanctions (
            id INTEGER PRIMARY KEY NOT NULL,
            data TEXT
          );

          CREATE TABLE IF NOT EXISTS user_profile (
            id INTEGER PRIMARY KEY NOT NULL,
            data TEXT
          );

          CREATE TABLE IF NOT EXISTS pending_reservations (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            spotId INTEGER,
            parkingId INTEGER,
            userId INTEGER,
            startTime TEXT,
            endTime TEXT,
            status TEXT DEFAULT 'PENDING'
          );

          CREATE TABLE IF NOT EXISTS pending_updates (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            type TEXT,
            data TEXT,
            status TEXT DEFAULT 'PENDING'
          );
        `);
        console.log('Local Database V2 Initialized');
      } catch (error) {
        console.error('Database Init Error:', error);
        this.initPromise = null; 
        throw error;
      }
    })();

    return this.initPromise;
  }

  private async getDb(): Promise<SQLite.SQLiteDatabase> {
    await this.init();
    if (!this.db) throw new Error('Database not initialized');
    return this.db;
  }

  // --- PARKING CACHE ---
  async saveParkings(parkings: any[]) {
    const db = await this.getDb();
    await db.runAsync('DELETE FROM parkings');
    for (const p of parkings) {
      await db.runAsync(
        'INSERT INTO parkings (id, name, address, latitude, longitude, data) VALUES (?, ?, ?, ?, ?, ?)',
        [p.id, p.name, p.address, p.latitude, p.longitude, JSON.stringify(p)]
      );
    }
    console.log(`CACHE: Saved ${parkings.length} parkings.`);
  }

  async getParkings() {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM parkings');
  }

  // --- PARKING SPOTS CACHE ---
  async saveParkingSpots(parkingId: number, spots: any[]) {
    const db = await this.getDb();
    await db.runAsync('DELETE FROM parking_spots WHERE parkingId = ?', [parkingId]);
    for (const s of spots) {
      await db.runAsync(
        'INSERT INTO parking_spots (id, parkingId, number, state, data) VALUES (?, ?, ?, ?, ?)',
        [s.id, parkingId, s.number, s.state ? 1 : 0, JSON.stringify(s)]
      );
    }
    console.log(`CACHE: Saved ${spots.length} spots for parking ${parkingId}.`);
  }

  async getParkingSpots(parkingId: number) {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM parking_spots WHERE parkingId = ?', [parkingId]);
  }

  // --- RESERVATIONS CACHE ---
  async saveUserReservations(reservations: any[]) {
    const db = await this.getDb();
    await db.runAsync('DELETE FROM user_reservations');
    for (const r of reservations) {
      await db.runAsync('INSERT INTO user_reservations (id, data) VALUES (?, ?)', [r.id, JSON.stringify(r)]);
    }
    console.log(`CACHE: Saved ${reservations.length} reservations.`);
  }

  async getUserReservations() {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM user_reservations');
  }

  // --- SANCTIONS CACHE ---
  async saveUserSanctions(sanctions: any[]) {
    const db = await this.getDb();
    await db.runAsync('DELETE FROM user_sanctions');
    for (const s of sanctions) {
      await db.runAsync('INSERT INTO user_sanctions (id, data) VALUES (?, ?)', [s.id, JSON.stringify(s)]);
    }
    console.log(`CACHE: Saved ${sanctions.length} sanctions.`);
  }

  async getUserSanctions() {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM user_sanctions');
  }

  // --- PROFILE CACHE ---
  async saveUserProfile(userId: number, profile: any) {
    const db = await this.getDb();
    await db.runAsync('DELETE FROM user_profile WHERE id = ?', [userId]);
    await db.runAsync('INSERT INTO user_profile (id, data) VALUES (?, ?)', [userId, JSON.stringify(profile)]);
    console.log(`CACHE: Saved profile for user ${userId}.`);
  }

  async getUserProfile(userId: number) {
    const db = await this.getDb();
    return await db.getFirstAsync('SELECT * FROM user_profile WHERE id = ?', [userId]);
  }

  // --- PENDING DATA (QUEUE) ---
  async addPendingReservation(reservation: any) {
    const db = await this.getDb();
    await db.runAsync(
      'INSERT INTO pending_reservations (spotId, parkingId, userId, startTime, endTime) VALUES (?, ?, ?, ?, ?)',
      [reservation.spotId, reservation.parkingId, reservation.userId, reservation.startTime, reservation.endTime]
    );
  }

  async getPendingReservations() {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM pending_reservations WHERE status != "SYNCED"');
  }

  async markAsSynced(id: number) {
    const db = await this.getDb();
    await db.runAsync('UPDATE pending_reservations SET status = "SYNCED" WHERE id = ?', [id]);
  }

  async markAsFailed(id: number) {
    const db = await this.getDb();
    await db.runAsync('UPDATE pending_reservations SET status = "FAILED" WHERE id = ?', [id]);
  }

  async addPendingUpdate(type: string, data: any) {
    const db = await this.getDb();
    await db.runAsync('INSERT INTO pending_updates (type, data) VALUES (?, ?)', [type, JSON.stringify(data)]);
  }

  async getPendingUpdates() {
    const db = await this.getDb();
    return await db.getAllAsync('SELECT * FROM pending_updates WHERE status = "PENDING"');
  }

  async markUpdateAsSynced(id: number) {
    const db = await this.getDb();
    await db.runAsync('UPDATE pending_updates SET status = "SYNCED" WHERE id = ?', [id]);
  }
}

export default new DatabaseService();
