import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as ImagePicker from 'expo-image-picker';
import EditProfileView from './EditProfileView';
import { useAuth } from '../../context/AuthContext';

const EditProfileScreen = () => {
    const { user, updateUser } = useAuth();
    
    // Estados inicializados con los datos del contexto
    const [name, setName] = useState(user?.name || '');
    const [email, setEmail] = useState(user?.email || '');
    const [phone, setPhone] = useState(user?.phone || '');
    const [avatar, setAvatar] = useState(user?.avatar || null);
    const [loading, setLoading] = useState(false);

    const navigation = useNavigation();

    // Función para pedir permisos y elegir imagen
    const pickImage = async () => {
        const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
        
        if (status !== 'granted') {
            Alert.alert('Permission Denied', 'We need access to your gallery to change the photo.');
            return;
        }

        const result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: [1, 1],
            quality: 1,
        });

        if (!result.canceled) {
            setAvatar(result.assets[0].uri);
        }
    };

    const handleSave = () => {
        setLoading(true);
        setTimeout(() => {
            // Guardamos todo en el contexto global
            updateUser({ name, email, phone, avatar: avatar || undefined });
            setLoading(false);
            Alert.alert('Success', 'Profile updated correctly');
            navigation.goBack();
        }, 1500);
    };

    return (
        <EditProfileView
            name={name} setName={setName}
            email={email} setEmail={setEmail}
            phone={phone} setPhone={setPhone}
            avatar={avatar}
            onPickImage={pickImage} // <-- Pasamos la función
            onSave={handleSave}
            loading={loading}
        />
    );
};

export default EditProfileScreen;
