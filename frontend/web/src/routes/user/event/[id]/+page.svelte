<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import EventDetails from '$lib/components/templates/EventDetails.svelte';
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

	function goTOGameDetails(gameId: string) {
		goto(`/user/board-game/${gameId}`);
	}
</script>

{#if eventData}
	<EventDetails {eventData} type="user" goToGameDetails={goTOGameDetails} />
{:else}
	<p>Carregando...</p>
{/if}
