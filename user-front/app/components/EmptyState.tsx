import React from 'react';
import { View, StyleSheet } from 'react-native';
import Typography from './Typography';

interface EmptyStateProps {
  icon: string;
  title: string;
  message: string;
}

const EmptyState: React.FC<EmptyStateProps> = ({ icon, title, message }) => (
  <View style={styles.container}>
    <Typography style={{ fontSize: 60, marginBottom: 20 }}>{icon}</Typography>
    <Typography variant="h2" style={{ textAlign: 'center' }}>{title}</Typography>
    <Typography variant="caption" style={{ textAlign: 'center', marginTop: 8 }}>{message}</Typography>
  </View>
);

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    alignItems: 'center', 
    justifyContent: 'center', 
    marginTop: 60, 
    padding: 40 
  },
});

export default EmptyState;
