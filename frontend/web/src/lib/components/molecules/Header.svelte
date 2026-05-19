<script lang="ts">
	import HeaderRoute from '../atoms/HeaderRoute.svelte';
	import ProfilePicture from '../atoms/ProfilePicture.svelte';
	import { page } from '$app/state';
	import { device } from '$lib/shared/hooks/useDevice';
	import { faBars, faChessBoard, faTimes } from '@fortawesome/free-solid-svg-icons';
	import IconButton from '../atoms/IconButton.svelte';
	import ProfileMenu from '../atoms/ProfileMenu.svelte';
	import { authService } from '$lib/shared/stores/auth';
	import { goto } from '$app/navigation';
	import type { JwtAuthData } from '$lib/shared/helpers/decode-jwt';
	import Fa from 'svelte-fa';

	interface Route {
		name: string;
		path: string;
	}

	let { type }: { type: 'admin' | 'user' } = $props();

	const profilePictureSize = $derived($device == 'desktop' ? '45px' : '40px');
	const currentPath = $derived(page.url.pathname);
	let menuIsVisible: boolean = $state(false);
	let profileMenuIsVisible: boolean = $state(false);

	let decoded = $state<JwtAuthData | null | undefined>(null);

	$effect(() => {
		return authService.decoded.subscribe((v) => (decoded = v));
	});

	let userName = $derived(decoded?.name ?? 'Não Autorizado');
	let userPublicId = $derived(decoded?.publicId ?? 'Não Autorizado');

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
	<div class="logo-section flex items-center gap-3">
		{#if $device == 'mobile'}
			<IconButton
				icon={menuIsVisible ? faTimes : faBars}
				onClick={toogleMenuVisibility}
				width="40px"
				height="40px"
				variant="naked"
			/>
		{:else}
			<div class="logo-icon">
				<Fa icon={faChessBoard} size="1x" style="color: var(--accent-red);" />
			</div>
			<div class="divider"></div>
		{/if}
	</div>
	{#if $device == 'desktop'}
		<div class="flex items-center justify-start gap-4">
			{#each type == 'admin' ? Array.prototype.concat(publicRoutes, adminRoutes) : publicRoutes as route (route.path)}
				<HeaderRoute name={route.name} path={route.path} selected={route.path == currentPath} />
			{/each}
		</div>
	{/if}
	<div>
		<ProfilePicture
			{userName}
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
				{userPublicId}
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
		background: linear-gradient(
			90deg,
			var(--primary-color) 0%,
			rgba(254, 236, 126, 0.9) 50%,
			var(--primary-color) 100%
		);
		z-index: 11;
		box-shadow:
			0 4px 16px rgba(212, 175, 55, 0.2),
			inset 0 1px 0 rgba(255, 255, 255, 0.5);
		border-bottom: 2px solid rgba(212, 175, 55, 0.3);
	}
	.header-menu {
		background: linear-gradient(135deg, var(--primary-color) 0%, rgba(254, 236, 126, 0.95) 100%);
		border: 2px solid rgba(212, 175, 55, 0.5);
		border-radius: 12px;
		z-index: 12;
		box-shadow:
			0 8px 24px rgba(0, 0, 0, 0.15),
			0 0 20px rgba(212, 175, 55, 0.15);
	}
	.overlay {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.25);
		backdrop-filter: blur(6px);
		z-index: 10;
	}

	.logo-section {
		flex: 0 0 auto;
	}

	.logo-icon {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 40px;
		height: 40px;
		border-radius: 8px;
		background: linear-gradient(135deg, rgba(196, 30, 58, 0.15) 0%, rgba(212, 175, 55, 0.1) 100%);
		border: 2px solid rgba(196, 30, 58, 0.3);
		transition: all 0.3s ease;
	}
</style>
