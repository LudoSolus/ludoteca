<script lang="ts">
	import type { IBoardGame } from '$lib/api/queries/list-board-games/list-board-games.interface';
	import { ListBoardGamesQuery } from '$lib/api/queries/list-board-games/list-board-games.query';
	import BoardGamesManager from '$lib/components/templates/BoardGamesManager.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);
	let boardGames: IBoardGame[] = [];

	onMount(() => {
		fetchBoardGames();
	});

	function fetchBoardGames() {
		queriesHandler.handle(new ListBoardGamesQuery()).subscribe({
			next: (res) => {
				boardGames = res.resultData;
                console.log(boardGames)
			},
			error: (err) => {
				console.log(err);
			}
		});
	}
</script>

<BoardGamesManager {boardGames} />
