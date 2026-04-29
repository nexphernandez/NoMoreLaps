import NetInfo from '@react-native-community/netinfo';
import databaseService from './databaseService';
import reservationService from './reservationService';
import userService from './userService';

/**
 * Service to manage synchronization between local storage and backend.
 */
class SyncService {
  private isSyncing = false;

  init() {
    NetInfo.addEventListener(state => {
      if (state.isConnected && state.isInternetReachable) {
        console.log('Internet restored, starting synchronization...');
        this.syncPendingData();
      }
    });
  }

  async syncPendingData() {
    if (this.isSyncing) return;
    this.isSyncing = true;

    try {
      // 1. Sync Pending Reservations
      const pendingRes = await databaseService.getPendingReservations();
      if (pendingRes.length > 0) {
        console.log(`Syncing ${pendingRes.length} pending reservations...`);
        for (const res of pendingRes as any[]) {
          try {
            await reservationService.create({
              parkingSpotId: res.spotId,
              startTime: res.startTime,
              endTime: res.endTime
            });
            await databaseService.markAsSynced(res.id);
          } catch (error) {
            console.error(`Failed to sync reservation ${res.id}:`, error);
          }
        }
      }

      // 2. Sync Pending Profile Updates
      const pendingUpdates = await databaseService.getPendingUpdates();
      if (pendingUpdates.length > 0) {
        console.log(`Syncing ${pendingUpdates.length} pending updates...`);
        for (const up of pendingUpdates as any[]) {
          try {
            const data = JSON.parse(up.data);
            if (up.type === 'PROFILE') {
              await userService.updateProfile(data);
            }
            await databaseService.markUpdateAsSynced(up.id);
          } catch (error) {
            console.error(`Failed to sync update ${up.id}:`, error);
          }
        }
      }
      
      console.log('Synchronization process finished.');
    } catch (err) {
      console.error('Error during sync process:', err);
    } finally {
      this.isSyncing = false;
    }
  }
}

export default new SyncService();
