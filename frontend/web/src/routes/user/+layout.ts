export const ssr = false;

import { get } from 'svelte/store';
import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';
import { authService } from '$lib/shared/stores/auth';
import { decodeAuthJwt } from '$lib/shared/helpers/decode-jwt';
import { UserRole } from '$lib/shared/enums/user-role.enum';

export const load: LayoutLoad = async () => {
	if (!authService.isAuthenticated()) {
		throw redirect(302, '/auth/login');
	}

	const tokenStore = authService.getUserToken();
	const userAuthData = get(tokenStore) ? decodeAuthJwt(get(tokenStore)) : null;

    if(!userAuthData){
        throw redirect(302, '/auth/login');
    }

    if(userAuthData.role == UserRole.ADMIN){
        throw redirect(303, '/admin');
    }

	return { userAuthData };
};