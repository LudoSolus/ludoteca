export const ssr = false;

import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';
import { authService } from '$lib/shared/stores/auth';

export const load: LayoutLoad = async () => {
	if (!authService.isAuthenticated()) {
		throw redirect(302, '/auth/login');
	}

	return {};
};
