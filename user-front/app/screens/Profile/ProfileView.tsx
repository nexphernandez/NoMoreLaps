import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Colors } from '../../constants/Colors';

interface ProfileViewProps {
  userName: string;
  userEmail: string;
  activeReservation: { parkingName: string; spot: string; timeRemaining: string } | null;
  onLogout: () => void;
}

const ProfileView: React.FC<ProfileViewProps> = ({ 
  userName, userEmail, activeReservation, onLogout 
}) => {
  return (
    <SafeAreaView style={styles.container} edges={['bottom']}>
      <ScrollView contentContainerStyle={styles.scrollWrapper}>
        
        {/* CABECERA DE USUARIO */}
        <View style={styles.header}>
          <View style={styles.avatar}>
            <Text style={styles.avatarText}>{userName.charAt(0).toUpperCase()}</Text>
          </View>
          <Text style={styles.name}>{userName}</Text>
          <Text style={styles.email}>{userEmail}</Text>
        </View>

        {/* SECCIÓN DE RESERVA ACTIVA */}
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Current Reservation</Text>
          {activeReservation ? (
            <View style={styles.resCard}>
              <View style={styles.resInfo}>
                <Text style={styles.resParking}>{activeReservation.parkingName}</Text>
                <Text style={styles.resSpot}>Spot: {activeReservation.spot}</Text>
              </View>
              <View style={styles.resTimer}>
                <Text style={styles.resTimerText}>{activeReservation.timeRemaining}</Text>
                <Text style={styles.resTimerLabel}>remaining</Text>
              </View>
            </View>
          ) : (
            <View style={styles.emptyCard}>
              <Text style={styles.emptyText}>No active reservations found.</Text>
            </View>
          )}
        </View>

        {/* BOTÓN DE LOGOUT */}
        <View style={styles.footer}>
          <TouchableOpacity style={styles.logoutBtn} onPress={onLogout}>
            <Text style={styles.logoutText}>Log Out</Text>
          </TouchableOpacity>
        </View>

      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  scrollWrapper: { padding: 24 },
  
  header: { alignItems: 'center', marginBottom: 40 },
  avatar: { 
    width: 80, 
    height: 80, 
    borderRadius: 40, 
    backgroundColor: Colors.primary, 
    justifyContent: 'center', 
    alignItems: 'center',
    marginBottom: 16,
    elevation: 4,
    shadowColor: Colors.primary,
    shadowOpacity: 0.3,
    shadowRadius: 5,
  },
  avatarText: { fontSize: 32, fontWeight: 'bold', color: '#fff' },
  name: { fontSize: 22, fontWeight: 'bold', color: Colors.text },
  email: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },

  section: { marginBottom: 32 },
  sectionTitle: { fontSize: 16, fontWeight: 'bold', color: Colors.text, marginBottom: 16, marginLeft: 4 },
  
  resCard: {
    backgroundColor: Colors.background,
    borderRadius: 16,
    padding: 20,
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: Colors.border,
    elevation: 2,
  },
  resInfo: { flex: 1 },
  resParking: { fontSize: 18, fontWeight: 'bold', color: Colors.text },
  resSpot: { fontSize: 14, color: Colors.primary, marginTop: 4, fontWeight: '600' },
  
  resTimer: { 
    backgroundColor: Colors.lightBackground, 
    padding: 12, 
    borderRadius: 12, 
    alignItems: 'center',
    borderWidth: 1,
    borderColor: Colors.border,
  },
  resTimerText: { fontSize: 18, fontWeight: 'bold', color: Colors.danger },
  resTimerLabel: { fontSize: 10, color: Colors.textSecondary, textTransform: 'uppercase' },

  emptyCard: {
    backgroundColor: Colors.background,
    borderRadius: 16,
    padding: 30,
    alignItems: 'center',
    borderStyle: 'dashed',
    borderWidth: 2,
    borderColor: Colors.border,
  },
  emptyText: { color: Colors.textSecondary, fontSize: 14 },

  footer: { marginTop: 20 },
  logoutBtn: {
    padding: 18,
    borderRadius: 12,
    backgroundColor: '#FFF1F2', // Fondo rojizo suave
    borderWidth: 1,
    borderColor: '#FECDD3',
    alignItems: 'center',
  },
  logoutText: { color: Colors.danger, fontWeight: 'bold', fontSize: 16 },
});

export default ProfileView;
