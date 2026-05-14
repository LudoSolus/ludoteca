<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { RegisterEventCommand } from '$lib/api/commands/events/register-event/register-event.command';
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import { ListBoardGamesQuery } from '$lib/api/queries/board-games/list-board-games/list-board-games.query';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import EventRegister from '$lib/components/templates/admin/EventRegister.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const commandsHandler = new CommandsHandlerService(axios);
	const queriesHandler = new QueriesHandlerService(axios);

	let isLoading: boolean = false;
	let boardGames: IBoardGame[] = [];
	let eventForCopy: GetEventDetailsResponse | null = null;

	onMount(() => {
		fetchBoardGames();

		const eventId = $page.url.searchParams.get('eventId');

		if (eventId !== null) {
			fetchEvent(eventId);
		}
	});

	function fetchEvent(eventId: string) {
		const query = new GetEventDetailsQuery(eventId);
		queriesHandler.handle(query).subscribe({
			next: (data) => {
				eventForCopy = data.resultData;
				console.log(eventForCopy);
			},
			error: (err) => {
				toast.error('Não foi possível encontrar o evento para copiar');
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
				toast.error(err.message, { closable: true });
			}
		});
	}

	function registerevent(data: IRegisterEventRequest) {
		isLoading = true;
		const command = new RegisterEventCommand(data);
		commandsHandler.handle(command).subscribe({
			next: () => {
				toast.success('Evento criado com sucesso!', { closable: true });
				goto('/admin/events');
				isLoading = false;
			},
			error: (err) => {
				toast.error(err.message, { closable: true });
				isLoading = false;
			}
		});
	}
</script>

<EventRegister {isLoading} onCreateEvent={registerevent} {boardGames} {eventForCopy} />
