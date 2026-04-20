import React from 'react';
import { View, StyleSheet, TouchableOpacity, Image } from 'react-native';
import Typography from '../../components/Typography';
import CustomButton from '../../components/CustomButton';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import Divider from '../../components/Divider';
import { useTheme } from '../../context/ThemeContext';

interface ProfileViewProps {
  userName: string;
  userEmail: string;
  userAvatar: string | null;
  activeReservation: { parkingName: string; spot: string; timeRemaining: string } | null;
  onLogout: () => void;
  onViewHistory: () => void;
  onGoToSanctions: () => void;
  onEditProfile: () => void;
  onCalendarSync: () => void;
}

const ProfileView: React.FC<ProfileViewProps> = ({
  userName, userEmail, userAvatar, activeReservation, onLogout, onViewHistory, onGoToSanctions, onEditProfile,onCalendarSync
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer>
      {/* CABECERA DE USUARIO */}
      <View style={styles.header}>
        <View style={[
          styles.avatar, 
          { 
            backgroundColor: theme.lightBackground,
            borderWidth: 1,
            borderColor: theme.border 
          }
        ]}>
          {userAvatar ? (
            <Image source={{ uri: userAvatar }} style={styles.avatarImg} />
          ) : (
            <Typography variant="h1" color={theme.primary}>
              {userName.charAt(0).toUpperCase()}
            </Typography>
          )}
        </View>
        <Typography variant="h2" style={{marginTop: 12}}>{userName}</Typography>
        <Typography variant="caption">{userEmail}</Typography>

        <TouchableOpacity style={styles.editMainBtn} onPress={onEditProfile}>
          <Typography variant="label" color={theme.primary}>Edit Profile</Typography>
        </TouchableOpacity>
      </View>

      <Divider marginVertical={24} />

      {/* SECCIÓN DE RESERVA ACTIVA */}
      <View style={styles.section}>
        <Typography variant="label" style={{marginBottom: 12}}>Current Reservation</Typography>
        {activeReservation ? (
          <Card style={styles.resCard}>
            <View style={styles.resInfo}>
              <Typography variant="h3">{activeReservation.parkingName}</Typography>
              <Typography variant="caption">Spot: {activeReservation.spot}</Typography>
            </View>
            <View style={[styles.resTimer, { backgroundColor: theme.lightBackground, borderColor: theme.border }]}>
              <Typography variant="h2" color={theme.primary}>{activeReservation.timeRemaining}</Typography>
              <Typography variant="label">remaining</Typography>
            </View>
          </Card>
        ) : (
          <Card style={styles.emptyCard}>
            <Typography variant="caption" style={{textAlign: 'center'}}>No active reservations found.</Typography>
          </Card>
        )}
      </View>

      <View style={styles.section}>
        <TouchableOpacity style={[styles.menuItem, { backgroundColor: theme.background, borderColor: theme.border }]} onPress={onViewHistory}>
          <View style={[styles.menuIcon, { backgroundColor: theme.lightBackground }]}>
            <Typography style={{ fontSize: 20 }}>🕒</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3">Reservation History</Typography>
            <Typography variant="caption">Check your past activity and receipts</Typography>
          </View>
          <Typography variant="h3" color={theme.border}>❯</Typography>
        </TouchableOpacity>

        <TouchableOpacity style={[styles.menuItem, { marginTop: 12, backgroundColor: theme.background, borderColor: theme.border }]} onPress={onGoToSanctions}>
          <View style={[styles.menuIcon, { backgroundColor: '#FFF1F2' }]}>
            <Typography style={{ fontSize: 20 }}>🚔</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3" color={theme.danger}>My Sanctions</Typography>
            <Typography variant="caption">View pending fines and violations</Typography>
          </View>
          <Typography variant="h3" color={theme.border}>❯</Typography>
        </TouchableOpacity>

        <TouchableOpacity style={[styles.menuItem, { marginTop: 12, backgroundColor: theme.background, borderColor: theme.border }]} onPress={onCalendarSync}>
          <View style={[styles.menuIcon, { backgroundColor: '#E0F2FE' }]}>
            <Typography style={{ fontSize: 20 }}>📅</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3" color={theme.primary}>Calendar Sync</Typography>
            <Typography variant="caption">Link your calendar for smart suggestions</Typography>
          </View>
          <Typography variant="h3" color={theme.border}>❯</Typography>
        </TouchableOpacity>
      </View>

      {/* BOTÓN DE LOGOUT */}
      <View style={styles.footer}>
        <CustomButton
           title="Log Out"
           onPress={onLogout}
           variant="danger"
        />
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { alignItems: 'center', marginBottom: 20, position: 'relative' },
  avatar: {
    width: 80,
    height: 80,
    borderRadius: 40,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 2,
    shadowColor: '#000',
    shadowOpacity: 0.05,
    shadowRadius: 5,
    shadowOffset: { width: 0, height: 2 },
  },
  avatarImg: { width: 80, height: 80, borderRadius: 40 },
  editMainBtn: {
    position: 'absolute',
    top: 0,
    right: 0,
    paddingVertical: 8,
  },
  section: { marginBottom: 32 },
  resCard: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
  },
  resInfo: { flex: 1 },
  resTimer: {
    padding: 10,
    borderRadius: 12,
    alignItems: 'center',
    borderWidth: 1,
  },
  emptyCard: {
    padding: 30,
    alignItems: 'center',
    borderStyle: 'dashed',
    borderWidth: 1.5,
  },
  menuItem: {
    borderRadius: 16,
    padding: 16,
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
  },
  menuIcon: {
    width: 44,
    height: 44,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 16,
  },
  footer: { marginTop: 20 },
});

export default ProfileView;
