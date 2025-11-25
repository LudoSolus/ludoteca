<script lang="ts">
	import type { IListEvent } from '$lib/api/queries/events/list-all-events/list-all-events.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventCard from '$lib/components/molecules/EventCard.svelte';
	import { faPlus } from '@fortawesome/free-solid-svg-icons';

	export let events: IListEvent[];

	const now = new Date();
	$: nextEvents = events.filter((e) => e.finalDate >= now);
	$: finishedEvents = events.filter((e) => e.finalDate < now);
</script>

<main class="flex w-full flex-col overflow-y-auto px-2 pt-7 pb-20 sm:px-4 md:px-10 xl:px-15">
	<div class="flex w-full justify-end">
		<Button text="Criar" leftIcon={faPlus} onClick={() => {}} />
	</div>
	<section class="mt-6 flex w-full flex-col gap-2 sm:mt-0 sm:gap-6">
		<h3 class="h3">Próximos Eventos</h3>
		<div class="flex w-full gap-8 overflow-x-scroll py-3">
			{#each nextEvents as event}
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
					hasBoardGame
					hasEscapeRoom
					hasRpg
				/>
			{/each}
		</div>
	</section>
	<section class="mt-5 flex w-full flex-col gap-2 sm:gap-6">
		<h3 class="h3">Eventos Finalizados</h3>
		<div class="flex w-full gap-8 overflow-x-scroll py-3">
			{#each finishedEvents as event}
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
					hasBoardGame
					hasEscapeRoom
					hasRpg
				/>
			{/each}
		</div>
	</section>
</main>
