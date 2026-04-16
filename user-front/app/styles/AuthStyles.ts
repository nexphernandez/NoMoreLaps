import { StyleSheet } from 'react-native';
import { Colors } from '../constants/Colors';

/**
 * Shared styles for Authentication screens (Login and Register).
 * Inspired by the clean and clear aesthetic of Google Maps.
 *
 * @author nexphernandez DiazLuisAlejandro
 */
export const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    justifyContent: 'center',
    backgroundColor: Colors.light.lightBackground,
  },
  card: {
    backgroundColor: Colors.light.background,
    borderRadius: 16,
    padding: 32,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 8,
    elevation: 4,
    borderWidth: 1,
    borderColor: Colors.light.border,
  },
  title: {
    fontSize: 28,
    fontWeight: '700',
    color: Colors.light.text,
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 16,
    color: Colors.light.textSecondary,
    textAlign: 'center',
    marginBottom: 40,
  },
  input: {
    backgroundColor: Colors.light.background,
    borderRadius: 8,
    padding: 16,
    color: Colors.light.text,
    marginBottom: 16,
    fontSize: 16,
    borderWidth: 1,
    borderColor: Colors.light.border,
  },
  button: {
    backgroundColor: Colors.light.primary,
    borderRadius: 8,
    padding: 18,
    alignItems: 'center',
    marginTop: 8,
    elevation: 2,
    shadowColor: Colors.light.primary,
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 4,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
  footerLink: {
    marginTop: 24,
    alignItems: 'center',
  },
  footerText: {
    color: Colors.light.primary,
    fontSize: 14,
    fontWeight: '500',
  },
  footerTextSecondary: {
    color: Colors.light.textSecondary,
    fontSize: 14,
    fontWeight: '500',
  },
  form: {
    width: '100%',
    gap: 16,
    marginTop: 10,
  },

});
