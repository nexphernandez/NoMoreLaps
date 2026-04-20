import React from 'react';
import { TouchableOpacity, Text, StyleSheet, ActivityIndicator, ViewStyle, StyleProp } from 'react-native';
import { useTheme } from '../context/ThemeContext';

interface ButtonProps {
  title: string;
  onPress: () => void;
  loading?: boolean;
  disabled?: boolean;
  variant?: 'primary' | 'danger' | 'outline' | 'success';
  style?: StyleProp<ViewStyle>;
}

const CustomButton: React.FC<ButtonProps> = ({ title, onPress, loading, disabled, variant = 'primary', style }) => {
  const { theme } = useTheme();

  const getVariantStyle = () => {
    switch (variant) {
      case 'danger': 
        return { bg: '#FFF1F2', text: theme.danger, border: '#FECDD3' };
      case 'success':
        return { bg: '#DCFCE7', text: '#166534', border: '#BBF7D0' };
      case 'outline': 
        return { bg: 'transparent', text: theme.primary, border: theme.primary };
      default: 
        return { bg: theme.primary, text: '#FFF', border: theme.primary };
    }
  };

  const currentVariant = getVariantStyle();

  return (
    <TouchableOpacity 
      style={[
        styles.btn, 
        { backgroundColor: currentVariant.bg, borderColor: currentVariant.border }, 
        disabled && { opacity: 0.5 },
        style
      ]} 
      onPress={onPress}
      disabled={disabled || loading}
    >
      {loading ? (
        <ActivityIndicator color={currentVariant.text} />
      ) : (
        <Text style={[styles.text, { color: currentVariant.text }]}>{title}</Text>
      )}
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  btn: { 
    padding: 16, 
    borderRadius: 16, 
    alignItems: 'center', 
    justifyContent: 'center', 
    borderWidth: 1,
    width: '100%'
  },
  text: { 
    fontSize: 16, 
    fontWeight: 'bold' 
  },
});

export default CustomButton;
