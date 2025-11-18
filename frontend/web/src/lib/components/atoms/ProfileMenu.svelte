<script lang="ts">
	import { faRightFromBracket, faUser } from '@fortawesome/free-solid-svg-icons';
	import Fa from 'svelte-fa';

	export let userPublicId: string;
	export let onProfileClick: () => void;
	export let onLogoutClick: () => void;
</script>

<div class="relative mt-1 w-40 rounded-lg border border-gray-300 bg-white p-2 text-black shadow-lg">
	<div class="id-text mb-2 flex items-center justify-start gap-2 px-2">
		<span class="font-bold w-4">#</span>
		<span>{userPublicId}</span>
	</div>

	<hr class="mb-2 border-gray-300" />

	<button
		class="flex cursor-pointer items-center gap-2 rounded-md px-2 py-1 transition hover:bg-yellow-100"
		on:click={onProfileClick}
	>
		<Fa icon={faUser} class="h-4 w-4" />
		<span class="font-bold">Perfil</span>
	</button>

	<button
		class="flex cursor-pointer items-center gap-2 rounded-md px-2 py-1 transition hover:bg-yellow-100"
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
</style>
