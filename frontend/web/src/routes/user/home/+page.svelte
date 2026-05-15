<script lang="ts">
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import { ListNextEventsQuery } from '$lib/api/queries/events/list-next-events/list-next-events.query';
	import Home from '$lib/components/templates/Home.svelte';
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
</script>

<Home {events} />
