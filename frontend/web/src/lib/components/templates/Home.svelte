<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { IListNextEventsResponse } from '$lib/api/queries/events/list-next-events/list-next-events.interface';
	import EventCarousel from '$lib/components/organisms/EventCarousel.svelte';
	import BoardGame from '../molecules/BoardGame.svelte';
	import BoardGameSkeleton from '../molecules/BoardGameSkeleton.svelte';

	export let events: IListNextEventsResponse[] | undefined = undefined;
	export let playedGames: IBoardGame[] | undefined = undefined;

	function handleClickEvent(id: string): void {
		goto(`/user/event/${id}`);
	}
</script>

<main class="flex w-full flex-col gap-10 px-2 py-7 sm:px-4 md:px-10 xl:px-15">
	<section class="mt-6 flex w-full sm:mt-0">
		<EventCarousel title="Próximos Eventos" {events} onClickEvent={handleClickEvent} />
	</section>

	<section class="games-section flex flex-col items-start gap-8">
		<h3>Jogos Jogados</h3>

		<div class="games-list flex w-fit flex-col gap-2 p-2">
			{#if playedGames === undefined}
				{#each Array(4) as _}
					<BoardGameSkeleton />
				{/each}
			{:else if playedGames.length === 0}
				<p class="w-[94vw] min-[590px]:w-136 text-center py-4 text-gray-500">Nenhum jogo jogado encontrado.</p>
			{:else}
				{#each playedGames as game}
					<BoardGame
						title={game.name}
						category={game.category}
						minParticipants={game.minParticipants}
						maxParticipants={game.maxParticipants}
						isActivate={true}
						onClick={() => goto(`/board-games/${game.id}`)}
					/>
				{/each}
			{/if}
		</div>
	</section>
</main>

<style>
	main {
		padding: 28px 2px;
	}

	.games-section {
		height: 50vh;
		min-height: 376px;
	}

	.games-list {
		height: fit-content;
		overflow-y: auto;
		scrollbar-width: thin;
	}

	@media (min-height: 796px) {
		main {
			overflow: hidden;
		}

		.games-section {
			height: calc(100% - 380px);
			min-height: 290px;
		}

		.games-list {
			height: fit-content;
			max-height: calc(100% - 77px);
		}
	}

	@media (min-width: 355px) {
		main {
			padding-left: 6px;
			padding-right: 6px;
		}
	}

	@media (min-width: 640px) {
		main {
			padding-left: 16px;
			padding-right: 16px;
		}
	}

	@media (min-width: 768px) {
		main {
			padding-left: 40px;
			padding-right: 40px;
		}
	}

	@media (min-width: 1280px) {
		main {
			padding-left: 60px;
			padding-right: 60px;
		}
	}
</style>
