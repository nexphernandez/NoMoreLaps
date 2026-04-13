import React from 'react';
import {
    Text,
    View,
    TextInput,
    TouchableOpacity,
    ActivityIndicator,
    KeyboardAvoidingView,
    Platform
} from 'react-native';
import { styles } from '../../styles/AuthStyles';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { AuthStackParamList } from '../../navigation/AuthNavigator';

interface RegisterViewProps {
    name: string;
    setName: (text: string) => void;
    email: string;
    setEmail: (text: string) => void;
    password: string;
    setPassword: (text: string) => void;
    onRegister: () => void;
    loading: boolean;
}

const RegisterView: React.FC<RegisterViewProps> = ({
    name, setName, email, setEmail, password, setPassword, onRegister, loading
}) => {

    const navigation = useNavigation<StackNavigationProp<AuthStackParamList>>();
    const goToLogin = () => navigation.navigate('Login');

    return (
        <KeyboardAvoidingView
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
            style={styles.container}
        >
            <View style={styles.card}>
                <Text style={styles.title}>NoMoreLaps</Text>
                <Text style={styles.subtitle}>Create your account</Text>

                <TextInput
                    style={styles.input}
                    placeholder="Full Name"
                    placeholderTextColor="#94a3b8"
                    value={name}
                    onChangeText={setName}
                />

                <TextInput
                    style={styles.input}
                    placeholder="Email address"
                    placeholderTextColor="#94a3b8"
                    value={email}
                    onChangeText={setEmail}
                    autoCapitalize="none"
                    keyboardType="email-address"
                />

                <TextInput
                    style={styles.input}
                    placeholder="Password"
                    placeholderTextColor="#94a3b8"
                    secureTextEntry
                    value={password}
                    onChangeText={setPassword}
                />

                <TouchableOpacity
                    style={styles.button}
                    onPress={onRegister}
                    disabled={loading}
                >
                    {loading ? (
                        <ActivityIndicator color="#fff" />
                    ) : (
                        <Text style={styles.buttonText}>Register</Text>
                    )}
                </TouchableOpacity>

                <TouchableOpacity style={styles.footerLink} onPress={goToLogin}>
                    <Text style={styles.footerText}>Already have an account? Sign In</Text>
                </TouchableOpacity>
            </View>
        </KeyboardAvoidingView>
    );
};

export default RegisterView;
