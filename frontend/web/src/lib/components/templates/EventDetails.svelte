<script lang="ts">
	import type { GetEventDetailsResponse } from '$lib/api/queries/get-event-details/get-event-details.interface';
	import Fa from 'svelte-fa';
	import GoBack from '../molecules/GoBack.svelte';
	import { faCalendar, faFilter, faLocationDot } from '@fortawesome/free-solid-svg-icons';
	import { formatCEP } from '$lib/shared/helpers/format-cep';
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { formatTime } from '$lib/shared/helpers/format-time';
	import SearchInput from '../atoms/SearchInput.svelte';
	import IconButton from '../atoms/IconButton.svelte';
	import BoardGame from '../atoms/BoardGame.svelte';

	export let eventData: GetEventDetailsResponse;

	let searchInputValue: string = '';

	function handleOnSearchInput(value: string) {}

	function handleOnClickFilter() {}
</script>

<main class="flex flex-col gap-10 px-3 py-7 sm:px-10 md:px-15">
	<GoBack title={eventData.name} description="Detalhes do evento" type="user" />
	<div class="flex items-start justify-between px-10">
		<div class="flex flex-1 flex-col gap-8">
			<h3 class="h3">Detalhes</h3>
			<div class="flex gap-4">
				<Fa icon={faLocationDot} size="2x" />
				<div>
					<p>{eventData.street}, n° {eventData.number}</p>
					<p>Bairro: {eventData.supplement}</p>
					<p>{eventData.city}, {eventData.state}</p>
					<p>{formatCEP(eventData.zipCode)}</p>
				</div>
			</div>
			<div class="flex gap-4">
				<Fa icon={faCalendar} size="2x" />
				<div class="flex flex-col gap-1">
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
		<div class="flex flex-1 flex-col gap-8">
			<h3 class="h3">Jogos</h3>
			<div class="flex w-150 items-center justify-start gap-5">
				<SearchInput
					bind:value={searchInputValue}
					placeholder="Pesquisar..."
					onInput={handleOnSearchInput}
				/>
				<IconButton
					icon={faFilter}
					onClick={handleOnClickFilter}
					variant="naked"
					size="2x"
					width="44px"
					height="44px"
				/>
			</div>
			<div class="flex flex-col gap-2 max-h-76 overflow-y-auto">
				{#each eventData.listGames as game}
					<BoardGame
						title={game.nameGame}
						category={game.category}
						minParticipants={game.minPlayers}
						maxParticipants={game.maxPlayers}
						isActivate={game.isAvailable}
					/>
				{/each}
			</div>
		</div>
	</div>
</main>
