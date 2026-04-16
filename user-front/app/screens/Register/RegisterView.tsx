import React from 'react';
import {
    Text,
    View,
    TouchableOpacity,
    KeyboardAvoidingView,
    Platform,
    ScrollView
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { styles } from '../../styles/AuthStyles';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import CustomButton from '../../components/CustomButton';
import InputField from '../../components/InputField';

interface RegisterViewProps {
    name: string;
    setName: (text: string) => void;
    email: string;
    setEmail: (text: string) => void;
    password: string;
    setPassword: (text: string) => void;
    confirmPassword: string;
    setConfirmPassword: (text: string) => void;
    onRegister: () => void;
    isLoading: boolean;
}

const RegisterView: React.FC<RegisterViewProps> = ({
    name, setName, email, setEmail, password, setPassword, 
    confirmPassword, setConfirmPassword, onRegister, isLoading
}) => {

    const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
    const goToLogin = () => navigation.navigate('Login');

    return (
        <SafeAreaView style={{ flex: 1 }} edges={['bottom']}>
            <KeyboardAvoidingView
                behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                style={styles.container}
            >
                <ScrollView contentContainerStyle={{flexGrow: 1, justifyContent: 'center'}}>
                    <View style={styles.card}>
                        <Text style={styles.title}>NoMoreLaps</Text>
                        <Text style={styles.subtitle}>Create your account</Text>

                        <View style={styles.form}>
                            <InputField
                                label="Full Name"
                                placeholder="John Doe"
                                value={name}
                                onChangeText={setName}
                            />

                            <InputField
                                label="Email Address"
                                placeholder="example@test.com"
                                value={email}
                                onChangeText={setEmail}
                                keyboardType="email-address"
                                autoCapitalize="none"
                            />

                            <InputField
                                label="Password"
                                placeholder="••••••••"
                                value={password}
                                onChangeText={setPassword}
                                secureTextEntry
                            />

                            <InputField
                                label="Confirm Password"
                                placeholder="••••••••"
                                value={confirmPassword}
                                onChangeText={setConfirmPassword}
                                secureTextEntry
                            />

                            <CustomButton
                                title="Create Account"
                                onPress={onRegister}
                                loading={isLoading}
                            />

                            <TouchableOpacity style={styles.footerLink} onPress={goToLogin}>
                                <Text style={styles.footerText}>Already have an account? Sign In</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </ScrollView>
            </KeyboardAvoidingView>
        </SafeAreaView>
    );
};

export default RegisterView;
