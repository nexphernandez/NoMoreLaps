import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import LoginView from './LoginView';
import authService from '../../services/authService';
import userService from '../../services/userService';
import api from '../../services/api';
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
    const cleanEmail = email.trim();
    const cleanPassword = password.trim();

    if (!cleanEmail || !cleanPassword) {
      Alert.alert('Error', 'Por favor, rellena todos los campos');
      return;
    }

    setLoading(true);
    try {
      // 1. LOGIN PARA OBTENER EL TOKEN
      const authResponse = await authService.login({ email: cleanEmail, password: cleanPassword });
      
      if (authResponse.token) {
        // Inyectamos el token manualmente para esta sesión inmediata
        api.defaults.headers.common['Authorization'] = `Bearer ${authResponse.token}`;

        // 2. OBTENER DATOS DEL USUARIO USANDO EL EMAIL
        // (El interceptor de Axios ya inyectará el token si lo acabamos de recibir, 
        // pero para estar seguros lo manejamos bien)
        const userDetails = await userService.getUserByEmail(cleanEmail);
        
        // 3. LOGUEAR EN EL CONTEXTO (Garantiza persistencia)
        await login(authResponse.token, userDetails);
        
        navigation.replace('Home');
      }
    } catch (error: any) {
      Alert.alert(
        'Error de Inicio de Sesión', 
        error.message || 'No se pudo conectar con el servidor.'
      );
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
      onLogin={handleLogin}
      loading={loading}
    />
  );
};

export default LoginScreen;
