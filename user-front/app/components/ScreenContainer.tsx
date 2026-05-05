import React from 'react';
import { KeyboardAvoidingView, Platform, StyleSheet, ScrollView, View, ViewStyle } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { useTheme } from '../context/ThemeContext';

interface ScreenContainerProps {
  children: React.ReactNode;
  withScroll?: boolean;
  style?: ViewStyle;
}

const ScreenContainer: React.FC<ScreenContainerProps> = ({ children, withScroll = true, style }) => {
  const { theme } = useTheme();
  const ContentWrapper = withScroll ? ScrollView : View;

  return (
    <SafeAreaView style={[styles.safe, { backgroundColor: theme.lightBackground }]} edges={['bottom']}>
      <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        style={{ flex: 1 }}
      >
        <ContentWrapper 
          style={{ flex: 1 }}
          contentContainerStyle={[withScroll ? styles.scroll : styles.view, style]}
        >
          {children}
        </ContentWrapper>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safe: { 
    flex: 1, 
  },
  scroll: { 
    padding: 24, 
    flexGrow: 1 
  },
  view: { 
    flex: 1, 
    padding: 24 
  },
});

export default ScreenContainer;
