import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import LoginView from './LoginView';


import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

const LoginScreen = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const handleLogin = async () => {
    const cleanEmail = email.trim().toLowerCase();
    const cleanPassword = password.trim();

    if (!cleanEmail || !cleanPassword) {
      Alert.alert('Error', 'Please fill all fields');
      return;
    }

    setLoading(true);
    setTimeout(async () => {
      if (cleanEmail === 'user@test.com' && cleanPassword === '123456') {
        await login('mock-token-123');
        navigation.replace('Home');
      } else {
        Alert.alert(
          'Login Failed', 
          'Invalid credentials.\n\nHint: user@test.com / 123456'
        );
      }
      setLoading(false);
    }, 800);
  };

  return (
    <LoginView
      email={email}
      setEmail={setEmail}
      password={password}
      setPassword={setPassword}
      onLogin={handleLogin}
      loading={loading}
    />
  );
};

export default LoginScreen;
