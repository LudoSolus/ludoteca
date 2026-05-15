<script lang="ts">
	import { page } from '$app/stores';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import UserEventDetails from '$lib/components/templates/UserEventDetails.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let eventData: GetEventDetailsResponse | null = null;

	onMount(() => {
		fetchEvent();
	});

	function fetchEvent() {
		const eventId = $page.params.id;
		if (!eventId) return;

		queriesHandler.handle(new GetEventDetailsQuery(eventId)).subscribe({
			next: (res) => {
				eventData = res.resultData;
			}
		});
	}
</script>

{#if eventData}
	<UserEventDetails {eventData} />
{:else}
	<p>Carregando...</p>
{/if}
