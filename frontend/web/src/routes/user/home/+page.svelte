<script lang="ts">
	import { GetRecentBoardGamesQuery } from '$lib/api/queries/board-games/get-recent-board-games/get-recent-board-games.query';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import { ListNextEventsQuery } from '$lib/api/queries/events/list-next-events/list-next-events.query';
	import Home from '$lib/components/templates/Home.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);
	let events: IListNextEventsResponse[] | undefined = undefined;
	let playedGames: IBoardGame[] = [];

	onMount(() => {
		fetchEvents();
		fetchRecentBoardGames();
	});

	function fetchEvents() {
		queriesHandler.handle(new ListNextEventsQuery()).subscribe({
			next: (res) => {
				events = res.resultData;
			}
		});
	}

	function fetchRecentBoardGames() {
		queriesHandler.handle(new GetRecentBoardGamesQuery()).subscribe({
			next: (res) => {
				playedGames = res.resultData;
			}
		});
	}
</script>

<Home {events} {playedGames} />
