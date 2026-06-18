<script lang="ts">
	import type { IListEvent } from '$lib/api/queries/events/list-all-events/list-all-events.interface';
	import { ListAllEventsQuery } from '$lib/api/queries/events/list-all-events/list-all-events.query';
	import EventsManager from '$lib/components/templates/admin/EventsManager.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);
	let events: IListEvent[] | undefined = undefined;

	onMount(() => {
		fetchEvents();
	});

	function fetchEvents() {
		queriesHandler.handle(new ListAllEventsQuery()).subscribe({
			next: (res) => {
				events = res.resultData;
			}
		});
	}
</script>

<EventsManager {events} />
