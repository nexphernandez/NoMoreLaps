import React from 'react';
import { TouchableOpacity, View, StyleSheet, Linking, Image } from 'react-native';
import Typography from './Typography';
import { useTheme } from '../context/ThemeContext';
import { Ad } from '../services/adService';
import { MaterialCommunityIcons } from '@expo/vector-icons';

interface AdBannerProps {
  ad: Ad;
  style?: any;
}

const AdBanner: React.FC<AdBannerProps> = ({ ad, style }) => {
  const { theme } = useTheme();

  if (!ad) return null;

  return (
    <TouchableOpacity 
      style={[styles.container, { backgroundColor: theme.background, borderColor: theme.primary }, style]}
      onPress={() => ad.targetUrl && Linking.openURL(ad.targetUrl)}
    >
      <View style={[styles.badge, { backgroundColor: theme.primary }]}>
        <Typography variant="label" color="#FFF">PROMO</Typography>
      </View>
      
      <View style={styles.content}>
        <View style={[styles.iconContainer, { backgroundColor: theme.lightBackground }]}>
          <MaterialCommunityIcons name="tag-outline" size={20} color={theme.primary} />
        </View>
        <View style={{ flex: 1 }}>
          <Typography variant="h3">{ad.name}</Typography>
          <Typography variant="caption" color={theme.textSecondary}>¡Aprovecha esta oferta exclusiva!</Typography>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 16,
    borderRadius: 20,
    borderWidth: 1,
    borderStyle: 'dashed',
    position: 'relative',
    overflow: 'hidden',
    marginVertical: 10,
  },
  badge: {
    position: 'absolute',
    top: 0,
    right: 0,
    paddingHorizontal: 10,
    paddingVertical: 2,
    borderBottomLeftRadius: 10,
  },
  content: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  iconContainer: {
    width: 40,
    height: 40,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 12,
  },
});

export default AdBanner;
