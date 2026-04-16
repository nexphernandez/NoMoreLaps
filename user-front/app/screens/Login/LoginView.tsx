import React from 'react';
import {
    Text,
    View,
    TouchableOpacity,
    KeyboardAvoidingView,
    Platform
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { styles } from '../../styles/AuthStyles';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { Colors } from '../../constants/Colors';
import CustomButton from '../../components/CustomButton';
import InputField from '../../components/InputField';

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
        <SafeAreaView style={{ flex: 1 }} edges={['bottom']}>
            <KeyboardAvoidingView
                behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                style={styles.container}
            >
                <View style={styles.card}>
                    <Text style={styles.title}>NoMoreLaps</Text>
                    <Text style={styles.subtitle}>Welcome back</Text>

                    <InputField
                        label="Email Address"
                        placeholder="example@test.com"
                        autoCapitalize="none"
                        autoCorrect={false}
                        value={email}
                        onChangeText={setEmail}
                        keyboardType="email-address"
                    />

                    <InputField
                        label="Password"
                        placeholder="••••••••"
                        secureTextEntry
                        value={password}
                        onChangeText={setPassword}
                    />

                    <CustomButton
                        title="Sign In"
                        onPress={onLogin}
                        loading={loading}
                    />
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
        </SafeAreaView>
    );
};

export default LoginView;
