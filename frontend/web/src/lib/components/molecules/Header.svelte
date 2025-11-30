<script lang="ts">
	import HeaderRoute from '../atoms/HeaderRoute.svelte';
	import ProfilePicture from '../atoms/ProfilePicture.svelte';
	import { page } from '$app/state';
	import { device } from '$lib/shared/hooks/useDevice';
	import { faBars } from '@fortawesome/free-solid-svg-icons';
	import IconButton from '../atoms/IconButton.svelte';
	import ProfileMenu from '../atoms/ProfileMenu.svelte';
	import { authService } from '$lib/shared/stores/auth';
	import { goto } from '$app/navigation';
	import { decodeAuthJwt } from '$lib/shared/helpers/decode-jwt';

	interface Route {
		name: string;
		path: string;
	}

	let { type }: { type: 'admin' | 'user' } = $props();

	const profilePictureSize = $derived($device == 'desktop' ? '45px' : '40px');
	const currentPath = $derived(page.url.pathname);
	let menuIsVisible: boolean = $state(false);
	let profileMenuIsVisible: boolean = $state(false);
	const token = authService.getUserToken();
	let authData = $derived(decodeAuthJwt($token));

	const adminRoutes: Route[] = [
		{ name: 'Dashboard', path: '/admin' },
		{ name: 'Usuários', path: '/admin/users' },
		{ name: 'Eventos', path: '/admin/events' },
		{ name: 'Board Games', path: '/admin/board-games' }
	];

	const publicRoutes: Route[] = [
		{ name: 'Ínicio', path: '/user/home' },
		{ name: 'Histórico', path: '/user/history' }
	];

	function toogleMenuVisibility() {
		if (!menuIsVisible && profileMenuIsVisible) {
			hiddeProfileMenu();
		}
		menuIsVisible = !menuIsVisible;
	}

	function hiddeMenu() {
		menuIsVisible = false;
	}

	function toogleProfileMenuVisibility() {
		if (!profileMenuIsVisible && menuIsVisible) {
			hiddeMenu();
		}
		profileMenuIsVisible = !profileMenuIsVisible;
	}

	function hiddeProfileMenu() {
		profileMenuIsVisible = false;
	}

	function handleOnOverlayClick() {
		if (menuIsVisible) {
			hiddeMenu();
		}
		if (profileMenuIsVisible) {
			hiddeProfileMenu();
		}
	}

	function handleOnLogout() {
		hiddeProfileMenu();
		authService.logout();
		goto('/auth/login');
	}

	function handleOnProfileClick() {
		hiddeProfileMenu();
		goto('/user/profile');
	}
</script>

<header class="header-box flex h-15 w-full items-center justify-between px-7 py-2">
	{#if $device == 'desktop'}
		<div class="flex items-center justify-start gap-4">
			{#each type == 'admin' ? Array.prototype.concat(publicRoutes, adminRoutes) : publicRoutes as route (route.path)}
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
			userName={authData!.name}
			width={profilePictureSize}
			height={profilePictureSize}
			fontSize="18px"
			onClick={toogleProfileMenuVisibility}
		/>
	</div>

	{#if $device == 'mobile' && menuIsVisible}
		<div
			class="header-menu absolute top-16 flex flex-col items-center justify-start gap-2 rounded-lg p-2 shadow-lg"
		>
			{#each type == 'admin' ? Array.prototype.concat(publicRoutes, adminRoutes) : publicRoutes as route (route.path)}
				<HeaderRoute
					name={route.name}
					path={route.path}
					selected={route.path == currentPath}
					onClick={hiddeMenu}
				/>
			{/each}
		</div>
	{/if}

	{#if profileMenuIsVisible}
		<div class="absolute top-15 right-3 z-12">
			<ProfileMenu
				userPublicId={authData!.publicId}
				onLogoutClick={handleOnLogout}
				onProfileClick={handleOnProfileClick}
			/>
		</div>
	{/if}
</header>

{#if ($device == 'mobile' && menuIsVisible) || profileMenuIsVisible}
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
		border: 1px solid #000;
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
