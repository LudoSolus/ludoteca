<script lang="ts">
	import { page } from '$app/stores';
	import type { IGetBoardGameDetailsWithLoanResponse } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.interface';
	import { GetBoardGameDetailsWithLoanQuery } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.query';
	import BoardGameDetails from '$lib/components/templates/admin/BoardGameDetails.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let boardGameDetails: IGetBoardGameDetailsWithLoanResponse | null = null;

	onMount(() => {
		fetchBoardGame();
	});

	function fetchBoardGame() {
		const boardGameId = $page.params.id;
		if (!boardGameId) return;

		queriesHandler.handle(new GetBoardGameDetailsWithLoanQuery(boardGameId)).subscribe({
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
	<BoardGameDetails {boardGameDetails} />
{:else}
	<p>Carregando...</p>
{/if}
