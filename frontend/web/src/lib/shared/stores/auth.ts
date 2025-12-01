import { derived, get, writable, type Writable } from 'svelte/store';
import { decodeAuthJwt } from '../helpers/decode-jwt';
import { EUserRole } from '../enums/user-role.enum';

class AuthService {
	private localStorageTokenKey: string = 'auth_token';
	private static instance: AuthService;
	private userToken: Writable<string | null> = writable<string | null>(null);

	private constructor() {
		if (typeof window !== 'undefined') {
			const stored = localStorage.getItem(this.localStorageTokenKey);
			this.userToken.set(stored);
		}

		this.userToken.subscribe((newToken) => {
			console.log(decodeAuthJwt(newToken))
			if (newToken) {
				localStorage.setItem(this.localStorageTokenKey, newToken);
			} else {
				localStorage.removeItem(this.localStorageTokenKey);
			}
		});
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
	}

	public logout(): void {
		this.userToken.set(null);
	}

	decoded = derived(this.userToken, ($token) => ($token ? decodeAuthJwt($token) : null));

	isAuthenticated = derived(this.decoded, ($decoded) => {
		if (!$decoded) return false;
		return $decoded.exp > new Date();
	});

	isAdmin = derived(this.decoded, ($decoded) => {
		if (!$decoded) return false;
		return $decoded.role === EUserRole.ADMIN;
	});
}

export const authService = AuthService.getInstance();
