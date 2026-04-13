import React, { useState } from 'react';
import { Alert } from 'react-native';
import authService from '../../services/authService';
import RegisterView from './RegisterView';

const RegisterScreen = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);

    const handleRegister = async () => {
        if (!name || !email || !password) {
            Alert.alert('Error', 'Please fill all fields');
            return;
        }

        setLoading(true);
        try {
            await authService.register({ name, email, password });
            Alert.alert('Success', 'Registration successful! Now you can login.');
        } catch (error: any) {
            Alert.alert('Registration Failed', error.message);
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
            onRegister={handleRegister}
            loading={loading}
        />
    );
};

export default RegisterScreen;
