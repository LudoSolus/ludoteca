<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { DeleteBoardGameCommand } from '$lib/api/commands/board-games/delete-board-game/delete-board-game.command';
	import type { IGetBoardGameDetailsWithLoanResponse } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.interface';
	import { GetBoardGameDetailsWithLoanQuery } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.query';
	import BoardGameDetails from '$lib/components/templates/BoardGameDetails.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let boardGameDetails: IGetBoardGameDetailsWithLoanResponse | null = null;
	let isLoading: boolean = true;

	onMount(() => {
		fetchBoardGame();
	});

	function deleteBoardGame() {
		const boardGameId = $page.params.id;
		if (!boardGameId) return;
		isLoading = true;

		commandsHandler.handle(new DeleteBoardGameCommand(boardGameId)).subscribe({
			next: () => {
				goto('/admin/board-games');
				toast.success('Jogo excluído com sucesso!');
				isLoading = false;
			},
			error: () => {
				isLoading = false;
			}
		});
	}

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
	handleOnDelete={deleteBoardGame}
	{isLoading}
	handleOnEdit={() => {
		goto(`/admin/board-games/${$page.params.id}/edit`);
	}}
	type="admin"
/>
