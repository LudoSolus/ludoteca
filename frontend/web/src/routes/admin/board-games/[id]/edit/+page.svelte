<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { EditBoardGameCommand } from '$lib/api/commands/board-games/edit-board-game/edit-board-game.command';
	import type { IEditBoardGameRequest } from '$lib/api/commands/board-games/edit-board-game/edit-board-game.interface';
	import type { IGetBoardGameDetailsResponse } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.interface';
	import { GetBoardGameDetailsQuery } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.query';
	import EditBoardGame from '$lib/components/templates/admin/EditBoardGame.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let boardGameDetails: IGetBoardGameDetailsResponse | null = null;
	let isLoading: boolean = false;

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

	function editBoardGame(data: IEditBoardGameRequest) {
		const boardGameId = $page.params.id;
		if (!boardGameId) return;

		isLoading = true;
		const command = new EditBoardGameCommand(boardGameId, data);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				goto('/admin/board-games');
				isLoading = false;
			},
			error: (err) => {
				toast.error(err.message, { closable: true });
				isLoading = false;
			}
		});
	}
</script>

{#if boardGameDetails}
	<EditBoardGame boardGame={boardGameDetails} {isLoading} onEditBoardGame={editBoardGame} />
{:else}
	<p>Carregando...</p>
{/if}
