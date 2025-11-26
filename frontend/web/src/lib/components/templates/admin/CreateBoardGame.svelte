<script lang="ts">
	import type { ICreateBoardGameRequest } from '$lib/api/commands/board-games/create-board-game/create-board-game.interface';
	import GoBack from '$lib/components/molecules/GoBack.svelte';

	export let isLoading: boolean;
	export let onCreateBoardGame: (data: ICreateBoardGameRequest) => void;
</script>

<main class="flex h-full flex-col gap-10 overflow-y-auto px-1 py-7 sm:px-10 xl:px-15">
	<GoBack title="Criar Jogo" description="Adicione um jogo a coleção" />
</main>
