export const ssr = false;

import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';
import { authService } from '$lib/shared/stores/auth';
import { get } from 'svelte/store';

export const load: LayoutLoad = async () => {
	if (!get(authService.isAuthenticated)) {
		throw redirect(302, '/auth/login');
	}

    if(!get(authService.isAdmin)){
        throw redirect(303, '/user/home');
    }

	return { };
};