import React from 'react';
import { View, StyleSheet, TouchableOpacity, KeyboardAvoidingView, Platform, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { useTheme } from '../../context/ThemeContext';
import Typography from '../../components/Typography';
import CustomButton from '../../components/CustomButton';
import InputField from '../../components/InputField';
import ScreenContainer from '../../components/ScreenContainer';

interface RegisterViewProps {
  name: string;
  setName: (text: string) => void;
  email: string;
  setEmail: (text: string) => void;
  password: string;
  setPassword: (text: string) => void;
  confirmPassword: string;
  setConfirmPassword: (text: string) => void;
  errors: {
    name?: string;
    email?: string;
    password?: string;
    confirmPassword?: string;
  };
  onRegister: () => void;
  isLoading: boolean;
}

const RegisterView: React.FC<RegisterViewProps> = ({
  name, setName, email, setEmail, password, setPassword, 
  confirmPassword, setConfirmPassword, errors, onRegister, isLoading
}) => {
  const { theme } = useTheme();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const goToLogin = () => navigation.navigate('Login');

  return (
    <ScreenContainer withScroll={true}>
      <View style={[styles.card, { backgroundColor: theme.background, borderColor: theme.border }]}>
        <Typography variant="h1" color={theme.primary} style={styles.title}>NoMoreLaps</Typography>
        <Typography variant="body" color={theme.textSecondary} style={styles.subtitle}>Create your account</Typography>

        <View style={styles.form}>
          <InputField
            label="Nombre Completo"
            placeholder="Juan Pérez"
            value={name}
            onChangeText={setName}
            error={errors.name}
          />

          <InputField
            label="Correo Electrónico"
            placeholder="ejemplo@test.com"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            error={errors.email}
          />

          <InputField
            label="Contraseña"
            placeholder="••••••••"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            error={errors.password}
          />

          <InputField
            label="Confirmar Contraseña"
            placeholder="••••••••"
            value={confirmPassword}
            onChangeText={setConfirmPassword}
            secureTextEntry
            error={errors.confirmPassword}
          />

          <CustomButton
            title="Create Account"
            onPress={onRegister}
            loading={isLoading}
            style={{ marginTop: 8 }}
          />

          <TouchableOpacity style={styles.footerLink} onPress={goToLogin}>
            <Typography variant="label" color={theme.primary}>
              Already have an account? Sign In
            </Typography>
          </TouchableOpacity>
        </View>
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  scrollContent: {
    flexGrow: 1,
    justifyContent: 'center',
    paddingVertical: 40,
  },
  card: {
    borderRadius: 24,
    padding: 32,
    elevation: 8,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 15,
    shadowOffset: { width: 0, height: 8 },
    borderWidth: 1,
  },
  title: {
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    textAlign: 'center',
    marginBottom: 32,
  },
  form: {
    width: '100%',
  },
  footerLink: {
    marginTop: 24,
    alignItems: 'center',
  },
});

export default RegisterView;
