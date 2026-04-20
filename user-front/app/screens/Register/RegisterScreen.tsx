import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import RegisterView from './RegisterView';
import authService from '../../services/authService';
import userService from '../../services/userService';
import api from '../../services/api';

const RegisterScreen = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [loading, setLoading] = useState(false);
    
    const { login } = useAuth();
    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

    const handleRegister = async () => {
        const cleanName = name.trim();
        const cleanEmail = email.trim().toLowerCase();
        const cleanPassword = password.trim();

        if (!cleanName || !cleanEmail || !cleanPassword || !confirmPassword) {
            Alert.alert('Error', 'Por favor, rellena todos los campos');
            return;
        }

        if (cleanPassword !== confirmPassword) {
            Alert.alert('Error', 'Las contraseñas no coinciden');
            return;
        }

        setLoading(true);
        try {
            // 1. REGISTRO REAL
            await authService.register({
                name: cleanName,
                email: cleanEmail,
                password: cleanPassword
            });

            // 2. LOGIN AUTOMÁTICO
            const authResponse = await authService.login({ 
                email: cleanEmail, 
                password: cleanPassword 
            });

            if (authResponse.token) {
                // Inyectamos el token momentáneamente
                api.defaults.headers.common['Authorization'] = `Bearer ${authResponse.token}`;

                // 3. OBTENER DATOS PARA EL CONTEXTO
                const userDetails = await userService.getUserByEmail(cleanEmail);
                
                // 4. GUARDAR Y ENTRAR
                await login(authResponse.token, userDetails);
                
                Alert.alert('¡Bienvenido!', `Hola ${cleanName}, tu cuenta ha sido creada y ya has iniciado sesión.`);
                navigation.replace('Home');
            }
        } catch (error: any) {
            Alert.alert(
                'Error en el proceso',
                error.message || 'No se pudo completar el registro automático.'
            );
        } finally {
            setLoading(false);
        }
    };

    return (
        <RegisterView
            name={name}
            setName={setName}
            email={email}
            setEmail={setEmail}
            password={password}
            setPassword={setPassword}
            confirmPassword={confirmPassword}
            setConfirmPassword={setConfirmPassword}
            onRegister={handleRegister}
            isLoading={loading}
        />
    );
};

export default RegisterScreen;
