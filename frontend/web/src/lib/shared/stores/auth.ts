import { writable, type Writable } from 'svelte/store';
import { decodeAuthJwt } from '../helpers/decode-jwt';

class AuthService {
	private localStorageTokenKey: string = 'auth_token';
	private static instance: AuthService;
	private userToken: Writable<string | null>;

	private constructor() {
		const storedToken = typeof window !== 'undefined' ? localStorage.getItem('auth_token') : null;

		this.userToken = writable<string | null>(storedToken);
	}

	public static getInstance(): AuthService {
		if (!AuthService.instance) {
			AuthService.instance = new AuthService();
		}
		return AuthService.instance;
	}

	public getUserToken(): Writable<string | null> {
		return this.userToken;
	}

	public login(token: string): void {
		this.userToken.set(token);
		localStorage.setItem(this.localStorageTokenKey, token);
	}

	public logout(): void {
		this.userToken.set(null);
		localStorage.removeItem(this.localStorageTokenKey);
	}

	public isAuthenticated(): boolean {
		const token = localStorage.getItem(this.localStorageTokenKey);
		if(!token) return false;

		const decoded = decodeAuthJwt(token)
		if(!decoded) return false;
		if(decoded.exp < new Date()) return false;

		return true;
	}
}

export const authService = AuthService.getInstance();