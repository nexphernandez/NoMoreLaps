import React from 'react';
import { View, StyleSheet, TouchableOpacity, Image } from 'react-native';
import Typography from '../../components/Typography';
import CustomButton from '../../components/CustomButton';
import Divider from '../../components/Divider';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import AdBanner from '../../components/AdBanner';
import { useTheme } from '../../context/ThemeContext';

import { MaterialCommunityIcons } from '@expo/vector-icons';
import { Ad } from '../../services/adService';

interface ProfileViewProps {
  userName: string;
  userEmail: string;
  userAvatar: string | null;
  activeReservation: { parkingName: string; spot: string; timeRemaining: string } | null;
  themeMode: 'auto' | 'light' | 'dark';
  onThemeChange: (mode: 'auto' | 'light' | 'dark') => void;
  onLogout: () => void;
  onViewHistory: () => void;
  onGoToSanctions: () => void;
  onEditProfile: () => void;
  onCalendarSync: () => void;
  ads: Ad[];
}

const ProfileView: React.FC<ProfileViewProps> = ({
  userName, userEmail, userAvatar, activeReservation, themeMode, onThemeChange, onLogout, onViewHistory, onGoToSanctions, onEditProfile, onCalendarSync, ads
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer>
      {/* CABECERA DE USUARIO */}
      <View style={styles.header}>
        <View style={styles.avatarWrapper}>
          <View style={[
            styles.avatar, 
            { 
              backgroundColor: theme.lightBackground,
              borderWidth: 1,
              borderColor: theme.border 
            }
          ]}>
            {userAvatar ? (
              <Image 
                source={{ uri: userAvatar }} 
                style={styles.avatarImg} 
                resizeMode="cover"
              />
            ) : (
              <Typography variant="h1" color={theme.primary}>
                {userName.charAt(0).toUpperCase()}
              </Typography>
            )}
          </View>
          <TouchableOpacity 
            style={[styles.cameraBadge, { backgroundColor: theme.primary }]} 
            onPress={onEditProfile}
          >
            <MaterialCommunityIcons name="camera" size={18} color="#FFF" />
          </TouchableOpacity>
        </View>
        
        <Typography variant="h2" style={{marginTop: 12}}>{userName}</Typography>
        <Typography variant="caption">{userEmail}</Typography>

        <TouchableOpacity style={[styles.editBtn, { borderColor: theme.primary, marginTop: 16 }]} onPress={onEditProfile}>
          <Typography variant="label" color={theme.primary}>Edit Profile</Typography>
        </TouchableOpacity>
      </View>

      <View style={styles.section}>
        <Typography variant="label" style={{marginBottom: 12}}>Appearance</Typography>
        <View style={styles.themeToggleContainer}>
          {(['auto', 'light', 'dark'] as const).map((mode) => (
            <TouchableOpacity
              key={mode}
              style={[
                styles.themeBtn,
                { 
                  backgroundColor: themeMode === mode ? theme.primary : theme.lightBackground,
                  borderColor: theme.border 
                }
              ]}
              onPress={() => onThemeChange(mode)}
            >
              <Typography 
                variant="label" 
                color={themeMode === mode ? '#FFF' : theme.text}
              >
                {mode.toUpperCase()}
              </Typography>
            </TouchableOpacity>
          ))}
        </View>
      </View>

      <Divider marginVertical={24} />

      {/* SECCIÓN DE RESERVA ACTIVA */}
      <View style={styles.section}>
        <Typography variant="label" style={{marginBottom: 12}}>Current Reservation</Typography>
        {activeReservation ? (
          <TouchableOpacity onPress={onViewHistory}>
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
          </TouchableOpacity>
        ) : (
          <Card style={styles.emptyCard}>
            <Typography variant="caption" style={{textAlign: 'center'}}>No active reservations found.</Typography>
          </Card>
        )}
      </View>

      <View style={styles.section}>
        <TouchableOpacity style={[styles.menuItem, { backgroundColor: theme.background, borderColor: theme.border }]} onPress={onViewHistory}>
          <View style={[styles.menuIcon, { backgroundColor: theme.lightBackground }]}>
            <MaterialCommunityIcons name="history" size={24} color={theme.primary} />
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3">Reservation History</Typography>
            <Typography variant="caption">Check your past activity and receipts</Typography>
          </View>
          <MaterialCommunityIcons name="chevron-right" size={24} color={theme.border} />
        </TouchableOpacity>

        <TouchableOpacity style={[styles.menuItem, { marginTop: 12, backgroundColor: theme.background, borderColor: theme.border }]} onPress={onGoToSanctions}>
          <View style={[styles.menuIcon, { backgroundColor: '#FFF1F2' }]}>
            <MaterialCommunityIcons name="alert-octagon" size={24} color={theme.danger} />
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3" color={theme.danger}>My Sanctions</Typography>
            <Typography variant="caption">View pending fines and violations</Typography>
          </View>
          <MaterialCommunityIcons name="chevron-right" size={24} color={theme.border} />
        </TouchableOpacity>

        <TouchableOpacity style={[styles.menuItem, { marginTop: 12, backgroundColor: theme.background, borderColor: theme.border }]} onPress={onCalendarSync}>
          <View style={[styles.menuIcon, { backgroundColor: '#E0F2FE' }]}>
            <MaterialCommunityIcons name="calendar-sync" size={24} color={theme.primary} />
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="h3" color={theme.primary}>Calendar Sync</Typography>
            <Typography variant="caption">Link your calendar for smart suggestions</Typography>
          </View>
          <MaterialCommunityIcons name="chevron-right" size={24} color={theme.border} />
        </TouchableOpacity>

        {/* ADS FROM ODOO */}
        {ads.length > 0 && (
          <AdBanner ad={ads[0]} style={{ marginTop: 32 }} />
        )}
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
  avatarWrapper: {
    position: 'relative',
    width: 80,
    height: 80,
  },
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
  cameraBadge: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    width: 28,
    height: 28,
    borderRadius: 14,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
    borderColor: '#FFF',
    elevation: 3,
  },
  avatarImg: { width: 80, height: 80, borderRadius: 40 },
  editBtn: {
    paddingHorizontal: 20,
    paddingVertical: 8,
    borderRadius: 20,
    borderWidth: 1.5,
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
  themeToggleContainer: {
    flexDirection: 'row',
    gap: 8,
  },
  themeBtn: {
    flex: 1,
    paddingVertical: 10,
    borderRadius: 12,
    alignItems: 'center',
    borderWidth: 1,
  },
});

export default ProfileView;
