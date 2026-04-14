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
import { RootStackParamList } from '../../navigation/RootNavigator';
import { Colors } from '../../constants/Colors';

interface LoginViewProps {
    email: string;
    setEmail: (text: string) => void;
    password: string;
    setPassword: (text: string) => void;
    onLogin: () => void;
    loading: boolean;
}

const LoginView: React.FC<LoginViewProps> = ({
    email, setEmail, password, setPassword, onLogin, loading
}) => {

    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
    const goToRegister = () => navigation.navigate('Register');
    const goToHome = () => navigation.navigate('Home');

    return (
        <KeyboardAvoidingView
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
            style={styles.container}
        >
            <View style={styles.card}>
                <Text style={styles.title}>NoMoreLaps</Text>
                <Text style={styles.subtitle}>Welcome back</Text>

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
                    onPress={onLogin}
                    disabled={loading}
                >
                    {loading ? (
                        <ActivityIndicator color="#fff" />
                    ) : (
                        <Text style={styles.buttonText}>Sign In</Text>
                    )}
                </TouchableOpacity>
                <TouchableOpacity style={styles.footerLink} onPress={goToHome}>
                    <Text style={styles.footerTextSecondary}>
                        Continue as guest
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.footerLink} onPress={goToRegister}>
                    <Text style={styles.footerText}>Don't have an account? Sign Up</Text>
                </TouchableOpacity>
            </View>
        </KeyboardAvoidingView>
    );
};

export default LoginView;
