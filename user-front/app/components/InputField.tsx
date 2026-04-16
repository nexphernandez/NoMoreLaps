import React from 'react';
import { View, TextInput, StyleSheet, TextInputProps } from 'react-native';
import Typography from './Typography';
import { useTheme } from '../context/ThemeContext';

interface InputFieldProps extends TextInputProps {
  label: string;
  error?: string;
}

const InputField: React.FC<InputFieldProps> = ({ label, error, ...props }) => {
  const { theme } = useTheme();
  return (
    <View style={styles.container}>
      <Typography variant="label">{label}</Typography>
      <TextInput
        style={[
          styles.input, 
          { 
            backgroundColor: theme.background, 
            color: theme.text, 
            borderColor: error ? theme.danger : theme.border 
          }
        ]}
        placeholderTextColor={theme.textSecondary}
        {...props}
      />
      {error ? <Typography variant="error">{error}</Typography> : null}
    </View>
  );
};

const styles = StyleSheet.create({
  container: { 
    gap: 8, 
    width: '100%',
    marginBottom: 16
  },
  input: {
    borderRadius: 12,
    padding: 16,
    fontSize: 16,
    borderWidth: 1,
  },
});

export default InputField;
