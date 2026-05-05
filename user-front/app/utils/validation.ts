/**
 * Validation utility for user forms.
 */

/**
 * Validates if an email is in a correct format.
 */
export const isValidEmail = (email: string): boolean => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

/**
 * Validates if a password meets the required complexity.
 * Requirements: At least 8 characters, contains at least one special character from [. @ # $ % ^ & *]
 */
export const isValidPassword = (password: string): boolean => {
  if (password.length < 8) return false;
  const specialCharRegex = /[.@#$%^&*]/;
  return specialCharRegex.test(password);
};

/**
 * Returns a descriptive error message for password validation.
 */
export const getPasswordError = (password: string): string | null => {
  if (password.length < 8) {
    return 'La contraseña debe tener al menos 8 caracteres';
  }
  if (!isValidPassword(password)) {
    return 'Debe contener al menos un carácter especial (ej: . @ # $)';
  }
  return null;
};
