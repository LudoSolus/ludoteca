<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IListEvent } from '$lib/api/queries/events/list-all-events/list-all-events.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventCarousel from '$lib/components/organisms/EventCarousel.svelte';
	import { EEventStatus } from '$lib/shared/enums/event-status.enum';
	import { faPlus } from '@fortawesome/free-solid-svg-icons';

	export let events: IListEvent[] | undefined = undefined;

	function goToEvent(eventId: string): void {
		goto(`/admin/events/${eventId}`);
	}

	const goToEventCreate = () => {
		goto(`/admin/events/create`);
	};

	const goToEventCreateByCopy = (eventId: string) => {
		goto(`/admin/events/create?eventId=${eventId}`);
	};

	$: nextEvents = events
		? events.filter(
				(e) => e.status == EEventStatus.SCHEDULED || e.status == EEventStatus.INPROGRESS
			)
		: undefined;
	$: finishedEvents = events ? events.filter((e) => e.status == EEventStatus.COMPLETED) : undefined;
</script>

<main class="flex w-full flex-col px-2 pt-7 pb-20 sm:px-4 md:px-10">
	<div class="flex w-full justify-end">
		<Button text="Criar" leftIcon={faPlus} onClick={goToEventCreate} />
	</div>
	<section class="mt-6 flex w-full sm:mt-0">
		<EventCarousel
			title="Próximos Eventos"
			events={nextEvents}
			onClickEvent={goToEvent}
			onCopyEvent={goToEventCreateByCopy}
		/>
	</section>
	<section class="mt-5 flex w-full">
		<EventCarousel
			title="Eventos Finalizados"
			events={finishedEvents}
			onClickEvent={goToEvent}
			onCopyEvent={goToEventCreateByCopy}
		/>
	</section>
</main>
