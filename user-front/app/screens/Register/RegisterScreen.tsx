import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import RegisterView from './RegisterView';

const RegisterScreen = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    
    const { login } = useAuth();
    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

    const handleRegister = async () => {
        if (!name || !email || !password) {
            Alert.alert('Error', 'Please fill all fields');
            return;
        }

        setLoading(true);
        // Simulamos registro y login automático
        setTimeout(async () => {
            // En el futuro aquí iría authService.register
            await login('mock-token-newuser');
            Alert.alert('Welcome!', 'Registration successful');
            navigation.replace('Home');
            setLoading(false);
        }, 1000);
    };

    return (
        <RegisterView
            name={name}
            setName={setName}
            email={email}
            setEmail={setEmail}
            password={password}
            setPassword={setPassword}
            onRegister={handleRegister}
            loading={loading}
        />
    );
};

export default RegisterScreen;
