const tintColorLight = '#1A73E8';
const tintColorDark = '#4da3ff';

export const Colors = {
  light: {
    primary: '#1A73E8',
    background: '#FFFFFF',
    lightBackground: '#F8F9FA',
    text: '#202124',
    textSecondary: '#5F6368',
    danger: '#F87171', 
    success: '#34A853',
    border: '#DADCE0',
    tint: tintColorLight,
    tabIconDefault: '#ccc',
    tabIconSelected: tintColorLight,
  },
  dark: {
    primary: '#539BF5', // Azul pastel (confortable)
    background: '#2D333B', // Gris-azul mate (fondo)
    lightBackground: '#22272E', // Gris oscuro mate (tarjetas)
    text: '#ADBAC7', // Texto color plata/arena (brillo muy bajo)
    textSecondary: '#768390', // Gris tenue
    danger: '#F47067', // Rojo pastel
    success: '#57AB5A', // Verde pastel
    border: '#444C56', // Bordes sutiles
    tint: '#539BF5',
    tabIconDefault: '#768390',
    tabIconSelected: '#539BF5',
  },
};

export default Colors;
