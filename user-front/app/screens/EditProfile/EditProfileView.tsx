import React from 'react';
import { View, StyleSheet, TouchableOpacity, Image } from 'react-native';
import CustomButton from '../../components/CustomButton';
import InputField from '../../components/InputField';
import ScreenContainer from '../../components/ScreenContainer';
import Typography from '../../components/Typography';
import { useTheme } from '../../context/ThemeContext';
import { MaterialCommunityIcons } from '@expo/vector-icons';

interface EditProfileViewProps {
  name: string;
  setName: (text: string) => void;
  email: string;
  setEmail: (text: string) => void;
  phone: string;
  setPhone: (text: string) => void;
  avatar: string | null;
  onPickImage: () => void;
  onSave: () => void;
  loading: boolean;
}

const EditProfileView: React.FC<EditProfileViewProps> = ({
  name, setName, email, setEmail, phone, setPhone, avatar, onPickImage, onSave, loading
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer>
        {/* HEADER CON FOTO */}
        <View style={styles.header}>
          <TouchableOpacity onPress={onPickImage} style={styles.avatarContainer}>
            {avatar ? (
              <Image source={{ uri: avatar }} style={styles.avatar} />
            ) : (
              <View style={[styles.placeholderAvatar, { backgroundColor: theme.lightBackground }]}>
                <Typography variant="h1" color={theme.primary}>
                  {name.charAt(0).toUpperCase()}
                </Typography>
              </View>
            )}
            <View style={[styles.cameraIcon, { backgroundColor: theme.primary, borderColor: '#FFF' }]}>
              <MaterialCommunityIcons name="camera" size={16} color="#FFF" />
            </View>
          </TouchableOpacity>
          <Typography variant="h2" style={{marginTop: 16}}>{name}</Typography>
          <Typography variant="caption">Change profile photo</Typography>
        </View>

        {/* FORMULARIO */}
        <View style={styles.form}>
          <InputField
            label="Full Name"
            value={name}
            onChangeText={setName}
            placeholder="e.g. John Doe"
          />

          <InputField
            label="Email Address"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            placeholder="e.g. john@example.com"
          />

          <InputField
            label="Phone Number"
            value={phone}
            onChangeText={setPhone}
            keyboardType="phone-pad"
            placeholder="e.g. +34 600..."
          />
        </View>

        <CustomButton
          title="Save Changes"
          onPress={onSave}
          loading={loading}
        />

    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { alignItems: 'center', marginBottom: 40 },
  avatarContainer: { position: 'relative' },
  avatar: { width: 100, height: 100, borderRadius: 50 },
  placeholderAvatar: {
    width: 100,
    height: 100,
    borderRadius: 50,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#E2E8F0', 
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 10,
    shadowOffset: { width: 0, height: 4 },
  },
  cameraIcon: {
    position: 'absolute',
    bottom: 0,
    right: 0,
    width: 32,
    height: 32,
    borderRadius: 16,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
    elevation: 4,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
    shadowOffset: { width: 0, height: 2 },
  },
  form: { gap: 20, marginBottom: 40 },
});

export default EditProfileView;
