import React, { useEffect, useState } from 'react';
import SanctionsView from './SanctionsView';
import { useAuth } from '../../context/AuthContext';
import sanctionService, { Sanction } from '../../services/sanctionService';
import { ActivityIndicator, View, Alert } from 'react-native';

const SanctionsScreen = () => {
  const { user } = useAuth();
  const [sanctions, setSanctions] = useState<Sanction[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      loadSanctions();
    }
  }, [user]);

  const loadSanctions = async () => {
    try {
      setLoading(true);
      const data = await sanctionService.getByUserId(user!.id);
      setSanctions(data);
    } catch (error) {
      console.error('Error loading sanctions:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#3B82F6" />
      </View>
    );
  }

  return (
    <SanctionsView 
      sanctions={sanctions} 
    />
  );
};

export default SanctionsScreen;
