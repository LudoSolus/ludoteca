<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import EventCarousel from '$lib/components/organisms/EventCarousel.svelte';

	export let events: IListNextEventsResponse[];

	function handleClickEvent(id: string): void {
		goto(`/events/${id}`);
	}
</script>

<main class="flex w-full flex-col px-2 pt-7 pb-20 sm:px-4 md:px-10 xl:px-15">
	<section class="mt-6 flex w-full sm:mt-0">
		<EventCarousel title="Próximos Eventos" {events} onClickEvent={handleClickEvent}/>
	</section>
</main>
