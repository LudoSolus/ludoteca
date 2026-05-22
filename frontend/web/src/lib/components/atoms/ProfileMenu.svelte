<script lang="ts">
	import { faRightFromBracket, faUser } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let userPublicId: string;
	export let onProfileClick: () => void;
	export let onLogoutClick: () => void;
</script>

<div class="box relative mt-1 w-40 rounded-lg border border-gray-300 bg-white p-2 text-black shadow-lg">
	<div class="id-text mb-2 flex items-center justify-start gap-2 px-2">
		<span class="font-bold w-4">#</span>
		<span>{userPublicId}</span>
	</div>

	<hr class="mb-2" />

	<button
		class="flex cursor-pointer items-center gap-2 rounded-md px-2 py-1 transition hover:bg-yellow-100 active:bg-yellow-100"
		on:click={onProfileClick}
	>
		<Fa icon={faUser} class="h-4 w-4" />
		<span class="font-bold">Perfil</span>
	</button>

	<button
		class="flex cursor-pointer items-center gap-2 rounded-md px-2 py-1 transition hover:bg-yellow-100 active:bg-yellow-100"
		on:click={onLogoutClick}
	>
		<Fa icon={faRightFromBracket} class="h-4 w-4" />
		<span class="font-bold">Sair</span>
	</button>
</div>

<style>
	.id-text {
		font-family: 'Inter', sans-serif;
		text-transform: uppercase;
		font-weight: 600;
	}

	.box {
		background: var(--card-background-color);
		border: 2px solid rgba(212, 175, 55, 0.5);
		border-radius: 12px;
		z-index: 12;
		box-shadow:
			0 8px 24px rgba(0, 0, 0, 0.15),
			0 0 20px rgba(212, 175, 55, 0.15);
	}
</style>
