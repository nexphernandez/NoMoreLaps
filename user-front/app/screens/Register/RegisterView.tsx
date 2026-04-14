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
import { SafeAreaView } from 'react-native-safe-area-context';
import { styles } from '../../styles/AuthStyles';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

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

    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
    const goToLogin = () => navigation.navigate('Login');
    const goToHome = () => navigation.navigate('Home');

    return (
        <SafeAreaView style={{ flex: 1 }} edges={['bottom']}>
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

                    <TouchableOpacity style={styles.footerLink} onPress={goToHome}>
                        <Text style={styles.footerTextSecondary}>
                            Continue as guest
                        </Text>
                    </TouchableOpacity>


                    <TouchableOpacity style={styles.footerLink} onPress={goToLogin}>
                        <Text style={styles.footerText}>Already have an account? Sign In</Text>
                    </TouchableOpacity>
                </View>
            </KeyboardAvoidingView>
        </SafeAreaView>
    );
};

export default RegisterView;
