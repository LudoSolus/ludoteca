<script lang="ts">
	import HeaderRoute from '../atoms/HeaderRoute.svelte';
	import ProfilePicture from '../atoms/ProfilePicture.svelte';
	import { page } from '$app/state';
	import { device } from '$lib/shared/hooks/useDevice';
	import Fa from 'svelte-fa';
	import { faBars } from '@fortawesome/free-solid-svg-icons';
	import IconButton from '../atoms/IconButton.svelte';

	interface Route {
		name: string;
		path: string;
	}

	let { type }: { type: 'admin' | 'user' } = $props();

	const currentPath = $derived(page.url.pathname);
	let menuIsVisible: boolean = false;

	const adminRoutes: Route[] = [
		{ name: 'Dashboard', path: '/admin' },
		{ name: 'Usuários', path: '/admin/users' },
		{ name: 'Eventos', path: '/admin/events' },
		{ name: 'Board Games', path: '/admin/board-games' },
		{ name: 'RPG', path: '/admin/rpg' },
		{ name: 'Escape Room', path: '/admin/escape-room' }
	];

	const userRoutes: Route[] = [
		{ name: 'Eventos', path: '/user/home' },
		{ name: 'Aventuras', path: '/user/history' }
	];

	function toogleMenuVisibility() {
		menuIsVisible = !menuIsVisible;
	}
</script>

<header class="header-box flex h-15 w-full items-center justify-between px-7 py-2">
	{#if $device == 'desktop'}
		<div class="flex items-center justify-start gap-4">
			{#each type == 'admin' ? adminRoutes : userRoutes as route (route.path)}
				<HeaderRoute name={route.name} path={route.path} selected={route.path == currentPath} />
			{/each}
		</div>
	{:else}
		<IconButton icon={faBars} onClick={toogleMenuVisibility} width="35px" height="35px" variant="naked"/>
	{/if}
	<div>
		<ProfilePicture userName="Felipe" width="45px" height="45px" fontSize="18px" />
	</div>
</header>

<style>
	.header-box {
		background: var(--primary-color);
		overflow: hidden;
	}
</style>
