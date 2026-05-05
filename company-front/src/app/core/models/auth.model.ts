export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  cif: string;
}

export interface AuthResponse {
  token: string;
  companyId: number;
  email: string;
  name: string;
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  phone?: string;
  avatar?: string;
}
