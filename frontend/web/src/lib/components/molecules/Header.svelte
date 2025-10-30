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
	let menuIsVisible: boolean = $state(false);

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

	function hiddeMenu() {
		menuIsVisible = false;
	}

	function handleOnOverlayClick() {
		if (menuIsVisible) {
			hiddeMenu();
		}
	}

	const profilePictureSize = $derived($device == 'desktop' ? '45px' : '40px');
</script>

<header class="header-box flex h-15 w-full items-center justify-between px-7 py-2">
	{#if $device == 'desktop'}
		<div class="flex items-center justify-start gap-4">
			{#each type == 'admin' ? adminRoutes : userRoutes as route (route.path)}
				<HeaderRoute name={route.name} path={route.path} selected={route.path == currentPath} />
			{/each}
		</div>
	{:else}
		<IconButton
			icon={faBars}
			onClick={toogleMenuVisibility}
			width="40px"
			height="40px"
			variant="naked"
		/>
	{/if}
	<div>
		<ProfilePicture
			userName="Felipe"
			width={profilePictureSize}
			height={profilePictureSize}
			fontSize="18px"
		/>
	</div>
	{#if $device == 'mobile' && menuIsVisible}
		<div class="header-menu absolute top-15 flex flex-col items-center justify-start gap-2 p-2">
			{#each type == 'admin' ? adminRoutes : userRoutes as route (route.path)}
				<HeaderRoute
					name={route.name}
					path={route.path}
					selected={route.path == currentPath}
					onClick={hiddeMenu}
				/>
			{/each}
		</div>
	{/if}
</header>
{#if $device == 'mobile' && menuIsVisible}
	<!-- svelte-ignore a11y_click_events_have_key_events -->
	<!-- svelte-ignore a11y_no_static_element_interactions -->
	<div class="overlay" onclick={handleOnOverlayClick}></div>
{/if}

<style>
	.header-box {
		position: relative;
		background: var(--primary-color);
		z-index: 11;
	}

	.header-menu {
		background: var(--primary-color);
		border: 2px solid #000;
		z-index: 12;
	}

	.overlay {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.3);
		backdrop-filter: blur(5px);
		z-index: 10;
	}
</style>
