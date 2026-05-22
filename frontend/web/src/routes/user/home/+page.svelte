<script lang="ts">
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import { ListNextEventsQuery } from '$lib/api/queries/events/list-next-events/list-next-events.query';
	import Home from '$lib/components/templates/Home.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);
	let events: IListNextEventsResponse[] = [];

	onMount(() => {
		fetchEvents();
	});

	function fetchEvents() {
		queriesHandler.handle(new ListNextEventsQuery()).subscribe({
			next: (res) => {
				events = res.resultData;
			}
		});
	}

	const playedGamesMock: IBoardGame[] = [
		{
			id: '1',
			name: 'A era glacial de ragnar',
			category: ECategory.ABSTRACT_GAMES,
			minParticipants: 3,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '2',
			name: 'WAR',
			category: ECategory.PARTY_GAME,
			minParticipants: 2,
			maxParticipants: 4,
			barcode: 'qw'
		},
		{
			id: '3',
			name: 'Fuja da sala',
			category: ECategory.DEDUCTION,
			minParticipants: 4,
			maxParticipants: 8,
			barcode: 'qw'
		},
		{
			id: '4',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '5',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '6',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '7',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '8',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '9',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '10',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		},
		{
			id: '11',
			name: 'Banco imobiliário',
			category: ECategory.NEGOTIATION,
			minParticipants: 2,
			maxParticipants: 6,
			barcode: 'qw'
		}
	];
</script>

<Home {events} playedGames={playedGamesMock} />
