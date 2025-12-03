export const ssr = false;

import type { LayoutLoad } from './$types';
import { redirect } from '@sveltejs/kit';
import { authService } from '$lib/shared/stores/auth';
import { get } from 'svelte/store';

export const load: LayoutLoad = async () => {
    if (!get(authService.isAuthenticated)) {
        throw redirect(302, '/auth/login');
    }
    return {};
};

