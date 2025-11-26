<script lang="ts">
	import type { IGetBoardGameDetailsResponse } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.interface';
	import GoBack from '$lib/components/molecules/GoBack.svelte';

	export let boardGame: IGetBoardGameDetailsResponse;
</script>

<main class="flex h-full flex-col gap-10 overflow-y-auto px-1 py-7 sm:px-10 xl:px-15">
	<GoBack title="Editar Jogo" description="Edite os dados do jogo" />
</main>
