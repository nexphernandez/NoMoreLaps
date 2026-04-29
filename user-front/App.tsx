import 'react-native-gesture-handler';
import { StatusBar } from 'expo-status-bar';
import { NavigationContainer } from '@react-navigation/native';
import { AuthProvider } from './app/context/AuthContext';
import { ThemeProvider } from './app/context/ThemeContext';
import RootNavigator from './app/navigation/RootNavigator';

export default function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <NavigationContainer>
          <RootNavigator />
          <StatusBar style="auto" />
        </NavigationContainer>
      </AuthProvider>
    </ThemeProvider>
  );
}
