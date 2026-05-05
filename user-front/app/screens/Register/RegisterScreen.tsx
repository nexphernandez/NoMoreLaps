import React, { useState, useEffect } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import RegisterView from './RegisterView';
import { isValidEmail, getPasswordError } from '../../utils/validation';
import authService from '../../services/authService';
import userService from '../../services/userService';
import api from '../../services/api';

const RegisterScreen = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [errors, setErrors] = useState<{name?: string, email?: string, password?: string, confirmPassword?: string}>({});
    
    const { login } = useAuth();
    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

    // Real-time validation
    useEffect(() => {
        const newErrors: any = {};
        
        // Only validate if the user has started typing in the field
        if (name && !name.trim()) newErrors.name = 'El nombre es obligatorio';
        
        if (email) {
            if (!isValidEmail(email.trim())) {
                newErrors.email = 'El formato del email no es válido';
            }
        }
        
        if (password) {
            const pwdError = getPasswordError(password.trim());
            if (pwdError) {
                newErrors.password = pwdError;
            }
        }

        if (confirmPassword && password.trim() !== confirmPassword) {
            newErrors.confirmPassword = 'Las contraseñas no coinciden';
        }

        setErrors(newErrors);
    }, [name, email, password, confirmPassword]);

    const validate = () => {
        const newErrors: any = {};
        if (!name.trim()) newErrors.name = 'El nombre es obligatorio';
        if (!email.trim()) {
            newErrors.email = 'El email es obligatorio';
        } else if (!isValidEmail(email.trim())) {
            newErrors.email = 'El formato del email no es válido';
        }
        
        const pwdError = getPasswordError(password.trim());
        if (pwdError) {
            newErrors.password = pwdError;
        }

        if (!confirmPassword) {
            newErrors.confirmPassword = 'Por favor confirma tu contraseña';
        } else if (password.trim() !== confirmPassword) {
            newErrors.confirmPassword = 'Las contraseñas no coinciden';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleRegister = async () => {
        if (!validate()) return;

        const cleanName = name.trim();
        const cleanEmail = email.trim().toLowerCase();
        const cleanPassword = password.trim();

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
                await login(authResponse.token, {
                  id: userDetails.id,
                  name: userDetails.name,
                  email: userDetails.email,
                  phone: '',
                  avatar: undefined
                });
                
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
            errors={errors}
            onRegister={handleRegister}
            isLoading={loading}
        />
    );
};

export default RegisterScreen;
