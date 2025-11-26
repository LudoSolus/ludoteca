<script lang="ts">
	import Fa from 'svelte-fa';
	import GoBack from '../molecules/GoBack.svelte';
	import { faCalendar, faLocationDot } from '@fortawesome/free-solid-svg-icons';
	import { formatCEP } from '$lib/shared/helpers/format-cep';
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { formatTime } from '$lib/shared/helpers/format-time';
	import SearchInput from '../atoms/SearchInput.svelte';
	import BoardGame from '../molecules/BoardGame.svelte';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';

	export let eventData: GetEventDetailsResponse;

	let searchInputValue: string = '';

	$: filteredGamesList = eventData.listGames.filter((game) => {
		return game.nameGame.toLowerCase().includes(searchInputValue.toLowerCase());
	});
</script>

<main class="flex h-full flex-col gap-10 overflow-y-auto py-7 px-1 sm:px-10 xl:px-15">
	<GoBack title={eventData.name} description="Detalhes do evento" />
	<div class="flex flex-wrap items-start justify-between gap-10 pb-20 px-0 sm:px-2 lg:px-4 xl:px-10">
		<div class="flex flex-1 flex-col gap-8">
			<h3 class="h3">Detalhes</h3>
			<div class="flex gap-4">
				<Fa icon={faLocationDot} size="2x" />
				<div>
					<p>{eventData.street}, n° {eventData.number}</p>
					<p>Bairro: {eventData.neighborhood}</p>
					<p>{eventData.city}, {eventData.state}</p>
					<p>{formatCEP(eventData.zipCode)}</p>
				</div>
			</div>
			<div class="flex gap-4">
				<Fa icon={faCalendar} size="2x" />
				<div class="flex min-w-50 flex-col gap-1">
					<p>
						<span class="font-bold">Começo:{' '}</span>{formatDate(eventData.startDate)} - {formatTime(
							eventData.startDate
						)}
					</p>
					<p>
						<span class="font-bold">Término:{' '}</span>{formatDate(eventData.finalDate)} - {formatTime(
							eventData.finalDate
						)}
					</p>
				</div>
			</div>
		</div>
		<div class="flex flex-1 flex-col items-center justify-center gap-8">
			<h3 class="h3 w-full text-start">Jogos</h3>
			<div class="flex flex-col gap-8">
				<div class="flex max-w-150 w-full items-center justify-start gap-5">
					<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
				</div>
				<div class="flex max-h-76 w-fit flex-col gap-2 overflow-y-auto px-1 md:px-4 py-2">
					{#if filteredGamesList.length > 0}
						{#each filteredGamesList as game}
							<BoardGame
								title={game.nameGame}
								category={game.category}
								minParticipants={game.minPlayers}
								maxParticipants={game.maxPlayers}
								isActivate={game.isAvailable}
							/>
						{/each}
					{:else}
						<p>Nenhum jogo encontrado.</p>
					{/if}
				</div>
			</div>
		</div>
	</div>
</main>
