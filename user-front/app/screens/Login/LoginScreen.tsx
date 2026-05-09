import React, { useState, useEffect } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import LoginView from './LoginView';
import authService from '../../services/authService';
import userService from '../../services/userService';
import api from '../../services/api';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

import { isValidEmail } from '../../utils/validation';

const LoginScreen = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState<{email?: string, password?: string}>({});
  const { login } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  // Real-time validation
  useEffect(() => {
    const newErrors: any = {};
    if (email && !isValidEmail(email.trim())) {
      newErrors.email = 'Invalid email format';
    }
    setErrors(newErrors);
  }, [email, password]);

  const validate = () => {
    const newErrors: any = {};
    if (!email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!isValidEmail(email.trim())) {
      newErrors.email = 'Invalid email format';
    }
    
    if (!password.trim()) {
      newErrors.password = 'Password is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleLogin = async () => {
    if (!validate()) return;

    const cleanEmail = email.trim();
    const cleanPassword = password.trim();

    setLoading(true);
    try {
      const authResponse = await authService.login({ email: cleanEmail, password: cleanPassword });
      
      if (authResponse.token) {
        api.defaults.headers.common['Authorization'] = `Bearer ${authResponse.token}`;

        
        const userDetails = await userService.getUserByEmail(cleanEmail);
        
        await login(authResponse.token, {
          id: userDetails.id,
          name: userDetails.name,
          email: userDetails.email,
          phone: '',
          avatar: undefined
        });
        
        navigation.replace('Home');
      }
    } catch (error: any) {
      console.error('Login error details:', error);
      const message = error.response?.status === 401 
        ? 'Email or password incorrect. Please try again.' 
        : 'Could not connect to the server. Please check your connection.';
      
      Alert.alert('Login Error', message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <LoginView
      email={email}
      setEmail={setEmail}
      password={password}
      setPassword={setPassword}
      errors={errors}
      onLogin={handleLogin}
      loading={loading}
    />
  );
};

export default LoginScreen;
