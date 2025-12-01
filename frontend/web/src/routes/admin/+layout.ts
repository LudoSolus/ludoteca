export const ssr = false;

import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';
import { authService } from '$lib/shared/stores/auth';

export const load: LayoutLoad = async () => {
	if (!authService.isAuthenticated) {
		throw redirect(302, '/auth/login');
	}

    if(!authService.isAdmin){
        throw redirect(303, '/user/home');
    }

	return { };
};