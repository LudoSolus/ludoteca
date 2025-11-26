<script lang="ts">
	import { page } from '$app/stores';
	import type { IGetBoardGameDetailsResponse } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.interface';
	import { GetBoardGameDetailsQuery } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.query';
	import EditBoardGame from '$lib/components/templates/admin/EditBoardGame.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let boardGameDetails: IGetBoardGameDetailsResponse | null = null;

	onMount(() => {
		fetchBoardGame();
	});

	function fetchBoardGame() {
		const boardGameId = $page.params.id;
		if (!boardGameId) return;

		queriesHandler.handle(new GetBoardGameDetailsQuery(boardGameId)).subscribe({
			next: (res) => {
				boardGameDetails = res.resultData;
			},
			error: (err) => {
				console.log(err);
			}
		});
	}
</script>

{#if boardGameDetails}
	<EditBoardGame boardGame={boardGameDetails} />
{:else}
	<p>Carregando...</p>
{/if}
