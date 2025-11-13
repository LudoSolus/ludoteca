import { writable, type Writable } from 'svelte/store';

class AuthService {
	private localStorageTokenKey: string = 'auth_token';
	private static instance: AuthService;
	private userToken: Writable<string | null>;

	private constructor() {
        console.log("no constructor")
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
		return !!token;
	}
}

export const authService = AuthService.getInstance();