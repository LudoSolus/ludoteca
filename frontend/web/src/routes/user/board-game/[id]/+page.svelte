<script lang="ts">
	import { GetBoardGameDetailsWithLoanQuery } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.query';
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import type { IGetBoardGameDetailsWithLoanResponse } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.interface';
	import BoardGameDetails from '$lib/components/templates/BoardGameDetails.svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let boardGameDetails: IGetBoardGameDetailsWithLoanResponse | null = null;
	let isLoading: boolean = true;

	onMount(() => {
		fetchBoardGame();
	});

	function fetchBoardGame() {
		const boardGameId = $page.params.id;
		if (!boardGameId) return;
		isLoading = true;

		queriesHandler.handle(new GetBoardGameDetailsWithLoanQuery(boardGameId)).subscribe({
			next: (res) => {
				boardGameDetails = res.resultData;
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<BoardGameDetails
	{boardGameDetails}
	{isLoading}
	type="user"
/>
