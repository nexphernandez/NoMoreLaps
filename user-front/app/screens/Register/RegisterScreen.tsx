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

    useEffect(() => {
        const newErrors: any = {};
        
        if (name && !name.trim()) newErrors.name = 'Name is required';
        
        if (email) {
            if (!isValidEmail(email.trim())) {
                newErrors.email = 'Invalid email format';
            }
        }
        
        if (password) {
            const pwdError = getPasswordError(password.trim());
            if (pwdError) {
                newErrors.password = pwdError;
            }
        }

        if (confirmPassword && password.trim() !== confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match';
        }

        setErrors(newErrors);
    }, [name, email, password, confirmPassword]);

    const validate = () => {
        const newErrors: any = {};
        if (!name.trim()) newErrors.name = 'Name is required';
        if (!email.trim()) {
            newErrors.email = 'Email is required';
        } else if (!isValidEmail(email.trim())) {
            newErrors.email = 'Invalid email format';
        }
        
        const pwdError = getPasswordError(password.trim());
        if (pwdError) {
            newErrors.password = pwdError;
        }

        if (!confirmPassword) {
            newErrors.confirmPassword = 'Please confirm your password';
        } else if (password.trim() !== confirmPassword) {
            newErrors.confirmPassword = 'Passwords do not match';
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
            await authService.register({
                name: cleanName,
                email: cleanEmail,
                password: cleanPassword
            });

            const authResponse = await authService.login({ 
                email: cleanEmail, 
                password: cleanPassword 
            });

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
                
                Alert.alert('Welcome!', `Hello ${cleanName}, your account has been created and you are now logged in.`);
                navigation.replace('Home');
            }
        } catch (error: any) {
            console.error('Register error details:', error);
            let message = 'Could not complete registration. Please try again later.';
            
            if (error.response?.status === 409) {
                message = 'This email address is already registered. Please try another one.';
            } else if (error.message && error.message.includes('network')) {
                message = 'Network error. Please check your internet connection.';
            }

            Alert.alert('Registration Error', message);
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
