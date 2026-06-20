<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { UpdateEventCommand } from '$lib/api/commands/events/update-event/update-event.command';
	import type { IUpdateEventRequest } from '$lib/api/commands/events/update-event/update-event.interface';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import { ListBoardGamesQuery } from '$lib/api/queries/board-games/list-board-games/list-board-games.query';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import EventUpdate from '$lib/components/templates/admin/EventUpdate.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let boardGames: IBoardGame[] = [];
	let eventDetails: GetEventDetailsResponse | null = null;
	let isLoading: boolean = false;

	onMount(() => {
		fetchEvent();
		fetchBoardGames();
	});

	function fetchEvent() {
		const eventId = $page.params.id;
		if (!eventId) return;

		queriesHandler.handle(new GetEventDetailsQuery(eventId)).subscribe({
			next: (res) => {
				eventDetails = res.resultData;
			}
		});
	}

	function fetchBoardGames() {
		const query = new ListBoardGamesQuery();
		queriesHandler.handle(query).subscribe({
			next: (data) => {
				boardGames = data.resultData;
			},
			error: (err) => {
				console.error(err);
			}
		});
	}

	function editEvent(data: IUpdateEventRequest) {
		const eventId = $page.params.id;
		if (!eventId) return;

		isLoading = true;
		const command = new UpdateEventCommand(eventId, data);
		commandsHandler.handle(command).subscribe({
			next: () => {
				toast.success('Evento atualizado com sucesso!', { closable: true });
				goto('/admin/events');
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<EventUpdate event={eventDetails} {isLoading} onEditEvent={editEvent} {boardGames} />

