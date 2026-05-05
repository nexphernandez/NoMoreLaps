import React from 'react';
import { Text, StyleSheet, TextStyle } from 'react-native';
import { useTheme } from '../context/ThemeContext';

interface TypographyProps {
  children: React.ReactNode;
  variant?: 'h1' | 'h2' | 'h3' | 'body' | 'caption' | 'label' | 'error';
  style?: TextStyle;
  color?: string;
  numberOfLines?: number;
}

const Typography: React.FC<TypographyProps> = ({ 
  children, 
  variant = 'body', 
  style, 
  color,
  numberOfLines 
}) => {
  const { theme } = useTheme();

  const getVariantStyle = () => {
    switch (variant) {
      case 'h1': return [styles.h1, { color: theme.text }];
      case 'h2': return [styles.h2, { color: theme.text }];
      case 'h3': return [styles.h3, { color: theme.text }];
      case 'caption': return [styles.caption, { color: theme.textSecondary }];
      case 'label': return [styles.label, { color: theme.textSecondary }];
      case 'error': return [styles.error, { color: theme.danger }];
      default: return [styles.body, { color: theme.text }];
    }
  };

  return (
    <Text 
      style={[getVariantStyle(), color ? { color } : null, style]}
      numberOfLines={numberOfLines}
    >
      {children}
    </Text>
  );
};

const styles = StyleSheet.create({
  h1: { fontSize: 28, fontWeight: 'bold' },
  h2: { fontSize: 22, fontWeight: 'bold' },
  h3: { fontSize: 18, fontWeight: 'bold' },
  body: { fontSize: 16 },
  caption: { fontSize: 14 },
  label: { fontSize: 12, fontWeight: '700', textTransform: 'uppercase' },
  error: { fontSize: 12 },
});

export default Typography;
