<script lang="ts">
	import type { GetEventDetailsResponse } from "$lib/api/queries/get-event-details/get-event-details.interface";
	import EventDetails from "$lib/components/templates/EventDetails.svelte";

    const mockEvent: GetEventDetailsResponse = {
		name: "Evento de Haloween",
		startDate: new Date(),
		finalDate: new Date(),
		street: "Ivo Afonso Zanini",
		number: 67,
		supplement: "casa dos fundos",
		neighborhood: "Centro",
		city: "Cornélio Procópio",
		state: "Paraná",
		zipCode: "86300000",
		listGames: []
	}

</script>

<EventDetails eventData={mockEvent} />