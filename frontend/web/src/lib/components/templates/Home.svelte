<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import EventCard from '$lib/components/molecules/EventCard.svelte';

	export let events: IListNextEventsResponse[];

	function goToEvent(eventId: string): void {
		goto(`/user/event/${eventId}`);
	}
</script>

<main class="flex w-full flex-col px-2 pt-7 pb-20 sm:px-4 md:px-10 xl:px-15">
	<section class="mt-6 flex w-full flex-col gap-2 sm:mt-0 sm:gap-6">
		<h3 class="h3">Próximos Eventos</h3>

		<div class="flex w-full gap-8 overflow-x-scroll py-3">
			{#each events as event}
				<EventCard
					name={event.name}
					startDate={event.startDate}
					endDate={event.finalDate}
					address={{
						street: event.street,
						number: event.number,
						neighborhood: event.neighborhood,
						city: event.city,
						state: event.state,
						zipCode: event.zipCode
					}}
					onCLickButton={() => goToEvent(event.id)}
					hasBoardGame
					hasEscapeRoom
					hasRpg
				/>
			{/each}
		</div>
	</section>
</main>
