export const ssr = false;

import { redirect } from '@sveltejs/kit';
import type { PageLoad } from './$types';
import { authService } from '$lib/shared/stores/auth';
import { get } from 'svelte/store';

export const load: PageLoad = () => {
	if (!get(authService.isAuthenticated)) {
		throw redirect(302, '/auth/login');
	}

	throw redirect(303, '/user/home');
};
