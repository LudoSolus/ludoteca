<script lang="ts">
	import { onMount } from 'svelte';
	import Fa from 'svelte-fa';
	import {
		faChevronLeft,
		faChevronRight,
		faCalendarMinus
	} from '@fortawesome/free-solid-svg-icons';
	import EventCard from '$lib/components/molecules/EventCard.svelte';
	import EventCardSkeleton from '$lib/components/molecules/EventCardSkeleton.svelte';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';

	export let title = 'Eventos';
	export let events: IListNextEventsResponse[] | undefined = undefined;
	export let onClickEvent: (id: string) => void;
	export let onCopyEvent: ((id: string) => void) | null = null;
	export let loading: boolean | undefined = undefined;

	$: isLoading = loading ?? events === undefined;

	let carouselTrack: HTMLDivElement | null = null;
	let scrollLeft = 0;
	let scrollWidth = 0;
	let clientWidth = 0;
	let isAnimating = false;

	$: canGoPrev = scrollLeft > 0;
	$: canGoNext = scrollLeft + clientWidth < scrollWidth - 1;

	function updateScrollState(): void {
		if (!carouselTrack) return;
		scrollLeft = carouselTrack.scrollLeft;
		scrollWidth = carouselTrack.scrollWidth;
		clientWidth = carouselTrack.clientWidth;
	}

	// Easing: ease-in-out cubic
	function easeInOut(t: number): number {
		return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
	}

	function smoothScroll(distance: number, duration = 420): void {
		if (!carouselTrack || isAnimating) return;
		isAnimating = true;

		const start = carouselTrack.scrollLeft;
		const target = start + distance;
		const startTime = performance.now();

		function step(now: number): void {
			if (!carouselTrack) return;
			const elapsed = now - startTime;
			const progress = Math.min(elapsed / duration, 1);
			carouselTrack.scrollLeft = start + distance * easeInOut(progress);
			updateScrollState();

			if (progress < 1) {
				requestAnimationFrame(step);
			} else {
				isAnimating = false;
			}
		}

		requestAnimationFrame(step);
	}

	function handlePrev(): void {
		if (!carouselTrack) return;
		smoothScroll(-(carouselTrack.clientWidth * 0.9));
	}

	function handleNext(): void {
		if (!carouselTrack) return;
		smoothScroll(carouselTrack.clientWidth * 0.9);
	}

	onMount(() => {
		updateScrollState();

		// ResizeObserver garante que as dimensões são lidas após o layout final,
		// inclusive quando o componente renderiza com dados já disponíveis
		const observer = new ResizeObserver(() => {
			updateScrollState();
		});

		if (carouselTrack) observer.observe(carouselTrack);

		return () => observer.disconnect();
	});
</script>

<section class="flex w-full flex-col gap-4">
	<h3 class="h3">{title}</h3>

	<div class="flex w-full items-center gap-2">
		<div class="hidden w-12 flex-none items-center justify-end max-sm:w-9 sm:flex">
			{#if canGoPrev}
				<button
					class="nav-button inline-flex h-12 w-12 shrink-0 cursor-pointer items-center justify-center rounded-full border border-(--border-color,#000) bg-(--card-background-color) text-(--text-color,#000) transition-[transform,background-color] duration-200 ease-in-out hover:scale-105 hover:bg-(--hover-background-color,rgba(0,0,0,0.05)) max-sm:h-9 max-sm:w-9"
					on:click={handlePrev}
					aria-label="Ver evento anterior"
				>
					<Fa icon={faChevronLeft} />
				</button>
			{/if}
		</div>

		<div
			class="flex min-w-0 flex-1 gap-8 overflow-x-auto p-4 [scrollbar-width:none] [&::-webkit-scrollbar]:hidden"
			bind:this={carouselTrack}
			on:scroll={updateScrollState}
		>
			{#if isLoading}
				{#each Array(3) as _}
					<div class="shrink-0 snap-start">
						<EventCardSkeleton />
					</div>
				{/each}
			{:else if !events || events.length === 0}
				<div
					class="flex w-full flex-col items-center justify-center gap-4 rounded-2xl border-2 border-dashed border-black bg-[var(--card-background-color)] p-8 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.25)]"
				>
					<div
						class="flex h-16 w-16 items-center justify-center rounded-full border-2 border-black bg-[var(--primary-color)] text-black"
					>
						<Fa icon={faCalendarMinus} size="2x" />
					</div>
					<div class="flex flex-col gap-1">
						<h4 class="inknut text-lg font-bold">Nenhum evento agendado</h4>
						<p class="text-sm text-gray-700">Fique de olho! Novos eventos acontecerão em breve.</p>
					</div>
				</div>
			{:else}
				{#each events as event}
					<div class="shrink-0 snap-start">
						<EventCard
							name={event.name}
							startDate={event.startDate}
							address={{
								street: event.street,
								number: event.number,
								neighborhood: event.neighborhood,
								city: event.city,
								state: event.state,
								zipCode: event.zipCode
							}}
							onCLickButton={() => onClickEvent(event.id)}
							onCopyEvent={onCopyEvent ? () => onCopyEvent(event.id) : null}
							hasBoardGames={event.hasBoardGames}
							hasEscapeRoom={event.hasEscapeRoom}
							hasRpg={event.hasRpg}
						/>
					</div>
				{/each}
			{/if}
		</div>

		<div class="hidden w-12 flex-none items-center justify-start max-sm:w-9 sm:flex">
			{#if canGoNext}
				<button
					class="nav-button inline-flex h-12 w-12 shrink-0 cursor-pointer items-center justify-center rounded-full border border-(--border-color,#000) bg-(--card-background-color) text-(--text-color,#000) transition-[transform,background-color] duration-200 ease-in-out hover:scale-105 hover:bg-(--hover-background-color,rgba(0,0,0,0.05)) max-sm:h-9 max-sm:w-9"
					on:click={handleNext}
					aria-label="Ver próximo evento"
				>
					<Fa icon={faChevronRight} />
				</button>
			{/if}
		</div>
	</div>
</section>
