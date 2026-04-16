import React from 'react';
import { View, StyleSheet } from 'react-native';
import Typography from './Typography';
import { useTheme } from '../context/ThemeContext';

interface BadgeProps {
  label: string;
  type?: 'success' | 'danger' | 'warning' | 'info';
}

const Badge: React.FC<BadgeProps> = ({ label, type = 'info' }) => {
  const { theme } = useTheme();

  const getStyles = () => {
    switch (type) {
      case 'success': return { bg: theme.success + '20', text: theme.success };
      case 'danger': return { bg: theme.danger + '20', text: theme.danger };
      case 'warning': return { bg: '#FEF9C3', text: '#854D0E' };
      default: return { bg: theme.lightBackground, text: theme.textSecondary };
    }
  };

  const colors = getStyles();

  return (
    <View style={[styles.badge, { backgroundColor: colors.bg }]}>
      <Typography variant="label" color={colors.text} style={{ fontSize: 10 }}>{label}</Typography>
    </View>
  );
};

const styles = StyleSheet.create({
  badge: { 
    paddingHorizontal: 10, 
    paddingVertical: 4, 
    borderRadius: 8, 
    alignSelf: 'flex-start' 
  },
});

export default Badge;
