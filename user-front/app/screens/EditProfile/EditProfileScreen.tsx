import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import * as ImagePicker from 'expo-image-picker';
import EditProfileView from './EditProfileView';
import { useAuth } from '../../context/AuthContext';
import userService from '../../services/userService';

const EditProfileScreen = () => {
    const { user, updateUser } = useAuth();
    
    const [name, setName] = useState(user?.name || '');
    const [email, setEmail] = useState(user?.email || '');
    const [phone, setPhone] = useState(user?.phone || '');
    const [avatar, setAvatar] = useState(user?.avatar || null);
    const [loading, setLoading] = useState(false);

    const navigation = useNavigation();

    const pickImage = async () => {
        const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
        
        if (status !== 'granted') {
            Alert.alert('Permission Denied', 'We need access to your gallery to change the photo.');
            return;
        }

        const result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ['images'],
            allowsEditing: true,
            aspect: [1, 1],
            quality: 1,
        });

        if (!result.canceled) {
            setAvatar(result.assets[0].uri);
        }
    };

    const handleSave = async () => {
        if (!user) return;

        setLoading(true);
        try {
            const updatedUser = await userService.updateProfile({
                id: user.id,
                name,
                email,
                calendarEnable: false // Default — UserData doesn't store this field
            });
            
            // Sync with global context — only update what we know
            updateUser({ name, email });
            
            Alert.alert('Success', 'Profile updated correctly');
            navigation.goBack();
        } catch (error: any) {
            Alert.alert('Error', error.message || 'Failed to update profile');
        } finally {
            setLoading(false);
        }
    };

    return (
        <EditProfileView
            name={name} setName={setName}
            email={email} setEmail={setEmail}
            phone={phone} setPhone={setPhone}
            avatar={avatar}
            onPickImage={pickImage}
            onSave={handleSave}
            loading={loading}
        />
    );
};

export default EditProfileScreen;
