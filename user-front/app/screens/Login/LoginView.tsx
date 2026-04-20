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

interface LoginViewProps {
  email: string;
  setEmail: (text: string) => void;
  password: string;
  setPassword: (text: string) => void;
  onLogin: () => void;
  loading: boolean;
}

const LoginView: React.FC<LoginViewProps> = ({
  email, setEmail, password, setPassword, onLogin, loading
}) => {
  const { theme } = useTheme();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const goToRegister = () => navigation.navigate('Register');
  const goToHome = () => navigation.navigate('Home');

  return (
    <ScreenContainer withScroll={true} style={{ justifyContent: 'center' }}>
      <View style={[styles.card, { backgroundColor: theme.background, borderColor: theme.border }]}>
        <Typography variant="h1" color={theme.primary} style={styles.title}>NoMoreLaps</Typography>
        <Typography variant="body" color={theme.textSecondary} style={styles.subtitle}>Welcome back</Typography>

        <InputField
          label="Email Address"
          placeholder="example@test.com"
          autoCapitalize="none"
          autoCorrect={false}
          value={email}
          onChangeText={setEmail}
          keyboardType="email-address"
        />

        <InputField
          label="Password"
          placeholder="••••••••"
          secureTextEntry
          value={password}
          onChangeText={setPassword}
        />

        <CustomButton
          title="Sign In"
          onPress={onLogin}
          loading={loading}
          style={{ marginTop: 8 }}
        />

        <TouchableOpacity style={styles.footerLink} onPress={goToHome}>
          <Typography variant="label" color={theme.textSecondary}>
            Continue as guest
          </Typography>
        </TouchableOpacity>

        <TouchableOpacity style={styles.footerLink} onPress={goToRegister}>
          <Typography variant="label" color={theme.primary}>
            Don't have an account? Sign Up
          </Typography>
        </TouchableOpacity>
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  scrollContent: {
    flexGrow: 1,
    justifyContent: 'center',
    paddingVertical: 20,
  },
  card: {
    borderRadius: 24,
    padding: 32,
    elevation: 10,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 20,
    shadowOffset: { width: 0, height: 10 },
    borderWidth: 1,
  },
  title: {
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    textAlign: 'center',
    marginBottom: 40,
  },
  footerLink: {
    marginTop: 24,
    alignItems: 'center',
  },
});

export default LoginView;
