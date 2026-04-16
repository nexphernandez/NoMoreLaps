import React from 'react';
import { View, StyleSheet, ViewStyle } from 'react-native';
import { useTheme } from '../context/ThemeContext';

interface DividerProps {
  style?: ViewStyle;
  marginVertical?: number;
}

const Divider: React.FC<DividerProps> = ({ style, marginVertical = 16 }) => {
  const { theme } = useTheme();
  return (
    <View style={[
      styles.divider, 
      { backgroundColor: theme.border, marginVertical }, 
      style
    ]} />
  );
};

const styles = StyleSheet.create({
  divider: {
    height: 1,
    width: '100%',
  },
});

export default Divider;
