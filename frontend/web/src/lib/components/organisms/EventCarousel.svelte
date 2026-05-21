<script lang="ts">
	import { onMount } from 'svelte';
	import Fa from 'svelte-fa';
	import { faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
	import EventCard from '$lib/components/molecules/EventCard.svelte';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';

	export let title = 'Eventos';
	export let events: IListNextEventsResponse[] = [];
	export let onClickEvent: (id: string) => void;
	export let onCopyEvent: ((id: string) => void) | null = null;

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

<section class="event-carousel">
	<h3 class="h3 carousel-title">{title}</h3>

	<div class="carousel-row">
		<div class="nav-wrapper nav-wrapper--left">
			{#if canGoPrev}
				<button class="nav-button" on:click={handlePrev} aria-label="Ver evento anterior">
					<Fa icon={faChevronLeft} />
				</button>
			{/if}
		</div>

		<div class="event-carousel-body" bind:this={carouselTrack} on:scroll={updateScrollState}>
			{#if events.length === 0}
				<div class="empty-state">Nenhum evento encontrado.</div>
			{:else}
				{#each events as event}
					<div class="carousel-item">
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

		<div class="nav-wrapper nav-wrapper--right">
			{#if canGoNext}
				<button class="nav-button" on:click={handleNext} aria-label="Ver próximo evento">
					<Fa icon={faChevronRight} />
				</button>
			{/if}
		</div>
	</div>
</section>

<style>
	.event-carousel {
		width: 100%;
		display: flex;
		flex-direction: column;
		gap: 1rem;
	}

	.carousel-row {
		display: flex;
		align-items: center;
		gap: 0.5rem;
		width: 100%;
	}

	.nav-wrapper {
		flex: 0 0 48px;
		width: 48px;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.nav-wrapper--left {
		justify-content: flex-end;
	}
	.nav-wrapper--right {
		justify-content: flex-start;
	}

	.nav-button {
		width: 48px;
		height: 48px;
		border: 1px solid var(--border-color, #000);
		border-radius: 999px;
		background: var(--card-background-color);
		color: var(--text-color, #000);
		display: inline-flex;
		align-items: center;
		justify-content: center;
		cursor: pointer;
		transition:
			transform 0.2s ease,
			background-color 0.2s ease;
		flex-shrink: 0;
	}

	.nav-button:hover {
		transform: scale(1.05);
		background: var(--hover-background-color, rgba(0, 0, 0, 0.05));
	}

	.event-carousel-body {
		flex: 1 1 0;
		min-width: 0;
		display: flex;
		gap: 2rem;
		overflow-x: auto;
		padding: 16px;
		scroll-snap-type: none;
	}

	.event-carousel-body::-webkit-scrollbar {
		display: none;
	}
	.event-carousel-body {
		-ms-overflow-style: none;
		scrollbar-width: none;
	}

	.carousel-item {
		scroll-snap-align: start;
		flex: 0 0 auto;
	}

	.empty-state {
		width: 100%;
		padding: 2rem;
		text-align: center;
		border: 1px dashed var(--border-color, #000);
		border-radius: 12px;
		color: var(--muted-color, #666);
		background: var(--background-color, rgba(0, 0, 0, 0.02));
	}

	@media (max-width: 639px) {
		.nav-wrapper {
			flex: 0 0 36px;
			width: 36px;
		}
		.nav-button {
			width: 36px;
			height: 36px;
		}
	}
</style>
