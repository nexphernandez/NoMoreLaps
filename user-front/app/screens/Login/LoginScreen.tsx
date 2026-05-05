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
      newErrors.email = 'El formato del email no es válido';
    }
    setErrors(newErrors);
  }, [email, password]);

  const validate = () => {
    const newErrors: any = {};
    if (!email.trim()) {
      newErrors.email = 'El email es obligatorio';
    } else if (!isValidEmail(email.trim())) {
      newErrors.email = 'El formato del email no es válido';
    }
    
    if (!password.trim()) {
      newErrors.password = 'La contraseña es obligatoria';
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
      errors={errors}
      onLogin={handleLogin}
      loading={loading}
    />
  );
};

export default LoginScreen;
